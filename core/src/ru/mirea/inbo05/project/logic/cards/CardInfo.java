package ru.mirea.inbo05.project.logic.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 *  Класс, описывающий свойства карты.
 * Используется для описания карт кораблей, для описания баз используется наследующий класс Base.
 * @see Base
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
public class CardInfo {
    /** Цена карты в торговом ряду*/
    public int cost = 0;
    /** Фракция карты */
    public Faction faction;
    /** Основной эффект */
    public CardEffect mainEffect = new CardEffect();
    /** Союзный эффект */
    public CardEffect allyEffect = new CardEffect();
    /** Эффект при отправке карты в утиль */
    public CardEffect trashEffect = new CardEffect();
    /** Имя текстуры */
    public String textureName;

    @JsonIgnore
    public Card instance;

    public Card CreateInstance()
    {
        Card card = new Card(this);
        instance = card;
        return card;
    }
}
