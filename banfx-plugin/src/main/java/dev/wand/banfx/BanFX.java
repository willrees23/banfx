package dev.wand.banfx;

import dev.wand.banfx.event.TestEv;
import lombok.Getter;
import net.insprill.spigotutils.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BanFX extends JavaPlugin {

    @Getter
    private static BanEffector banEffector;

    @Override
    public void onEnable() {
        getLogger().info("dev.wand.banfx.BanFX has been enabled!");

        if (MinecraftVersion.isNew()) {
            banEffector = new BanEffectorV1_13();
        } else {
            getLogger().severe("Unsupported version!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new TestEv(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("dev.wand.banfx.BanFX has been disabled!");
    }


}
