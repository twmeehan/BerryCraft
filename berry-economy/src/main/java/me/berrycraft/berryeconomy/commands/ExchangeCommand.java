package me.berrycraft.berryeconomy.commands;

import de.tr7zw.nbtapi.NBTItem;
import me.berrycraft.berryeconomy.items.CustomItem;
import me.berrycraft.berryeconomy.items.Pinkberry;
import me.berrycraft.berryeconomy.items.Rainbowberry;
import me.berrycraft.berryeconomy.items.Raspberry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

public class ExchangeCommand implements CommandExecutor, Listener {

    LinkedList<String> playersExchanging = new LinkedList<>();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!(commandSender instanceof Player)) return false;
        Player p = (Player)commandSender;

        playersExchanging.add(p.getName());

        Inventory GUI = p.getServer().createInventory(p,36,ChatColor.DARK_GRAY+"Berry Exchange");
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = empty.getItemMeta();
        meta.setDisplayName(" ");
        empty.setItemMeta(meta);

        ItemStack raspberry = getSkull("http://textures.minecraft.net/texture/b12ef1b486e97e4cb124aa7629aceb91edc51d63338c91a012885493c5d9c");
        meta = raspberry.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Convert to " + ChatColor.YELLOW + "Raspberry");
        raspberry.setItemMeta(meta);

        ItemStack pinkberry = getSkull("http://textures.minecraft.net/texture/778308fe4dda1bb8a96f5b226b32542f49fd65bc55b44bbc31343eb400cf5e2");
        meta = pinkberry.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Convert to " + ChatColor.LIGHT_PURPLE + "Pinkberry");
        pinkberry.setItemMeta(meta);

        ItemStack rainbowberry = getSkull("http://textures.minecraft.net/texture/496f07d831d07fe1dd698569a974d2fcd8d83f7583b3877e89c9968b007f3a5d");
        meta = rainbowberry.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Convert to " + ChatColor.RED + "R" + ChatColor.GOLD+"a"+ChatColor.YELLOW+"i"+ChatColor.GREEN+"n"+ChatColor.BLUE+"b"+ChatColor.DARK_PURPLE+"o"+ChatColor.LIGHT_PURPLE+"w"+ChatColor.RED + "b" + ChatColor.GOLD+"e"+ChatColor.YELLOW+"r"+ChatColor.GREEN+"r"+ChatColor.BLUE+"y");
        rainbowberry.setItemMeta(meta);

        ItemStack cancel = new ItemStack(Material.RED_TERRACOTTA);
        meta = cancel.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD+ "CANCEL");
        cancel.setItemMeta(meta);

        ItemStack confirm = new ItemStack(Material.LIME_TERRACOTTA);
        meta = confirm.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD+ "TAKE ALL");
        confirm.setItemMeta(meta);

        GUI.setItem(1,empty);
        GUI.setItem(10,empty);
        GUI.setItem(19,empty);
        GUI.setItem(28,empty);
        GUI.setItem(18,raspberry);
        GUI.setItem(9,pinkberry);
        GUI.setItem(0,rainbowberry);
        GUI.setItem(27,confirm);

        p.openInventory(GUI);
        return true;
    }
    @EventHandler
    public void onItemClick(InventoryClickEvent e) {

        if (!playersExchanging.contains(e.getWhoClicked().getName())) return;

        Player p = (Player)e.getWhoClicked();
        if (e.getClickedInventory()!=p.getInventory()) {
                if (e.getSlot()==1 || e.getSlot()==10||e.getSlot()==19||e.getSlot()==28) {
                    e.setCancelled(true);
                }



            if (e.getSlot()==0) {
                LinkedList<ItemStack> items = getItems(e.getClickedInventory());
                raspberryToPinkberry(items,e.getClickedInventory());
                items = getItems(e.getClickedInventory());
                pinkberryToRainbowberry(items,e.getClickedInventory());
                items = getItems(e.getClickedInventory());
                rearrangeInventory(items,e.getClickedInventory());
                e.setCancelled(true);

            }
            if (e.getSlot()==9) {
                LinkedList<ItemStack> items = getItems(e.getClickedInventory());
                raspberryToPinkberry(items,e.getClickedInventory());
                rainbowberryToPinkberry(items,e.getClickedInventory());
                items = getItems(e.getClickedInventory());
                rearrangeInventory(items,e.getClickedInventory());
                e.setCancelled(true);


            }
            if (e.getSlot()==18) {
                LinkedList<ItemStack> items = getItems(e.getClickedInventory());
                rainbowberryToPinkberry(items,e.getClickedInventory());
                items = getItems(e.getClickedInventory());
                pinkberryToRaspberry(items,e.getClickedInventory());
                items = getItems(e.getClickedInventory());
                rearrangeInventory(items,e.getClickedInventory());
                e.setCancelled(true);

            }
            if (e.getSlot()==27) {
                LinkedList<ItemStack> items = new LinkedList<ItemStack>();
                for (int i = 0; i < 36;i++) {
                    if (i % 9 == 0 || i % 9 == 1) {
                        continue;
                    }
                    if (e.getClickedInventory().getItem(i) != null) {
                        items.add(e.getClickedInventory().getItem(i));
                        e.getClickedInventory().setItem(i, null);
                    }
                }

                for (ItemStack item : items) {
                    CustomItem.give(item,p);
                }

                e.setCancelled(true);

            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {

        if (!playersExchanging.contains(e.getPlayer().getName())) return;
        LinkedList<ItemStack> items = new LinkedList<ItemStack>();
        for (int i = 0; i < 36;i++) {
            if (i % 9 == 0 || i % 9 == 1) {
                continue;
            }
            if (e.getInventory().getItem(i) != null) {
                items.add(e.getInventory().getItem(i));
                e.getInventory().setItem(i, null);
            }
        }

        for (ItemStack item : items) {
            CustomItem.give(item,(Player)e.getPlayer());
        }
        playersExchanging.remove(e.getPlayer().getName());

    }
    public void rearrangeInventory(LinkedList<ItemStack> items, Inventory inventory) {
        for (int i = 0; i < 36;i++) {
            if (i % 9 == 0 || i % 9 == 1) {
                continue;
            }
            if (inventory.getItem(i) != null) {
                inventory.setItem(i,null);
            }
        }
        for (ItemStack item : items) {
            inventory.addItem(item);
        }
    }
    public void pinkberryToRaspberry(LinkedList<ItemStack> items, Inventory inventory){
        int totalPink = 0;
        for (ItemStack item : items) {
            totalPink += Pinkberry.getAmount(item);
        }

        while (totalPink >= 1) {
            inventory.removeItem(new Pinkberry());
            if (!addStack(new Raspberry(10),inventory)) {
                addStack(new Pinkberry(),inventory);
                break;
            } else {
                totalPink -= 1;
            }
        }
    }
    public void rainbowberryToPinkberry(LinkedList<ItemStack> items, Inventory inventory){
        int totalRain = 0;
        for (ItemStack item : items) {
            totalRain += Rainbowberry.getAmount(item);
        }

        while (totalRain >= 1) {
            inventory.removeItem(new Rainbowberry());
            if (!addStack(new Pinkberry(10),inventory)) {
                addStack(new Rainbowberry(),inventory);
                break;
            } else {
                totalRain -= 1;
            }
        }
    }
    public void pinkberryToRainbowberry(LinkedList<ItemStack> items, Inventory inventory){
        int totalPink = 0;
        for (ItemStack item : items) {
            totalPink += Pinkberry.getAmount(item);
        }

        while (totalPink >= 10) {
            inventory.removeItem(new Pinkberry(10));
            if (!addStack(new Rainbowberry(),inventory)) {
                addStack(new Pinkberry(10),inventory);
                break;
            } else {
                totalPink -= 10;
            }
        }
    }
    public void raspberryToPinkberry(LinkedList<ItemStack> items, Inventory inventory){
        int totalRasp = 0;
        for (ItemStack item : items) {
            totalRasp += Raspberry.getAmount(item);
        }

        while (totalRasp >= 10) {
            inventory.removeItem(new Raspberry(10));
            if (!addStack(new Pinkberry(),inventory)) {
                addStack(new Raspberry(10),inventory);
                break;
            } else {
                totalRasp -= 10;
            }
        }
    }
    public boolean addStack(ItemStack stack, Inventory inventory) {

        if (inventory.firstEmpty()!=-1) {
            inventory.addItem(stack);
            return true;
        }
        for (ItemStack i : inventory.getContents()) {
            if (i == null || i.getType()==Material.AIR) continue;

            if (i.getType()==Material.PLAYER_HEAD && stack.getType()==Material.PLAYER_HEAD) {
                NBTItem nbti = new NBTItem(stack);
                NBTItem nbti2 = new NBTItem(i);

                if( nbti.getString("CustomItem").equals(nbti2.getString("CustomItem")) && i.getAmount()<64) {
                    if (64-i.getAmount() >= stack.getAmount()) {
                        i.setAmount(i.getAmount()+stack.getAmount());
                        return true;
                    } else {
                        return false;
                    }
                }

            }

            if (i.isSimilar(stack) && i.getAmount()<64) {
                if (64-i.getAmount() >= stack.getAmount()) {
                    i.setAmount(i.getAmount()+stack.getAmount());
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    public LinkedList<ItemStack> getItems(Inventory inventory) {
        LinkedList<ItemStack> items = new LinkedList<ItemStack>();
        for (int i = 0; i < 36;i++) {
            if (i % 9 == 0 || i % 9 == 1) {
                continue;
            }
            if (inventory.getItem(i) != null) {
                items.add(inventory.getItem(i));
            }
        }
        return items;
    }
    public ItemStack getSkull(String base64EncodedString) {

        ItemStack stack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) stack.getItemMeta();
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

        stack.setItemMeta(meta);

        return stack;

    }

}
