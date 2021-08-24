package me.definedoddy.fluidapi.tasks;

import me.definedoddy.fluidapi.FluidPlugin;
import org.bukkit.Bukkit;

public abstract class DelayedTask {
    private final int taskId;
    private final long delay;
    private boolean async;

    public DelayedTask(long delay) {
        this.delay = delay;
        taskId = Bukkit.getScheduler().runTaskLater(FluidPlugin.getPlugin(), this::run, delay).getTaskId();
    }

    public DelayedTask(long delay, boolean async) {
        this.delay = delay;
        this.async = async;
        if (async) {
            taskId = Bukkit.getScheduler().runTaskLaterAsynchronously(FluidPlugin.getPlugin(), () -> {
                run();
                onComplete();
            }, delay).getTaskId();
        } else {
            taskId = Bukkit.getScheduler().runTaskLater(FluidPlugin.getPlugin(), () -> {
                run();
                onComplete();
            }, delay).getTaskId();
        }
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
        private final int taskId;
        private T type;

        public Type() {
            if (DelayedTask.this.async) {
                taskId = Bukkit.getScheduler().runTaskLaterAsynchronously(FluidPlugin.getPlugin(), () -> type = run(), delay).getTaskId();
            } else {
                taskId = Bukkit.getScheduler().runTaskLater(FluidPlugin.getPlugin(), () -> type = run(), delay).getTaskId();
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
    }
}
