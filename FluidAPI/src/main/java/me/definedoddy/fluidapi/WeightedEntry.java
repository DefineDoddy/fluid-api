package me.definedoddy.fluidapi;

public class WeightedEntry {
    FluidRandom random;
    final Object item;
    int weight = 1;
    double chance = 1;

    public WeightedEntry(Object item) {
        this.item = item;
    }

    public WeightedEntry(Object item, double chance) {
        this.item = item;
        this.chance = chance;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        update();
    }

    public void setChance(double chance) {
        this.chance = chance;
        update();
    }

    public Object getItem() {
        return item;
    }

    public int getWeight() {
        return weight;
    }

    public double getChance() {
        return chance;
    }

    private void update() {
        if (random != null) {
            random.build();
        }
    }
}
