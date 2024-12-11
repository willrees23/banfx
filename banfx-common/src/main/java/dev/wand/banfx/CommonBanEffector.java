package dev.wand.banfx;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class CommonBanEffector implements BanEffector{
    @Override
    public void applyEffect(Location location, BanEffectType type) {
        switch (type) {
            case ZEUS:
                location.getWorld().strikeLightning(location);
                Bukkit.broadcastMessage("Zeus has struck " + location.toString());
                break;
            case MINEPLEX_GWEN:
                location.getWorld().strikeLightningEffect(location);
                Bukkit.broadcastMessage("Mineplex GWEN has struck " + location.toString());
                break;
        }
    }

    @Override
    public void log() {
        Bukkit.getLogger().info("Common ban effector!");
    }
}
