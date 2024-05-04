package me.berrycraft.berryeconomy.auction.windows;

import me.berrycraft.berryeconomy.BerryUtility;
import me.berrycraft.berryeconomy.auction.AuctionEventHandler;
import me.berrycraft.berryeconomy.auction.MarketEntry;
import me.berrycraft.berryeconomy.items.Pinkberry;
import me.berrycraft.berryeconomy.items.Rainbowberry;
import me.berrycraft.berryeconomy.items.Raspberry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ConfirmPurchaseWindow extends Window {

    AuctionWindow prevWindow;
    MarketEntry entry;

    public ConfirmPurchaseWindow(Player viewer, AuctionWindow prevWindow, MarketEntry entry) {

        this.viewer = viewer;
        this.prevWindow = prevWindow;
        this.entry = entry;
        size = 27;
        name = "Auction House";
        window = viewer.getServer().createInventory(viewer,size,name);

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta meta = back.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Back");
        back.setItemMeta(meta);
        window.setItem(11,back);

        ItemStack confirm = new ItemStack(Material.GREEN_TERRACOTTA);
        meta = confirm.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Purchase Item");
        confirm.setItemMeta(meta);
        window.setItem(15,confirm);

        window.setItem(13,entry.getDisplayIcon());

    }
    @Override
    public void click(int slot) {

        if (slot==11) {
            AuctionEventHandler.openWindow(viewer,prevWindow);
        } else if (slot==15) {
            if (Raspberry.getAmount(viewer)*0.01+ Pinkberry.getAmount(viewer)*0.1+ Rainbowberry.getAmount(viewer)>entry.getPrice()) {
                entry.setBuyer(viewer);
                BerryUtility.give(viewer, entry.getItem());
                AuctionEventHandler.openWindow(viewer,prevWindow);
            } else {
                viewer.sendMessage(ChatColor.RED + "You do not have enough berries");
            }


        }

    }
}
