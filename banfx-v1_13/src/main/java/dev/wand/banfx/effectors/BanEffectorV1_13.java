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

public class BanEffectorV1_13 implements BanEffector {
    @Override
    public void applyEffect(JavaPlugin plugin, Location location, BanEffectType type, Runnable callback) {
        if (Objects.requireNonNull(type) == BanEffectType.MINEPLEX_GWEN) {
            double radius = 3.0; // Distance from the player
            double height = 5.0; // Height above the player
            List<Guardian> guardians = new ArrayList<>();
            LivingEntity armorStand = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

            for (int i = 0; i < 4; i++) {
                double angle = Math.toRadians(90 * i); // 0°, 90°, 180°, 270°
                double x = location.getX() + radius * Math.cos(angle);
                double z = location.getZ() + radius * Math.sin(angle);
                Location guardianLoc = new Location(location.getWorld(), x, location.getY() + height, z);

                Guardian guardian = (Guardian) location.getWorld().spawnEntity(guardianLoc, EntityType.GUARDIAN);
                guardian.setAI(false);
                guardian.setGravity(false);
                guardian.setInvulnerable(true);
                guardian.setSilent(true);

                // Target invisible armor stand
                ((ArmorStand) armorStand).setVisible(false);
                armorStand.setInvulnerable(true);
                armorStand.setAI(false);
                armorStand.setGravity(false);

                guardian.setTarget(armorStand);

                guardians.add(guardian);
            }

            // Start circling behavior
            new BukkitRunnable() {
                final int duration = 5 * 20; // Duration in ticks (6 seconds * 20 ticks per second)
                double angle = 0.0; // Current angle in radians
                double speed = Math.toRadians(1); // Initial rotation speed (radians per tick)
                int ticks = 0; // Counter to track elapsed ticks

                @Override
                public void run() {
                    // Increment counters
                    angle += speed;
                    speed += Math.toRadians(.5); // Gradually increase speed
                    ticks++;

                    // Update guardian positions
                    for (int i = 0; i < guardians.size(); i++) {
                        double guardianAngle = angle + Math.toRadians(90 * i); // Offset each guardian by 90°
                        double x = location.getX() + radius * Math.cos(guardianAngle);
                        double z = location.getZ() + radius * Math.sin(guardianAngle);
                        Location newLoc = new Location(location.getWorld(), x, location.getY() + height, z);

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

                        location.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, location, 1);
                        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);

                        callback.run();
                    }
                }
            }.runTaskTimer(plugin, 0L, 1L); // Run every tick (1L = 1 tick)
        }else if (type == BanEffectType.BLOOD) {
            location.getWorld().spawnParticle(Particle.BLOCK_CRACK, location, 80, Material.REDSTONE_BLOCK.createBlockData());

            callback.run();
        } else {
            BanEffector.super.applyEffect(plugin, location, type, callback);
        }
    }
}
