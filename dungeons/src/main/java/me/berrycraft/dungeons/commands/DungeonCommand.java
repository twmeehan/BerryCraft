package me.berrycraft.dungeons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.berrycraft.dungeons.World.createWorld;

public class DungeonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        createWorld.printHello((Player) commandSender);
        return true;
    }
}
