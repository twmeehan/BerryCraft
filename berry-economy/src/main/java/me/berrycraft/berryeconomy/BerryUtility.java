package me.berrycraft.berryeconomy;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BerryUtility {

    /*
     * Adds a item to the players inventory. If the inventory
     * is full, drop the item on the ground
     *
     * Same as CustomItem
     *
     * Parameters:
     * stack - the item to give
     * p - the player to give it to
     */
    public static void give(Player p, ItemStack stack) {

        if (stack==null) return;


        // if there are empty slots then add the item with Bukkit's normal .addItem() method
        if (p.getInventory().firstEmpty()!=-1) {
            p.getInventory().addItem(stack);
            return;
        }
        // if not:

        // loop through all the items in the inventory
        for (ItemStack i : p.getInventory().getContents()) {
            if (i == null || i.getType()== Material.AIR) continue;

            // if the item we are adding is a player head and we found a stack of player heads in the inventory

            if (i.getType()==Material.PLAYER_HEAD && stack.getType()==Material.PLAYER_HEAD) {

                // check the nbt
                NBTItem nbti = new NBTItem(stack);
                NBTItem nbti2 = new NBTItem(i);

                // if they are the same custom item
                if( nbti.getString("CustomItem").equals(nbti2.getString("CustomItem")) && i.getAmount()<stack.getMaxStackSize()) {

                    // see if you can fit more items in the stack
                    if (stack.getMaxStackSize()-i.getAmount() >= stack.getAmount()) {
                        i.setAmount(i.getAmount()+stack.getAmount());
                        return;
                    } else {

                        // put as many in the stack as you can and then recursively call this
                        // function with the remaining amount (they will be dropped on the ground)
                        int prevAmount = i.getAmount();
                        i.setAmount(stack.getMaxStackSize());
                        stack.setAmount((stack.getAmount()+prevAmount-stack.getMaxStackSize()));
                        give(p,stack);
                        return;

                    }

                }

            }

            // if the item is a normal item, check if it's the same item with i.isSimilar(stack)
            if (i.isSimilar(stack) && i.getAmount()<stack.getMaxStackSize()) {

                // see if the items can be fit in this stack
                if (stack.getMaxStackSize()-i.getAmount() >= stack.getAmount()) {
                    i.setAmount(i.getAmount()+stack.getAmount());
                    return;
                } else {

                    // put as many in the stack as you can and then recursively call this
                    // function with the remaining amount (they will be dropped on the ground)
                    int prevAmount = i.getAmount();
                    i.setAmount(stack.getMaxStackSize());
                    stack.setAmount((stack.getAmount()+prevAmount-stack.getMaxStackSize()));
                    give(p,stack);
                    return;

                }

            }

        }

        // if item can't be fit, drop it on the ground
        p.getWorld().dropItem(p.getLocation(),stack);
    }


    public static ItemStack addRandUUID(ItemStack icon) {
        NBTItem nbti = new NBTItem(icon);
        nbti.setString("Rand", UUID.randomUUID().toString());
        nbti.applyNBT(icon);
        return icon;
    }
}
