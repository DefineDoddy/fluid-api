package me.definedoddy.fluidapi;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

public class FluidEntity {
    private final Entity entity;

    public FluidEntity(Entity entity) {
        this.entity = entity;
    }

    public <T, Z> Z getData(String key, PersistentDataType<T, Z> type) {
        return getData(entity, key, type);
    }

    public <T, Z> FluidEntity setData(String key, PersistentDataType<T, Z> type, Z value) {
        setData(entity, key, type, value);
        return this;
    }

    public <T, Z> boolean hasData(String key, PersistentDataType<T, Z> type) {
        return hasData(entity, key, type);
    }

    public static <T, Z> Z getData(Entity entity, String key, PersistentDataType<T, Z> type) {
        return hasData(entity, key, type) ? entity.getPersistentDataContainer()
                .get(new NamespacedKey(FluidPlugin.getPlugin(), key), type) : null;
    }

    public static <T, Z> void setData(Entity entity, String key, PersistentDataType<T, Z> type, Z value) {
        entity.getPersistentDataContainer().set(new NamespacedKey(FluidPlugin.getPlugin(), key), type, value);
    }

    public static <T, Z> boolean hasData(Entity entity, String key, PersistentDataType<T, Z> type) {
        if (entity == null) {
            return false;
        }
        return entity.getPersistentDataContainer().has(new NamespacedKey(FluidPlugin.getPlugin(), key), type);
    }
}
