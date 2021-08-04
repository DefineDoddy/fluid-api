package io.github.definedoddy.fluidapi;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Items {
    public static ItemStack fromMeta(Material material, int customModelID, ItemFlag[] flags, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(displayName));
        meta.setCustomModelData(customModelID);
        meta.addItemFlags(flags);
        item.setItemMeta(meta);
        return setLore(item, lore);
    }

    public static ItemStack fromMeta(Material material, int customModelID, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(displayName));
        meta.setCustomModelData(customModelID);
        item.setItemMeta(meta);
        return setLore(item, lore);
    }

    public static ItemStack fromMeta(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(displayName));
        item.setItemMeta(meta);
        return setLore(item, lore);
    }

    public static ItemStack addGlow(ItemStack item) {
        return ItemGlow.addToItem(item);
    }

    public static ItemStack fromMeta(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(displayName));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setMeta(ItemStack item, String displayName, String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(displayName));
        item.setItemMeta(meta);
        return setLore(item, lore);
    }

    public static ItemStack setDisplayName(ItemStack item, String displayName) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.toColor(displayName));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> colourLore = new ArrayList<>();
        for (String string : lore) {
            colourLore.add(Utils.toColor(string));
        }
        meta.setLore(colourLore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, int index, String lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> list = meta.getLore();
        if (list.size() - 1 >= index) {
            list.set(index, Utils.toColor(lore));
        } else {
            list.add(Utils.toColor(lore));
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
                list.set(index[i], Utils.toColor(lore[i]));
            } else {
                list.add(Utils.toColor(lore[i]));
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
            colourLore.add(Utils.toColor(string));
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
            colourLore.add(Utils.toColor(string));
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

    public static ItemStack setModelData(ItemStack item, int customModelID) {
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelID);
        item.setItemMeta(meta);
        return item;
    }

    public static boolean haveSameMeta(ItemStack a, ItemStack b) {
        return a != null && b != null & a.hasItemMeta() && b.hasItemMeta() &&
                a.getItemMeta().equals(b.getItemMeta());
    }

    public static <T, Z> Z getDataContainer(ItemStack item, String key, PersistentDataType<T, Z> type) {
        return hasContainerValue(item, key, type) ? item.getItemMeta().getPersistentDataContainer()
                .get(new NamespacedKey(FluidPlugin.getPlugin(), key), type) : null;
    }

    public static <T, Z> ItemStack setDataContainer(ItemStack item, String key, PersistentDataType<T, Z> type, Z value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(FluidPlugin.getPlugin(), key), type, value);
        item.setItemMeta(meta);
        return item;
    }

    public static <T, Z> boolean hasContainerValue(ItemStack item, String key, PersistentDataType<T, Z> type) {
        if (item == null || item.getItemMeta() == null) {
            return false;
        }
        return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(FluidPlugin.getPlugin(), key), type);
    }

    public static ItemStack createEnchantedBook(Enchantment enchantment, int level) {
        return createEnchantedBook(enchantment, level, false);
    }

    public static ItemStack createEnchantedBook(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        meta.addStoredEnchant(enchantment, level, ignoreLevelRestriction);
        book.setItemMeta(meta);
        return book;
    }

    public static ItemStack addEnchantment(ItemStack item, Enchantment enchantment, int level) {
        return addEnchantment(item, enchantment, level, false);
    }

    public static ItemStack addEnchantment(ItemStack item, Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        item.setItemMeta(meta);
        return item;
    }

    public static Map<Enchantment, Integer> getEnchantmentsFromBook(ItemStack book) {
        return ((EnchantmentStorageMeta)book.getItemMeta()).getStoredEnchants();
    }
}
