package me.definedoddy.fluidapi;

import org.bukkit.command.*;
import org.bukkit.event.EventHandler;

import java.util.*;

public abstract class FluidCommand implements CommandExecutor, TabCompleter {
    private final Map<String, Integer> tabArguments = new HashMap<>();

    public FluidCommand(String name) {
        FluidPlugin.getPlugin().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return run(sender, cmd, args);
    }

    public abstract boolean run(CommandSender sender, Command cmd, String[] args);

    public FluidCommand addTabArguments(Map<String, Integer> args) {
        tabArguments.putAll(args);
        return this;
    }

    public FluidCommand removeTabArguments(String... args) {
        for (String arg : args) {
            tabArguments.remove(arg);
        }
        return this;
    }

    public FluidCommand removeTabArguments() {
        tabArguments.clear();
        return this;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> toSend = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : tabArguments.entrySet()) {
            if (args.length == entry.getValue()) {
                toSend.add(entry.getKey());
            }
        }
        return toSend;
    }
}
