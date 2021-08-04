package io.github.definedoddy.fluidapi;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public record CustomLootTable(ArrayList<LootTableEntry> entries) {
    public LootTableEntry getRandom() {
        double random = Math.random();
        for (int i = 0; i < entries.size() - 1; i++) {
            LootTableEntry entry = entries.get(i + 1);
            if (entry.getChance() > random) {
                if (entry.getVanishChance() > new Random().nextDouble() + 0.01D || entry.getVanishChance() >= 1.0D) {
                    return null;
                }
                entry = entries.get(i);
                entry.getItem().setAmount(Utils.random(entry.getMinCount(), entry.getMaxCount()));
                return entry;
            }
        }
        LootTableEntry entry = entries.get(entries.size() - 1);
        entry.getItem().setAmount(Utils.random(entry.getMinCount(), entry.getMaxCount()));
        return entry;
    }

    public void populateInventory(Inventory inventory, int minCount, int maxCount) {
        for (int i = 0; i < Math.min(Utils.random(minCount, maxCount), inventory.getSize()); i++) {
            int slot = new Random().nextInt(inventory.getSize());
            LootTableEntry entry = getRandom();
            if (entry != null) {
                entry.getItem().setAmount(Utils.random(entry.getMinCount(), entry.getMaxCount()));
                inventory.setItem(slot, entry.getItem());
            }
        }
    }

    public static class LootTableBuilder {
        private int totalWeight = 0;
        private final ArrayList<LootTableEntry> entries = new ArrayList<>();

        public LootTableBuilder add(ItemStack item, int weight, double vanishChance, int minCount, int maxCount) {
            totalWeight += weight;
            entries.add(new LootTableEntry(item, weight, vanishChance, minCount, maxCount));
            return this;
        }

        public LootTableBuilder addAll(ItemStack[] items, int[] weights, double[] vanishChance, int[] minCount, int[] maxCount) {
            for (int i = 0; i < items.length; i++) {
                totalWeight += weights[i];
                entries.add(new LootTableEntry(items[i], weights[i], vanishChance[i], minCount[i], maxCount[i]));
            }
            return this;
        }

        public boolean isBuilt() {
            return entries.size() > 0 && totalWeight > 0;
        }

        public CustomLootTable build() {
            if (!isBuilt()) {
                return null;
            }
            double base = 0;
            for (LootTableEntry entry : entries) {
                double chance = getChance(base);
                entry.setChance(chance);
                base += entry.getWeight();
            }
            return new CustomLootTable(entries);
        }

        private double getChance(double weight) {
            return weight / totalWeight;
        }
    }
}
