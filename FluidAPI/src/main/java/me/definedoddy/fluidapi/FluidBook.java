package me.definedoddy.fluidapi;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class FluidBook {
    private ItemStack book;

    public FluidBook(Enchantment enchantment, int level) {
        new FluidBook(enchantment, level, false);
    }

    public FluidBook(Enchantment enchantment, int level, boolean bypassLimit) {
        book = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) book.getItemMeta();
        meta.addStoredEnchant(enchantment, level, bypassLimit);
        book.setItemMeta(meta);
    }

    public ItemStack build() {
        return book;
    }

    public Map<Enchantment, Integer> getEnchants() {
        return ((EnchantmentStorageMeta)book.getItemMeta()).getStoredEnchants();
    }

    public static Map<Enchantment, Integer> getEnchants(ItemStack book) {
        return ((EnchantmentStorageMeta)book.getItemMeta()).getStoredEnchants();
    }

    public FluidBook(String name, String author, String... text) {
        book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta)book.getItemMeta();
        bookMeta.setTitle(name);
        bookMeta.setAuthor(author);
        for (String line : text) {
            bookMeta.addPage(line);
        }
        book.setItemMeta(bookMeta);
    }
}
