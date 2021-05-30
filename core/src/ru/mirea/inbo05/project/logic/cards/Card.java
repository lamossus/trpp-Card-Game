package ru.mirea.inbo05.project.logic.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import ru.mirea.inbo05.project.StarRealms;

/**
 * Класс, описывающий свойства карты.
 * Используется для описания карт кораблей, для описания баз используется наследующий класс Base.
 * @see Base
 */
public class Card extends Actor {
    /** Цена карты в торговом ряду*/
    private int cost = 0;
    /** Название карты */
    private String name;
    /** Спрайт карты */
    private Sprite image;
    /** Фракция карты */
    private Faction faction;
    /** Основной эффект */
    private CardEffect mainEffect = new CardEffect();
    /** Союзный эффект */
    private CardEffect allyEffect;
    /** Эффект при отправке карты в утиль */
    private CardEffect trashEffect;
    /** Имя текстуры */
    private String textureName;
    /** Спрайт */
    private Drawable sprite;

    Card()
    {
        Texture texture = StarRealms.assets.getTexture(textureName);
        sprite = new TextureRegionDrawable(texture);
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
    }

    // Для тестирования, потом удалить
    public Card(String textureName)
    {
        Texture texture = StarRealms.assets.getTexture(textureName);
        sprite = new TextureRegionDrawable(texture);
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setScale(0.8f);
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public Faction getFaction() {
        return faction;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
    }

    /** Разыграть карту из руки и активировать её эффекты при разыгрывании */
    public void play()
    {
        clearListeners(); // Очистить список слушателей, чтобы разыгранную карту нельзя было разыграть ещё раз

        setScale(0.7f);
        int playedCards = StarRealms.playerState.playedCards.size();
        setPosition(playedCards * getWidth() * getScaleX(), getHeight() * 0.8f, Align.bottomLeft); // Расположить карту над рукой. Надо бы сделать покрасивше
        StarRealms.playerState.playedCards.add(this);
        StarRealms.playerState.hand.remove(this);

        addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) { // Теперь карта это кнопка, нажатие которой вызывает ещё 4 кнопки - основной, союзный и эффект утиля, а также кнопка отмены
                super.clicked(event, x, y);
                Skin skin = StarRealms.assets.getSkin();

                int width = Gdx.graphics.getWidth();
                int height = Gdx.graphics.getHeight();

                final TextButton main = new TextButton(mainEffect.getEffectText(), skin);
                main.setPosition(width/2f, height/2f + 100, Align.center);
                main.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        mainEffect.activate(); // Добавленной кнопки добавляется соответствующий функционал
                    }
                });
                StarRealms.stage.addActor(main);

                final TextButton ally = new TextButton(allyEffect != null ? allyEffect.getEffectText() : "There is no ally effect on this card.", skin);
                ally.setPosition(width/2f, height/2f + 50, Align.center);
                if (allyEffect != null)
                    ally.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            allyEffect.activate();
                        }
                    });
                else
                    ally.setColor(new Color(1,1,1,0.5f));
                StarRealms.stage.addActor(ally);


                final TextButton trash = new TextButton(trashEffect != null ? trashEffect.getEffectText() : "There is no trash effect on this card.", skin);
                trash.setPosition(width/2f, height/2f, Align.center);
                if (trashEffect != null)
                    trash.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            trashEffect.activate();
                        }
                    });
                else
                    trash.setColor(new Color(1,1,1,0.5f));
                StarRealms.stage.addActor(trash);

                final TextButton cancel = new TextButton("Cancel", skin);
                cancel.setPosition(width/2f, height/2f - 50, Align.center);
                cancel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        main.remove(); // Кнопка отмены убирает все остальные кнопки
                        ally.remove();
                        trash.remove();
                        cancel.remove();
                    }
                });
                StarRealms.stage.addActor(cancel);
            }
        });
    }
}