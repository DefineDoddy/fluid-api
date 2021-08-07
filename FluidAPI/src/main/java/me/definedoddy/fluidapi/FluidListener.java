package me.definedoddy.fluidapi;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public abstract class FluidListener<T extends Event> implements Listener {
    private boolean active;
    private int delay;

    public FluidListener() {
        FluidPlugin.getPlugin().getServer().getPluginManager().registerEvents(this, FluidPlugin.getPlugin());
        active = true;
    }

    @EventHandler
    private void trigger(T event) {
        if (active) {
            if (delay > 0) {
                new DelayedTask(delay) {
                    @Override
                    public void run() {
                        run();
                    }
                };
            } else {
                run(event);
            }
        }
    }

    public abstract void run(T event);

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
