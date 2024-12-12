package dev.wand.banfx.command;


import dev.wand.banfx.BanEffectType;
import dev.wand.banfx.BanFX;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public class MainCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /banfx <reload|list|set>");
            return true;
        }
        switch (args[0]) {
            case "reload" -> {
                BanFX.getInstance().reloadConfig();
                sender.sendMessage("Reloaded!");
            }
            case "set" -> {
                if (args.length < 2) {
                    sender.sendMessage("Usage: /banfx set <punishType> <effect>");
                    sender.sendMessage("Example: /banfx set * zeus");
                    sender.sendMessage("Example: /banfx set ban zeus");
                    return true;
                }
                String punishType = args[1];
                BanEffectType effectType = BanEffectType.valueOf(args[2].toUpperCase());
                BanFX.getBanEffectManager().setEffect(punishType, effectType);
                sender.sendMessage("Effect set!");
            }
            case "list" -> {
                sender.sendMessage("Available effects:");
                for (BanEffectType effect : BanEffectType.values()) {
                    sender.sendMessage("- " + effect.getDisplayName());
                }
            }
            default -> sender.sendMessage("Usage: /banfx <reload|list|set>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return List.of();
    }


}
