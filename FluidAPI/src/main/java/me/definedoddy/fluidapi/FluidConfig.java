package me.definedoddy.fluidapi;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FluidConfig {
    private static Configuration mainConfig;
    private final String name;
    private final YamlConfiguration config;

    public static Configuration saveMainConfig() {
        return saveMainConfig("config");
    }

    public static Configuration saveMainConfig(String fileName) {
        File configFile = new File(FluidPlugin.getPlugin().getDataFolder(), fileName + ".yml");
        if (!configFile.exists()) {
            FluidPlugin.getPlugin().saveDefaultConfig();
        }
        return reload();
    }

    public static Configuration getMainConfig() {
        return mainConfig;
    }

    public static YamlConfiguration getConfig(String fileName) {
        return YamlConfiguration.loadConfiguration(new File(FluidPlugin.getPlugin().getDataFolder(), fileName + ".yml"));
    }

    public static Configuration reload() {
        FluidPlugin.getPlugin().reloadConfig();
        return mainConfig = FluidPlugin.getPlugin().getConfig();
    }

    public FluidConfig(String name) {
        this.config = new YamlConfiguration();
        this.name = name;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public FluidConfig save() {
        return save("");
    }

    public FluidConfig save(String subfolder) {
        try {
            config.save(new File(FluidPlugin.getPlugin().getDataFolder(), subfolder + name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
