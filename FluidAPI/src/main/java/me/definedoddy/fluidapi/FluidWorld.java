package me.definedoddy.fluidapi;

import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FluidWorld {
    private World world;
    private final String name;
    private World.Environment environment;
    private WorldType type;
    private String generation;
    private long seed;
    private boolean hasSeed;
    private boolean allowAnimals = true;
    private boolean allowMonsters = true;
    private final Map<GameRule<?>, Object> gameRules = new HashMap<>();
    private Difficulty difficulty = Difficulty.NORMAL;

    public FluidWorld(String name) {
        this.name = name;
    }

    public FluidWorld environment(World.Environment environment) {
        this.environment = environment;
        return this;
    }

    public FluidWorld type(WorldType type) {
        this.type = type;
        return this;
    }

    public FluidWorld generation(String generation) {
        this.generation = generation;
        return this;
    }

    public FluidWorld seed(long seed) {
        this.seed = seed;
        hasSeed = true;
        return this;
    }

    public FluidWorld animals(boolean allowAnimals) {
        this.allowAnimals = allowAnimals;
        return this;
    }

    public FluidWorld monsters(boolean allowMonsters) {
        this.allowMonsters = allowMonsters;
        return this;
    }

    public FluidWorld gameRule(GameRule<?> gameRule, Object value) {
        gameRules.put(gameRule, value);
        return this;
    }

    public FluidWorld difficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public <T> FluidWorld create() {
        new FluidTask(() -> {
            WorldCreator wc = new WorldCreator(name);
            wc.environment(environment == null ? World.Environment.NORMAL : environment);
            wc.type(type == null ? WorldType.NORMAL : type);
            if (generation != null) wc.generator(generation);
            if (hasSeed) wc.seed(seed);
            world = wc.createWorld();
            world.setSpawnFlags(allowMonsters, allowAnimals);
            for (Map.Entry<GameRule<?>, ?> rule : gameRules.entrySet()) {
                world.setGameRule((GameRule<T>)rule.getKey(), (T)rule.getValue());
            }
            world.setDifficulty(difficulty);
        }).async().run().onComplete(() -> onWorldCreated(world));
        return this;
    }

    public void onWorldCreated(World world) {

    }

    public boolean delete() {
        return delete(world);
    }

    public static boolean delete(World world) {
        unloadWorldData(world);
        Bukkit.unloadWorld(world, false);
        return deleteFiles(world.getWorldFolder());
    }

    private static void unloadWorldData(World world) {
        for (Player player : world.getPlayers()) {
            Location loc = player.getBedSpawnLocation() != null &&
                    !player.getBedSpawnLocation().getWorld().getName().equalsIgnoreCase(world.getName()) ?
                    player.getBedSpawnLocation() : Bukkit.getWorlds().get(0).getSpawnLocation();
            player.teleport(loc);
        }
    }

    private static boolean deleteFiles(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFiles(file);
                } else {
                    file.delete();
                }
            }
        }
        return path.delete();
    }
}
