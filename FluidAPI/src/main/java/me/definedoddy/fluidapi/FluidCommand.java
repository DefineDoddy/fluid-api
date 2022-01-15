package me.definedoddy.fluidapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FluidCommand implements CommandExecutor, TabCompleter {
    private final List<CommandArg> arguments = new ArrayList<>();

    public FluidCommand(String name) {
        FluidAPI.getPlugin().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        return run(sender, cmd, label, args);
    }

    public abstract boolean run(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args);

    public FluidCommand addTabArguments(CommandArg... args) {
        arguments.addAll(Arrays.asList(args));
        return this;
    }

    public FluidCommand removeTabArguments(CommandArg... args) {
        for (CommandArg arg : args) {
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
        for (CommandArg arg : arguments) {
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