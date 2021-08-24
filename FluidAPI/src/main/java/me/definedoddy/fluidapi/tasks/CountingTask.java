package me.definedoddy.fluidapi.tasks;

import me.definedoddy.fluidapi.FluidPlugin;
import org.bukkit.Bukkit;

public abstract class CountingTask {
    private int taskId;
    private final long initialDelay;
    private final long repeatingDelay;
    private final int count;
    private boolean async;

    public CountingTask(long initialDelay, long repeatingDelay, int count) {
        this.initialDelay = initialDelay;
        this.repeatingDelay = repeatingDelay;
        this.count = count;
        runSync();
    }

    public CountingTask(long initialDelay, long repeatingDelay, int count, boolean async) {
        this.initialDelay = initialDelay;
        this.repeatingDelay = repeatingDelay;
        this.count = count;
        this.async = async;
        if (async) {
            runAsync();
        } else {
            runSync();
        }
    }

    private void runSync() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), new Runnable() {
            int loop = 0;
            @Override
            public void run() {
                loop++;
                if (loop >= count) {
                    cancel();
                    onComplete();
                }
                CountingTask.this.run();
            }
        }, initialDelay, repeatingDelay);
    }

    private void runAsync() {
        taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FluidPlugin.getPlugin(), new Runnable() {
            int loop = 0;
            @Override
            public void run() {
                loop++;
                if (loop >= count) {
                    cancel();
                    onComplete();
                }
                CountingTask.this.run();
            }
        }, initialDelay, repeatingDelay);
    }

    public abstract void run();

    public void onComplete() { };

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public int getId() {
        return taskId;
    }

    public abstract class Type<T> {
        private int taskId;
        private T type;

        public Type() {
            if (CountingTask.this.async) {
                taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FluidPlugin.getPlugin(), this::run, initialDelay, repeatingDelay);
            } else {
                taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), this::run, initialDelay, repeatingDelay);
            }
        }

        public abstract T run();

        public void onComplete() { };

        public void cancel() {
            Bukkit.getScheduler().cancelTask(taskId);
        }

        public int getId() {
            return taskId;
        }

        public T getReturn() {
            return type;
        }

        private void runSync() {
            taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidPlugin.getPlugin(), new Runnable() {
                int loop = 0;
                @Override
                public void run() {
                    loop++;
                    if (loop >= count) {
                        cancel();
                        onComplete();
                    }
                    type = Type.this.run();
                }
            }, initialDelay, repeatingDelay);
        }

        private void runAsync() {
            taskId = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FluidPlugin.getPlugin(), new Runnable() {
                int loop = 0;
                @Override
                public void run() {
                    loop++;
                    if (loop >= count) {
                        cancel();
                        onComplete();
                    }
                    type = Type.this.run();
                }
            }, initialDelay, repeatingDelay);
        }
    }
}
