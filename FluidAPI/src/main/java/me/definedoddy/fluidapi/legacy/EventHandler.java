package me.definedoddy.fluidapi.legacy;

import me.definedoddy.fluidapi.legacy.events.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

class EventHandler {
    private static final PluginManager pluginManager = Bukkit.getServer().getPluginManager();
    private static final Set<Player> prevPlayersOnGround = new HashSet<>();

    public static void init() {
        new FluidListener() {
            public void playerMove(PlayerMoveEvent e) {
                Player player = e.getPlayer();
                if (player.getVelocity().getY() > 0) {
                    double velocity = 0.42F;
                    if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                        velocity += (float) (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F;
                    }
                    if (e.getPlayer().getLocation().getBlock().getType() != Material.LADDER && prevPlayersOnGround.contains(player) &&
                            !player.isOnGround() && Double.compare(player.getVelocity().getY(), velocity) == 0) {
                        pluginManager.callEvent(new PlayerJumpEvent(player));
                    }
                }
                if (player.isOnGround()) {
                    prevPlayersOnGround.add(player);
                } else {
                    prevPlayersOnGround.remove(player);
                }
            }
        };
    }
}
