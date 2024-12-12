package dev.wand.banfx;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public interface BanEffector {

    default void applyEffect(JavaPlugin plugin, Location location, BanEffectType type) {

    }

    default void applyEffect(JavaPlugin plugin, Location location, BanEffectType type, Runnable callback) {

    }
}