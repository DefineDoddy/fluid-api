package me.definedoddy.fluidapi.tasks;

import me.definedoddy.fluidapi.FluidPlugin;
import org.bukkit.Bukkit;

public abstract class AsynchronousTask {
    private final int taskId;

    public AsynchronousTask() {
        taskId = Bukkit.getScheduler().runTaskAsynchronously(FluidPlugin.getPlugin(), () -> {
            run();
            onComplete();
        }).getTaskId();
    }

    public abstract void run();

    public void onComplete() { };

    public void cancel() {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public int getId() {
        return taskId;
    }

    public static abstract class Type<T> {
        private final int taskId;
        private T returnValue;

        public Type() {
            taskId = Bukkit.getScheduler().runTaskAsynchronously(FluidPlugin.getPlugin(), () -> {
                returnValue = Type.this.run();
                onComplete();
            }).getTaskId();
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
            return returnValue;
        }
    }
}
