package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;

public abstract class RepeatingTask {
    private final int taskId;
    private final long initialDelay;
    private final long repeatingDelay;
    private boolean async;

    public RepeatingTask(long initialDelay, long repeatingDelay) {
        this.initialDelay = initialDelay;
        this.repeatingDelay = repeatingDelay;
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), this::run, initialDelay, repeatingDelay);
    }

    public RepeatingTask(long initialDelay, long repeatingDelay, boolean async) {
        this.initialDelay = initialDelay;
        this.repeatingDelay = repeatingDelay;
        this.async = async;
        taskId = async ? Bukkit.getScheduler().scheduleAsyncRepeatingTask(FluidPlugin.getPlugin(), this::run, initialDelay, repeatingDelay)
                : Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), this::run, initialDelay, repeatingDelay);
    }

    public abstract void run();

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public int getId() {
        return taskId;
    }

    public abstract class Type<T> {
        private final int taskId;
        private T type;

        public Type() {
            if (RepeatingTask.this.async) {
                taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FluidPlugin.getPlugin(), () -> type = run(), initialDelay, repeatingDelay);
            } else {
                taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), () -> type = run(), initialDelay, repeatingDelay);
            }
        }

        public abstract T run();

        public void cancel() {
            Bukkit.getScheduler().cancelTask(taskId);
        }

        public int getId() {
            return taskId;
        }

        public T getReturn() {
            return type;
        }
    }
}
