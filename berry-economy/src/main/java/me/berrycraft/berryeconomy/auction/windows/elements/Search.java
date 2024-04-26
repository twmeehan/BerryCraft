package me.berrycraft.berryeconomy.auction.windows.elements;

import me.berrycraft.berryeconomy.Berry;
import me.berrycraft.berryeconomy.auction.AuctionEventHandler;
import me.berrycraft.berryeconomy.auction.windows.Window;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Search extends Element implements Listener {

    public String searchCriteria;
    private static HashMap<Player,Search> openSearches = new HashMap<>();

    private BukkitRunnable searchTimeout;


    public Search(Window window, int slot) {
        this.window = window;
        this.slot = slot;
        createSpyglass();
        setIcon();
    }
    public Search() {
        // this is just for events
    }

    @EventHandler
    public void onOpenInventory(InventoryOpenEvent e) {
        if (e.getPlayer().getScoreboardTags().contains("searching")) e.setCancelled(true);
    }
    @EventHandler
    public void onPlayerType(AsyncPlayerChatEvent e) {
        if (e.getPlayer().getScoreboardTags().contains("searching")) {
            Search s = openSearches.get(e.getPlayer());
            s.searchCriteria = e.getMessage();
            e.getPlayer().removeScoreboardTag("searching");
            e.setCancelled(true);


            new BukkitRunnable() {
                public void run() {
                    e.getPlayer().removePotionEffect(PotionEffectType.SLOW);
                    s.createBrush();
                    s.setIcon();
                    s.searchTimeout.cancel();
                    s.window.display();
                    openSearches.remove(e.getPlayer());
                }
            }.runTask(Berry.getInstance());


        }
    }
    public void click() {
        Player p = window.getViewer();
        if (searchCriteria==null) {
            p.sendMessage(ChatColor.GREEN+"Please enter the item you would like to search for:");
            p.addScoreboardTag("searching");
            openSearches.put(p,this);
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,200,2,false, false, true));
            p.closeInventory();
            searchTimeout = new BukkitRunnable() {
                public void run() {
                    if (p.getScoreboardTags().contains("searching")) {
                        p.removeScoreboardTag("searching");
                        openSearches.remove(p);
                        p.sendMessage(ChatColor.RED+"Search cancelled");
                    }
                }
            };
            searchTimeout.runTaskLater(Berry.getInstance(),200);
        } else {
            searchCriteria = null;
            createSpyglass();
            setIcon();
        }
    }

    private void createSpyglass() {
        icon = new ItemStack(Material.SPYGLASS);
        ItemMeta spyglassMeta = icon.getItemMeta();
        spyglassMeta.setDisplayName(ChatColor.AQUA + "Search");
        icon.setItemMeta(spyglassMeta);
    }

    private void createBrush() {
        icon = new ItemStack(Material.BRUSH);
        ItemMeta spyglassMeta = icon.getItemMeta();
        spyglassMeta.setDisplayName(ChatColor.AQUA + "Clear Search");
        spyglassMeta.setLore(getLore());
        icon.setItemMeta(spyglassMeta);
    }

    private ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.DARK_GRAY + "Searching for \"" +searchCriteria+"\"");
        return lore;

    }
}
