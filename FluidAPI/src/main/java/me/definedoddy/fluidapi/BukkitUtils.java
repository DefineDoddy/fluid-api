package me.definedoddy.fluidapi;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class BukkitUtils {
/*    public void populateInventory(Inventory inventory, int minCount, int maxCount) {
        for (int i = 0; i < Math.min(MathUtils.random(minCount, maxCount), inventory.getSize()); i++) {
            int slot = new Random().nextInt(inventory.getSize());
            WeightedRandom.Entry entry = getRandom();
            if (entry != null) {
                entry.getItem().setAmount(MathUtils.random(entry.getMinCount(), entry.getMaxCount()));
                inventory.setItem(slot, entry.getItem());
            }
        }
    }*/

    public static List<Location> circle(Location center, int radius, int points) {
        List<Location> locs = new ArrayList<>();
        double increment = (2 * Math.PI) / points;
        for (int i = 0; i < points; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locs.add(new Location(center.getWorld(), x, center.getY(), z));
        }
        return locs;
    }
}
