package me.berrycraft.berryeconomy.auction.windows;

import me.berrycraft.berryeconomy.BerryUtility;
import me.berrycraft.berryeconomy.auction.AuctionEventHandler;
import me.berrycraft.berryeconomy.auction.MarketEntry;
import me.berrycraft.berryeconomy.auction.windows.elements.Price;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;

public class CreateListingWindow extends Window {

    Price price;

    ItemStack item;
    public CreateListingWindow(Player viewer) {
        size = 54;
        name = "Creating new listing";
        this.viewer = viewer;
        window = viewer.getServer().createInventory(viewer,size,name);

        ItemStack fill = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        fill.addUnsafeEnchantment(Enchantment.DAMAGE_ALL,1);
        ItemMeta fillMeta = fill.getItemMeta();
        fillMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        fillMeta.setDisplayName(" ");
        fill.setItemMeta(fillMeta);
        fill(fill);

        ItemStack itemBorder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = itemBorder.getItemMeta();
        borderMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        borderMeta.setDisplayName(" ");
        itemBorder.setItemMeta(borderMeta);
        window.setItem(21,itemBorder);
        window.setItem(19,itemBorder);
        window.setItem(30,itemBorder);
        window.setItem(29,itemBorder);
        window.setItem(28,itemBorder);
        window.setItem(10,itemBorder);
        window.setItem(11,itemBorder);
        window.setItem(12,itemBorder);

        ItemStack close = new ItemStack(Material.ARROW);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(ChatColor.RED+ "Cancel");
        close.setItemMeta(closeMeta);
        window.setItem(49,close);

        ItemStack accept = new ItemStack(Material.GOLDEN_CARROT);
        ItemMeta acceptMeta = accept.getItemMeta();
        acceptMeta.setDisplayName(ChatColor.GREEN+ "Sell Item");
        accept.setItemMeta(acceptMeta);
        window.setItem(25,accept);

        price=new Price(this,23);

        window.setItem(20,null);

    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack stack) {
        BerryUtility.give(viewer,item);
        item = stack;
        window.setItem(20,item);
    }

    @Override
    public void click(int slot) {

        if (slot==23) {
            price.click();
        } else if (slot==49) {
            viewer.closeInventory();
            AuctionEventHandler.openMyListingsWindow(viewer);
        } else if (slot==25) {
            AuctionWindow.marketEntries.add(new MarketEntry(getItem(),price.price,viewer, LocalDateTime.now().plusMinutes(1)));
            AuctionEventHandler.openMyListingsWindow(viewer);
        } else if (slot==20) {
            BerryUtility.give(viewer,getItem());
            item = null;
            window.setItem(20,null);
        }
    }
}
