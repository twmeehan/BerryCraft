package me.berrycraft.berryeconomy.auction;

import me.berrycraft.berryeconomy.auction.windows.AuctionWindow;
import me.berrycraft.berryeconomy.auction.windows.Window;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.LinkedList;

public class AuctionEventHandler implements Listener {

    private static HashMap<Player, Window> openedWindow = new HashMap<Player,Window>();

    public static void openAuctionWindow(Player p) {
        openedWindow.put(p,new AuctionWindow(p));
        openedWindow.get(p).display();
    }

    public static void closeWindow(Player p) {
        openedWindow.remove(p);

    }

    public static Window getOpenedWindow(Player p) {
        return openedWindow.get(p);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (openedWindow.containsKey((Player)e.getWhoClicked())) {
            if (e.getClickedInventory().getType()==InventoryType.PLAYER) return;
            openedWindow.get((Player)e.getWhoClicked()).click(e.getSlot());
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        if (e.getPlayer().getScoreboardTags().contains("searching")) return;
        closeWindow((Player)e.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        closeWindow(e.getPlayer());
        e.getPlayer().removeScoreboardTag("searching");
    }


}
