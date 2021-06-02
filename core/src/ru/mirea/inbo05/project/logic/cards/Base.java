package ru.mirea.inbo05.project.logic.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
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

    public Base(CardInfo cardInfo) {
        super(cardInfo);
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

        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();

        int playedCards = StarRealms.playerState.bases.size();

        setScale(0.55f);
        setRotation(-90);
        setPosition(width - (1 + playedCards) * getHeight() * getScaleY(), height/2f, Align.bottomLeft); // Расположить карту над рукой. Надо бы сделать покрасивше
        StarRealms.playerState.bases.add(this);

        StarRealms.playerState.hand.remove(cardInfo);

        StarRealms.playerState.basesGroup.addActor(this);
        createEffectButtons();
    }
}
