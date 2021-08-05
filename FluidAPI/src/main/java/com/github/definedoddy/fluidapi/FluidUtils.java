package com.github.definedoddy.fluidapi;

import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class FluidUtils {
    public static String randomLineFromFile(File file) {
        String result = null;
        Random rand = new Random();
        int n = 0;
        try {
            for (Scanner scanner = new Scanner(file); scanner.hasNext(); ) {
                ++n;
                String line = scanner.nextLine();
                if (rand.nextInt(n) == 0) {
                    result = line;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getActionType(Action action) {
        if (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) return 0;
        if (action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) return 1;
        else return -1;
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

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static int[] fillArray(int[] array, int value) {
        Arrays.fill(array, value);
        return array;
    }

    public static double[] fillArray(double[] array, double value) {
        Arrays.fill(array, value);
        return array;
    }

    public static float[] fillArray(float[] array, float value) {
        Arrays.fill(array, value);
        return array;
    }

    public static boolean[] fillArray(boolean[] array, boolean value) {
        Arrays.fill(array, value);
        return array;
    }

    public static String[] fillArray(String[] array, String value) {
        Arrays.fill(array, value);
        return array;
    }
}
