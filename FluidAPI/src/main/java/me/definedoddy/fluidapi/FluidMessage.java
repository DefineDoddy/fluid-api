package me.definedoddy.fluidapi;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
<<<<<<< Updated upstream

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
=======
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FluidMessage implements MessageBase {
    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();
    private static String prefix = "[" + FluidAPI.getPlugin().getName() + "] ";

    private TextComponent message = new TextComponent();
    private final List<Player> receivers = new ArrayList<>();
    private boolean usePrefix;

    public FluidMessage() {

    }

    public FluidMessage(String message) {
        this.message = new TextComponent(toColor(message));
    }

    public FluidMessage(TextComponent message) {
        this.message = new TextComponent(toColor(message.getText()));
    }

    public FluidMessage send() {
        console.spigot().sendMessage(new TextComponent(usePrefix ? prefix + message.getText() : message.getText()));
        return this;
    }

    private FluidMessage sendTo() {
        for (Player player : receivers) {
            player.spigot().sendMessage(new TextComponent(usePrefix ? prefix + message.getText() : message.getText()));
>>>>>>> Stashed changes
        }
        return this;
    }

<<<<<<< Updated upstream
    public FluidMessage addPlayer(Player player) {
        players.add(player);
        return this;
    }

    public FluidMessage addPlayers(Player... players) {
        this.players.addAll(Arrays.stream(players).toList());
        return this;
    }

    public FluidMessage removePlayer(Player player) {
        players.remove(player);
        return this;
    }

    public FluidMessage addPlayer(String player) {
        players.add(Bukkit.getPlayer(player));
        return this;
    }

    public FluidMessage addPlayers(String... players) {
        for (String player : players) {
            this.players.add(Bukkit.getPlayer(player));
        }
        return this;
    }

    public FluidMessage removePlayer(String player) {
        players.remove(Bukkit.getPlayer(player));
        return this;
    }

    public FluidMessage removePlayers() {
        players.clear();
        return this;
    }

    public FluidMessage setType(Type type) {
        this.type = type;
        return this;
=======
    @Override
    public FluidMessage send(Preset... presets) {
        for (Preset preset : presets) {
            if (preset == Preset.ALL_PLAYERS) {
                receivers.clear();
                receivers.addAll(Bukkit.getOnlinePlayers());
            }
        }
        return sendTo();
    }

    @Override
    public FluidMessage send(Player... players) {
        receivers.clear();
        receivers.addAll(List.of(players));
        return sendTo();
    }

    @Override
    public FluidMessage send(CommandSender... senders) {
        receivers.clear();
        receivers.addAll(List.of((Player[])senders));
        return sendTo();
    }

    @Override
    public FluidMessage send(Collection<Player> players) {
        receivers.clear();
        receivers.addAll(players);
        return sendTo();
    }

    public FluidMessage prefix() {
        usePrefix = true;
        return this;
    }

    public enum Preset {
        ALL_PLAYERS,
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

    public static void setPrefix(String prefix) {
        FluidMessage.prefix = prefix;
    }

    public static String toColor(String text) {
        return toColor('&', new String[]{text}).get(0);
>>>>>>> Stashed changes
    }

    public static List<String> toColor(String... text) {
        return toColor('&', text);
    }

<<<<<<< Updated upstream
    public FluidMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public FluidMessage usePrefix() {
        return setUsePrefix(true);
    }
=======
    public static String toColor(Character character, String text) {
        return toColor(character, new String[]{text}).get(0);
    }

    public static List<String> toColor(Character character, String... text) {
        List<String> coloured = new ArrayList<>();
        for (String string : text) {
            Matcher match = pattern.matcher(string);
            while (match.find()) {
                String colour = string.substring(match.start(), match.end());
                string = string.replace(colour, String.valueOf(net.md_5.bungee.api.ChatColor.of(colour)));
                match = pattern.matcher(string);
            }
            coloured.add(ChatColor.translateAlternateColorCodes(character, string));
        }
        return coloured;
    }

    public Title title(String title, String subtitle) {
        return new Title(title, subtitle);
    }

    public Actionbar actionbar(String message) {
        return new Actionbar(message);
    }

    public static class Title implements MessageBase {
        private final String title;
        private String subtitle = "";
        private int in = 20;
        private int stay = 60;
        private int out = 20;
        private Runnable onFinish;
>>>>>>> Stashed changes

        public Title(String title) {
            this.title = toColor(title);
        }

<<<<<<< Updated upstream
    public FluidMessage setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public static String toColor(Character character, String text) {
        return ChatColor.translateAlternateColorCodes(character, text);
    }
=======
        public Title(String title, String subtitle) {
            this.title = toColor(title);
            this.subtitle = toColor(subtitle);
        }

        public Title in(int in) {
            this.in = in;
            return this;
        }

        public Title stay(int stay) {
            this.stay = stay;
            return this;
        }

        public Title out(int out) {
            this.out = out;
            return this;
        }

        public Title onFinish(Runnable runnable) {
            onFinish = runnable;
            return this;
        }
>>>>>>> Stashed changes

        private Title sendTo() {
            for (Player player : receivers) {
                player.sendTitle(title, subtitle, in, stay, out);
            }
            if (onFinish != null) {
                new FluidTask(onFinish).run(in + stay + out);
            }
            return this;
        }

<<<<<<< Updated upstream
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
=======
        @Override
        public Title send(Preset... presets) {
            for (Preset preset : presets) {
                if (preset == Preset.ALL_PLAYERS) {
                    receivers.clear();
                    receivers.addAll(Bukkit.getOnlinePlayers());
                }
            }
            return sendTo();
        }

        @Override
        public Title send(Player... players) {
            receivers.clear();
            receivers.addAll(List.of(players));
            return sendTo();
        }

        @Override
        public Title send(Collection<Player> players) {
            receivers.clear();
            receivers.addAll(players);
            return sendTo();
        }

        @Override
        public MessageBase send(CommandSender... senders) {
            receivers.clear();
            receivers.addAll(List.of((Player[])senders));
            return sendTo();
        }
    }

    public static class Actionbar implements MessageBase {
        private final String message;
        private Runnable onFinish;
        private int stay = 40;

        public Actionbar(String message) {
            this.message = toColor(message);
        }

        public Actionbar onFinish(Runnable runnable) {
            onFinish = runnable;
            return this;
        }

        public Actionbar stay(int stay) {
            this.stay = Math.max(stay, 40);
            return this;
        }

        private Actionbar sendTo() {
            if (stay > 40) {
                new FluidTask(this::sendMessage).repeat(0, 1, stay - 40);
            } else {
                sendMessage();
            }
            if (onFinish != null) {
                new FluidTask(onFinish).run(stay + 20);
            }
            return this;
        }

        @Override
        public Actionbar send(Preset... presets) {
            for (Preset preset : presets) {
                if (preset == Preset.ALL_PLAYERS) {
                    receivers.clear();
                    receivers.addAll(Bukkit.getOnlinePlayers());
                }
            }
            return sendTo();
        }

        @Override
        public Actionbar send(Player... players) {
            receivers.clear();
            receivers.addAll(List.of(players));
            return sendTo();
        }

        @Override
        public Actionbar send(Collection<Player> players) {
            receivers.clear();
            receivers.addAll(players);
            return sendTo();
        }

        @Override
        public MessageBase send(CommandSender... senders) {
            receivers.clear();
            receivers.addAll(List.of((Player[])senders));
            return sendTo();
        }

        private void sendMessage() {
            for (Player player : receivers) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            }
        }
    }

    public static class Builder {
        private TextComponent text;

        public Builder() {
            this.text = new TextComponent();
        }

        public Builder(String text) {
            this.text = new TextComponent(toColor(text));
        }

        public Builder(String text, Map<Action, String> actions) {
            add(text, actions);
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
            if (this.text != null) {
                this.text.addExtra(component);
            } else {
                this.text = component;
            }
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
>>>>>>> Stashed changes
    }
}
