package ru.mirea.inbo05.project.logic.cards;

import com.badlogic.gdx.graphics.g2d.Sprite;
import ru.mirea.inbo05.project.logic.commands.Command;

import java.util.List;

/**
 * Класс, описывающий свойства карты.
 * используется для описания карт кораблей, для описания баз используется наследующий класс Base.
 * @see Base
 */
public class Card {
    /** Цена карты в торговом ряду*/
    private int cost;
    /** Название карты */
    private String name;
    /** Спрайт карты */
    private Sprite image;
    /** Фракция карты */
    private Faction faction;
    /** Список основных эффектов */
    private List<Command> mainEffect;
    /** Список союзных эффектов */
    private List<Command> allyEffect;
    /** Список эффектов при отправке карты в утиль */
    private List<Command> trashEffect;

    /*
    Тут должны быть конструкторы, наверно...
     */

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public Faction getFaction() {
        return faction;
    }
}
