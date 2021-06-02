package ru.mirea.inbo05.project.logic.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.mirea.inbo05.project.StarRealms;

/**
 * Класс, представляющий собой кнопку, созданную на основе информации о карте
 */
public class Card extends Image {
    /** Информация о карте */
    protected CardInfo cardInfo;
    /** Спрайт */
    //protected Drawable sprite;

    public Card(CardInfo cardInfo)
    {
        this.cardInfo = cardInfo;
        Texture texture = StarRealms.assets.getTexture(cardInfo.textureName);
        setDrawable(new TextureRegionDrawable(texture));
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setScale(0.7f);
    }

    /** Разыграть карту из руки и активировать её эффекты при разыгрывании */
    public void play()
    {
        clearListeners(); // Очистить список слушателей, чтобы разыгранную карту нельзя было разыграть ещё раз
        remove();

        setScale(0.6f);
        int playedCards = StarRealms.playerState.playedCards.size();
        setPosition(playedCards * getWidth() * getScaleX(), getHeight() * 0.7f, Align.bottomLeft); // Расположить карту над рукой. Надо бы сделать покрасивше
        StarRealms.playerState.playedCards.add(cardInfo);
        StarRealms.playerState.hand.remove(cardInfo);

        StarRealms.playerState.playedCardsGroup.addActor(this);
        createEffectButtons();
    }

    void createEffectButtons()
    {
        addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) { // Теперь карта это кнопка, нажатие которой вызывает ещё 4 кнопки - основной, союзный и эффект утиля, а также кнопка отмены
                Skin skin = StarRealms.assets.getSkin();

                int width = Gdx.graphics.getWidth();
                int height = Gdx.graphics.getHeight();

                final Group buttons = new Group();

                final TextButton main = new TextButton(cardInfo.mainEffect.getEffectText(), skin);
                final TextButton ally = new TextButton(cardInfo.allyEffect != null ? cardInfo.allyEffect.getEffectText() : "There is no ally effect on this card.", skin);
                final TextButton trash = new TextButton(cardInfo.trashEffect != null ? cardInfo.trashEffect.getEffectText() : "There is no trash effect on this card.", skin);
                final TextButton cancel = new TextButton("Cancel", skin);

                for (Actor actor : StarRealms.stage.getActors())
                    actor.setTouchable(Touchable.disabled);
                StarRealms.stage.addActor(buttons);

                main.setPosition(width/2f, height/2f + 100, Align.center);
                main.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        cardInfo.mainEffect.activate(); // Добавленной кнопки добавляется соответствующий функционал
                        buttons.remove();
                        for (Actor actor : StarRealms.stage.getActors())
                            actor.setTouchable(Touchable.enabled);
                    }
                });

                ally.setPosition(width/2f, height/2f + 50, Align.center);
                if (cardInfo.allyEffect != null)
                    ally.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            cardInfo.allyEffect.activate();
                            buttons.remove();
                            for (Actor actor : StarRealms.stage.getActors())
                                actor.setTouchable(Touchable.enabled);
                        }
                    });
                else
                    ally.setColor(new Color(1,1,1,0.5f));


                trash.setPosition(width/2f, height/2f, Align.center);
                if (cardInfo.trashEffect != null)
                    trash.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            cardInfo.trashEffect.activate();
                            buttons.remove();
                            for (Actor actor : StarRealms.stage.getActors())
                                actor.setTouchable(Touchable.enabled);
                        }
                    });
                else
                    trash.setColor(new Color(1,1,1,0.5f));


                cancel.setPosition(width/2f, height/2f - 50, Align.center);
                cancel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buttons.remove();
                        for (Actor actor : StarRealms.stage.getActors())
                            actor.setTouchable(Touchable.enabled);
                    }
                });

                buttons.addActor(main);
                buttons.addActor(ally);
                buttons.addActor(trash);
                buttons.addActor(cancel);
            }
        });
    }

    public CardInfo getCardInfo()
    {
        return cardInfo;
    }
}