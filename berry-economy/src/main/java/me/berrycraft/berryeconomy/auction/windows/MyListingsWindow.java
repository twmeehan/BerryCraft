package me.berrycraft.berryeconomy.auction.windows;

import me.berrycraft.berryeconomy.Berry;
import me.berrycraft.berryeconomy.BerryUtility;
import me.berrycraft.berryeconomy.auction.MarketEntry;
import me.berrycraft.berryeconomy.auction.windows.elements.Stats;
import me.berrycraft.berryeconomy.auction.AuctionEventHandler;
import me.berrycraft.berryeconomy.items.Pinkberry;
import me.berrycraft.berryeconomy.items.Rainbowberry;
import me.berrycraft.berryeconomy.items.Raspberry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MyListingsWindow extends Window {

    Stats stats;
    int numberOfListings = 0;

    MarketEntry[] entries = new MarketEntry[20];
    public MyListingsWindow(Player viewer) {

        size = 54;
        name = "My Listings";
        this.viewer = viewer;
        window = viewer.getServer().createInventory(viewer,size,name);

        ItemStack border = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        border.addUnsafeEnchantment(Enchantment.DAMAGE_ALL,1);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        borderMeta.setDisplayName(" ");
        border.setItemMeta(borderMeta);
        addBorder(border);

        ItemStack lectern = new ItemStack(Material.LECTERN);
        ItemMeta lecternMeta = lectern.getItemMeta();
        lecternMeta.setDisplayName(ChatColor.GOLD + ""+ChatColor.BOLD + "Browse Auctions");
        lectern.setItemMeta(lecternMeta);
        window.setItem(49,lectern);

        ItemStack nameTag = new ItemStack(Material.NAME_TAG);
        ItemMeta nameTagMeta = nameTag.getItemMeta();
        nameTagMeta.setDisplayName(ChatColor.YELLOW + "Create Auction");
        nameTag.setItemMeta(nameTagMeta);
        window.setItem(50,nameTag);

        stats = new Stats(this, 48);

        updateListings();

    }


    private void updateListings() {
        clearCenter();
        int index = 0;
        numberOfListings=0;
        for (MarketEntry entry : AuctionWindow.marketEntries) {
            if (entry.getSeller().getUniqueId().equals(viewer.getUniqueId())) {
                add(entry.getDisplayIcon());
                entries[index] = entry;
                index++;
                numberOfListings++;
            }
        }
    }

    private MarketEntry getMarketEntry(int slot) {
        return entries[(slot - 10 - ((slot-9)/9)*2)];
    }
    @Override
    public void click(int slot) {
        if (slot==49) {
            AuctionEventHandler.openAuctionWindow(viewer);
        } else if (slot == 50) {
            if (numberOfListings >= 28) {
                viewer.sendMessage(ChatColor.RED + "You have too many listings already");
                return;
            }
            AuctionEventHandler.openCreateListingWindow(viewer);

        } else if (!(slot%9 == 0 || slot%9 == 8 || slot < 9 || slot>44)) {

            MarketEntry entry = getMarketEntry(slot);
            if (entry!= null) {
                if (entry.getBuyer()!=null) {
                    recieveProfits(entry);
                    AuctionWindow.marketEntries.remove(entry);
                    updateListings();
                } else if ((int) LocalDateTime.now().until(entry.getExpirationDate(), ChronoUnit.MINUTES)<0) {
                    BerryUtility.give(viewer,entry.getItem());
                    AuctionWindow.marketEntries.remove(entry);
                    updateListings();
                } else {

                }
            }
        }
    }

    public void recieveProfits(MarketEntry entry) {

        double profit = entry.getPrice();
        ItemStack berry;
        if ((int)profit > 0) {
            berry = new Rainbowberry();
            berry.setAmount((int)profit);
            BerryUtility.give(viewer,berry);
            profit-=(int)profit;
        }
        profit = profit*10;
        if ((int)profit > 0) {
            berry = new Pinkberry();
            berry.setAmount((int)profit);
            BerryUtility.give(viewer,berry);
            profit-=(int)profit;
        }
        profit = profit*10;
        if ((int)profit > 0) {
            berry = new Raspberry();
            berry.setAmount((int)profit);
            BerryUtility.give(viewer,berry);
        }
    }
}
