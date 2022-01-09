package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;

import java.util.LinkedList;
import java.util.Queue;

public class FluidTask {
    private final Queue<Runner> runners = new LinkedList<>();
    private Runner runner;

    public FluidTask(TaskRunnable runnable) {
        runners.add(new Runner(runnable));
    }

/*    public FluidTask chain(TaskRunnable runnable) {
        runners.add(new Runner(runnable));
        return this;
    }*/

    public FluidTask async() {
        runner().async = true;
        return this;
    }

    public FluidTask sync() {
        runner().async = false;
        return this;
    }

    public FluidTask setAsync(boolean async) {
        runner().async = async;
        return this;
    }

    public FluidTask run() {
        TaskRunnable runnable = getRunnable(Type.RUN_INSTANT);
        if (runner().async) {
            runner().id = Bukkit.getScheduler().runTaskAsynchronously(FluidAPI.getPlugin(), runnable).getTaskId();
        } else {
            runner().id = Bukkit.getScheduler().runTask(FluidAPI.getPlugin(), runnable).getTaskId();
        }
        return this;
    }

    public FluidTask run(long delay) {
        TaskRunnable runnable = getRunnable(Type.RUN_DELAY);
        if (runner().async) {
            runner().id = Bukkit.getScheduler().runTaskLaterAsynchronously(FluidAPI.getPlugin(), runnable, delay).getTaskId();
        } else {
            runner().id = Bukkit.getScheduler().runTaskLater(FluidAPI.getPlugin(), runnable, delay).getTaskId();
        }
        return this;
    }

    public FluidTask repeat(long initialDelay, long repeatingDelay) {
        TaskRunnable runnable = getRunnable(Type.REPEAT_INFINITE);
        if (runner().async) {
            runner().id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FluidAPI.getPlugin(), runnable, initialDelay, repeatingDelay);
        } else {
            runner().id = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidAPI.getPlugin(), runnable, initialDelay, repeatingDelay);
        }
        return this;
    }

    public FluidTask repeat(long initialDelay, long repeatingDelay, int count) {
        TaskRunnable runnable = getRunnable(Type.REPEAT_COUNT, count);
        if (runner().async) {
            runner().id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(FluidAPI.getPlugin(), runnable, initialDelay, repeatingDelay);
        } else {
            runner().id = Bukkit.getScheduler().scheduleSyncRepeatingTask(FluidAPI.getPlugin(), runnable, initialDelay, repeatingDelay);
        }
        return this;
    }

    public FluidTask onComplete(TaskRunnable runnable) {
        runner().onComplete = runnable;
        runner().runnable.onComplete();
        return this;
    }

    public FluidTask onCancel(TaskRunnable runnable) {
        runner().onCancel = runnable;
        runner().runnable.onCancel();
        return this;
    }

    public FluidTask cancel() {
        Runner runner = runners.poll();
        if (runner != null) {
            Bukkit.getScheduler().cancelTask(runner.id);
            if (runner.onCancel != null) {
                runner.onCancel.run();
            }
            runners.poll();
        }
        return this;
    }

    public Runner getCurrentTask() {
        return runner();
    }

    public int getIteration() {
        return runner().currentLoop;
    }

    private void complete() {
        Runner runner = runners.peek();
        if (runner != null && runner.onComplete != null) {
            runner.onComplete.run();
            runners.poll();
        }
    }

    private TaskRunnable getRunnable(Type type, Object... params) {
        if (type == Type.REPEAT_COUNT) {
            return () -> {
                runner().currentLoop++;
                runner().runnable.run();
                if (runner().currentLoop >= (int) params[0]) {
                    Bukkit.getScheduler().cancelTask(runner().id);
                    complete();
                }
            };
        }
        return () -> {
            runner().runnable.run();
            complete();
        };
    }

    private Runner runner() {
        if (runner != null) {
            return runner;
        }
        return runner = runners.peek();
    }

    private enum Type {
        RUN_INSTANT,
        RUN_DELAY,
        REPEAT_INFINITE,
        REPEAT_COUNT,
    }

    public static class Runner {
        TaskRunnable runnable;
        TaskRunnable onComplete;
        TaskRunnable onCancel;

        int currentLoop;
        boolean async;
        int id;

        public Runner(TaskRunnable runnable) {
            this.runnable = runnable;
        }
    }
}
