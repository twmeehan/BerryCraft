package me.berrycraft.berryeconomy;

import me.berrycraft.berryeconomy.auction.AuctionEventHandler;
import me.berrycraft.berryeconomy.auction.windows.AuctionWindow;
import me.berrycraft.berryeconomy.auction.windows.elements.Price;
import me.berrycraft.berryeconomy.auction.windows.elements.Search;
import me.berrycraft.berryeconomy.commands.AuctionCommand;
import me.berrycraft.berryeconomy.commands.ExchangeCommand;
import me.berrycraft.berryeconomy.commands.GiveCommand;
import me.berrycraft.berryeconomy.items.CustomItemEventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.A;

/*
 * Main class of Berry Economy plugin
 *
 * Berry Economy is one of Berrycraft's main plugins that
 * handles everything about custom items and the in-game economy
 * such as the auction house and trading. (*Spell books may be handled
 * by another plugin)
 */
public final class Berry extends JavaPlugin {

    private static Berry instance;
    @Override
    public void onEnable() {

        instance = this;
        getServer().getPluginManager().registerEvents(new CustomItemEventHandler(), this);
        getServer().getPluginManager().registerEvents(new GiveCommand(), this);
        getServer().getPluginManager().registerEvents(new AuctionEventHandler(), this);
        getServer().getPluginManager().registerEvents(new Search(), this);
        getServer().getPluginManager().registerEvents(new Price(), this);


        ExchangeCommand exchangeCommand = new ExchangeCommand();
        getServer().getPluginManager().registerEvents(exchangeCommand, this);
        this.getCommand("exchange").setExecutor(exchangeCommand);
        this.getCommand("auction").setExecutor(new AuctionCommand());


    }

    @Override
    public void onDisable() {


    }

    public static Berry getInstance() {
        return instance;
    }

}
