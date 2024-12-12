package dev.wand.banfx.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EnumUtil {

    public static <T extends Enum<T>> T getEnum(Class<T> enumClass, String name) {
        return Enum.valueOf(enumClass, name.toUpperCase());
    }
}
