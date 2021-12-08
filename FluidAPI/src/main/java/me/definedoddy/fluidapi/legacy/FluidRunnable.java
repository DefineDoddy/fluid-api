package me.definedoddy.fluidapi.legacy;

public interface FluidRunnable {
    void run();

    default void onComplete() {

    }

    default void onCancel() {

    }
}
