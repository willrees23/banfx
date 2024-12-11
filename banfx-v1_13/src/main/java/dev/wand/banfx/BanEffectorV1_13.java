package dev.wand.banfx;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class BanEffectorV1_13 implements BanEffector{
    @Override
    public void applyEffect(Location location, BanEffectType type) {
        switch (type) {
            case ZEUS:
                location.getWorld().strikeLightning(location);
                break;
            case MINEPLEX_GWEN:
                location.getWorld().createExplosion(location, 4);
                break;
        }
    }

    @Override
    public void log() {
        Bukkit.getLogger().info("Ban effector for 1.13!");
    }
}
