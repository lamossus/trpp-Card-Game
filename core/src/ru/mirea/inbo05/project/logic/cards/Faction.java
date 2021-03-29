package ru.mirea.inbo05.project.logic.cards;

public enum Faction {
    UNALIGNED("Unaligned"),
    TRADE_FEDERATION("Trade federation"),
    STAR_EMPIRE("Star empire"),
    MACHINE_CULT("Machine cult"),
    BLOB("Blob");

    private final String toString;

    Faction(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}
