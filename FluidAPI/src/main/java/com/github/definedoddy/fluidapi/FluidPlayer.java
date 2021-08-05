package com.github.definedoddy.fluidapi;

import org.bukkit.entity.Player;

public class FluidPlayer {
    private Player player;

    public FluidPlayer(Player player) {
        this.player = player;
    }

    public FluidPlayer setPlayer(Player player) {
        this.player = player;
        return this;
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
