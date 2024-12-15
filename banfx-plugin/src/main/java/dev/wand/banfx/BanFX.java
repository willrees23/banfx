package dev.wand.banfx;

import dev.wand.banfx.command.MainCommand;
import dev.wand.banfx.effectors.BanEffectorV1_18;
import dev.wand.banfx.effectors.BanEffectorV1_8;
import dev.wand.banfx.event.CommandEvent;
import dev.wand.banfx.event.MoveEvent;
import dev.wand.banfx.manager.BanEffectManager;
import lombok.Getter;
import net.insprill.spigotutils.MinecraftVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class BanFX extends JavaPlugin {

    @Getter
    private static final ArrayList<Player> playerQueue = new ArrayList<>();
    @Getter
    private static final BanEffectManager banEffectManager = new BanEffectManager();
    @Getter
    private static BanEffector banEffector;
    @Getter
    private static BanFX instance;

    public static void affect(Player player, BanEffectType type, Runnable callback) {
        banEffector.applyEffect(instance, player.getLocation(), type, callback);
    }

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("BanFX is enabling!");

        getLogger().info("Detected version: " + MinecraftVersion.getCurrentVersion().getDisplayName());
        if (MinecraftVersion.isMajor(MinecraftVersion.v1_21_0)) {
            banEffector = new BanEffectorV1_18();
        } else if (MinecraftVersion.isMajor(MinecraftVersion.v1_8_0)) {
            banEffector = new BanEffectorV1_8();
        } else {
            getLogger().severe("Unsupported version!");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getLogger().info("Registering events...");
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new CommandEvent(), this);
        pm.registerEvents(new MoveEvent(), this);

        getLogger().info("Registering commands...");
        getCommand("banfx").setExecutor(new MainCommand());

        getLogger().info("Loading configuration file...");

        getLogger().info("BanFX has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BanFX has been disabled!");
    }
}
