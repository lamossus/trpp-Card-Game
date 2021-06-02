package ru.mirea.inbo05.project.logic.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.mirea.inbo05.project.StarRealms;

/**
 *  Класс, описывающий свойства карты.
 * Используется для описания карт кораблей, для описания баз используется наследующий класс Base.
 * @see Base
 */
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

    // TODO тестовое, удалить
    public CardInfo(String textureName) {
        this.textureName = textureName;
    }

    public Card CreateInstance()
    {
        Card card = new Card(this);
        instance = card;
        return card;
    }
}
