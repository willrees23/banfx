package dev.wand.banfx.effectors;

import dev.wand.banfx.BanEffectType;
import dev.wand.banfx.BanEffector;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BanEffectorV1_8 implements BanEffector {
    @Override
    public void applyEffect(JavaPlugin plugin, Location location, BanEffectType type, Runnable callback) {
        if (type == BanEffectType.MINEPLEX_GWEN) {
            double radius = 3.0; // Distance from the player
            double height = 5.0; // Height above the player
            List<Guardian> guardians = new ArrayList<>();
            LivingEntity armorStand = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

            for (int i = 0; i < 4; i++) {
                double angle = Math.toRadians(90 * i); // 0°, 90°, 180°, 270°
                double x = location.getX() + radius * Math.cos(angle);
                double z = location.getZ() + radius * Math.sin(angle);
                Location guardianLoc = new Location(location.getWorld(), x, location.getY() + height, z);

                ArmorStand as = (ArmorStand) location.getWorld().spawnEntity(guardianLoc, EntityType.ARMOR_STAND);
                Guardian guardian = (Guardian) location.getWorld().spawnEntity(guardianLoc, EntityType.GUARDIAN);

                as.setVisible(false);
                as.setPassenger(guardian);
                as.setGravity(false);

                // Target invisible armor stand
                ((ArmorStand) armorStand).setVisible(false);
                ((ArmorStand) armorStand).setGravity(false);

                guardian.setTarget(armorStand);

                guardians.add(guardian);
            }

            // Start circling behavior
            new BukkitRunnable() {
                double angle = 0.0; // Current angle in radians
                double speed = Math.toRadians(1); // Initial rotation speed (radians per tick)
                int ticks = 0; // Counter to track elapsed ticks
                int duration = 5 * 20; // Duration in ticks (6 seconds * 20 ticks per second)

                @Override
                public void run() {
                    // Increment counters
                    angle += speed;
                    speed += Math.toRadians(.5); // Gradually increase speed
                    ticks++;

                    // Update guardian positions
                    for (int i = 0; i < guardians.size(); i++) {
                        Location newLoc = getLocation(i);

                        guardians.get(i).teleport(newLoc); // Update guardian's position
                    }

                    // Stop the task after the specified duration
                    if (ticks >= duration) {
                        this.cancel();

                        // despawn enttiies
                        for (Guardian guardian : guardians) {
                            guardian.remove();
                        }
                        armorStand.remove();

                        location.getWorld().playEffect(location, Effect.EXPLOSION_HUGE, 0);
                        location.getWorld().playSound(location, Sound.EXPLODE, 1.0f, 1.0f);

                        callback.run();
                    }
                }

                private Location getLocation(int i) {
                    double guardianAngle = angle + Math.toRadians(90 * i); // Offset each guardian by 90°
                    double x = location.getX() + radius * Math.cos(guardianAngle);
                    double z = location.getZ() + radius * Math.sin(guardianAngle);
                    double y = location.getY() + height;
                    // need to increase y slightly as guardians fall naturally
                    y += 1;
                    return new Location(location.getWorld(), x, y, z);
                }
            }.runTaskTimer(plugin, 0L, 1L); // Run every tick (1L = 1 tick)
        } else if (type == BanEffectType.BLOOD) {
            // in future versions, we do this:
            // #getWorld#spawnParticle(Particle.BLOCK_CRACK, location, 80, Material.REDSTONE_BLOCK.createBlockData());
            // but that doesn't exist in 1.8, so we'll just use playEffect
            int id = Material.REDSTONE_BLOCK.getId();
            for (int i = 0; i < 80; i++) {
                location.getWorld().playEffect(location.clone().add(0, 0.5, 0), Effect.TILE_BREAK, id);
            }
        }
        else {
            // effect isn't version specific, so just use default implementation
            BanEffector.super.applyEffect(plugin, location, type, callback);
        }
    }
}
