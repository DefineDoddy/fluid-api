package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;

public abstract class DelayedTask {
    private final int taskId;

    public DelayedTask(long delay) {
        taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(FluidPlugin.getPlugin(), this::run, delay);
    }

    public abstract void run();

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public int getId() {
        return taskId;
    }
}
