package me.definedoddy.fluidapi;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class FluidBlock {
    private final Block block;

    public FluidBlock(Block block) {
        this.block = block;
    }

    public <T, Z> Z getData(String key, PersistentDataType<T, Z> type) {
        return getData(block, key, type);
    }

    public <T, Z> FluidBlock setData(String key, PersistentDataType<T, Z> type, Z value) {
        setData(block, key, type, value);
        return this;
    }

    public <T, Z> boolean hasData(String key, PersistentDataType<T, Z> type) {
        return hasData(block, key, type);
    }

    public static <T, Z> Z getData(Block block, String key, PersistentDataType<T, Z> type) {
        PersistentDataContainer container = ((TileState)block.getState()).getPersistentDataContainer();
        return hasData(block, key, type) ? container.get(new NamespacedKey(FluidPlugin.getPlugin(), key), type) : null;
    }

    public static <T, Z> void setData(Block block, String key, PersistentDataType<T, Z> type, Z value) {
        PersistentDataContainer container = ((TileState)block.getState()).getPersistentDataContainer();
        container.set(new NamespacedKey(FluidPlugin.getPlugin(), key), type, value);
    }

    public static <T, Z> boolean hasData(Block block, String key, PersistentDataType<T, Z> type) {
        if (block == null) {
            return false;
        }
        PersistentDataContainer container = ((TileState)block.getState()).getPersistentDataContainer();
        return container.has(new NamespacedKey(FluidPlugin.getPlugin(), key), type);
    }
}
