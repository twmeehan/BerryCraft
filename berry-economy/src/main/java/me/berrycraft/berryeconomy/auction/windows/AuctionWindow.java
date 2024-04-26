package me.berrycraft.berryeconomy.auction.windows;

import me.berrycraft.berryeconomy.Berry;
import me.berrycraft.berryeconomy.auction.MarketEntry;
import me.berrycraft.berryeconomy.auction.windows.elements.Filter;
import me.berrycraft.berryeconomy.auction.windows.elements.Search;
import me.berrycraft.berryeconomy.items.CustomItemEventHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;

/*
 * This is the GUI for browsing items that
 * other players are selling. It's the first window
 * that pops up when you run /auction
 */
public class AuctionWindow extends Window {

    // all auction windows share a list of items that are for sale
    static LinkedList<MarketEntry> marketEntries = new LinkedList<MarketEntry>();
    Filter filter;
    Search search;

    public AuctionWindow(Player viewer) {
        size = 54;
        name = "Auction House";
        this.viewer = viewer;
        window = viewer.getServer().createInventory(viewer,size,name);
        setupEmtpyPage();
    }

    // sets up the items in the GUI
    private void setupEmtpyPage() {

        ItemStack border = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        border.addUnsafeEnchantment(Enchantment.DAMAGE_ALL,1);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        borderMeta.setDisplayName(" ");
        border.setItemMeta(borderMeta);
        addBorder(border);

        ItemStack emerald = new ItemStack(Material.EMERALD);
        ItemMeta emeraldMeta = emerald.getItemMeta();
        emeraldMeta.setDisplayName(ChatColor.GOLD + ""+ChatColor.BOLD + "My Listings");
        emerald.setItemMeta(emeraldMeta);
        window.setItem(49,emerald);

        filter = new Filter(this,50);

        search = new Search(this,48);

    }

    // runs when anything in the GUI is clicked
    @Override
    public void click(int slot) {
        if (slot==50) {
            filter.click();
        } else if (slot==48) {
            search.click();
        }
    }
}
