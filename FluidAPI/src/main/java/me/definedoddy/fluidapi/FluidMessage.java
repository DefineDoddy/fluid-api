package me.definedoddy.fluidapi;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluidMessage {
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    private final List<Reciever> recievers = new ArrayList<>();
    private String message;
    private Type type = Type.CHAT;
    private boolean usePrefix;
    private String prefix = FluidPlugin.getChatPrefix();

    public FluidMessage(String message) {
        this.message = toColor(message);
    }

    public FluidMessage(String message, Player... players) {
        this(message);
        addRecievers(players);
    }

    public FluidMessage(String message, CommandSender... senders) {
        this(message);
        addRecievers(senders);
    }

    public FluidMessage(String message, String... players) {
        this(message);
        addRecievers(players);
    }

    public FluidMessage send() {
        String message = this.message;
        if (usePrefix) {
            message = prefix + this.message;
        }
        if (recievers.size() > 0) {
            for (Reciever reciever : recievers) {
                if (type == Type.CHAT) {
                    reciever.sendMessage(message);
                } else if (type == Type.ACTIONBAR) {
                    reciever.sendBarMessage(message);
                } else if (type == Type.TITLE) {
                    reciever.sendTitle(message, "", 1, 3, 1);
                }
            }
        } else {
            console.sendMessage(message);
        }
        return this;
    }

    public FluidMessage addRecievers(CommandSender... senders) {
        for (CommandSender sender : senders) {
            recievers.add(new Reciever(sender));
        }
        return this;
    }

    public FluidMessage addRecievers(Player... players) {
        for (Player player : players) {
            recievers.add(new Reciever(player));
        }
        return this;
    }

    public FluidMessage addRecievers(String... players) {
        for (String player : players) {
            recievers.add(new Reciever(player));
        }
        return this;
    }

    public FluidMessage setRecievers(CommandSender... senders) {
        recievers.clear();
        addRecievers(senders);
        return this;
    }

    public FluidMessage setRecievers(Player... players) {
        recievers.clear();
        addRecievers(players);
        return this;
    }

    public FluidMessage setRecievers(String... players) {
        recievers.clear();
        addRecievers(players);
        return this;
    }

    public FluidMessage removeRecievers(Player... players) {
        for (Player player : players) {
            recievers.remove(new Reciever(player));
        }
        return this;
    }

    public FluidMessage removeRecievers(String... players) {
        for (String player : players) {
            recievers.remove(new Reciever(player));
        }
        return this;
    }

    public FluidMessage removeRecievers() {
        recievers.clear();
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

    private class Reciever {
        private Player player;
        private CommandSender sender;

        public Reciever(Player player) {
            this.player = player;
        }

        public Reciever(String player) {
            this.player = Bukkit.getPlayer(player);
        }

        public Reciever(CommandSender sender) {
            if (sender instanceof Player player) {
                this.player = player;
            } else {
                this.sender = sender;
            }
        }

        public Player getPlayer() {
            return player;
        }

        public CommandSender getSender() {
            return sender;
        }

        public void sendMessage(String message) {
            if (player != null) {
                player.sendMessage(message);
            } else if (sender != null) {
                sender.sendMessage(message);
            }
        }

        public void sendBarMessage(String message) {
            if (player != null) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            }
        }

        public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
            if (player != null) {
                player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
            }
        }
    }
}
