package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class FluidListener implements Listener, EventExecutor {
    public FluidListener() {
        Bukkit.getPluginManager().registerEvents(this, FluidAPI.getPlugin());
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) {
        if (listener == this) {
            callEvent(event.getClass());
        }
    }

    private void callEvent(Class<? extends Event> event) {
        for (Method method : getClass().getMethods()) {
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
    }
}
