package me.definedoddy.fluidapi.legacy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class FluidCommand implements CommandExecutor {
    public FluidCommand(String name) {
        FluidPlugin.getPlugin().getCommand(name).setExecutor(this);
    }

    public abstract boolean run(CommandSender sender, Command cmd, String[] args);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return run(sender, command, args);
    }
}
