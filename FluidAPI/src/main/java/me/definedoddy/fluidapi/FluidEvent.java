package me.definedoddy.fluidapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public abstract class FluidEvent<T> implements Listener {
    public FluidEvent() {
        FluidPlugin.getPlugin().getServer().getPluginManager().registerEvents(this, FluidPlugin.getPlugin());
    }

    @EventHandler
    public abstract void run(T event);
}
