package dev.wand.banfx;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public interface BanEffector {

    default void applyEffect(JavaPlugin plugin, Location location, BanEffectType type, Runnable callback) {
        switch (type) {
            case ZEUS -> {
                location.getWorld().strikeLightningEffect(location);
                callback.run();
            }
            default -> {
            }
        }
    }
}