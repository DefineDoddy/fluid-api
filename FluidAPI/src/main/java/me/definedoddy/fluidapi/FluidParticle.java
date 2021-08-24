package me.definedoddy.fluidapi;

import me.definedoddy.fluidapi.tasks.CountingTask;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class FluidParticle {
    private final Particle particle;
    private final Location location;
    private int count = 1;
    private Vector offset;

    public FluidParticle(Particle particle, Location location) {
        this.particle = particle;
        this.location = location;
    }

    public FluidParticle play() {
        location.getWorld().spawnParticle(particle, location, count, offset.getX(), offset.getY(), offset.getZ());
        return this;
    }

    public FluidParticle play(int ticks) {
        new CountingTask(0, 1, ticks) {
            @Override
            public void run() {
                location.getWorld().spawnParticle(particle, location, count, offset.getX(), offset.getY(), offset.getZ());
            }
        };
        return this;
    }

    public FluidParticle setCount(int count) {
        this.count = count;
        return this;
    }

    public FluidParticle setOffset(Vector offset) {
        this.offset = offset;
        return this;
    }
}
