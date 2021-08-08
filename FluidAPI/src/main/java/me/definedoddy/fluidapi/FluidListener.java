package me.definedoddy.fluidapi;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import java.lang.reflect.InvocationTargetException;

public abstract class FluidListener<T extends Event> implements Listener, EventExecutor {
    private boolean active = true;
    private int delay;
    private Event event;
    private int calls;

    public FluidListener(Class<T> type) {
        FluidPlugin.getPlugin().getServer().getPluginManager().registerEvent(type, this, EventPriority.NORMAL, this, FluidPlugin.getPlugin());
    }

    public void execute(Listener listener, Event event) {
        this.event = event;
        if (active) {
            calls++;
            if (delay > 0) {
                new DelayedTask(delay) {
                    @Override
                    public void run() {
                        FluidListener.this.run();
                    }
                };
            } else {
                run();
            }
        }
    }

    public abstract void run();

    public T getData() {
        return (T)event;
    }

    public int getCalls() {
        return calls;
    }

    public FluidListener<T> setActive(boolean active) {
        this.active = active;
        return this;
    }

    public FluidListener<T> setDelay(int delay) {
        this.delay = delay;
        return this;
    }
}
