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

/*
 * This is the controlling class of the auction system. It
 * keeps track of which player is looking at what window and
 * handles GUI events for that player.
 */
public class AuctionEventHandler implements Listener {

    // maps each player to the window they have opened
    private static HashMap<Player, Window> openedWindow = new HashMap<Player,Window>();

    // opens a new auction window for Player p
    public static void openAuctionWindow(Player p) {
        openedWindow.put(p,new AuctionWindow(p));
        openedWindow.get(p).display();
    }

    // tells the system that Player p is no longer looking at a window
    // note that this doesn't close the GUI
    public static void closeWindow(Player p) {
        openedWindow.remove(p);
    }

    // checks which Window player p is looking at
    public static Window getOpenedWindow(Player p) {
        return openedWindow.get(p);
    }

    // if the clicking player has an auction GUI up, call that windows click() function
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        if (openedWindow.containsKey((Player)e.getWhoClicked())) {
            if (e.getClickedInventory().getType()==InventoryType.PLAYER) return;
            openedWindow.get((Player)e.getWhoClicked()).click(e.getSlot());
            e.setCancelled(true);
        }
    }

    // if the player closes the auction house tell the system that this player is no longer
    // looking at a window
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (!(e.getPlayer() instanceof Player)) return;
        // when the player searches for something the inventory is closed but the system should
        // still keep track of that player's auction window
        if (e.getPlayer().getScoreboardTags().contains("searching")) return;
        closeWindow((Player)e.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        closeWindow(e.getPlayer());
        e.getPlayer().removeScoreboardTag("searching");
    }


}
