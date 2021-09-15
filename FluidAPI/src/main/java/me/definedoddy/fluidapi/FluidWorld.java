package me.definedoddy.fluidapi;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.generator.WorldInfo;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class FluidWorld<T> {
    private World world;
    private String name;
    private World.Environment environment;
    private WorldType type;
    private String generation;
    private Generator generator;
    private long seed;
    private boolean hasSeed;
    private boolean allowAnimals = true;
    private boolean allowMonsters = true;
    private final Map<GameRule<T>, T> gameRules = new HashMap<>();
    private Difficulty difficulty = Difficulty.NORMAL;

    public FluidWorld(String name) {
        this.name = name;
    }

    public FluidWorld<T> setName(String name) {
        this.name = name;
        return this;
    }

    public FluidWorld<T> setEnvironment(World.Environment environment) {
        this.environment = environment;
        return this;
    }

    public FluidWorld<T> setType(WorldType type) {
        this.type = type;
        return this;
    }

    public FluidWorld<T> setGeneration(String generation) {
        this.generation = generation;
        return this;
    }

    public FluidWorld<T> setSeed(long seed) {
        this.seed = seed;
        hasSeed = true;
        return this;
    }

    public FluidWorld<T> setGenerator(Generator generator) {
        this.generator = generator;
        return this;
    }

    public FluidWorld<T> setAllowAnimals(boolean allowAnimals) {
        this.allowAnimals = allowAnimals;
        return this;
    }

    public FluidWorld<T> setAllowMonsters(boolean allowMonsters) {
        this.allowMonsters = allowMonsters;
        return this;
    }

    public FluidWorld<T> addGameRule(GameRule<T> gameRule, T value) {
        gameRules.put(gameRule, value);
        return this;
    }

    public FluidWorld<T> setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public World create() {
        WorldCreator wc = new WorldCreator(name);
        wc.environment(environment == null ? World.Environment.NORMAL : environment);
        wc.type(type == null ? WorldType.NORMAL : type);
        if (generation != null) {
            wc.generator(generation);
        }
        if (hasSeed) {
            wc.seed(seed);
        }
        if (generator != null) {
            wc.generator(generator.generator);
        }
        world = wc.createWorld();
        world.setSpawnFlags(allowMonsters, allowAnimals);
        for (Map.Entry<GameRule<T>, T> rule : gameRules.entrySet()) {
            world.setGameRule(rule.getKey(), rule.getValue());
        }
        world.setDifficulty(difficulty);
        return world;
    }

    public <A> A create(Class<A> returnClass) {
        if (returnClass == World.class) {
            return (A)(world == null ? create() : world);
        } else if (returnClass == FluidWorld.class) {
            world = create();
            return (A)this;
        }
        return null;
    }

    public World getWorld() {
        return world;
    }

    public void teleportPlayer(Player player) {
        player.teleport(world.getSpawnLocation());
    }

    public void teleportPlayer(Player player, Vector location) {
        player.teleport(new Location(world, location.getX(), location.getY(), location.getZ()));
    }

    public static boolean delete(World world) {
        Bukkit.unloadWorld(world, false);
        return deleteFiles(world.getWorldFolder());
    }

    public boolean delete() {
        for (Player player : world.getPlayers()) {
            Location loc = player.getBedSpawnLocation() != null ?
                    player.getBedSpawnLocation() : Bukkit.getWorlds().get(0).getSpawnLocation();
            player.teleport(loc);
        }
        Bukkit.unloadWorld(world, false);
        return deleteFiles(world.getWorldFolder());
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

    public class Generator {
        private final ChunkGenerator generator;
        private final World world = FluidWorld.this.world;
        private final WorldInfo info;
        private final Random random = new Random();

        public Generator() {
            info = new WorldInfo() {
                @Override
                public String getName() {
                    return world.getName();
                }

                @Override
                public UUID getUID() {
                    return world.getUID();
                }

                @Override
                public World.Environment getEnvironment() {
                    return world.getEnvironment();
                }

                @Override
                public long getSeed() {
                    return world.getSeed();
                }

                @Override
                public int getMinHeight() {
                    return world.getMinHeight();
                }

                @Override
                public int getMaxHeight() {
                    return world.getMaxHeight();
                }
            };

            generator = new ChunkGenerator() {
                @Override
                public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull ChunkData chunkData) {
                    super.generateNoise(worldInfo, random, x, z, chunkData);
                }
            };
        }

        private ChunkData getChunkData(Chunk chunk) {
            return new ChunkData() {
                @Override
                public int getMinHeight() {
                    return chunk.getWorld().getMinHeight();
                }

                @Override
                public int getMaxHeight() {
                    return chunk.getWorld().getMaxHeight();
                }

                @Override
                public Biome getBiome(int x, int y, int z) {
                    return chunk.getChunkSnapshot().getBiome(x, y, z);
                }

                @Override
                public void setBlock(int x, int y, int z, @NotNull Material material) {

                }

                @Override
                public void setBlock(int x, int y, int z, @NotNull MaterialData material) {

                }

                @Override
                public void setBlock(int x, int y, int z, @NotNull BlockData blockData) {

                }

                @Override
                public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull Material material) {

                }

                @Override
                public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull MaterialData material) {

                }

                @Override
                public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull BlockData blockData) {

                }

                @Override
                public Material getType(int x, int y, int z) {
                    return chunk.getWorld().getType(new Location(world, x, y, z));
                }

                @Override
                public MaterialData getTypeAndData(int x, int y, int z) {
                    return chunk.getWorld().getBlockAt(x, y, z).getState().getData();
                }

                @Override
                public BlockData getBlockData(int x, int y, int z) {
                    return chunk.getWorld().getBlockAt(x, y, z).getBlockData();
                }

                @Override
                public byte getData(int x, int y, int z) {
                    return chunk.getWorld().getBlockAt(x, y, z).getData();
                }
            };
        }

        public Generator surface(int x, int z, Chunk chunk) {
            generator.generateSurface(info, random, x, z, getChunkData(chunk));
            return this;
        }

        public Generator bedrock(int x, int z, Chunk chunk) {
            generator.generateBedrock(info, random, x, z, getChunkData(chunk));
            return this;
        }

        public Generator caves(int x, int z, Chunk chunk) {
            generator.generateCaves(info, random, x, z, getChunkData(chunk));
            return this;
        }
    }
}
