package me.berrycraft.berryeconomy;

import de.tr7zw.nbtapi.NBTItem;
import me.berrycraft.berryeconomy.items.CustomItemEventHandler;
import me.berrycraft.berryeconomy.items.Rainbowberry;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public final class Berry extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new CustomItemEventHandler(), this);
        getServer().getPluginManager().registerEvents(new GiveCommand(), this);
    }

    @Override
    public void onDisable() {

        //getServer().getPlayer("Doctrinal").setItemInHand(new Rainbowberry());

    }

}
