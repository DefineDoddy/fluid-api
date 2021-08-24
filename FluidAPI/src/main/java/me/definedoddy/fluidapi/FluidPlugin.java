package me.definedoddy.fluidapi;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class FluidPlugin {
    private static JavaPlugin plugin;
    private static String name;
    private static String chatPrefix;
    private static int resourceId;

    public static void register(JavaPlugin plugin, String name) {
        register(plugin, name, null);
    }

    public static void register(JavaPlugin plugin, String name, String chatPrefix) {
        FluidPlugin.plugin = plugin;
        FluidPlugin.name = name;
        FluidPlugin.chatPrefix = chatPrefix;
        EventHandler.init();
        new FluidMessage("&b[FluidAPI] &aSuccessfully registered " + name).send();
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static String getName() {
        return name;
    }

    public static String getChatPrefix() {
        return chatPrefix;
    }

    public static void setChatPrefix(String prefix) {
        chatPrefix = prefix;
    }

    public static void setResourceId(int id) {
        resourceId = id;
    }

    public static String getLatestVersion() {
        try (InputStream stream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openStream()) {
            Scanner scanner = new Scanner(stream);
            if (scanner.hasNext()) {
                return scanner.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public static boolean isLatestVersion() {
        return getVersion().equalsIgnoreCase(getLatestVersion());
    }
}
