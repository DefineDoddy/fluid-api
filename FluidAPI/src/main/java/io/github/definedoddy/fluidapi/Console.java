package io.github.definedoddy.fluidapi;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Console {
    private static final String chatPrefix = FluidPlugin.getChatPrefix();
    private static final ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();

    public static void message(Player player, String message, ChatColor colour, boolean usePrefix) {
        if (usePrefix) {
            player.sendMessage(Utils.toColor(colour + message));
        } else {
            player.sendMessage(colour + message);
        }
    }

    public static void message(Player player, String message, boolean usePrefix) {
        if (usePrefix) {
            player.sendMessage(Utils.toColor(chatPrefix + message));
        } else {
            player.sendMessage(message);
        }
    }

    public static void message(Player player, String message, ChatColor colour) {
        player.sendMessage(colour + message);
    }

    public static void message(Player player, String message) {
        player.sendMessage(message);
    }

    public static void actionBar(Player player, String message, ChatColor colour) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(colour + message));
    }

    public static void actionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public static void runCommandAsOperator(Player player, String command) {
        boolean isOperator = player.isOp();
        try {
            player.setOp(true);
            player.performCommand(command);
        } finally {
            player.setOp(isOperator);
        }
    }

    public static void logInfo(String message) {
        Bukkit.getServer().getLogger().info(Utils.toColor(chatPrefix + message));
    }

    public static void logWarning(String message) {
        Bukkit.getServer().getLogger().warning(Utils.toColor(chatPrefix + message));
    }

    public static void logError(String message) {
        Bukkit.getServer().getLogger().severe(Utils.toColor(chatPrefix + message));
    }

    public static void logRaw(String message) {
        Bukkit.getServer().getLogger().info(Utils.toColor(message));
    }

    public static void runCommand(String command) {
        Bukkit.dispatchCommand(consoleSender, command);
    }
}
