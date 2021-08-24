package me.definedoddy.fluidapi;

import me.definedoddy.fluidapi.tasks.DelayedTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FluidSchematic {
    private String name;
    private final File file;
    private FileConfiguration config = new YamlConfiguration();

    public FluidSchematic(Block point1, Block point2, String name) {
        this.name = name;
        file = new File(FluidPlugin.getPlugin().getDataFolder() + "/schematics/" + name + ".yml");

        int minX = Math.min(point1.getX(), point2.getX());
        int minZ = Math.min(point1.getZ(), point2.getZ());
        int minY = Math.min(point1.getY(), point2.getY());
        int maxX = Math.max(point1.getX(), point2.getX());
        int maxZ = Math.max(point1.getZ(), point2.getZ());
        int maxY = Math.max(point1.getY(), point2.getY());
        Block[][][] blocks = new Block[maxX - minX + 1][maxY - minY + 1][maxZ - minZ + 1];
        List<Inventory> inventories = new ArrayList<>();
        List<CreatureSpawner> spawners = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = point1.getWorld().getBlockAt(x, y, z);
                    blocks[x - minX][y - minY][z - minZ] = block;
                    if (block.getState() instanceof InventoryHolder holder) {
                        if (FluidUtils.getInventoryCount(holder.getInventory()) > 0) {
                            inventories.add(holder.getInventory());
                        }
                    } else if (block.getState() instanceof CreatureSpawner spawner) {
                        spawners.add(spawner);
                    }
                }
            }
        }
        Location point = blocks[0][0][0].getLocation();
        for (Block[][] xAxis : blocks) {
            for (Block[] yAxis : xAxis) {
                for (Block block : yAxis) {
                    Location loc = block.getLocation().subtract(point);
                    config.set("blocks." + loc.getBlockX() + "." + loc.getBlockY() + "." + loc.getBlockZ(),
                            block.getBlockData().getAsString());
                }
            }
        }
        for (Entity entity : point1.getWorld().getNearbyEntities(BoundingBox.of(point1, point2))) {
            if (!(entity instanceof Player)) {
                Location loc = entity.getLocation().subtract(point);
                config.set("entities." + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ(),
                        entity.getType().toString().toUpperCase());
            }
        }
        for (Inventory inventory : inventories) {
            Location loc = inventory.getLocation().subtract(point);
            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                Material type = item == null ? Material.AIR : item.getType();
                int amount = item == null ? 1 : item.getAmount();
                config.set("inventories." + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ() + "." + i,
                        type.toString().toUpperCase() + "," + amount);
            }
        }
        for (CreatureSpawner spawner : spawners) {
            Location loc = spawner.getLocation().subtract(point);
            config.set("spawners." + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ(), spawner.toString().toUpperCase());
        }
    }

    private FluidSchematic(String name) {
        this.name = name;
        file = new File(FluidPlugin.getPlugin().getDataFolder() + "/schematics/" + name + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public static FluidSchematic get(String name) {
        return new FluidSchematic(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Configuration getConfig() {
        return config;
    }

    public void save(String name) {
        try {
            config.save(new File(FluidPlugin.getPlugin().getDataFolder() + "/schematics/" + name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        reload();
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        reload();
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public enum AnimType {
        LAYER,
        STEP,
        NONE
    }

    public void paste(Location location) {
        paste(location, AnimType.LAYER, 2);
    }

    public void paste(Location location, AnimType animType) {
        paste(location, animType, animType == AnimType.STEP ? 1 : 2);
    }

    public void paste(Location location, AnimType animType, int interval) {
        ConfigurationSection blocksX = config.getConfigurationSection("blocks");
        int count = 0;
        int finishCount = 0;
        for (String x : blocksX.getKeys(false)) {
            ConfigurationSection blocksY = config.getConfigurationSection("blocks." + x);
            for (String y : blocksY.getKeys(false)) {
                ConfigurationSection blocksZ = config.getConfigurationSection("blocks." + x + "." + y);
                if (animType == AnimType.LAYER && Integer.parseInt(y) > finishCount) {
                    finishCount = Integer.parseInt(y);
                }
                for (String z : blocksZ.getKeys(false)) {
                    String blockData = config.getString("blocks." + x + "." + y + "." + z);
                    if (getMaterial(blockData) != Material.SPAWNER) {
                        if (animType == AnimType.LAYER || animType == AnimType.STEP) {
                            new DelayedTask((long) count * interval) {
                                @Override
                                public void run() {
                                    Location loc = location.clone().add(new Vector(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)));
                                    placeBlock(blockData, loc);
                                }
                            };
                        } else {
                            Location loc = location.clone().add(new Vector(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z)));
                            placeBlock(blockData, loc);
                        }
                    }
                }
                if (animType == AnimType.LAYER || animType == AnimType.STEP) {
                    count++;
                    if (animType == AnimType.STEP) {
                        finishCount++;
                    }
                }
            }
            if (animType == AnimType.LAYER) {
                count = 0;
            }
        }
        new DelayedTask((long) finishCount * interval) {
            @Override
            public void run() {
                ConfigurationSection spawners = config.getConfigurationSection("spawners");
                if (spawners != null) {
                    for (String loc : spawners.getKeys(false)) {
                        String[] locSplit = loc.split(",");
                        EntityType type = EntityType.valueOf(spawners.get(loc).toString());
                        Location newLoc = location.clone().add(new Vector(Integer.parseInt(locSplit[0]),
                                Integer.parseInt(locSplit[1]), Integer.parseInt(locSplit[2])));
                        setSpawner(type, newLoc);
                    }
                }
                ConfigurationSection entities = config.getConfigurationSection("entities");
                if (entities != null) {
                    for (String loc : entities.getKeys(false)) {
                        String[] locSplit = loc.split(",");
                        EntityType type = EntityType.valueOf(config.getString("entities." + loc));
                        Location newLoc = location.clone().add(new Vector(Integer.parseInt(locSplit[0]),
                                Integer.parseInt(locSplit[1]), Integer.parseInt(locSplit[2])));
                        try {
                            newLoc.getWorld().spawnEntity(getCenter(newLoc), type);
                        } catch (Exception ignored) { }
                    }
                }
                ConfigurationSection inventories = config.getConfigurationSection("inventories");
                if (inventories != null) {
                    for (String loc : inventories.getKeys(false)) {
                        String[] locSplit = loc.split(",");
                        List<ItemStack> contents = new ArrayList<>();
                        for (Object item : inventories.getConfigurationSection(loc).getValues(false).values()) {
                            String string = item.toString();
                            String[] itemSplit = string.split(",");
                            contents.add(new ItemStack(Material.valueOf(itemSplit[0]), Integer.parseInt(itemSplit[1])));
                        }
                        Location newLoc = location.clone().add(new Vector(Integer.parseInt(locSplit[0]),
                                Integer.parseInt(locSplit[1]), Integer.parseInt(locSplit[2])));
                        setInventory(contents.toArray(new ItemStack[0]), newLoc);
                    }
                }
            }
        };
    }

    private Location getCenter(Location loc) {
        return new Location(loc.getWorld(), getRelativeCoord(loc.getBlockX()),
                getRelativeCoord(loc.getBlockY()), getRelativeCoord(loc.getBlockZ()));
    }

    private double getRelativeCoord(int i) {
        double d = i;
        d = d < 0 ? d - 0.5F : d + 0.5F;
        return d;
    }

    private void placeBlock(String data, Location location) {
        if (data != null) {
            Block block = location.getBlock();
            Material material = getMaterial(data);
            if (material != null && material != Material.AIR) {
                block.setType(material);
            }
            if (data.contains("[")) {
                block.setBlockData(FluidPlugin.getPlugin().getServer().createBlockData(data));
            }
            block.getState().update(true);
        }
    }

    private Material getMaterial(String data) {
        return Material.getMaterial(data.replace("minecraft:", "").toUpperCase());
    }

    private void setInventory(ItemStack[] contents, Location location) {
        if (contents != null) {
            Block block = location.getBlock();
            if (block.getState() instanceof InventoryHolder holder) {
                holder.getInventory().setContents(contents);
            }
        }
    }

    private void setSpawner(EntityType type, Location location) {
        Block block = location.getBlock();
        block.setType(Material.SPAWNER);
        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        spawner.setSpawnedType(type);
        spawner.update();
    }
}
