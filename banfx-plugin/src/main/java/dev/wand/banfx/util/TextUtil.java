package dev.wand.banfx.util;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

@UtilityClass
public class TextUtil {

    public String translate(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
