package me.definedoddy.fluidapi;

import me.definedoddy.fluidapi.legacy.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FluidCommand implements CommandExecutor, TabCompleter {
    private final List<CommandArgument> arguments = new ArrayList<>();

    public FluidCommand(String name) {
        FluidAPI.getPlugin().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        return run(sender, cmd, label, args);
    }

    public abstract boolean run(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args);

    public FluidCommand addTabArguments(CommandArgument... args) {
        arguments.addAll(Arrays.asList(args));
        return this;
    }

    public FluidCommand removeTabArguments(CommandArgument... args) {
        for (CommandArgument arg : args) {
            arguments.remove(arg);
        }
        return this;
    }

    public FluidCommand removeTabArguments() {
        arguments.clear();
        return this;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        List<String> toSend = new ArrayList<>();
        for (CommandArgument arg : arguments) {
            if (args.length == arg.index) {
                if (arg.parent != null) {
                    if (args[arg.index - 2].equalsIgnoreCase(arg.parent.name) && !arg.exclusions.contains((Player) sender)) {
                        toSend.add(arg.name);
                    }
                } else if (!arg.exclusions.contains((Player) sender)) {
                    toSend.add(arg.name);
                }
            }
        }
        return toSend;
    }
}