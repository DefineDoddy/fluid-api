package me.definedoddy.fluidapi;

public interface FluidRunnable {
    void run();

    default void onComplete() {

    }

    default void onCancel() {

    }
}
