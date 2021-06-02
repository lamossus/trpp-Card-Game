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

import java.util.List;
import java.util.Stack;

public class GameState {
    /** Колода торгового ряда */
    public Stack<CardInfo> tradeDeck = new Stack<>();
    /** Торговый ряд */
    CardInfo[] tradeRow = new CardInfo[5];
    /** Карты в утиле */
    public List<CardInfo> trash;
    /** Количество доступных исследователей */
    private int explorerQuantity;

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
    public void removeFromCardRow(Card card)
    {
        for (int i = 0; i < 5; i ++)
        {
            if (tradeRow[i] == card.getCardInfo())
            {
                card.remove();
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
            StarRealms.gameState.removeFromCardRow(card);

            playerState.discard(card);
            playerState.setMoney(playerState.getMoney() - cost);
        }
    }

    public int getExplorerQuantity() {
        return explorerQuantity;
    }
    public void setExplorerQuantity(int explorerQuantity) {
        this.explorerQuantity = explorerQuantity;
    }
}
