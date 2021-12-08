package me.definedoddy.fluidapi;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

interface MessageBase {
    List<Player> receivers = new ArrayList<>();

    MessageBase send(FluidMessage.Preset... presets);

    MessageBase send(Player... players);

    MessageBase send(Collection<Player> players);

    MessageBase send(CommandSender... senders);
}
