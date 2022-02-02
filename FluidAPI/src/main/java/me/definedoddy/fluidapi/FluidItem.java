package me.definedoddy.fluidapi;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FluidItem {
    final ItemStack item;
    private final List<FluidItem.ListenerType> listenerTypes = new ArrayList<>();
    private FluidListener interactListener;
    private FluidListener dropListener;
    private FluidListener pickupListener;
    private static int globalIdCount;
    private int id;

    public FluidItem(Material material) {
        item = new ItemStack(material);
    }

    public FluidItem(Material material, int count) {
        item = new ItemStack(material, count);
    }

    public FluidItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack build() {
        return item;
    }

    public FluidItem registerListeners() {
        registerListeners(FluidItem.ListenerType.values());
        return this;
    }

    public FluidItem registerListeners(@NotNull FluidItem.ListenerType... types) {
        if (equalsIgnoreAmount(new ItemStack(item.getType()))) {
            setData("fluid_item_event_" + globalIdCount, PersistentDataType.INTEGER, 1);
            id = globalIdCount;
            globalIdCount++;
        }
        for (FluidItem.ListenerType type : types) {
            if (type == FluidItem.ListenerType.INTERACT && interactListener == null) {
                interactListener = new FluidListener() {
                    @EventHandler
                    public void onInteract(PlayerInteractEvent e) {
                        if (e.getItem() != null && equalsIgnoreAmount(e.getItem())) {
                            onInteract(e);
                        }
                    }
                };
            } else if (type == FluidItem.ListenerType.DROP && dropListener == null) {
                dropListener = new FluidListener() {
                    @EventHandler
                    public void onDrop(PlayerDropItemEvent e) {
                        if (equalsIgnoreAmount(e.getItemDrop().getItemStack())) {
                            onDrop(e);
                        }
                    }
                };
            } else if (type == FluidItem.ListenerType.PICKUP && pickupListener == null) {
                pickupListener = new FluidListener() {
                    @EventHandler
                    public void onPickup(PlayerPickupItemEvent e) {
                        if (equalsIgnoreAmount(e.getItem().getItemStack())) {
                            onPickup(e);
                        }
                    }
                };
            }
        }
        return this;
    }

    public FluidItem unregisterListeners() {
        unregisterListeners(FluidItem.ListenerType.values());
        return this;
    }

    public FluidItem unregisterListeners(FluidItem.ListenerType... types) {
        for (FluidItem.ListenerType type : types) {
            if (type == FluidItem.ListenerType.INTERACT && interactListener != null) {
                interactListener.unregister();
                interactListener = null;
            } else if (type == FluidItem.ListenerType.DROP && dropListener != null) {
                dropListener.unregister();
                dropListener = null;
            } else if (type == FluidItem.ListenerType.PICKUP && pickupListener != null) {
                pickupListener.unregister();
                pickupListener = null;
            }
        }
        if (interactListener == null && dropListener == null && pickupListener == null &&
                hasData("fluid_item_event_" + id, PersistentDataType.INTEGER)) {
            removeData("fluid_item_event_" + id);
        }
        return this;
    }

    public void onInteract(PlayerInteractEvent event) {

    }

    public void onDrop(PlayerDropItemEvent event) {

    }

    public void onPickup(PlayerPickupItemEvent event) {

    }

    public enum ListenerType {
        INTERACT,
        DROP,
        PICKUP
    }

    public FluidItem setName(String name) {
        setName(item, name);
        return this;
    }

    public FluidItem setLore(String... lore) {
        setLore(item, lore);
        return this;
    }

    public FluidItem setLore(int index, String lore) {
        setLore(item, index, lore);
        return this;
    }

    public FluidItem setLore(int[] index, String[] lore) {
        setLore(item, index, lore);
        return this;
    }

    public FluidItem addLore(String... lore) {
        addLore(item, false, lore);
        return this;
    }

    public FluidItem addLore(boolean allowDuplicates, String... lore) {
        addLore(item, allowDuplicates, lore);
        return this;
    }

    public FluidItem setModel(int id) {
        setModel(item, id);
        return this;
    }

    public FluidItem addFlags(ItemFlag... flags) {
        setFlags(item, flags);
        return this;
    }

    public FluidItem removeFlags(ItemFlag... flags) {
        removeFlags(item, flags);
        return this;
    }

    public FluidItem removeFlags() {
        for (ItemFlag flag : item.getItemMeta().getItemFlags()) {
            removeFlags(item, flag);
        }
        return this;
    }

    public FluidItem setUnbreakable() {
        setUnbreakable(item, true);
        return this;
    }

    public FluidItem setUnbreakable(boolean unbreakable) {
        setUnbreakable(item, unbreakable);
        return this;
    }

    public boolean hasSameMeta(ItemStack item) {
        return haveSameMeta(this.item, item);
    }

    public boolean equalsIgnoreAmount(ItemStack item) {
        return equalsIgnoreAmount(this.item, item);
    }

    public static boolean haveSameMeta(ItemStack a, ItemStack b) {
        return a != null && b != null & a.hasItemMeta() && b.hasItemMeta() && a.getItemMeta().equals(b.getItemMeta());
    }

    public static boolean equalsIgnoreAmount(ItemStack a, ItemStack b) {
        if (a == null || b == null) return false;
        ItemStack ab = a.clone();
        ab.setAmount(b.getAmount());
        return ab.equals(b);
    }

    public <T, Z> Z getData(String key, PersistentDataType<T, Z> type) {
        return getData(item, key, type);
    }

    public <T, Z> FluidItem setData(String key, PersistentDataType<T, Z> type, Z value) {
        setData(item, key, type, value);
        return this;
    }

    public <T, Z> boolean hasData(String key, PersistentDataType<T, Z> type) {
        return hasData(item, key, type);
    }

    public FluidItem removeData(String key) {
        removeData(item, key);
        return this;
    }

    public static <T, Z> Z getData(ItemStack item, String key, PersistentDataType<T, Z> type) {
        return hasData(item, key, type) ? item.getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(FluidAPI.getPlugin(), key), type) : null;
    }

    public static <T, Z> ItemStack setData(ItemStack item, String key, PersistentDataType<T, Z> type, Z value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(FluidAPI.getPlugin(), key), type, value);
        item.setItemMeta(meta);
        return item;
    }

    public static <T, Z> boolean hasData(ItemStack item, String key, PersistentDataType<T, Z> type) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }
        return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(FluidAPI.getPlugin(), key), type);
    }

    public static ItemStack removeData(ItemStack item, String key) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().remove(new NamespacedKey(FluidAPI.getPlugin(), key));
        item.setItemMeta(meta);
        return item;
    }

    public FluidItem enchant(Enchantment enchantment, int level) {
        return enchant(enchantment, level, false);
    }

    public FluidItem enchant(Enchantment enchantment, int level, boolean bypassLimit) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, bypassLimit);
        item.setItemMeta(meta);
        return this;
    }

    public FluidItem addEnchantGlow() {
        addEnchantGlow(item);
        return this;
    }

    public boolean isNull() {
        return FluidItem.isNull(item);
    }

    public static ItemStack setMeta(ItemStack item, String displayName, String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(FluidMessage.toColor(displayName));
        item.setItemMeta(meta);
        return setLore(item, lore);
    }

    public static ItemStack setName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(FluidMessage.toColor(name));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> colourLore = new ArrayList<>();
        for (String string : lore) {
            colourLore.add(FluidMessage.toColor(string));
        }
        meta.setLore(colourLore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, int index, String lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> list = meta.getLore();
        if (list.size() - 1 >= index) {
            list.set(index, FluidMessage.toColor(lore));
        } else {
            list.add(FluidMessage.toColor(lore));
        }
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, int[] index, String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> list = meta.getLore();
        for (int i = 0; i < index.length; i++) {
            if (list.size() - 1 >= index[i]) {
                list.set(index[i], FluidMessage.toColor(lore[i]));
            } else {
                list.add(FluidMessage.toColor(lore[i]));
            }
        }
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addLore(ItemStack item, boolean allowDuplicates, String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> colourLore = new ArrayList<>();
        for (String string : lore) {
            colourLore.add(FluidMessage.toColor(string));
        }
        if (meta.getLore() == null) {
            meta.setLore(colourLore);
        } else {
            for (String newLine : colourLore) {
                boolean matches = false;
                if (!allowDuplicates) {
                    for (String oldLine : meta.getLore()) {
                        if (newLine.equalsIgnoreCase(oldLine)) {
                            matches = true;
                            break;
                        }
                    }
                }
                if (!matches) {
                    List<String> list = meta.getLore();
                    list.add(newLine);
                    meta.setLore(list);
                }
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addLore(ItemStack item, String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> colourLore = new ArrayList<>();
        for (String string : lore) {
            colourLore.add(FluidMessage.toColor(string));
        }
        if (meta.getLore() == null) {
            meta.setLore(colourLore);
        } else {
            for (String newLine : colourLore) {
                boolean matches = false;
                for (String oldLine : meta.getLore()) {
                    if (newLine.equalsIgnoreCase(oldLine)) {
                        matches = true;
                        break;
                    }
                }
                if (!matches) {
                    List<String> list = meta.getLore();
                    list.add(newLine);
                    meta.setLore(list);
                }
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setOtherMeta(ItemStack item, int customModelID, ItemFlag... flags) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelID);
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setFlags(ItemStack item, ItemFlag... flags) {
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setUnbreakable(ItemStack item) {
        return setUnbreakable(item, true);
    }

    public static ItemStack setUnbreakable(ItemStack item, boolean unbreakable) {
        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(unbreakable);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setModel(ItemStack item, int customModelID) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelID);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack removeFlags(ItemStack item, ItemFlag... flags) {
        item.getItemMeta().removeItemFlags(flags);
        return item;
    }

    public static ItemStack addEnchantGlow(ItemStack item) {
        return FluidItem.EnchantGlow.addToItem(item);
    }


    public static boolean isNull(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    private static class EnchantGlow extends Enchantment {
        private static Enchantment glow;

        public EnchantGlow(NamespacedKey id) {
            super(id);
        }

        @Override
        public boolean canEnchantItem(ItemStack item) {
            return false;
        }

        @Override
        public boolean conflictsWith(Enchantment other) {
            return false;
        }

        @Override
        public EnchantmentTarget getItemTarget() {
            return null;
        }

        @Override
        public boolean isTreasure() {
            return false;
        }

        @Override
        public boolean isCursed() {
            return false;
        }

        @Override
        public int getMaxLevel() {
            return 10;
        }

        @Override
        public String getName() {
            return "Glow";
        }

        @Override
        public int getStartLevel() {
            return 1;
        }

        public static Enchantment getEnchantment() {
            if (glow != null) {
                return glow;
            }
            if (Enchantment.getByName("Glow") != null) {
                return Enchantment.getByName("Glow");
            }
            try {
                Field field = Enchantment.class.getDeclaredField("acceptingNew");
                field.setAccessible(true);
                field.set(null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            glow = new FluidItem.EnchantGlow(new NamespacedKey(FluidAPI.getPlugin(), "Glow"));
            Enchantment.registerEnchantment(glow);
            return glow;
        }

        public static ItemStack addToItem(ItemStack item) {
            Enchantment glow = getEnchantment();
            if (!item.containsEnchantment(glow)) {
                item.addUnsafeEnchantment(glow, 1);
            }
            return item;
        }

        public static ItemStack removeFromItem(ItemStack item) {
            Enchantment glow = getEnchantment();
            if (item.containsEnchantment(glow)) {
                item.removeEnchantment(glow);
            }
            return item;
        }
    }

    public static class Head extends FluidItem {
        public Head(String owner) {
            super(Material.PLAYER_HEAD);
            setOwner(owner);
        }

        public Head(Player owner) {
            super(Material.PLAYER_HEAD);
            setOwner(owner);
        }

        public Head setOwner(String owner) {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwner(owner);
            item.setItemMeta(meta);
            return this;
        }

        public Head setOwner(Player owner) {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setOwningPlayer(owner);
            item.setItemMeta(meta);
            return this;
        }
    }
}
