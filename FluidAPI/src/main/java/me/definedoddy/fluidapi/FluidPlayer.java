package me.definedoddy.fluidapi;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class FluidPlayer {
    private final Player player;

    public FluidPlayer(Player player) {
        this.player = player;
    }

    public <T, Z> Z getData(String key, PersistentDataType<T, Z> type) {
        return getData(player, key, type);
    }

    public <T, Z> FluidPlayer setData(String key, PersistentDataType<T, Z> type, Z value) {
        setData(player, key, type, value);
        return this;
    }

    public <T, Z> boolean hasData(String key, PersistentDataType<T, Z> type) {
        return hasData(player, key, type);
    }

    public FluidPlayer removeData(String key) {
        removeData(player, key);
        return this;
    }

    public static <T, Z> Z getData(Player player, String key, PersistentDataType<T, Z> type) {
        return hasData(player, key, type) ? player.getPersistentDataContainer()
                .get(new NamespacedKey(FluidPlugin.getPlugin(), key), type) : null;
    }

    public static <T, Z> Player setData(Player player, String key, PersistentDataType<T, Z> type, Z value) {
        player.getPersistentDataContainer().set(new NamespacedKey(FluidPlugin.getPlugin(), key), type, value);
        return player;
    }

    public static <T, Z> boolean hasData(Player player, String key, PersistentDataType<T, Z> type) {
        if (player == null) {
            return false;
        }
        return player.getPersistentDataContainer().has(new NamespacedKey(FluidPlugin.getPlugin(), key), type);
    }

    public static Player removeData(Player player, String key) {
        player.getPersistentDataContainer().remove(new NamespacedKey(FluidPlugin.getPlugin(), key));
        return player;
    }

    public FluidPlayer runCmdAsOp(String command) {
        runCmdAsOp(player, command);
        return this;
    }

    public static void runCmdAsOp(Player player, String command) {
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            player.performCommand(command);
        } finally {
            player.setOp(isOp);
        }
    }
}
