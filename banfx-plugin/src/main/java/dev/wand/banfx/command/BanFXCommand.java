package dev.wand.banfx.command;

import dev.wand.banfx.BanEffectType;
import dev.wand.banfx.BanFX;
import dev.wand.banfx.util.TextUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public interface BanFXCommand extends TabExecutor {

    default void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(TextUtil.translate(message));
    }

    default void sendHelp(CommandSender sender) {
        sendMessage(sender, "&aBanFX &7v" + BanFX.getInstance().getDescription().getVersion() + " by &aWand&7.");
        sendMessage(sender, "&7/banfx reload &8- &7Reload the configuration file.");
        sendMessage(sender, "&7/banfx list &8- &7List available effects.");
        sendMessage(sender, "&7/banfx set <punishType> <effect> &8- &7Set the effect for a punish type.");
    }

    default void sendSetHelp(CommandSender sender) {
        sendMessage(sender, "&aUsage: &7/banfx set <punishType> <effect>");
        sendMessage(sender, "&aExample: &7/banfx set ban zeus");
    }

    default void sendAvailableEffects(CommandSender sender) {
        sendMessage(sender, "&aAvailable effects:");
        for (BanEffectType effect : BanEffectType.values()) {
            sendMessage(sender, "&7- " + effect.name());
        }
    }
}
