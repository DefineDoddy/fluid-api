package me.definedoddy.fluidapi.utils;

import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FluidUtils {
    public static int getActionValue(Action action) {
        return switch (action) {
            case LEFT_CLICK_BLOCK, LEFT_CLICK_AIR -> 0;
            case RIGHT_CLICK_BLOCK, RIGHT_CLICK_AIR -> 1;
            case PHYSICAL -> 2;
        };
    }

    public static int getInventoryCount(Inventory inventory) {
        return getInventoryCount(inventory, false);
    }

    public static int getInventoryCount(Inventory inventory, boolean includeStackSize) {
        int count = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                count += includeStackSize ? item.getAmount() : 1;
            }
        }
        return count;
    }
}
