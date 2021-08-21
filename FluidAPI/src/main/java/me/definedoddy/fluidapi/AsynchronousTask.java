package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;

public abstract class AsynchronousTask {
    private final int taskId;

    public AsynchronousTask() {
        taskId = Bukkit.getScheduler().runTaskAsynchronously(FluidPlugin.getPlugin(), this::run).getTaskId();
    }

    public abstract void run();

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public int getId() {
        return taskId;
    }

    public static abstract class Type<T> {
        private final int taskId;
        private T type;

        public Type() {
            taskId = Bukkit.getScheduler().runTaskAsynchronously(FluidPlugin.getPlugin(), () -> type = run()).getTaskId();
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
