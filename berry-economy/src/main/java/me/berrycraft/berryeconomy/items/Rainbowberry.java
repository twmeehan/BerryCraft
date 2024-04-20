package me.berrycraft.berryeconomy.items;

import de.tr7zw.nbtapi.NBTItem;
import me.berrycraft.berryeconomy.Berry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

public class Rainbowberry extends CustomItem {

    public Rainbowberry() {

        super(Material.PLAYER_HEAD);

        setSkull("http://textures.minecraft.net/texture/496f07d831d07fe1dd698569a974d2fcd8d83f7583b3877e89c9968b007f3a5d");
        ItemMeta meta = this.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "R" + ChatColor.GOLD+"a"+ChatColor.YELLOW+"i"+ChatColor.GREEN+"n"+ChatColor.BLUE+"b"+ChatColor.DARK_PURPLE+"o"+ChatColor.LIGHT_PURPLE+"w"+ChatColor.RED + "b" + ChatColor.GOLD+"e"+ChatColor.YELLOW+"r"+ChatColor.GREEN+"r"+ChatColor.BLUE+"y");

        ArrayList<String> lore = new ArrayList<>();
//        lore.add(ChatColor.GRAY + "");
//        lore.add(ChatColor.GOLD + "Exchange " + ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT-CLICK");
//        lore.add(ChatColor.GRAY + "Can be converted into 10 " + "pinkberries");
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.DARK_GRAY + "Berries are the currency of Berrycraft.");
        lore.add(ChatColor.DARK_GRAY + "They can be used to trade in the auction");
        lore.add(ChatColor.DARK_GRAY + "house or enchant items.");

        lore.add(ChatColor.GRAY + "");

        lore.add(ChatColor.YELLOW + "Currency Item");
        meta.setLore(lore);
        this.setItemMeta(meta);
        NBTItem nbti = new NBTItem(this);
        nbti.setString("CustomItem","Rainbowberry");
        nbti.applyNBT(this);
        this.setAmount(1);

    }
    public Rainbowberry(int amount) {

        super(Material.PLAYER_HEAD);

        setSkull("http://textures.minecraft.net/texture/496f07d831d07fe1dd698569a974d2fcd8d83f7583b3877e89c9968b007f3a5d");
        ItemMeta meta = this.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "R" + ChatColor.GOLD+"a"+ChatColor.YELLOW+"i"+ChatColor.GREEN+"n"+ChatColor.BLUE+"b"+ChatColor.DARK_PURPLE+"o"+ChatColor.LIGHT_PURPLE+"w"+ChatColor.RED + "b" + ChatColor.GOLD+"e"+ChatColor.YELLOW+"r"+ChatColor.GREEN+"r"+ChatColor.BLUE+"y");

        ArrayList<String> lore = new ArrayList<>();
//        lore.add(ChatColor.GRAY + "");
//        lore.add(ChatColor.GOLD + "Exchange " + ChatColor.YELLOW + "" + ChatColor.BOLD + "RIGHT-CLICK");
//        lore.add(ChatColor.GRAY + "Can be converted into 10 " + "pinkberries");
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.DARK_GRAY + "Berries are the currency of Berrycraft.");
        lore.add(ChatColor.DARK_GRAY + "They can be used to trade in the auction");
        lore.add(ChatColor.DARK_GRAY + "house or enchant items.");

        lore.add(ChatColor.GRAY + "");

        lore.add(ChatColor.YELLOW + "Currency Item");
        meta.setLore(lore);
        this.setItemMeta(meta);
        NBTItem nbti = new NBTItem(this);
        nbti.setString("CustomItem","Rainbowberry");
        nbti.applyNBT(this);
        this.setAmount(amount);

    }
    public static int getAmount(ItemStack stack) {
        if (stack.getType()!=Material.PLAYER_HEAD) return 0;
        NBTItem nbti = new NBTItem(stack);
        return nbti.getString("CustomItem").equals("Rainbowberry") ? stack.getAmount() : 0;
    }
}
