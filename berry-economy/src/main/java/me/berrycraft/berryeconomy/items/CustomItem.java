package me.berrycraft.berryeconomy.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class CustomItem extends ItemStack {

    public CustomItem(Material material) {
        super(material);
    }

    public void setSkull(String base64EncodedString) {
        SkullMeta meta = (SkullMeta) this.getItemMeta();
        assert meta != null;
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
}
