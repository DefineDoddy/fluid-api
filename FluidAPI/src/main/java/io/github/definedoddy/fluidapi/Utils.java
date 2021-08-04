package io.github.definedoddy.fluidapi;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static ItemStack getHead(String name, String displayName, String... lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName + " Head"));
        List<String> colourLore = new ArrayList<>();
        for (String string : lore) {
            colourLore.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        skull.setLore(colourLore);
        skull.setOwner(name);
        item.setItemMeta(skull);
        return item;
    }

    public static ItemStack getHead(String name) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setOwner(name);
        item.setItemMeta(skull);
        return item;
    }

    public static ItemStack getHead(Player player, String displayName, String... lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName + " Head"));
        List<String> colourLore = new ArrayList<>();
        for (String string : lore) {
            colourLore.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        skull.setLore(colourLore);
        skull.setOwningPlayer(player);
        item.setItemMeta(skull);
        return item;
    }

    public static ItemStack getHead(Player player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setOwningPlayer(player);
        item.setItemMeta(skull);
        return item;
    }

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

    public static int getInventoryCount(Inventory inventory, boolean includeStackSize) {
        int count = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                count += includeStackSize ? item.getAmount() : 1;
            }
        }
        return count;
    }

    public static int getInventoryCount(Inventory inventory) {
        int count = 0;
        for (ItemStack item : inventory.getContents()) {
            if (item != null) {
                count++;
            }
        }
        return count;
    }

    public static String toColor(Character character, String text) {
        return ChatColor.translateAlternateColorCodes(character, text);
    }

    public static String toColor(String text) {
        return toColor('&', text);
    }

    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static int[] fill(int[] array, int value) {
        Arrays.fill(array, value);
        return array;
    }

    public static double[] fill(double[] array, double value) {
        Arrays.fill(array, value);
        return array;
    }

    public static float[] fill(float[] array, float value) {
        Arrays.fill(array, value);
        return array;
    }

    public static boolean[] fill(boolean[] array, boolean value) {
        Arrays.fill(array, value);
        return array;
    }

    public static String[] fill(String[] array, String value) {
        Arrays.fill(array, value);
        return array;
    }
}
