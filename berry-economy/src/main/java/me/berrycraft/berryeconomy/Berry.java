package me.berrycraft.berryeconomy;

import me.berrycraft.berryeconomy.commands.GiveCommand;
import me.berrycraft.berryeconomy.items.CustomItemEventHandler;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Main class of Berry Economy plugin
 *
 * Berry Economy is one of Berrycraft's main plugins that
 * handles everything about custom items and the in-game economy
 * such as the auction house and trading. (*Spell books may be handled
 * by another plugin)
 */
public final class Berry extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new CustomItemEventHandler(), this);
        getServer().getPluginManager().registerEvents(new GiveCommand(), this);
    }

    @Override
    public void onDisable() {


    }

}
