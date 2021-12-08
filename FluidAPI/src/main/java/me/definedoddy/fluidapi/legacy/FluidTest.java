package me.definedoddy.fluidapi.legacy;

import me.definedoddy.fluidapi.FluidTask;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public final class FluidTest extends JavaPlugin {
    @Override
    public void onEnable() {
        FluidPlugin.register(this, false);
        new FluidMessage("&aPlugin enabled!").usePrefix().send();
/*        ItemStack giveItem = new FluidItem(Material.STONE) {
            @Override
            public void onInteract(PlayerInteractEvent e) {
                e.getPlayer().sendMessage("interacted");
            }
        }.setName("#00F695Stone").registerListeners(FluidItem.ListenerType.INTERACT).build();
        Bukkit.getPlayer("DefineDoddy").getInventory().addItem(giveItem);*/
        new FluidListener() {
            public void chat(AsyncPlayerChatEvent e) {
                e.setMessage(FluidMessage.toColor(e.getMessage()));
            }
        };
    }

    @Override
    public void onDisable() {
        new FluidMessage("&cPlugin disabled!").usePrefix().send();
    }

    @CommandHook("gui")
    public void openGui() {
        FluidGUI inv = new FluidGUI(9).setTitle("inv").canAddItems();
        FluidGUI.Item item = new FluidGUI.Item(Material.DIRT, 6) {
            @Override
            public void onClick() {
                if (getClickType() == ClickType.LEFT) {
                    getClicker().sendMessage("left click");
                } else if (getClickType() == ClickType.SHIFT_RIGHT) {
                    getClicker().sendMessage("shift right click");
                }
            }
        };
        Bukkit.getPlayer("DefineDoddy").openInventory(inv.addItems(Map.of(0, item)).build());
    }

    @CommandHook("gui")
    public Group guiCommandTab() {
        CommandArgument arg1 = new CommandArgument("arg1", 1);
        return new Group(arg1, new CommandArgument("arg1-1", arg1), new CommandArgument("arg2", 1));
    }

    @CommandHook("hologram")
    public void spawnHologram(CommandInfo info) {
        FluidHologram holo = new FluidHologram("This is line 1", "This is line 2", "This is another line")
                .place(((Player) info.getSender()).getEyeLocation());
        new me.definedoddy.fluidapi.FluidTask(() -> holo.addLines("Another lineeeee", "Moreeeee liness")).run(60);
        new FluidTask(holo::remove).run(100);
    }
}
