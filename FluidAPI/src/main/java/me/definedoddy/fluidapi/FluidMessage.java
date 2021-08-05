package me.definedoddy.fluidapi;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FluidMessage {
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private final List<Player> players = new ArrayList<>();
    private String message;
    private Type type = Type.CHAT;
    private boolean usePrefix;
    private String prefix = FluidPlugin.getChatPrefix();

    public FluidMessage(String message) {
        this.message = toColor(message);
    }

    public FluidMessage(String message, boolean usePrefix) {
        new FluidMessage(message);
        this.usePrefix = usePrefix;
    }

    public FluidMessage send() {
        String message = this.message;
        if (usePrefix) {
            message = prefix + this.message;
        }
        if (players.size() > 0) {
            for (Player player : players) {
                if (type == Type.CHAT) {
                    player.sendMessage(message);
                } else if (type == Type.ACTIONBAR) {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                } else if (type == Type.TITLE) {
                    player.sendTitle(message, "", 1, 3, 1);
                }
            }
        } else {
            console.sendMessage(message);
        }
        return this;
    }

    public FluidMessage addPlayer(Player player) {
        players.add(player);
        return this;
    }

    public FluidMessage addPlayers(List<Player> players) {
        this.players.addAll(players);
        return this;
    }

    public FluidMessage setType(Type type) {
        this.type = type;
        return this;
    }

    public enum Type {
        CHAT,
        ACTIONBAR,
        TITLE
    }

    public FluidMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public FluidMessage usePrefix() {
        return setUsePrefix(true);
    }

    public FluidMessage setUsePrefix(boolean usePrefix) {
        this.usePrefix = usePrefix;
        return this;
    }

    public FluidMessage setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public static String toColor(Character character, String text) {
        return ChatColor.translateAlternateColorCodes(character, text);
    }

    public static String toColor(String text) {
        return toColor('&', text);
    }

    public void logInfo(String message) {
        Bukkit.getServer().getLogger().info(usePrefix ? (prefix + message) : message);
    }

    public void logWarning(String message) {
        Bukkit.getServer().getLogger().warning(usePrefix ? (prefix + message) : message);
    }

    public void logSevere(String message) {
        Bukkit.getServer().getLogger().severe(usePrefix ? (prefix + message) : message);
    }

    public void runCmd(String command) {
        Bukkit.dispatchCommand(console, command);
    }
}
