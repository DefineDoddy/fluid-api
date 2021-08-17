package me.definedoddy.fluidapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public abstract class FluidCommand implements CommandExecutor, TabCompleter {
    private final List<Argument> tabArguments = new ArrayList<>();
    private final List<ArgumentIf> argumentIfs = new ArrayList<>();

    public FluidCommand(String name) {
        FluidPlugin.getPlugin().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return run(sender, cmd, args);
    }

    public abstract boolean run(CommandSender sender, Command cmd, String[] args);

    public FluidCommand addTabArguments(Argument... args) {
        tabArguments.addAll(Arrays.asList(args));
        return this;
    }

    public FluidCommand removeTabArguments(Argument... args) {
        for (Argument arg : args) {
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
        argumentIfs.clear();
        for (Argument arg : tabArguments) {
            loopArgs(arg);
        }
        for (ArgumentIf argumentIf : argumentIfs) {
            if (args.length == argumentIf.index) {
                if (argumentIf.ifArgument != null) {
                    if (args[argumentIf.index - 2].equalsIgnoreCase(argumentIf.ifArgument)) {
                        toSend.add(argumentIf.name);
                    }
                } else {
                    toSend.add(argumentIf.name);
                }
            }
        }
        return toSend;
    }

    private void loopArgs(Argument arg) {
        if (arg.parent != null) {
            argumentIfs.add(new ArgumentIf(arg.name, arg.index, arg.parent.name));
            loopArgs(arg.parent);
        } else {
            argumentIfs.add(new ArgumentIf(arg.name, arg.index));
        }
    }

    private static class ArgumentIf {
        public String name;
        public int index;
        public String ifArgument;

        public ArgumentIf(String name, int index) {
            this(name, index, null);
        }

        public ArgumentIf(String name, int index, String ifArgument) {
            this.name = name;
            this.index = index;
            this.ifArgument = ifArgument;
        }
    }

    public static class Argument {
        private final String name;
        private int index;
        private Argument parent;

        public Argument(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public Argument(String name, Argument parent) {
            this.name = name;
            this.parent = parent;
            this.index = parent.index + 1;
        }

        public Argument setIndex(int index) {
            this.index = index;
            return this;
        }

        public Argument setParent(Argument parent) {
            this.parent = parent;
            return this;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }

        public Argument getParent() {
            return parent;
        }
    }
}
