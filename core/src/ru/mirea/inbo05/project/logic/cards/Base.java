package ru.mirea.inbo05.project.logic.cards;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import ru.mirea.inbo05.project.StarRealms;

/**
 * Класс описывающий свойства карт баз.
 */
public class Base extends Card {
    /** Здоровье базы */
    private int health;
    /** Является ли карта авангардом */
    private boolean isTaunt;

    private CardInfo baseInfo;

    public Base(CardInfo cardInfo) {
        super(cardInfo);
        this.baseInfo = cardInfo;
    }

    public int getHealth() {
        return health;
    }

    public boolean isTaunt() {
        return isTaunt;
    }

    @Override
    public void play() {
        clearListeners(); // Очистить список слушателей, чтобы разыгранную карту нельзя было разыграть ещё раз
        remove();

        setScale(0.7f);
        int playedCards = StarRealms.playerState.playedCards.size();
        setPosition(playedCards * getWidth() * getScaleX(), getHeight() * 0.8f, Align.bottomRight); // Расположить карту над рукой. Надо бы сделать покрасивше
        StarRealms.playerState.bases.add(this);

        StarRealms.playerState.hand.remove(baseInfo);

        StarRealms.playerState.basesGroup.addActor(this);
        createEffectButtons();
    }
}
