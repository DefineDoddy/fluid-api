package me.definedoddy.fluidapi;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluidConfig {
    private static Configuration mainConfig;
    private static final Map<String, YamlConfiguration> configs = new HashMap<>();

    private final String name;
    private final YamlConfiguration config;

    public static Configuration saveDefault() {
        return saveDefault("config");
    }

    public static Configuration saveDefault(String fileName) {
        File configFile = new File(FluidPlugin.getPlugin().getDataFolder(), fileName + ".yml");
        if (!configFile.exists()) {
            FluidPlugin.getPlugin().saveDefaultConfig();
        }
        return reload();
    }

    public static Configuration getMainConfig() {
        return mainConfig;
    }

    public static YamlConfiguration getConfig(String name) {
        return configs.get(name);
    }

    public static List<YamlConfiguration> getConfigs() {
        return configs.values().stream().toList();
    }

    public static Configuration reload() {
        FluidPlugin.getPlugin().reloadConfig();
        return mainConfig = FluidPlugin.getPlugin().getConfig();
    }

    public FluidConfig(String name) {
        YamlConfiguration config = new YamlConfiguration();
        this.config = config;
        this.name = name;
        configs.put(name, config);
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
