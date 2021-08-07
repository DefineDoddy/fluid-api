package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;

public abstract class CountingTask {
    private final int taskId;

    public CountingTask(long initialDelay, long repeatingDelay, int count) {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), new Runnable() {
            int loop = 0;
            @Override
            public void run() {
                loop++;
                if (loop >= count) {
                    cancel();
                }
                CountingTask.this.run();
            }
        }, initialDelay, repeatingDelay);
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public abstract void run();
}
