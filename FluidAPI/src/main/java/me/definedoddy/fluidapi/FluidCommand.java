package me.definedoddy.fluidapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class FluidCommand implements CommandExecutor {
    public FluidCommand(String name) {
        FluidPlugin.getPlugin().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return run(sender, cmd, label, args);
    }

    public abstract boolean run(CommandSender sender, Command cmd, String label, String[] args);
}
