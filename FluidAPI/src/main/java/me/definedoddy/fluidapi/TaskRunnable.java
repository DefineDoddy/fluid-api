package me.definedoddy.fluidapi;

public interface TaskRunnable extends Runnable {
    FluidTask task = null;

    default void cancel() {
        task.cancel();
    }

    default void onCancel() {
        task.onCancel(this);
    }

    default void onComplete() {
        task.onComplete(this);
    }

    default int getIteration() {
        return task.getCurrentTask().currentLoop;
    }
}
