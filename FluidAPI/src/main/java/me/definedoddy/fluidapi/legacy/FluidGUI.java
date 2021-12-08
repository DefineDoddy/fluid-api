package me.definedoddy.fluidapi.legacy;

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
    private Inventory inventory;
    private FluidListener listener;
    private final Map<Integer, Item> items = new HashMap<>();
    private String title;
    private boolean canAddItems;
    private boolean canTakeItems;

    public FluidGUI(InventoryType type) {
        this.type = type;
    }

    public FluidGUI(int size) {
        this.size = getSize(size);
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
        if (listener != null) {
            listener.unregister();
        }
        return this;
    }

    private int getSize(int size) {
        return Math.max(9, 9 * (Math.round(size / 9f)));
    }

    private void initListeners() {
        listener = new FluidListener() {
            public void drag(InventoryClickEvent e) {
                if (e.getClickedInventory() == inventory) {
                    if (isNull(e.getCursor())) {
                        if (!e.isShiftClick() || !canTakeItems) {
                            e.setCancelled(true);
                        }
                    } else if (!canAddItems) {
                        e.setCancelled(true);
                    }
                    Item item = items.get(e.getSlot());
                    if (item != null) {
                        item.clickType = e.getClick();
                        item.shiftClick = e.isShiftClick();
                        item.clicker = (Player) e.getWhoClicked();
                        item.internalClick();
                        item.onClick();
                    }
                } else if (e.getView().getTopInventory() == inventory &&
                        !isNull(e.getCurrentItem()) && e.isShiftClick() && !canAddItems) {
                    e.setCancelled(true);
                }
            }

            public void click(InventoryDragEvent e) {
                Inventory inv = e.getView().getInventory(e.getRawSlots().stream().toList().get(0));
                if (inv == inventory && !canAddItems) {
                    e.setCancelled(true);
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
