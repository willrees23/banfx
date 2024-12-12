package dev.wand.banfx.command;


import dev.wand.banfx.BanEffectType;
import dev.wand.banfx.BanFX;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MainCommand implements BanFXCommand {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        switch (args[0]) {
            case "reload" -> {
                BanFX.getInstance().reloadConfig();
                sendMessage(sender, "&aConfiguration file reloaded!");
            }
            case "set" -> {
                if (args.length < 3) {
                    sendSetHelp(sender);
                    return true;
                }
                String punishType = args[1];
                try {
                    BanEffectType.valueOf(args[2].toUpperCase());
                } catch (IllegalArgumentException e) {
                    sendAvailableEffects(sender);
                    return true;
                }
                BanEffectType effectType = BanEffectType.valueOf(args[2].toUpperCase());
                BanFX.getBanEffectManager().setEffect(punishType, effectType);
                sendMessage(sender, "&aEffect for &7" + punishType + " &aset to &7" + effectType.getDisplayName());
            }
            case "list" -> {
                sendAvailableEffects(sender);
            }
            default -> sendHelp(sender);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of();
    }


}
