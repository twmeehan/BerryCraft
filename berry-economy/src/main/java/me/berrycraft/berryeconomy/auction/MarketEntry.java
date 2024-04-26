package me.berrycraft.berryeconomy.auction;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;

public class MarketEntry {

    ItemStack item;
    double price;
    Player seller;
    Player buyer;
    LocalDateTime expirationDate;

}
