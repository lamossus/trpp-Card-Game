package ru.mirea.inbo05.project.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.mirea.inbo05.project.StarRealms;
import ru.mirea.inbo05.project.logic.cards.Card;
import ru.mirea.inbo05.project.logic.cards.CardInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameState {
    /** Колода торгового ряда */
    public Stack<CardInfo> tradeDeck = new Stack<>();
    /** Торговый ряд */
    public CardInfo[] tradeRow = new CardInfo[5];
    /** Карты в утиле */
    public List<CardInfo> trash = new ArrayList<>();
    /** Количество доступных исследователей */
    private int explorerQuantity = 12;
    /** Шаблон исследователя */
    private CardInfo explorerTemplate;

    /** Группа элементов торгового ряда */
    @JsonIgnore
    public Group tradeRowGroup;

    public GameState() {
        tradeRowGroup = new Group();
        StarRealms.stage.addActor(tradeRowGroup);
    }

    /** Пополнить торговый ряд */
    public void refill()
    {
        int height = Gdx.graphics.getHeight();

        for (int i = 0; i < 5; i++)
        {
            if (tradeRow[i] == null)
            {
                tradeRow[i] = tradeDeck.pop();
                final Card card = tradeRow[i].CreateInstance();
                card.setPosition(i * card.getWidth() * card.getScaleX(), height + (1 - card.getScaleY()) * card.getHeight(), Align.topLeft);

                card.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buy(card);
                    }
                });
                tradeRowGroup.addActor(card);
            }
        }

    }

    /** Удалить карту с торгового ряда */
    public void removeFromCardRow(CardInfo card)
    {
        for (int i = 0; i < 5; i ++)
        {
            if (tradeRow[i] == card)
            {
                card.instance.remove();
                tradeRow[i] = null;
                break;
            }
        }
        refill();
    }

    /** Купить карту из торгового ряда */
    public void buy(Card card) {
        PlayerState playerState = StarRealms.playerState;
        int cost = card.getCardInfo().cost;
        if (cost <= playerState.getMoney()) {
            StarRealms.gameState.removeFromCardRow(card.getCardInfo());

            playerState.discard(card);
            StarRealms.setMoney(playerState.getMoney() - cost);
        }
    }

    /**
     * Перемешивание трейд ряда
     */
    public void shuffle() {
        int index;
        CardInfo temp;
        for (int i = 0; i < tradeDeck.size(); i++) {
            index = (int) (Math.random() * (tradeDeck.size() - i)) + i;
            temp = tradeDeck.get(i);
            tradeDeck.set(i, tradeDeck.get(index));
            tradeDeck.set(index, temp);
        }
    }

    public int getExplorerQuantity() {
        return explorerQuantity;
    }

    public CardInfo getExplorerTemplate() {
        return explorerTemplate;
    }

    public void setExplorerQuantity(int explorerQuantity) {
        this.explorerQuantity = explorerQuantity;
    }
}
