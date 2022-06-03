package store.yaff.feature;

public enum Category {
    COMBAT("Combat", "A"), MOVEMENT("Movement", "B"), PLAYER("Player", "C"), WORLD("World", "E"), RENDER("Render", "F"), EXPLOIT("Exploit", "G"), MISC("Misc", "H"), HUD("HUD", "I");

    public String name;
    public String icon;

    Category(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
