package me.berrycraft.berryeconomy.auction.windows;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class Window {

    protected String name;
    protected int size;
    
    protected Inventory window;

    protected Player viewer;

    public void display() {

        viewer.openInventory(window);

    } 
    public void addBorder(ItemStack border) {

        for (int i =0; i < 9; i++) {
            window.setItem(i,border);
        }
        for (int i =0;i <window.getSize();i+=9) {
            window.setItem(i,border);
        }
        for (int i =8;i <window.getSize();i+=9) {
            window.setItem(i,border);
        }
        for (int i =0; i < 9; i++) {
            window.setItem(i+window.getSize()-10,border);
        }

    }

    public void clearCenter() {

        for (int i =1; i < 8; i++) {
            for (int j =i;j <window.getSize()-9;j+=9) {
                window.setItem(j,null);
            }
        }

    }

    public abstract void click(int slot);

    public void add(ItemStack stack) {
        window.addItem(stack);
    }

    public void add(ItemStack[] stacks) {
        window.addItem(stacks);
    }

    public int getSize() {
        return size;
    }

    public Inventory getInventory() {
        return window;
    }

    public String getName() {
        return name;
    }

    public Player getViewer() {
        return viewer;
    }
}
