package me.berrycraft.berryeconomy.items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Pinkberry extends CustomItem{

    public Pinkberry() {

        super(Material.PLAYER_HEAD);

        setSkull("http://textures.minecraft.net/texture/778308fe4dda1bb8a96f5b226b32542f49fd65bc55b44bbc31343eb400cf5e2");
        ItemMeta meta = this.getItemMeta();

        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Pinkberry");

        ArrayList<String> lore = new ArrayList<>();
//        lore.add(ChatColor.GRAY + "");
//        lore.add(ChatColor.GOLD + "Exchange " + ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT-CLICK");
//        lore.add(ChatColor.GRAY + "Converts into 10 " + "raspberries");
//        lore.add(ChatColor.GRAY + "");
//        lore.add(ChatColor.GOLD + "Exchange " + ChatColor.YELLOW + "" + ChatColor.BOLD + "LEFT-CLICK");
//        lore.add(ChatColor.GRAY + "Converts 10 into a rainbowberry");
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.DARK_GRAY + "Berries are the currency of Berrycraft.");
        lore.add(ChatColor.DARK_GRAY + "They can be used to trade in the auction");
        lore.add(ChatColor.DARK_GRAY + "house or enchant items.");

        lore.add(ChatColor.GRAY + "");

        lore.add(ChatColor.YELLOW + "Currency Item");
        meta.setLore(lore);

        this.setItemMeta(meta);
        NBTItem nbti = new NBTItem(this);
        nbti.setString("CustomItem","Pinkberry");
        nbti.applyNBT(this);
        this.setAmount(1);

    }
    public Pinkberry(int amount) {

        super(Material.PLAYER_HEAD);

        setSkull("http://textures.minecraft.net/texture/778308fe4dda1bb8a96f5b226b32542f49fd65bc55b44bbc31343eb400cf5e2");
        ItemMeta meta = this.getItemMeta();

        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Pinkberry");
        ArrayList<String> lore = new ArrayList<>();
//        lore.add(ChatColor.GRAY + "");
//        lore.add(ChatColor.GOLD + "Exchange " + ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT-CLICK");
//        lore.add(ChatColor.GRAY + "Converts into 10 " + "raspberries");
//        lore.add(ChatColor.GRAY + "");
//        lore.add(ChatColor.GOLD + "Exchange " + ChatColor.YELLOW + "" + ChatColor.BOLD + "LEFT-CLICK");
//        lore.add(ChatColor.GRAY + "Converts 10 into a rainbowberry");
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.DARK_GRAY + "Berries are the currency of Berrycraft.");
        lore.add(ChatColor.DARK_GRAY + "They can be used to trade in the auction");
        lore.add(ChatColor.DARK_GRAY + "house or enchant items.");

        lore.add(ChatColor.GRAY + "");

        lore.add(ChatColor.YELLOW + "Currency Item");
        meta.setLore(lore);

        this.setItemMeta(meta);
        NBTItem nbti = new NBTItem(this);
        nbti.setString("CustomItem","Pinkberry");
        nbti.applyNBT(this);
        this.setAmount(amount);

    }
    public static int getAmount(ItemStack stack) {
        if (stack.getType()!=Material.PLAYER_HEAD) return 0;
        NBTItem nbti = new NBTItem(stack);
        return nbti.getString("CustomItem").equals("Pinkberry") ? stack.getAmount() : 0;
    }
}
