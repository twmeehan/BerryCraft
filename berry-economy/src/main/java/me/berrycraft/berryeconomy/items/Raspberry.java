package me.berrycraft.berryeconomy.items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Raspberry extends CustomItem{

    public Raspberry() {

        super(Material.PLAYER_HEAD);

        setSkull("http://textures.minecraft.net/texture/b12ef1b486e97e4cb124aa7629aceb91edc51d63338c91a012885493c5d9c");
        ItemMeta meta = this.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "Raspberry");
        ArrayList<String> lore = new ArrayList<>();
//        lore.add(ChatColor.GRAY + "");
//        lore.add(ChatColor.GOLD + "Exchange " + ChatColor.YELLOW + "" + ChatColor.BOLD + "LEFT-CLICK");
//        lore.add(ChatColor.GRAY + "Converts 10 into a " + "pinkberry");
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.DARK_GRAY + "Berries are the currency of Berrycraft.");
        lore.add(ChatColor.DARK_GRAY + "They can be used to trade in the auction");
        lore.add(ChatColor.DARK_GRAY + "house or enchant items.");

        lore.add(ChatColor.GRAY + "");

        lore.add(ChatColor.YELLOW + "Currency Item");
        meta.setLore(lore);

        this.setItemMeta(meta);
        NBTItem nbti = new NBTItem(this);
        nbti.setString("CustomItem","Raspberry");
        nbti.applyNBT(this);
        this.setAmount(1);

    }
    public Raspberry(int amount) {

        super(Material.PLAYER_HEAD);

        setSkull("http://textures.minecraft.net/texture/b12ef1b486e97e4cb124aa7629aceb91edc51d63338c91a012885493c5d9c");
        ItemMeta meta = this.getItemMeta();

        meta.setDisplayName(ChatColor.YELLOW + "Raspberry");
        ArrayList<String> lore = new ArrayList<>();
//        lore.add(ChatColor.GRAY + "");
//        lore.add(ChatColor.GOLD + "Exchange " + ChatColor.YELLOW + "" + ChatColor.BOLD + "LEFT-CLICK");
//        lore.add(ChatColor.GRAY + "Converts 10 into a " + "pinkberry");
        lore.add(ChatColor.GRAY + "");
        lore.add(ChatColor.DARK_GRAY + "Berries are the currency of Berrycraft.");
        lore.add(ChatColor.DARK_GRAY + "They can be used to trade in the auction");
        lore.add(ChatColor.DARK_GRAY + "house or enchant items.");

        lore.add(ChatColor.GRAY + "");

        lore.add(ChatColor.YELLOW + "Currency Item");
        meta.setLore(lore);
        this.setItemMeta(meta);
        NBTItem nbti = new NBTItem(this);
        nbti.setString("CustomItem","Raspberry");
        nbti.applyNBT(this);
        this.setAmount(amount);

    }
    public static int getAmount(ItemStack stack) {
        if (stack.getType()!=Material.PLAYER_HEAD) return 0;
        NBTItem nbti = new NBTItem(stack);
        return nbti.getString("CustomItem").equals("Raspberry") ? stack.getAmount() : 0;
    }


}
