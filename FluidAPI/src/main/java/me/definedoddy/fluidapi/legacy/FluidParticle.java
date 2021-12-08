package me.definedoddy.fluidapi.legacy;

import me.definedoddy.fluidapi.FluidTask;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class FluidParticle {
    private Particle particle;
    private Location location;
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
        new FluidTask(() -> location.getWorld().spawnParticle(particle, location, count, offset.getX(), offset.getY(), offset.getZ()))
                .repeat(0, 1, ticks);
        return this;
    }

    public FluidParticle setParticle(Particle particle) {
        this.particle = particle;
        return this;
    }

    public FluidParticle setLocation(Location location) {
        this.location = location;
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

    public FluidParticle setOffset(double x, double y, double z) {
        this.offset = new Vector(x, y, z);
        return this;
    }
}
