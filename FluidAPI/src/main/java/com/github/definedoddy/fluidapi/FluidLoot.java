package com.github.definedoddy.fluidapi;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public record FluidLoot(ArrayList<FluidLootEntry> entries) {
    public FluidLootEntry getRandom() {
        double random = Math.random();
        for (int i = 0; i < entries.size() - 1; i++) {
            FluidLootEntry entry = entries.get(i + 1);
            if (entry.getChance() > random) {
                if (entry.getVanishChance() > new Random().nextDouble() + 0.01D || entry.getVanishChance() >= 1.0D) {
                    return null;
                }
                entry = entries.get(i);
                entry.getItem().setAmount(FluidUtils.random(entry.getMinCount(), entry.getMaxCount()));
                return entry;
            }
        }
        FluidLootEntry entry = entries.get(entries.size() - 1);
        entry.getItem().setAmount(FluidUtils.random(entry.getMinCount(), entry.getMaxCount()));
        return entry;
    }

    public void populateInventory(Inventory inventory, int minCount, int maxCount) {
        for (int i = 0; i < Math.min(FluidUtils.random(minCount, maxCount), inventory.getSize()); i++) {
            int slot = new Random().nextInt(inventory.getSize());
            FluidLootEntry entry = getRandom();
            if (entry != null) {
                entry.getItem().setAmount(FluidUtils.random(entry.getMinCount(), entry.getMaxCount()));
                inventory.setItem(slot, entry.getItem());
            }
        }
    }

    public static class LootTableBuilder {
        private int totalWeight = 0;
        private final ArrayList<FluidLootEntry> entries = new ArrayList<>();

        public LootTableBuilder add(ItemStack item, int weight, double vanishChance, int minCount, int maxCount) {
            totalWeight += weight;
            entries.add(new FluidLootEntry(item, weight, vanishChance, minCount, maxCount));
            return this;
        }

        public LootTableBuilder addAll(ItemStack[] items, int[] weights, double[] vanishChance, int[] minCount, int[] maxCount) {
            for (int i = 0; i < items.length; i++) {
                totalWeight += weights[i];
                entries.add(new FluidLootEntry(items[i], weights[i], vanishChance[i], minCount[i], maxCount[i]));
            }
            return this;
        }

        public boolean isBuilt() {
            return entries.size() > 0 && totalWeight > 0;
        }

        public FluidLoot build() {
            if (!isBuilt()) {
                return null;
            }
            double base = 0;
            for (FluidLootEntry entry : entries) {
                double chance = getChance(base);
                entry.setChance(chance);
                base += entry.getWeight();
            }
            return new FluidLoot(entries);
        }

        private double getChance(double weight) {
            return weight / totalWeight;
        }
    }
}
