package dev.wand.banfx;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public interface BanEffector {

    void applyEffect(Location location, BanEffectType type);

    void log();
}