package dev.wand.banfx.manager;

import dev.wand.banfx.BanEffectType;

import java.util.HashMap;
import java.util.Map;

public class BanEffectManager {

    // store using a map, key is the punish type, value is the effect type
    private final Map<String, BanEffectType> effects = new HashMap<>();

    //    different punishments can ha ve different effects
    public void loadFromConfig() {
        // todo: implement
        // load effects from config
    }

    public void saveToConfig() {
        // todo: implement
    }

    public void setEffect(String punishType, BanEffectType effectType) {
        effects.put(punishType, effectType);
    }

    public BanEffectType getEffect(String punishType) {
        return effects.getOrDefault(punishType, BanEffectType.ZEUS);
    }
}
