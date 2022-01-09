package me.definedoddy.fluidapi;

public interface TaskRunnable extends Runnable {
    FluidTask task = null;

    default void cancel() {
        task.cancel();
    }

    default void onCancel() {};

    default void onComplete() {};

    default int getIteration() {
        return task.getCurrentTask().currentLoop;
    }
}
