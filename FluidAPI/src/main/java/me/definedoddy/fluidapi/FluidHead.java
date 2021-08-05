package me.definedoddy.fluidapi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class FluidHead {
    private final ItemStack head;

    public FluidHead(String owner) {
        head = new ItemStack(Material.PLAYER_HEAD);
        setOwner(owner);
    }

    public FluidHead(Player owner) {
        head = new ItemStack(Material.PLAYER_HEAD);
        setOwner(owner);
    }

    public FluidHead setOwner(String owner) {
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(owner);
        head.setItemMeta(meta);
        return this;
    }

    public FluidHead setOwner(Player owner) {
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwningPlayer(owner);
        head.setItemMeta(meta);
        return this;
    }

    public FluidHead setName(String name) {
        FluidItem.setName(head, name);
        return this;
    }

    public FluidHead setLore(String... lore) {
        FluidItem.setLore(head, lore);
        return this;
    }
}
