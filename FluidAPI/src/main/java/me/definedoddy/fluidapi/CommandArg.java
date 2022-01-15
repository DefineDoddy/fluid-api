package me.definedoddy.fluidapi;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandArg {
    final String name;
    int index;
    CommandArg parent;
    List<Player> exclusions = new ArrayList<>();

    public CommandArg(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public CommandArg(String name, CommandArg parent) {
        this.name = name;
        this.parent = parent;
        this.index = parent.index + 1;
    }

    public CommandArg setIndex(int index) {
        this.index = index;
        return this;
    }

    public CommandArg setParent(CommandArg parent) {
        this.parent = parent;
        return this;
    }

    public CommandArg removeParent() {
        this.parent = null;
        return this;
    }

    public CommandArg setExclusions(List<Player> exclusions) {
        this.exclusions = exclusions;
        return this;
    }

    public CommandArg addExclusions(Player... exclusions) {
        this.exclusions.addAll(Arrays.asList(exclusions));
        return this;
    }

    public CommandArg removeExclusions(Player... exclusions) {
        this.exclusions.removeAll(Arrays.asList(exclusions));
        return this;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public CommandArg getParent() {
        return parent;
    }

    public static List<CommandArg> from(List<String> args) {
        return from(1, args);
    }

    public static List<CommandArg> from(String... args) {
        return from(1, List.of(args));
    }

    public static List<CommandArg> from(int index, List<String> args) {
        List<CommandArg> list = new ArrayList<>();
        for (String arg : args) {
            list.add(new CommandArg(arg, index));
        }
        return list;
    }

    public static List<CommandArg> from(int index, String... args) {
        return from(index, List.of(args));
    }

    public static List<CommandArg> from(CommandArg parent, List<String> args) {
        List<CommandArg> list = new ArrayList<>();
        for (String arg : args) {
            list.add(new CommandArg(arg, parent));
        }
        return list;
    }

    public static List<CommandArg> from(CommandArg parent, String... args) {
        return from(parent, List.of(args));
    }
}