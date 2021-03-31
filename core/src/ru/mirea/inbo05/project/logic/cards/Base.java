package ru.mirea.inbo05.project.logic.cards;

public class Base extends Card {
    private int health;
    private boolean isTaunt;

    public Base(int health, boolean isTaunt) {
        this.health = health;
        this.isTaunt = isTaunt;

        // TODO добавить чтение из JSON
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isTaunt() {
        return isTaunt;
    }

    public void setTaunt(boolean taunt) {
        isTaunt = taunt;
    }
}
