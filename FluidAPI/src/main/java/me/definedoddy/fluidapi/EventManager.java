package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

class EventManager implements Listener {
    public static void init() {
        Bukkit.getPluginManager().registerEvents(new EventManager(), FluidAPI.getPlugin());
    }
}
