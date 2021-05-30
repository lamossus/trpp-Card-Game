package ru.mirea.inbo05.project.logic.cards;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Класс описывающий свойства карт баз.
 */
public class Base extends Card {
    /** Здоровье базы */
    private int health;
    /** Является ли карта авангардом */
    private boolean isTaunt;

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
