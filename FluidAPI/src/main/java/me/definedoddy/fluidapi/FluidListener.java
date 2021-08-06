package me.definedoddy.fluidapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public abstract class FluidListener<T> implements Listener {
    public FluidListener() {
        FluidPlugin.getPlugin().getServer().getPluginManager().registerEvents(this, FluidPlugin.getPlugin());
    }

    @EventHandler
    public abstract void run(T event);
}
