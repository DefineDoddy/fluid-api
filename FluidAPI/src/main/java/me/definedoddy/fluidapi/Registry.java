package me.definedoddy.fluidapi;

import java.util.ArrayList;
import java.util.List;

class Registry {
    public static List<FluidListener> listenerQueue = new ArrayList<>();

    public static void register() {
        for (FluidListener listener : listenerQueue) {
            listener.register();
        }
        listenerQueue.clear();
    }
}
