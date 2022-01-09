package me.definedoddy.fluidapi;

import java.util.ArrayList;
import java.util.List;

public class FluidRandom {
    private final List<WeightedEntry> entries;

    public FluidRandom(WeightedEntry entries) {
        this.entries = new ArrayList<>(List.of(entries));
        build();
    }

    public void add(WeightedEntry... entries) {
        this.entries.addAll(List.of(entries));
    }

    public void remove(Object... entries) {
        for (Object item : entries) {
            this.entries.removeIf(entry -> entry.item.equals(item));
        }
    }

    public WeightedEntry pick() {
        for (int i = 0; i < entries.size() - 1; i++) {
            WeightedEntry entry = entries.get(i + 1);
            if (entry.chance > Math.random()) {
                entry = entries.get(i);
                return entry;
            }
        }
        return entries.get(entries.size() - 1);
    }

    public WeightedEntry pick(boolean remove) {
        WeightedEntry entry = pick();
        if (remove) entries.remove(entry);
        return entry;
    }

    void build() {
        int totalWeight = 0;
        for (WeightedEntry entry : entries) {
            totalWeight += entry.weight;
            entry.random = this;
        }
        double base = 0;
        for (WeightedEntry entry : entries) {
            entry.chance = base / totalWeight;
            base += entry.weight;
        }
    }
}
