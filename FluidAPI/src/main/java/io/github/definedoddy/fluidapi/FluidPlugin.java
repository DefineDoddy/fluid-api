package io.github.definedoddy.fluidapi;

import org.bukkit.plugin.Plugin;

public class FluidPlugin {
    private static Plugin plugin;
    private static String name;
    private static String chatPrefix;

    public static void register(Plugin plugin, String name) {
        FluidPlugin.plugin = plugin;
        FluidPlugin.name = name;
    }

    public static void register(Plugin plugin, String name, String chatPrefix) {
        FluidPlugin.plugin = plugin;
        FluidPlugin.name = name;
        FluidPlugin.chatPrefix = chatPrefix;
        Console.logRaw("&b[FluidAPI] &aSuccessfully registered " + name);
    }

    public static Plugin getPlugin() {
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
