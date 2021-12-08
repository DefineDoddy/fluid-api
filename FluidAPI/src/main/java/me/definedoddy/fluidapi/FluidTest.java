package me.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.World;

public final class FluidTest extends FluidPlugin {
    @Override
    public void onEnable() {
        FluidAPI.register(this);
        new FluidTask(() -> new FluidTask(() -> test(1)).async().run().onComplete(() -> test(2)).run(40)).run(20);
        new FluidMessage.Title("TITLE", "SUBTITLE").in(0).out(40).send(FluidMessage.Preset.ALL_PLAYERS);
        new FluidMessage().actionbar("ACTIONBAR").stay(60).send(FluidMessage.Preset.ALL_PLAYERS);
        new FluidMessage("Hello world!").send();
        FluidWorld<?> world = new FluidWorld<>("newWorld") {
            @Override
            public void onWorldCreated(World world) {
                Bukkit.broadcastMessage("World created!");
            }
        }.create();
        new FluidTask(world::delete).run(200);
    }

    public void test(int test) {
        Bukkit.broadcastMessage("test: " + test);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public int getResourceId() {
        return 519499;
    }
}
