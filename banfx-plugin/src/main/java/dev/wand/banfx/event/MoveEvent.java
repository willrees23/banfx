package dev.wand.banfx.event;

import dev.wand.banfx.BanFX;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!BanFX.getPlayerQueue().contains(event.getPlayer())) return;

        // cancel event only if trying to move, not looking around
        if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ())
            event.setCancelled(true);
    }
}
