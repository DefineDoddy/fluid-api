package io.github.definedoddy.fluidapi;

import org.bukkit.Bukkit;

public abstract class RepeatingCountTask {
    private final int taskId;

    public RepeatingCountTask(long initialDelay, long repeatingDelay, int count) {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), new Runnable() {
            int loop = 0;
            @Override
            public void run() {
                loop++;
                if (loop >= count) {
                    cancel();
                }
                function();
            }
        }, initialDelay, repeatingDelay);
    }

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public abstract void function();
}
