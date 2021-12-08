package me.definedoddy.fluidapi;

<<<<<<< Updated upstream
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
=======
import me.definedoddy.fluidapi.legacy.FluidMessage;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
>>>>>>> Stashed changes

import java.util.List;

public class FluidItem {
<<<<<<< Updated upstream
    private ItemStack item;

    public FluidItem(Material material) {
        new FluidItem(material, 1);
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

    public FluidItem setUnbreakable() {
        setUnbreakable(item, true);
        return this;
    }

    public FluidItem setUnbreakable(boolean unbreakable) {
        setUnbreakable(item, unbreakable);
        return this;
    }

    public boolean hasSameMeta(ItemStack item) {
        return item != null & this.item.hasItemMeta() && item.hasItemMeta() && this.item.getItemMeta().equals(item.getItemMeta());
    }

    public static boolean haveSameMeta(ItemStack a, ItemStack b) {
        return a != null && b != null & a.hasItemMeta() && b.hasItemMeta() && a.getItemMeta().equals(b.getItemMeta());
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

    public static <T, Z> Z getData(ItemStack item, String key, PersistentDataType<T, Z> type) {
        return hasData(item, key, type) ? item.getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(FluidPlugin.getPlugin(), key), type) : null;
    }

    public static <T, Z> ItemStack setData(ItemStack item, String key, PersistentDataType<T, Z> type, Z value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(FluidPlugin.getPlugin(), key), type, value);
        item.setItemMeta(meta);
        return item;
    }

    public static <T, Z> boolean hasData(ItemStack item, String key, PersistentDataType<T, Z> type) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }
        return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(FluidPlugin.getPlugin(), key), type);
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

    public static ItemStack setMeta(ItemStack item, String displayName, String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(FluidMessage.toColor(displayName));
        item.setItemMeta(meta);
        return setLore(item, lore);
    }

=======
>>>>>>> Stashed changes
    public static ItemStack setName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(FluidMessage.toColor(name));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(List.of(FluidMessage.toColor(lore)));
        item.setItemMeta(meta);
        return item;
    }
<<<<<<< Updated upstream

    public static ItemStack removeFlags(ItemStack item, ItemFlag... flags) {
        item.getItemMeta().removeItemFlags(flags);
        return item;
    }

    public ItemStack addEnchantGlow(ItemStack item) {
        return EnchantGlow.addToItem(item);
    }

    private static class EnchantGlow extends Enchantment
    {
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
            glow = new EnchantGlow(new NamespacedKey(FluidPlugin.getPlugin(), "Glow"));
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
=======
>>>>>>> Stashed changes
}
