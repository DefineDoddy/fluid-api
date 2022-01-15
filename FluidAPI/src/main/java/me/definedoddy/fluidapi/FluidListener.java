package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FluidListener implements Listener/*, EventExecutor*/ {
    public FluidListener() {
        Bukkit.getPluginManager().registerEvents(this, FluidAPI.getPlugin());
        //registerEvents();
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

/*    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) {
        Bukkit.broadcastMessage("event: " + event.getClass().getSimpleName());
        callEvent(event.getClass());
        if (listener == this) {
            Bukkit.broadcastMessage("listener is this");
            callEvent(event.getClass());
        }
    }

    private void registerEvents() {
        for (Method method : getClass().getDeclaredMethods()) {
            Class<?>[] types = method.getParameterTypes();
            if (types.length == 1 && types[0].isInstance(Event.class)) {
                Bukkit.getPluginManager().registerEvent((Class<? extends Event>) types[0],
                        this, EventPriority.NORMAL, this, FluidAPI.getPlugin());
            }
        }
    }

    private void callEvent(Class<? extends Event> event) {
        for (Method method : getClass().getDeclaredMethods()) {
            Class<?>[] types = method.getParameterTypes();
            if (types.length == 1 && types[0].isInstance(event)) {
                try {
                    if (Modifier.isStatic(method.getModifiers())) {
                        method.invoke(null, event);
                    } else {
                        method.invoke(FluidListener.this, event);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}
