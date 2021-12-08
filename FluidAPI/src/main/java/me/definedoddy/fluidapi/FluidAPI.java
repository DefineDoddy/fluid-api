package me.definedoddy.fluidapi;

/**The main API class used for registering plugins*/
public class FluidAPI {
    private static FluidPlugin plugin;

    /**Registers a plugin to the API*/
    public static void register(FluidPlugin plugin) {
        FluidAPI.plugin = plugin;
        EventManager.init();
    }

    /**@return the plugin instance registered to the API*/
    public static FluidPlugin getPlugin() {
        return plugin;
    }
}
