package me.berrycraft.dungeons.World;

import org.bukkit.*;
import org.bukkit.entity.Player;

public class createWorld {
    public static void printHello(Player p) {
        System.out.println("DUNGEONS CREATED");
        Location loc = new Location(p.getLocation().getWorld(), 0, 200, 0);
        WorldCreator wc = new WorldCreator("World of Dungeons");

        wc.environment(World.Environment.NORMAL);
        wc.type(WorldType.NORMAL);

        wc.createWorld();

        World world = Bukkit.getWorld("World of Dungeons");
        loc = new Location(world, 0, 100, 0);

        p.teleport(loc);
    }
}
