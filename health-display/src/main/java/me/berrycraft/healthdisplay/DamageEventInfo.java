package me.berrycraft.healthdisplay;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

// Simple class to link the attacked entity and time of attack together
public class DamageEventInfo {

    public LivingEntity entity;
    public long timestamp;

    // which entity was hit and at what time
    public DamageEventInfo(LivingEntity e, long time) {
        entity = e;
        timestamp = time;
    }
}
