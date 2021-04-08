package ru.mirea.inbo05.project.logic.cards;

/** Фракции карт */
public enum Faction {
    /** Нейтральная фракция */
    UNALIGNED("Unaligned"),
    /** Торговая федерация */
    TRADE_FEDERATION("Trade federation"),
    /** Звёздная империя */
    STAR_EMPIRE("Star empire"),
    /** Культ машин */
    MACHINE_CULT("Machine cult"),
    /** Слизни */
    BLOB("Blob");

    private final String toString;

    Faction(String toString) {
        this.toString = toString;
    }

    public String toString() {
        return toString;
    }
}
