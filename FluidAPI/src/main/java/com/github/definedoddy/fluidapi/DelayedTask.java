package com.github.definedoddy.fluidapi;

import org.bukkit.Bukkit;

public abstract class DelayedTask {
    private final int taskId;

    public DelayedTask(long delay) {
        taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(FluidPlugin.getPlugin(), this::function, delay);
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public abstract void function();
}
