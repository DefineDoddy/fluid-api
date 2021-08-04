package io.github.definedoddy.fluidapi;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

public class Entities {
    public static <T, Z> Z getDataContainer(Entity entity, String key, PersistentDataType<T, Z> type) {
        return hasContainerValue(entity, key, type) ? entity.getPersistentDataContainer()
                .get(new NamespacedKey(FluidPlugin.getPlugin(), key), type) : null;
    }

    public static <T, Z> void setDataContainer(Entity entity, String key, PersistentDataType<T, Z> type, Z value) {
        entity.getPersistentDataContainer().set(new NamespacedKey(FluidPlugin.getPlugin(), key), type, value);
    }

    public static <T, Z> boolean hasContainerValue(Entity entity, String key, PersistentDataType<T, Z> type) {
        if (entity == null) {
            return false;
        }
        return entity.getPersistentDataContainer().has(new NamespacedKey(FluidPlugin.getPlugin(), key), type);
    }
}
