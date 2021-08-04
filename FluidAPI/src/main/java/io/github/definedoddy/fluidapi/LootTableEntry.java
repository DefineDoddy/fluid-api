package io.github.definedoddy.fluidapi;

import org.bukkit.inventory.ItemStack;

public class LootTableEntry {
    private final int weight;
    private final ItemStack item;
    private double chance;
    private final double vanishChance;
    private final int minCount;
    private final int maxCount;

    public LootTableEntry(ItemStack item, int weight, double vanishChance, int minCount, int maxCount) {
        this.item = item.clone();
        this.weight = weight;
        this.vanishChance = vanishChance;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public int getWeight() {
        return weight;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public double getChance() {
        return chance;
    }

    public double getVanishChance() {
        return vanishChance;
    }

    public int getMinCount() {
        return minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public ItemStack getItem() {
        return item;
    }
}
