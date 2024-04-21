package me.berrycraft.berryeconomy.items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
/*
 * Raspberry is a custom item that is a part of
 * the currency of berrycraft. It is worth 0.1 of a pinkberry
 */
public class Raspberry extends CustomItem{

    // use new Raspberry() to get an item stack containing 1 raspberry
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

    // use new Raspberry(amount) to get an item stack containing x amount of raspberry
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
    public static int getAmount(Player p) {
        int total = 0;
        for (ItemStack stack : p.getInventory().getContents()) {
            if (stack== null || stack.getType()!=Material.PLAYER_HEAD) continue;
            else {
                NBTItem nbti = new NBTItem(stack);
                total += nbti.getString("CustomItem").equals("Raspberry") ? stack.getAmount() : 0;
            }
        }
        return total;

    }


}
