package me.definedoddy.fluidapi;

import org.bukkit.plugin.java.JavaPlugin;

public class FluidPlugin {
    private static JavaPlugin plugin;
    private static String name;
    private static String chatPrefix;

    public static void register(JavaPlugin plugin, String name) {
        register(plugin, name, null);
    }

    public static void register(JavaPlugin plugin, String name, String chatPrefix) {
        FluidPlugin.plugin = plugin;
        FluidPlugin.name = name;
        FluidPlugin.chatPrefix = chatPrefix;
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
}
