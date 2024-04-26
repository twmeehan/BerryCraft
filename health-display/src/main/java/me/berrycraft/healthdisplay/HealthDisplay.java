package me.berrycraft.healthdisplay;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
 * Main class of BerryCraft's health-display plugin.
 * Displays the health of mobs in the subtitle bar when
 * attacked by a player. Has a timer system that checks to
 * how long it's been since a player attacked an entity in
 * case that entity continues to take damage (fire, other players,
 * poison, etc).
 *
 * Uses two HashMaps to access which entities are attacked by
 * which players and vise versa in O(1) time.
 *
 * This plugin replaces the original v1.0 on BerryCraft season 1
 * which is now broken for some reason
 *
 * Made by: Timothy Meehan
 * Date: April 15, 2024
 */
public final class HealthDisplay extends JavaPlugin implements Listener {

    // this map of entity -> players is used to check if an entity has been damaged before
    // and then to check which players are currently displaying that entity's health
    HashMap<LivingEntity, LinkedList<Player>> playersTargetingEntity = new HashMap<LivingEntity,LinkedList<Player>>();


    /*
     * a damageEvent is created when a player attacks an entity.
     * The time of the event and entity damaged are saved as the value
     * in a hashtable and the Player is the key used to access this info
     */
    HashMap<Player,DamageEventInfo> damageEventsList = new HashMap<Player,DamageEventInfo>();

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this,this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    /*
     * When an entity takes damage within 4s of when a player
     * attacked it, the player will see the updated health.
     * This helps if more than one player is attacking a monster.
     */
    @EventHandler
    public void onEntityTakeDamage(EntityDamageEvent e) {

        if (playersTargetingEntity.containsKey(e.getEntity())) {
            if (!(e.getEntity() instanceof LivingEntity)) return;

            for (Player p : playersTargetingEntity.get(e.getEntity())) {

                // If it's been more than 4000ms disassociate the player and the entity
                if (System.currentTimeMillis() - damageEventsList.get(p).timestamp > 4000) {

                    deleteDamageEvent(p);

                } else {
                    showHealthDisplay(p, (LivingEntity) e.getEntity(), e.getDamage());
                    damageEventsList.get(p).timestamp = System.currentTimeMillis();
                }
            }
        }
    }
    

    // Run when the player attacks an entity
    @EventHandler
    public void onEntityDamaged(EntityDamageByEntityEvent e) {

        EntityDamageEvent.DamageCause damageCause = e.getCause();
        Player p = null;

        // check to see if the attacker is a player
        switch (damageCause) {
            case MAGIC: break;
            // direct melee attack
            case ENTITY_ATTACK:
                if (!(e.getDamager() instanceof Player)) {
                    return;
                }
                p = (Player)e.getDamager();
                break;
            case PROJECTILE:

                // Only certain projectiles are player originating
                if (e.getDamager().getType() == EntityType.ARROW) {

                    Projectile arrow = (Arrow) e.getDamager();
                    if (!(arrow.getShooter() instanceof Player)) {
                        return;
                    }
                    p = (Player)arrow.getShooter();
                } else if (e.getDamager().getType() == EntityType.SPECTRAL_ARROW) {

                    Projectile arrow = (SpectralArrow) e.getDamager();
                    if (!(arrow.getShooter() instanceof Player)) {
                        return;
                    }
                    p = (Player)arrow.getShooter();
                } else if (e.getDamager().getType() == EntityType.FIREBALL) {

                    Fireball fireball = (Fireball) e.getDamager();
                    if (!(fireball.getShooter() instanceof Player)) {
                        return;
                    }
                    p = (Player)fireball.getShooter();
                } else if (e.getDamager().getType() == EntityType.TRIDENT) {

                    Projectile arrow = (Trident) e.getDamager();
                    if (!(arrow.getShooter() instanceof Player)) {
                        return;
                    }
                    p = (Player)arrow.getShooter();
                } else if (e.getDamager().getType() == EntityType.SPLASH_POTION) {
                    Projectile potion = (ThrownPotion) e.getDamager();
                    if (!(potion.getShooter() instanceof Player)) {
                        return;
                    }
                    p = (Player)potion.getShooter();
                } else {
                    return;
                }
                break;
        }
        if (!(e.getEntity() instanceof LivingEntity) || p == null){
            return;
        }
        LivingEntity entity = (LivingEntity) e.getEntity();

        if (e.getDamager().getType() == EntityType.ARROW && !(((Projectile)e.getDamager()).getShooter() instanceof Player)) {
            return;
        } else if (e.getDamager() instanceof Projectile) {
            p = (Player)(((Projectile)e.getDamager()).getShooter());
        }

        // if it is a player attacking create a new damageEvent

        createNewDamageEvent(p,entity);
        showHealthDisplay(p,damageEventsList.get(p).entity,e.getDamage());



    }


    /*
     * When <entity> is damaged, the player is shown how much
     * remaining health <entity> has as a percentage of their max health.
     * Uses the player's subtitle display to show hearts
     *
     * Parameters:
     * p - the player that sees the display
     * entity - the entity that was damaged by the player
     * damage - the amount of damage <entity> took
     */
    public void showHealthDisplay(Player p, LivingEntity entity, double damage) {

        double health = entity.getHealth();
        double absorption = entity.getAbsorptionAmount();

        // this code runs BEFORE the <entity> takes damage so we modify health and
        // absorption to the amount <entity> will have after taking damage
        if (absorption>damage) {
            absorption = absorption-damage;
        } else {
            absorption = 0;
            health = health-damage+absorption;
        }
        if (health < 0) health = 0;

        // what will be displayed in the subtitle
        String message;

        // if entity is dead, put a line through the name
        if (health==0) {
            message = ChatColor.GOLD + "[" + ChatColor.YELLOW+""+ChatColor.STRIKETHROUGH + entity.getName() +ChatColor.GOLD+ "] ";
        } else message = ChatColor.GOLD + "[" + ChatColor.YELLOW+entity.getName() +ChatColor.GOLD+ "] ";


        double percentMaxHealth = health/entity.getMaxHealth();
        // red hearts are the remaining hearts
        int redHearts = (int)Math.round(percentMaxHealth*10);
        // pink heart is used to show half a heart
        boolean pinkHeart = Math.round(percentMaxHealth*10)-percentMaxHealth*10<0;
        double percentAbsorption =absorption/entity.getMaxHealth();
        // no pink heart if <entity> has absorption
        if (percentAbsorption>0 && pinkHeart) {
            pinkHeart = false;
            redHearts++;
            percentAbsorption = (absorption-1)/entity.getMaxHealth();
        }
        // yellow hearts are used for absorption hearts
        int yellowHearts = (int)Math.round(percentAbsorption*10);
        // gold heart is used to show half a heart for absorption hearts
        boolean goldHeart = Math.round(percentAbsorption*10)-percentAbsorption*10<0;

        int totalHearts = redHearts+yellowHearts;
        if (pinkHeart) totalHearts++;
        if (goldHeart) totalHearts++;

        for (int i = 0; i < redHearts;i++) {
            message+=ChatColor.DARK_RED+"❤";
        }
        if (pinkHeart)
            message+=ChatColor.RED+"❤";
        for (int i = 0; i < yellowHearts;i++) {
            message+=ChatColor.YELLOW+"❤";
        }
        if (goldHeart)
            message+=ChatColor.GOLD+"❤";
        // gray hearts for any missing hearts
        for (int i = 0; i < 10-totalHearts; i++) {
            message+=ChatColor.GRAY+"❤";

        }

        // display numerical health
        message+=ChatColor.GRAY+" ";
        message+=ChatColor.GOLD + Long.toString(Math.round(health*100)/100);
        message+=ChatColor.GRAY+"/";
        message+=ChatColor.GOLD + Long.toString(Math.round(entity.getMaxHealth()*100)/100);

        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));

    }


    /*
     * called when a player attacks an entity. Records information
     * about the damageEvent
     *
     * Parameters:
     * p - player attacking
     * entity - the entity that was hit
     */
    public void createNewDamageEvent(Player p, LivingEntity entity) {

        // if player is already targeting entity2, remove that player from
        // the list of players targeting entity2
        if (damageEventsList.containsKey(p)) {
            if (damageEventsList.get(p).entity.equals(entity)) {
                return;
            }
            removePlayerTargetingEntity(p,damageEventsList.get(p).entity);
        }
        // player is now targeting entity1
        addPlayerTargetingEntity(p,entity);

        // record the information associated damage event
        damageEventsList.put(p,new DamageEventInfo(entity, System.currentTimeMillis()));

    }

    // called when player is no longer targeting any entities
    public void deleteDamageEvent(Player p) {

        if (damageEventsList.get(p) != null) {
            removePlayerTargetingEntity(p,damageEventsList.get(p).entity);
            damageEventsList.remove(p);
        }


    }

    // call when adding an entry to playersTargetingEntity
    public void addPlayerTargetingEntity(Player p,LivingEntity entity) {

        // if entity is not targeted by any players, create a new list of players
        // targeting that entity and put it in the hashMap
        if (!playersTargetingEntity.containsKey(entity)) {
            LinkedList<Player> listOfPlayersTargetingEntity = new LinkedList<Player>();
            listOfPlayersTargetingEntity.add(p);
            playersTargetingEntity.put(entity,listOfPlayersTargetingEntity);
            return;
        }

        // if the entity is not already targeted by this player, add the player
        // to the list of players targeting that entity
        if (!playersTargetingEntity.get(entity).contains(p))
            playersTargetingEntity.get(entity).add(p);

    }

    // call when removing an entry to playersTargetingEntity
    public void removePlayerTargetingEntity(Player p,LivingEntity entity) {
        playersTargetingEntity.get(entity).remove(p);
        if (playersTargetingEntity.get(entity).isEmpty()) {
            playersTargetingEntity.remove(entity);
        }
    }

}
