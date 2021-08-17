package me.definedoddy.fluidapi;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FluidMessage {
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private final Logger logger = Bukkit.getLogger();

    private final List<Receiver> receivers = new ArrayList<>();
    private TextComponent message;
    private Type type = Type.CHAT;
    private boolean usePrefix;
    private String prefix = FluidPlugin.getChatPrefix();

    public FluidMessage(String message) {
        this.message = new TextComponent(toColor(message));
    }

    public FluidMessage(String message, Player... players) {
        this(message);
        addReceivers(players);
    }

    public FluidMessage(String message, CommandSender... senders) {
        this(message);
        addReceivers(senders);
    }

    public FluidMessage(String message, String... players) {
        this(message);
        addReceivers(players);
    }

    public FluidMessage(TextComponent message) {
        this.message = message;
    }

    public FluidMessage(TextComponent message, Player... players) {
        this(message);
        addReceivers(players);
    }

    public FluidMessage(TextComponent message, CommandSender... senders) {
        this(message);
        addReceivers(senders);
    }

    public FluidMessage(TextComponent message, String... players) {
        this(message);
        addReceivers(players);
    }

    public static Player[] toPlayerArray(List<? extends Player> players) {
        return players.toArray(new Player[0]);
    }

    public static CommandSender[] toSenderArray(List<? extends CommandSender> senders) {
        return senders.toArray(new CommandSender[0]);
    }

    public static String[] toStringArray(List<? extends String> players) {
        return players.toArray(new String[0]);
    }

    public static Player[] toPlayerArray(Collection<? extends Player> players) {
        return players.toArray(new Player[0]);
    }

    public static CommandSender[] toSenderArray(Collection<? extends CommandSender> senders) {
        return senders.toArray(new CommandSender[0]);
    }

    public static String[] toStringArray(Collection<? extends String> players) {
        return players.toArray(new String[0]);
    }

    public FluidMessage send() {
        TextComponent message = this.message;
        if (receivers.size() > 0) {
            for (Receiver receiver : receivers) {
                if (type == Type.CHAT) {
                    receiver.sendMessage(message);
                } else if (type == Type.ACTIONBAR) {
                    receiver.sendBarMessage(message.getText());
                } else if (type == Type.TITLE) {
                    receiver.sendTitle(message.getText(), "", 1, 3, 1);
                }
            }
        } else {
            console.sendMessage(message.getText());
        }
        return this;
    }

    public FluidMessage send(int... args) {
        TextComponent message = this.message;
        if (receivers.size() > 0) {
            for (Receiver receiver : receivers) {
                if (type == Type.TITLE) {
                    receiver.sendTitle(message.getText(), "", args[0], args[1], args[2]);
                }
            }
        }
        return this;
    }

    public FluidMessage send(String arg) {
        TextComponent message = this.message;
        if (receivers.size() > 0) {
            for (Receiver receiver : receivers) {
                if (type == Type.TITLE) {
                    receiver.sendTitle(message.getText(), arg, 1, 3, 1);
                }
            }
        }
        return this;
    }

    public FluidMessage send(String arg, int... args) {
        TextComponent message = this.message;
        if (receivers.size() > 0) {
            for (Receiver receiver : receivers) {
                if (type == Type.TITLE) {
                    receiver.sendTitle(message.getText(), arg, args[0], args[1], args[2]);
                }
            }
        }
        return this;
    }

    public FluidMessage addReceivers(CommandSender... senders) {
        for (CommandSender sender : senders) {
            receivers.add(new Receiver(sender));
        }
        return this;
    }

    public FluidMessage addReceivers(Player... players) {
        for (Player player : players) {
            receivers.add(new Receiver(player));
        }
        return this;
    }

    public FluidMessage addReceivers(String... players) {
        for (String player : players) {
            receivers.add(new Receiver(player));
        }
        return this;
    }

    public FluidMessage setReceivers(CommandSender... senders) {
        receivers.clear();
        addReceivers(senders);
        return this;
    }

    public FluidMessage setReceivers(Player... players) {
        receivers.clear();
        addReceivers(players);
        return this;
    }

    public FluidMessage setReceivers(String... players) {
        receivers.clear();
        addReceivers(players);
        return this;
    }

    public FluidMessage removeReceivers(Player... players) {
        for (Player player : players) {
            receivers.remove(new Receiver(player));
        }
        return this;
    }

    public FluidMessage removeReceivers(String... players) {
        for (String player : players) {
            receivers.remove(new Receiver(player));
        }
        return this;
    }

    public FluidMessage removeReceivers() {
        receivers.clear();
        return this;
    }

    public List<Receiver> getReceivers() {
        return receivers;
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

    public FluidMessage setMessage(TextComponent message) {
        this.message = message;
        return this;
    }

    public FluidMessage setMessage(String message) {
        this.message = new TextComponent(message);
        return this;
    }

    public TextComponent getMessage() {
        return message;
    }

    public <T> T getMessage(Class<T> returnClass) {
        if (returnClass == String.class) {
            return (T)message.getText();
        } else if (returnClass == TextComponent.class) {
            return (T)message;
        }
        return null;
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

    public enum Action {
        RUN_COMMAND,
        SUGGEST_COMMAND,
        OPEN_URL,
        COPY_TO_CLIPBOARD,
        CHANGE_PAGE,
        OPEN_FILE,
        SHOW_TOOLTIP_TEXT
    }

    public static String toColor(String text) {
        return toColor('&', text);
    }

    public static String toColor(Character character, String text) {
        return ChatColor.translateAlternateColorCodes(character, text);
    }

    public void logInfo(String message) {
        logger.info(usePrefix ? (prefix + toColor(message)) : toColor(message));
    }

    public void logWarning(String message) {
        logger.warning(usePrefix ? (prefix + toColor(message)) : toColor(message));
    }

    public void logSevere(String message) {
        logger.severe(usePrefix ? (prefix + toColor(message)) : toColor(message));
    }

    public void runCmd(String command) {
        Bukkit.dispatchCommand(console, command);
    }

    private class Receiver {
        private Player player;
        private CommandSender sender;

        public Receiver(Player player) {
            this.player = player;
        }

        public Receiver(String player) {
            this.player = Bukkit.getPlayer(player);
        }

        public Receiver(CommandSender sender) {
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

        public void sendMessage(TextComponent message) {
            if (FluidMessage.this.usePrefix) {
                TextComponent component = new TextComponent(prefix);
                component.addExtra(FluidMessage.this.message);
                message = component;
            }
            if (player != null) {
                player.spigot().sendMessage(message);
            } else if (sender != null) {
                sender.spigot().sendMessage(message);
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

    public static class Builder {
        private final TextComponent text;

        public Builder() {
            this.text = new TextComponent();
        }

        public Builder(String text) {
            this.text = new TextComponent(toColor(text));
        }

        public Builder add(String text, Map<Action, String> actions) {
            TextComponent component = new TextComponent(toColor(text));
            for (Map.Entry<Action, String> entry : actions.entrySet()) {
                if (entry.getKey() != Action.SHOW_TOOLTIP_TEXT) {
                    component.setClickEvent(new ClickEvent(toAction(entry.getKey()), entry.getValue()));
                } else {
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(entry.getValue())));
                }
            }
            this.text.addExtra(component);
            return this;
        }

        public Builder add(String text, Action type, String action) {
            TextComponent component = new TextComponent(toColor(text));
            if (type != Action.SHOW_TOOLTIP_TEXT) {
                component.setClickEvent(new ClickEvent(toAction(type), action));
            } else {
                component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(action)));
            }
            this.text.addExtra(component);
            return this;
        }

        public Builder add(String text) {
            this.text.addExtra(toColor(text));
            return this;
        }

        private ClickEvent.Action toAction(Action action) {
            switch (action) {
                case RUN_COMMAND -> { return ClickEvent.Action.RUN_COMMAND; }
                case SUGGEST_COMMAND -> { return ClickEvent.Action.SUGGEST_COMMAND; }
                case OPEN_URL -> { return ClickEvent.Action.OPEN_URL; }
                case COPY_TO_CLIPBOARD -> { return ClickEvent.Action.COPY_TO_CLIPBOARD; }
                case CHANGE_PAGE -> { return ClickEvent.Action.CHANGE_PAGE; }
                case OPEN_FILE -> { return ClickEvent.Action.OPEN_FILE; }
                default -> { return null; }
            }
        }

        public TextComponent build() {
            return text;
        }
    }
}
