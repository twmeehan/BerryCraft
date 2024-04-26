package me.berrycraft.berryeconomy.auction.windows.elements;

import me.berrycraft.berryeconomy.auction.windows.Window;
import org.bukkit.inventory.ItemStack;

public abstract class Element {

    public ItemStack icon;

    protected Window window;
    protected int slot;
    public abstract void click();

    public void setIcon() {
        window.getInventory().setItem(slot,icon);
        icon = window.getInventory().getItem(slot);
    }
}
