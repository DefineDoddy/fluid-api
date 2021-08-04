package io.github.definedoddy.fluidapi;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class ItemGlow extends Enchantment
{
    private static Enchantment glow;

    public ItemGlow(NamespacedKey id) {
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
        glow = new ItemGlow(new NamespacedKey(FluidPlugin.getPlugin(), "Glow"));
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