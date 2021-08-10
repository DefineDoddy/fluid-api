package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class FluidGUI {
    private InventoryType type;
    private int size;
    private InventoryHolder owner;
    private FluidListener<InventoryClickEvent> listener;
    private Inventory inventory;
    private boolean listenerDefaultActive;
    private Map<Integer, Item> items = new HashMap<>();
    private String title;

    public FluidGUI(InventoryType type) {
        this.type = type;
    }

    public FluidGUI(int size) {
        this.size = size;
    }

    public Inventory build() {
        if (inventory == null) {
            if (type != null) {
                inventory = title == null ? Bukkit.createInventory(owner, type) : Bukkit.createInventory(owner, type, title);
            } else {
                inventory = title == null ? Bukkit.createInventory(owner, size) : Bukkit.createInventory(owner, size, title);
            }
            for (Map.Entry<Integer, Item> item : items.entrySet()) {
                inventory.setItem(item.getKey(), item.getValue().item);
            }
            initListeners();
        }
        return inventory;
    }

    public FluidGUI setOwner(InventoryHolder owner) {
        this.owner = owner;
        return this;
    }

    public FluidGUI draggable() {
        return setDraggable(true);
    }

    public FluidGUI setDraggable(boolean draggable) {
        if (listener != null) {
            listener.setActive(!draggable);
        } else {
            listenerDefaultActive = !draggable;
        }
        return this;
    }

    private void initListeners() {
        listener = new FluidListener<>(InventoryClickEvent.class) {
            @Override
            public void run() {
                Inventory inv = getData().getInventory();
                if (inv == inventory) {
                    getData().setCancelled(true);
                    Item item = items.get(getData().getSlot());
                    item.clicker = (Player) getData().getWhoClicked();
                    item.shiftClick = getData().isShiftClick();
                    if (getData().isLeftClick()) {
                        item.leftClick();
                    } else if (getData().isRightClick()) {
                        item.rightClick();
                    }
                }
            }
        }.setActive(listenerDefaultActive);
    }

    public FluidGUI addItems(Map<Integer, Item> items) {
        this.items = items;
        return this;
    }

    public FluidGUI setTitle(String title) {
        this.title = title;
        return this;
    }

    public abstract static class Item {
        private final ItemStack item;
        private boolean shiftClick;
        private Player clicker;

        public Item(ItemStack item) {
            this.item = item;
        }

        public Item(FluidItem item) {
            this.item = item.build();
        }

        public Item(Material material) {
            this.item = new ItemStack(material);
        }

        public Item(Material material, int amount) {
            this.item = new ItemStack(material, amount);
        }

        public abstract void leftClick();

        public abstract void rightClick();

        public ItemStack getItem() {
            return item;
        }

        public boolean isShiftClick() {
            return shiftClick;
        }

        public Player getClicker() {
            return clicker;
        }
    }
}
