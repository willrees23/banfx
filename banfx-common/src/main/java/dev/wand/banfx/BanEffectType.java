package dev.wand.banfx;

public enum BanEffectType {

    ZEUS,
    MINEPLEX_GWEN,
    CUSTOM
    ;

    public String getDisplayName() {
        return switch (this) {
            case MINEPLEX_GWEN -> "Mineplex GWEN";
            default -> {
                // capitalize first letter
                String name = this.name().toLowerCase();
                yield name.substring(0, 1).toUpperCase() + name.substring(1);
            }
        };
    }
}
