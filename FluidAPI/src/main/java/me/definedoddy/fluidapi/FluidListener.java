package me.definedoddy.fluidapi;

import me.definedoddy.fluidapi.tasks.DelayedTask;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

public abstract class FluidListener<T extends Event> implements Listener, EventExecutor {
    private boolean active = true;
    private int delay;
    private Event event;
    private int calls;

    public FluidListener(Class<T> type) {
        FluidPlugin.getPlugin().getServer().getPluginManager().registerEvent(type, this, EventPriority.NORMAL, this, FluidPlugin.getPlugin());
    }

    public void execute(@NotNull Listener listener, @NotNull Event event) {
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

    public FluidListener<T> unregister() {
        event.getHandlers().unregister(this);
        return this;
    }

    public static class Group implements Listener {
        public Group() {
            FluidPlugin.getPlugin().getServer().getPluginManager().registerEvents(this, FluidPlugin.getPlugin());
        }
    }
}
