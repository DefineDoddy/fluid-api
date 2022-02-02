package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class FluidGUI {
    private final Inventory inventory;
    private final List<Player> players;
    private FluidListener listener;

    private final List<Item> items;
    private final boolean preventClosing;
    private final boolean canTakeItems;
    private final boolean canAddItems;

    private final Consumer<InventoryClickEvent> clickEvent;
    private final Consumer<InventoryDragEvent> dragEvent;
    private final Consumer<InventoryCloseEvent> closeEvent;

    FluidGUI(List<Player> players, String title, int size, List<Item> items, boolean preventClosing, boolean canTakeItems, boolean canAddItems,
             Consumer<InventoryClickEvent> clickEvent, Consumer<InventoryDragEvent> dragEvent, Consumer<InventoryCloseEvent> closeEvent) {
        this.players = players;
        this.items = items;
        this.preventClosing = preventClosing;
        this.clickEvent = clickEvent;
        this.dragEvent = dragEvent;
        this.closeEvent = closeEvent;
        this.canTakeItems = canTakeItems;
        this.canAddItems = canAddItems;

        inventory = Bukkit.createInventory(null, size, title);
        items.forEach(item -> {
            item.gui = this;
            inventory.setItem(item.slot, item.stack);
        });
        initListeners();
    }

    public void close() {
        for (Player player : players) {
            player.closeInventory();
        }
        listener.unregister();
    }

    public void open() {
        for (Player player : players) {
            player.openInventory(inventory);
        }
        if (listener == null) {
            initListeners();
        }
    }

    public void open(Player... players) {
        for (Player player : players) {
            this.players.add(player);
            player.openInventory(inventory);
        }
        if (listener == null) {
            initListeners();
        }
    }

    void initListeners() {
        listener = new FluidListener() {
            @EventHandler
            public void onClick(InventoryClickEvent e) {
                if (e.getClickedInventory() == inventory) {
                    if (clickEvent != null) {
                        clickEvent.accept(e);
                    }
                    for (Item item : items) {
                        if (item.slot == e.getSlot()) {
                            item.clickEvent.accept(new Item.ClickEvent((Player)e.getWhoClicked(),
                                    e.getClick(), e.getSlotType(), e.getAction()), item);
                        }
                    }

                    if (FluidItem.isNull(e.getCursor())) {
                        if (!e.isShiftClick() || !canTakeItems) {
                            e.setCancelled(true);
                        }
                    } else if (!canAddItems) {
                        e.setCancelled(true);
                    }
                } else if (e.getView().getTopInventory() == inventory &&
                        !FluidItem.isNull(e.getCurrentItem()) && e.isShiftClick() && !canAddItems) {
                    e.setCancelled(true);
                }
            }

            @EventHandler
            public void onDrag(InventoryDragEvent e) {
                Inventory inv = e.getView().getInventory(e.getRawSlots().stream().toList().get(0));
                if (inv == inventory) {
                    if (!canAddItems) {
                        e.setCancelled(true);
                    }
                    if (dragEvent != null) {
                        dragEvent.accept(e);
                    }
                }
            }

            @EventHandler
            public void onClose(InventoryCloseEvent e) {
                if (e.getInventory() == inventory) {
                    if (preventClosing) {
                        e.getPlayer().openInventory(inventory);
                    } else {
                        if (closeEvent != null) {
                            closeEvent.accept(e);
                        }
                        unregister();
                    }
                }
            }
        };
    }

    public static class Builder {
        private String title = "Custom GUI";
        private int size = 27;
        private final List<Item> items = new ArrayList<>();
        private boolean preventClosing;
        private boolean canTakeItems;
        private boolean canAddItems;

        private Consumer<InventoryClickEvent> clickEvent;
        private Consumer<InventoryDragEvent> dragEvent;
        private Consumer<InventoryCloseEvent> closeEvent;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder items(Item... items) {
            this.items.addAll(Arrays.asList(items));
            return this;
        }

        public Builder onClick(Consumer<InventoryClickEvent> clickEvent) {
            this.clickEvent = clickEvent;
            return this;
        }

        public Builder onDrag(Consumer<InventoryDragEvent> dragEvent) {
            this.dragEvent = dragEvent;
            return this;
        }

        public Builder onClose(Consumer<InventoryCloseEvent> closeEvent) {
            this.closeEvent = closeEvent;
            return this;
        }

        public Builder preventClosing() {
            preventClosing = true;
            return this;
        }

        public Builder canTakeItems() {
            canTakeItems = true;
            return this;
        }

        public Builder canAddItems() {
            canAddItems = true;
            return this;
        }

        public FluidGUI build() {
            return new FluidGUI(new ArrayList<>(), title, size, items, preventClosing, canTakeItems,
                    canAddItems, clickEvent, dragEvent, closeEvent);
        }

        public void open(Player... players) {
            FluidGUI gui = new FluidGUI(List.of(players), title, size, items, preventClosing, canTakeItems,
                    canAddItems, clickEvent, dragEvent, closeEvent);
            for (Player player : players) {
                player.openInventory(gui.inventory);
            }
        }
    }

    public static class Item {
        FluidGUI gui;
        private final ItemStack stack;
        BiConsumer<ClickEvent, Item> clickEvent;
        int slot;

        public Item(ItemStack stack) {
            this.stack = stack;
        }

        public Item slot(int slot) {
            this.slot = slot;
            return this;
        }

        public Item onClick(BiConsumer<ClickEvent, Item> clickEvent) {
            this.clickEvent = clickEvent;
            return this;
        }

        public FluidGUI getGUI() {
            return gui;
        }

        public static class ClickEvent {
            Player clicker;
            ClickType clickType;
            InventoryType.SlotType slotType;
            InventoryAction action;

            ClickEvent(Player clicker, ClickType clickType, InventoryType.SlotType slotType, InventoryAction action) {
                this.clicker = clicker;
                this.clickType = clickType;
                this.slotType = slotType;
                this.action = action;
            }

            public Player getClicker() {
                return clicker;
            }

            public ClickType getClickType() {
                return clickType;
            }

            public InventoryType.SlotType getSlotType() {
                return slotType;
            }

            public InventoryAction getAction() {
                return action;
            }
        }
    }
}
