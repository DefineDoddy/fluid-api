package com.github.definedoddy.fluidapi;

import org.bukkit.Bukkit;

public abstract class RepeatingTask {
    private final int taskId;

    public RepeatingTask(long initialDelay, long repeatingDelay) {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), this::function, initialDelay, repeatingDelay);
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public abstract void function();
}
