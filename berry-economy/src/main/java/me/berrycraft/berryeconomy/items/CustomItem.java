package me.berrycraft.berryeconomy.items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/*
 * Parent class for all custom items. Contains
 * static methods that are used by multiple
 * custom items.
 */
public abstract class CustomItem extends ItemStack {

    // All custom items must use this constructor by using super(Material)
    public CustomItem(Material material) {
        super(material);
    }

    /*
     * Sets the Material of this custom object
     * to a player head with a texture
     *
     * Parameters:
     * base64EncodedString - the texture that will be used
     */
    public void setSkull(String base64EncodedString) {

        SkullMeta meta = (SkullMeta) this.getItemMeta();
        assert meta != null;

        // Always use the name Rainbowberry so that heads can stack
        PlayerProfile profile = Bukkit.getServer().createPlayerProfile("Rainbowberry");

        PlayerTextures texture = profile.getTextures();
        try {
            texture.setSkin(new URL(base64EncodedString), PlayerTextures.SkinModel.CLASSIC);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        profile.setTextures(texture);
        meta.setOwnerProfile(profile);

        this.setItemMeta(meta);

    }

    /*
     * Adds a item to the players inventory. If the inventory
     * is full, drop the item on the ground
     *
     * Parameters:
     * stack - the item to give
     * p - the player to give it to
     */
    public static void give(ItemStack stack, Player p) {

        // if there are empty slots then add the item with Bukkit's normal .addItem() method
        if (p.getInventory().firstEmpty()!=-1) {
            p.getInventory().addItem(stack);
            return;
        }
        // if not:

        // loop through all the items in the inventory
        for (ItemStack i : p.getInventory().getContents()) {
            if (i == null || i.getType()==Material.AIR) continue;

            // if the item we are adding is a player head and we found a stack of player heads in the inventory

            if (i.getType()==Material.PLAYER_HEAD && stack.getType()==Material.PLAYER_HEAD) {

                // check the nbt
                NBTItem nbti = new NBTItem(stack);
                NBTItem nbti2 = new NBTItem(i);

                // if they are the same custom item
                if( nbti.getString("CustomItem").equals(nbti2.getString("CustomItem")) && i.getAmount()<64) {

                    // see if you can fit more items in the stack
                    if (64-i.getAmount() >= stack.getAmount()) {
                        i.setAmount(i.getAmount()+stack.getAmount());
                        return;
                    } else {

                        // put as many in the stack as you can and then recursively call this
                        // function with the remaining amount (they will be dropped on the ground)
                        int prevAmount = i.getAmount();
                        i.setAmount(64);
                        stack.setAmount((stack.getAmount()+prevAmount-64));
                        give(stack,p);
                        return;

                    }

                }

            }

            // if the item is a normal item, check if it's the same item with i.isSimilar(stack)
            if (i.isSimilar(stack) && i.getAmount()<64) {

                // see if the items can be fit in this stack
                if (64-i.getAmount() >= stack.getAmount()) {
                    i.setAmount(i.getAmount()+stack.getAmount());
                    return;
                } else {

                    // put as many in the stack as you can and then recursively call this
                    // function with the remaining amount (they will be dropped on the ground)
                    int prevAmount = i.getAmount();
                    i.setAmount(64);
                    stack.setAmount((stack.getAmount()+prevAmount-64));
                    give(stack,p);
                    return;

                }

            }

        }

        // if item can't be fit, drop it on the ground
        p.getWorld().dropItem(p.getLocation(),stack);
    }
}
