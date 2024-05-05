package me.berrycraft.dungeons;

import org.bukkit.plugin.java.JavaPlugin;
import me.berrycraft.dungeons.commands.DungeonCommand;

public final class Dungeons extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("dungeon").setExecutor(new DungeonCommand());

    }

    @Override
    public void onDisable() {

    }
}
