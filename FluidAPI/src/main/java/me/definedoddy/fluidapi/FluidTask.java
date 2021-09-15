package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;

public class FluidTask {
    private FluidRunnable runnable;
    private int lastId = -1;
    private boolean async;
    private int lastRepeatingCount;

    public FluidTask(FluidRunnable runnable) {
        this.runnable = runnable;
    }

    public FluidTask setRunnable(FluidRunnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public FluidRunnable getRunnable() {
        return runnable;
    }

    public FluidTask async() {
        return setAsync(true);
    }

    public FluidTask setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public FluidTask run() {
        if (async) {
            lastId = Bukkit.getScheduler().runTask(FluidPlugin.getPlugin(), () -> {
                runnable.run();
                runnable.onComplete();
            }).getTaskId();
        } else {
            lastId = Bukkit.getScheduler().runTaskAsynchronously(FluidPlugin.getPlugin(), () -> {
                runnable.run();
                runnable.onComplete();
            }).getTaskId();
        }
        return this;
    }

    public FluidTask run(long delay) {
        if (async) {
            lastId = Bukkit.getScheduler().runTaskLaterAsynchronously(FluidPlugin.getPlugin(), () -> {
                runnable.run();
                runnable.onComplete();
            }, delay).getTaskId();
        } else {
            lastId = Bukkit.getScheduler().runTaskLater(FluidPlugin.getPlugin(), () -> {
                runnable.run();
                runnable.onComplete();
            }, delay).getTaskId();
        }
        return this;
    }

    public FluidTask repeat(long initialDelay, long repeatingDelay) {
        if (async) {
            lastId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FluidPlugin.getPlugin(), runnable::run, initialDelay, repeatingDelay);
        } else {
            lastId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), runnable::run, initialDelay, repeatingDelay);
        }
        return this;
    }

    public FluidTask repeat(long initialDelay, long repeatingDelay, int count) {
        lastRepeatingCount = 0;
        if (async) {
            lastId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FluidPlugin.getPlugin(), () -> {
                lastRepeatingCount++;
                runnable.run();
                if (lastRepeatingCount >= count) {
                    Bukkit.getScheduler().cancelTask(lastId);
                    runnable.onComplete();
                }
            }, initialDelay, repeatingDelay);
        } else {
            lastId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), () -> {
                lastRepeatingCount++;
                runnable.run();
                if (lastRepeatingCount >= count) {
                    Bukkit.getScheduler().cancelTask(lastId);
                    runnable.onComplete();
                }
            }, initialDelay, repeatingDelay);
        }
        return this;
    }

    public FluidTask cancel() {
        return cancel(lastId);
    }

    public FluidTask cancel(int id) {
        Bukkit.getScheduler().cancelTask(id);
        runnable.onCancel();
        return this;
    }

    public int getLastId() {
        return lastId;
    }

    public int getLastRepeatingCount() {
        return lastRepeatingCount;
    }
}
