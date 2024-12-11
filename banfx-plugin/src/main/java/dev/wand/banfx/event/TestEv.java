package dev.wand.banfx.event;

import dev.wand.banfx.BanFX;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class TestEv implements Listener {

    @EventHandler
    public void move(PlayerInteractEvent event) {
        BanFX.getBanEffector().log();
    }
}
