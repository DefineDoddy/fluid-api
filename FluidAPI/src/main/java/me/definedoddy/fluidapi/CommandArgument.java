package me.definedoddy.fluidapi;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandArgument {
    final String name;
    int index;
    CommandArgument parent;
    List<Player> exclusions = new ArrayList<>();

    public CommandArgument(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public CommandArgument(String name, CommandArgument parent) {
        this.name = name;
        this.parent = parent;
        this.index = parent.index + 1;
    }

    public CommandArgument setIndex(int index) {
        this.index = index;
        return this;
    }

    public CommandArgument setParent(CommandArgument parent) {
        this.parent = parent;
        return this;
    }

    public CommandArgument removeParent() {
        this.parent = null;
        return this;
    }

    public CommandArgument setExclusions(List<Player> exclusions) {
        this.exclusions = exclusions;
        return this;
    }

    public CommandArgument addExclusions(Player... exclusions) {
        this.exclusions.addAll(Arrays.asList(exclusions));
        return this;
    }

    public CommandArgument removeExclusions(Player... exclusions) {
        this.exclusions.removeAll(Arrays.asList(exclusions));
        return this;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public CommandArgument getParent() {
        return parent;
    }

    public static List<CommandArgument> from(List<String> args) {
        return from(1, args);
    }

    public static List<CommandArgument> from(String... args) {
        return from(1, List.of(args));
    }

    public static List<CommandArgument> from(int index, List<String> args) {
        List<CommandArgument> list = new ArrayList<>();
        for (String arg : args) {
            list.add(new CommandArgument(arg, index));
        }
        return list;
    }

    public static List<CommandArgument> from(int index, String... args) {
        return from(index, List.of(args));
    }

    public static List<CommandArgument> from(CommandArgument parent, List<String> args) {
        List<CommandArgument> list = new ArrayList<>();
        for (String arg : args) {
            list.add(new CommandArgument(arg, parent));
        }
        return list;
    }

    public static List<CommandArgument> from(CommandArgument parent, String... args) {
        return from(parent, List.of(args));
    }
}
