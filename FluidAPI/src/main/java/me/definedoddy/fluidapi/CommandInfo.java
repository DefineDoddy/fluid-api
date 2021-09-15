package me.definedoddy.fluidapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandInfo {
    CommandSender sender;
    Command command;
    String[] args;

    CommandInfo(CommandSender sender, Command command, String[] args) {
        this.sender = sender;
        this.command = command;
        this.args = args;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Command getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }
}
