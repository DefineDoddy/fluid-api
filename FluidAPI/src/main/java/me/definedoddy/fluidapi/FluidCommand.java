package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
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
    private static final List<Object> classes = new ArrayList<>();
    private final List<CommandArgument> arguments = new ArrayList<>();
    private static FluidCommand instance;
    private boolean instantiated;

    public FluidCommand(String name) {
        FluidPlugin.getPlugin().getCommand(name).setExecutor(this);
        instantiated = true;
    }

    private FluidCommand() {

    }

    public static void register(Object... classes) {
        FluidCommand.classes.addAll(Arrays.asList(classes));
        if (instance == null) {
            instance = new FluidCommand() {
                @Override
                public boolean run(CommandInfo info) {
                    return true;
                }
            };
        }
        for (Method method : getHookMethods(CommandHook.class)) {
            CommandHook annotation = method.getAnnotation(CommandHook.class);
            FluidPlugin.getPlugin().getCommand(annotation.value()).setExecutor(instance);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (instantiated) {
            return run(new CommandInfo(sender, cmd, args));
        }
        return callCommandMethods(getHookMethods(CommandHook.class), new CommandInfo(sender, cmd, args));
    }

    public abstract boolean run(CommandInfo info);

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
        List<Object> tabArgs = getTabArguments(getHookMethods(CommandTabHook.class), new TabInfo(sender, cmd, args));
        if (arguments.size() > 0) {
            tabArgs.addAll(arguments);
        }
        for (Object arg : tabArgs) {
            if (arg instanceof CommandArgument argument) {
                Bukkit.broadcastMessage("instance of argument");
                if (args.length == argument.index) {
                    if (argument.parent != null) {
                        if (args[argument.index - 2].equalsIgnoreCase(argument.parent.name) && !argument.exclusions.contains((Player) sender)) {
                            toSend.add(argument.name);
                        }
                    } else if (!argument.exclusions.contains((Player) sender)) {
                        toSend.add(argument.name);
                    }
                }
            }
        }
        return toSend;
    }

    private static List<Method> getHookMethods(Class<? extends Annotation> type) {
        List<Method> methods = new ArrayList<>();
        for (Object object : classes) {
            for (Method method : object.getClass().getDeclaredMethods()) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(type)) {
                    methods.add(method);
                }
            }
        }
        return methods;
    }

    private static boolean callCommandMethods(List<Method> methods, CommandInfo info) {
        boolean value = true;
        for (Method method : methods) {
            CommandHook annotation = method.getAnnotation(CommandHook.class);
            if (annotation.value().equals(info.getCommand().getLabel())) {
                try {
                    if (method.getParameterCount() == 1 && method.getParameters()[0].getType() == CommandInfo.class) {
                        if (Modifier.isStatic(method.getModifiers())) {
                            if (method.getReturnType() == Boolean.class) {
                                value = (boolean) method.invoke(null, info);
                            } else {
                                method.invoke(null, info);
                            }
                        } else {
                            if (method.getReturnType() == Boolean.class) {
                                if (JavaPlugin.class.isAssignableFrom(method.getDeclaringClass())) {
                                    value = (boolean) method.invoke(FluidPlugin.getPlugin(), info);
                                } else {
                                    value = (boolean) method.invoke(method.getDeclaringClass().newInstance(), info);
                                }
                            } else {
                                if (JavaPlugin.class.isAssignableFrom(method.getDeclaringClass())) {
                                    method.invoke(FluidPlugin.getPlugin(), info);
                                } else {
                                    method.invoke(method.getDeclaringClass().newInstance(), info);
                                }
                            }
                        }
                    } else {
                        if (Modifier.isStatic(method.getModifiers())) {
                            if (method.getReturnType() == Boolean.class) {
                                value = (boolean) method.invoke(null);
                            } else {
                                method.invoke(null);
                            }
                        } else {
                            if (method.getReturnType() == Boolean.class) {
                                if (JavaPlugin.class.isAssignableFrom(method.getDeclaringClass())) {
                                    value = (boolean) method.invoke(FluidPlugin.getPlugin());
                                } else {
                                    value = (boolean) method.invoke(method.getDeclaringClass().newInstance());
                                }
                            } else {
                                if (JavaPlugin.class.isAssignableFrom(method.getDeclaringClass())) {
                                    method.invoke(FluidPlugin.getPlugin());
                                } else {
                                    method.invoke(method.getDeclaringClass().newInstance());
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    private static List<Object> getTabArguments(List<Method> methods, TabInfo info) {
        List<Object> list = new ArrayList<>();
        for (Method method : methods) {
            CommandTabHook annotation = method.getAnnotation(CommandTabHook.class);
            if (annotation.value().equals(info.getCommand().getName())) {
                try {
                    if (method.getParameterCount() == 1 && method.getParameters()[0].getType() == TabInfo.class) {
                        if (Modifier.isStatic(method.getModifiers())) {
                            if (method.getReturnType() == FluidListener.class) {
                                list = ((Group) method.invoke(method.invoke(null, info))).toList();
                            } else {
                                method.invoke(null, info);
                            }
                        } else {
                            if (method.getReturnType() == FluidListener.class) {
                                if (JavaPlugin.class.isAssignableFrom(method.getDeclaringClass())) {
                                    list = ((Group) method.invoke(method.invoke(FluidPlugin.getPlugin(), info))).toList();
                                } else {
                                    list = ((Group) method.invoke(method.invoke(method.getDeclaringClass().newInstance(), info))).toList();
                                }
                            } else {
                                if (JavaPlugin.class.isAssignableFrom(method.getDeclaringClass())) {
                                    method.invoke(FluidPlugin.getPlugin(), info);
                                } else {
                                    method.invoke(method.getDeclaringClass().newInstance(), info);
                                }
                            }
                        }
                    } else {
                        if (Modifier.isStatic(method.getModifiers())) {
                            if (method.getReturnType() == FluidListener.class) {
                                list = ((Group) method.invoke(method.invoke(null))).toList();
                            } else {
                                method.invoke(null);
                            }
                        } else {
                            if (method.getReturnType() == FluidListener.class) {
                                if (JavaPlugin.class.isAssignableFrom(method.getDeclaringClass())) {
                                    list = ((Group) method.invoke(FluidPlugin.getPlugin())).toList();
                                } else {
                                    list = ((Group) method.invoke(method.getDeclaringClass().newInstance())).toList();
                                }
                            } else {
                                if (JavaPlugin.class.isAssignableFrom(method.getDeclaringClass())) {
                                    method.invoke(FluidPlugin.getPlugin());
                                } else {
                                    method.invoke(method.getDeclaringClass().newInstance());
                                }
                            }
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
