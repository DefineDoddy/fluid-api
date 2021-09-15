package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class FluidListener implements Listener, EventExecutor {
    private boolean active = true;
    private boolean registered;
    private final HashMap<String, Method> toUnregister = new HashMap<>();

    public FluidListener() {
        registerFromAnnotations();
    }

    public void execute(@NotNull Listener listener, @NotNull Event event) {
        if (toUnregister.size() > 0 && containsListener(event)) {
            event.getHandlers().unregister(this);
            toUnregister.remove(event.getEventName().toLowerCase());
        } else if (active) {
            callEvent(event);
        }
    }

    private boolean containsListener(Event event) {
        for (RegisteredListener listener : event.getHandlers().getRegisteredListeners()) {
            if (listener.getListener() == this) {
                return true;
            }
        }
        return false;
    }

    public FluidListener setActive(boolean active) {
        this.active = active;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void unregister() {
        if (registered) {
            registered = false;
            for (Method method : getEventMethods().keySet()) {
                toUnregister.put(method.getParameters()[0].getName().toLowerCase(), method);
            }
            Bukkit.broadcastMessage(toUnregister.toString());
        }
    }

    public void register() {
        if (!registered) {
            registerFromAnnotations();
        }
    }

    private void registerFromAnnotations() {
        for (Map.Entry<Method, Class<? extends Event>> entry : getEventMethods().entrySet()) {
            EventData data = entry.getKey().getAnnotation(EventData.class);
            EventPriority priority = data != null ? data.priority() : EventPriority.NORMAL;
            FluidPlugin.getPlugin().getServer().getPluginManager().registerEvent(entry.getValue(), this, priority,
                    this, FluidPlugin.getPlugin());
        }
        registered = true;
    }

    private HashMap<Method, Class<? extends Event>> getEventMethods() {
        HashMap<Method, Class<? extends Event>> map = new HashMap<>();
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getParameterCount() > 0 && paramInstanceOf(method)) {
                map.put(method, (Class<? extends Event>)method.getParameters()[0].getType());
            }
        }
        return map;
    };

    private void callEvent(Event event) {
        for (Method method : getClass().getDeclaredMethods()) {
            if (method.getParameterCount() == 1 && paramInstanceOf(method)) {
                EventData data = method.getAnnotation(EventData.class);
                int delay = data != null ? data.delay() : 0;
                if (delay > 0) {
                    new FluidTask(() -> run(method, event)).run(delay);
                } else run(method, event);
            }
        }
    };

    private void run(Method method, Event event) {
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

    private boolean paramInstanceOf(Method method) {
        return method.getGenericParameterTypes()[0].getClass().isInstance(Event.class);
    }
}
