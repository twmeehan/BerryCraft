package me.berrycraft.berryeconomy.items;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomItemEventHandler implements Listener {

    @EventHandler
    public void onBerryPlaced(BlockPlaceEvent e) {
        if (e.getItemInHand().getType()== Material.PLAYER_HEAD) {
            
            NBTItem nbti = new NBTItem(e.getItemInHand());
            e.setCancelled(nbti.hasTag("CustomItem"));



        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (e.getItem().getType()== Material.PLAYER_HEAD) {

            NBTItem nbti = new NBTItem(e.getItem());
            if (e.getAction()== Action.RIGHT_CLICK_AIR || e.getAction()== Action.RIGHT_CLICK_BLOCK) {
                if (Rainbowberry.getAmount(e.getItem()) > 1) {

                }
                e.setCancelled(true);
            } else if (e.getAction()== Action.LEFT_CLICK_AIR || e.getAction()== Action.LEFT_CLICK_BLOCK){
                e.setCancelled(true);

            }



        }

    }

}
