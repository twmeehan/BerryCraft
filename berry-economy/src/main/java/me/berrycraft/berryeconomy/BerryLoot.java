package me.berrycraft.berryeconomy;

import me.berrycraft.berryeconomy.items.Pinkberry;
import me.berrycraft.berryeconomy.items.Rainbowberry;
import me.berrycraft.berryeconomy.items.Raspberry;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BerryLoot implements Listener {


    @EventHandler
    public void onLoot(LootGenerateEvent e) {
        List<ItemStack> loot = e.getLoot();
        double score = 1.0;
        if (e.getEntity() != null && e.getEntity().getLocation().getWorld().getName().endsWith("_nether")) {
            score = 1.5;
        } else if (e.getEntity() != null && e.getEntity().getLocation().getWorld().getName().endsWith("_end")) {
            score = 1.75;
        } else if (e.getEntity() != null && e.getEntity().getLocation().getY() < 0) {
            score = 1.75;
        } else if (e.getEntity() != null && e.getEntity().getLocation().getY() < 50) {
            score = 1.4;
        }
        for (ItemStack item : loot) {
            if (item.getType() == Material.DIAMOND) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.IRON_INGOT) {
                score = updateScore(score, 3*item.getAmount());
            } else if (item.getType() == Material.GOLD_INGOT) {
                score = updateScore(score, 3*item.getAmount());
            } else if (item.getType() == Material.EMERALD) {
                score = updateScore(score, 6*item.getAmount());
            } else if (item.getType() == Material.SADDLE) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.NAME_TAG) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.REDSTONE) {
                score = updateScore(score, 2*item.getAmount());
            } else if (item.getType() == Material.OBSIDIAN) {
                score = updateScore(score, 2*item.getAmount());
            } else if (item.getType() == Material.GOLDEN_APPLE) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.GOLD_NUGGET) {
                score = updateScore(score, item.getAmount());
            } else if (item.getType() == Material.GOLDEN_CARROT) {
                score = updateScore(score, item.getAmount());
            } else if (item.getType() == Material.CHORUS_FRUIT) {
                score = updateScore(score, item.getAmount());
            } else if (item.getType() == Material.ANCIENT_DEBRIS) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.ECHO_SHARD) {
                score = updateScore(score, 4*item.getAmount());
            } else if (item.getType() == Material.RAW_IRON) {
                score = updateScore(score, item.getAmount());
            } else if (item.getType() == Material.RAW_GOLD) {
                score = updateScore(score, item.getAmount());
            } else if (item.getType() == Material.ENCHANTED_BOOK) {
                score = updateScore(score, 10*item.getAmount());
            } else if (item.getType() == Material.DIAMOND_BOOTS) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.DIAMOND_LEGGINGS) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.DIAMOND_CHESTPLATE) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.DIAMOND_HELMET) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.DIAMOND_SWORD) {
                score = updateScore(score, 20*item.getAmount());
            } else if (item.getType() == Material.DIAMOND_PICKAXE) {
                score = updateScore(score, 15*item.getAmount());
            } else if (item.getType() == Material.DIAMOND_AXE) {
                score = updateScore(score, 15*item.getAmount());
            } else if (item.getType() == Material.DIAMOND_HOE) {
                score = updateScore(score, 15*item.getAmount());
            } else if (item.getType() == Material.DIAMOND_SHOVEL) {
                score = updateScore(score, 15*item.getAmount());
            } else if (item.getType() == Material.IRON_BOOTS) {
                score = updateScore(score, 5*item.getAmount());
            } else if (item.getType() == Material.IRON_LEGGINGS) {
                score = updateScore(score, 5*item.getAmount());
            } else if (item.getType() == Material.IRON_CHESTPLATE) {
                score = updateScore(score, 5*item.getAmount());
            } else if (item.getType() == Material.IRON_HELMET) {
                score = updateScore(score, 5*item.getAmount());
            } else if (item.getType() == Material.IRON_SWORD) {
                score = updateScore(score, 5*item.getAmount());
            } else if (item.getType() == Material.IRON_PICKAXE) {
                score = updateScore(score, 5*item.getAmount());
            } else if (item.getType() == Material.IRON_AXE) {
                score = updateScore(score, 5*item.getAmount());
            } else if (item.getType() == Material.IRON_HOE) {
                score = updateScore(score, 5*item.getAmount());
            } else if (item.getType() == Material.IRON_SHOVEL) {
                score = updateScore(score, 5 * item.getAmount());
            } else if (item.getType() == Material.SCULK) {
                score = updateScore(score, 4*item.getAmount());
            }
        }
        Random rand = new Random();
        if (score < 1.5) {
            loot.add(new Raspberry(rand.nextInt(4)+1));
        } else if (score < 2.0) {
            loot.add(new Raspberry(rand.nextInt(7)+3));
        } else if (score < 2.5) {
            loot.add(new Raspberry(rand.nextInt(3)+3));
            loot.add(new Pinkberry(rand.nextInt(3)+1));
        } else if (score < 3.0) {
            loot.add(new Pinkberry(rand.nextInt(4)+3));
        } else {
            if (Math.random()<0.4) {
                loot.add(new Rainbowberry(1));
            }
            loot.add(new Pinkberry(rand.nextInt(5)+5));
        }
    }

    private double updateScore(double currentScore, int itemValue) {
        return currentScore+((4-currentScore)*(0.01*itemValue));
    }
}
