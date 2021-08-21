package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
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
    private FluidListener<InventoryClickEvent> clickListener;
    private FluidListener<InventoryDragEvent> dragListener;
    private Inventory inventory;
    private final Map<Integer, Item> items = new HashMap<>();
    private String title;
    private boolean canAddItems;
    private boolean canTakeItems;

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

    public FluidGUI canAddItems() {
        setCanAddItems(true);
        return this;
    }

    public FluidGUI setCanAddItems(boolean canAddItems) {
        this.canAddItems = canAddItems;
        return this;
    }

    public FluidGUI canTakeItems() {
        setCanTakeItems(true);
        return this;
    }

    public FluidGUI setCanTakeItems(boolean canTakeItems) {
        this.canTakeItems = canTakeItems;
        return this;
    }

    public FluidGUI addItems(Map<Integer, Item> items) {
        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
            entry.getValue().gui = this;
            entry.getValue().slot = entry.getKey();
            this.items.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public FluidGUI setTitle(String title) {
        this.title = title;
        return this;
    }

    public FluidGUI unregister() {
        clickListener.unregister();
        dragListener.unregister();
        return this;
    }

    private void initListeners() {
        clickListener = new FluidListener<>(InventoryClickEvent.class) {
            @Override
            public void run() {
                if (getData().getClickedInventory() == inventory) {
                    if (isNull(getData().getCursor())) {
                        if (!getData().isShiftClick() || !canTakeItems) {
                            getData().setCancelled(true);
                        }
                    } else if (!canAddItems) {
                        getData().setCancelled(true);
                    }
                    Item item = items.get(getData().getSlot());
                    if (item != null) {
                        item.clickType = getData().getClick();
                        item.shiftClick = getData().isShiftClick();
                        item.clicker = (Player) getData().getWhoClicked();
                        item.internalClick();
                        item.onClick();
                    }
                } else if (getData().getView().getTopInventory() == inventory &&
                        !isNull(getData().getCurrentItem()) && getData().isShiftClick() && !canAddItems) {
                    getData().setCancelled(true);
                }
            }
        };

        dragListener = new FluidListener<>(InventoryDragEvent.class) {
            @Override
            public void run() {
                Inventory inv = getData().getView().getInventory(getData().getRawSlots().stream().toList().get(0));
                if (inv == inventory && !canAddItems) {
                    getData().setCancelled(true);
                }
            }
        };
    }

    private boolean isNull(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    public static class Item {
        private final ItemStack item;
        private int slot;
        private FluidGUI gui;
        private ClickType clickType;
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

        public void onClick() { };

        public ItemStack getItem() {
            return item;
        }

        public ClickType getClickType() {
            return clickType;
        }

        public boolean isShiftClick() {
            return shiftClick;
        }

        public Player getClicker() {
            return clicker;
        }

        private void internalClick() {
            if (gui.canTakeItems) {
                gui.items.remove(slot);
            }
        }

    }
}
