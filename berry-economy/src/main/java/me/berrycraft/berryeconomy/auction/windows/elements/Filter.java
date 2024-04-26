package me.berrycraft.berryeconomy.auction.windows.elements;

import me.berrycraft.berryeconomy.auction.windows.Window;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Filter extends Element{

    private static final String[] filters = {ChatColor.WHITE + "All",ChatColor.YELLOW + "Vanilla",
            ChatColor.DARK_GRAY + "Common",ChatColor.DARK_AQUA + "Uncommon",ChatColor.BLUE + "Rare",
            ChatColor.DARK_PURPLE + "Epic",ChatColor.GOLD + "Legendary"};

    public int selection = 0;

    public Filter(Window window, int slot) {
        this.window = window;
        this.slot = slot;
        icon = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta totemMeta = icon.getItemMeta();
        totemMeta.setDisplayName(ChatColor.YELLOW + "Filter");
        totemMeta.setLore(getLore());
        icon.setItemMeta(totemMeta);
        setIcon();
    }

    public void update() {
        ItemMeta totemMeta = icon.getItemMeta();
        totemMeta.setLore(getLore());
        icon.setItemMeta(totemMeta);
    }

    private ArrayList<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "");
        for (int i = 0; i < filters.length; i++) {
            if (i==selection) {
                lore.add(ChatColor.GOLD + "‣ "+filters[i]);
            } else {
                lore.add(ChatColor.GRAY + "⁃ "+filters[i]);
            }
        }
        return lore;

    }
    @Override
    public void click() {
        selection++;
        selection %= filters.length;
        update();
    }
}
