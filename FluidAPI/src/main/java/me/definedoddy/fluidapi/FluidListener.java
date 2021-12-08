package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class FluidListener implements Listener {
    public FluidListener() {
        Bukkit.getPluginManager().registerEvents(this, FluidAPI.getPlugin());
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }
}
