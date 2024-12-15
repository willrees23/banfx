package dev.wand.banfx.event;

import dev.wand.banfx.BanFX;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().hasPermission("banfx.use")) return;

        String commandNoSlash = event.getMessage().substring(1);

        String[] commands = {
                "ban",
                "tempban",
                "tempipban",
                "tempbanip",
                "kick",
                "ipban",
                "ban-ip",
                "banip",
                "tban"
        };

        String punishType = commandNoSlash.split(" ")[0];

        // check if command is in the list
        boolean isCommand = false;
        for (String command : commands) {
            if (punishType.equalsIgnoreCase(command)) {
                isCommand = true;
                break;
            }
        }
        if (!isCommand) return;

        // get target player (argument 1)
        String[] args = event.getMessage().split(" ");
        if (args.length < 2) {
            return;
        }
        String target = args[1];
        Player player = Bukkit.getPlayer(target);
        if (player == null) return;

        event.setCancelled(true);
        event.getPlayer().sendMessage("Player will be punished...");
        BanFX.getPlayerQueue().add(player);

        // apply effect
        // and since we are cancelling the event above, use the callback to re-run what the player was trying to do
        BanFX.affect(player, BanFX.getBanEffectManager().getEffect(punishType), () -> {
            BanFX.getPlayerQueue().remove(player);
            player.performCommand(commandNoSlash);
        });
    }
}
