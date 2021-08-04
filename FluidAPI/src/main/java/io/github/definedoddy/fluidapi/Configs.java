package io.github.definedoddy.fluidapi;

import org.bukkit.configuration.Configuration;

import java.io.File;

public class Configs {
    private static Configuration config;

    public static Configuration saveDefault(String fileName) {
        File configFile = new File(FluidPlugin.getPlugin().getDataFolder(), fileName + ".yml");
        if (!configFile.exists()) {
            FluidPlugin.getPlugin().saveDefaultConfig();
        }
        return reload();
    }

    public static Configuration saveDefault() {
        File configFile = new File(FluidPlugin.getPlugin().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            FluidPlugin.getPlugin().saveDefaultConfig();
        }
        return reload();
    }

    public static Configuration getConfig() {
        return config;
    }

    public static Configuration reload() {
        FluidPlugin.getPlugin().reloadConfig();
        config = FluidPlugin.getPlugin().getConfig();
        return config;
    }
}
