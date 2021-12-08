package me.definedoddy.fluidapi.legacy;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class FluidPlugin {
    private static JavaPlugin plugin;
    private static String name;
    private static int resourceId;

    public static <T> void register(T plugin) {
        register(plugin, ((JavaPlugin) plugin).getName(), true);
    }

    public static <T> void register(T plugin, boolean showMessage) {
        register(plugin, ((JavaPlugin) plugin).getName(), showMessage);
    }

    public static <T> void register(T plugin, String name) {
        register(plugin, name, true);
    }

    public static <T> void register(T plugin, String name, boolean showMessage) {
        FluidPlugin.plugin = (JavaPlugin) plugin;
        FluidPlugin.name = name;
        EventHandler.init();
        FluidCommand.register(plugin);
        Registry.register();
        if (showMessage) {
            new FluidMessage("&b[FluidAPI] &aSuccessfully registered " + name + " - " + getVersion()).send();
        }
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static Plugin getRawPlugin() {
        return plugin;
    }

    public static String getName() {
        return name;
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
