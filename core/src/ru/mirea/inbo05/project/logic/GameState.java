package ru.mirea.inbo05.project.logic;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import ru.mirea.inbo05.project.StarRealms;
import ru.mirea.inbo05.project.logic.cards.Card;

import java.util.List;
import java.util.Stack;

public class GameState {
    public Stack<Card> tradeDeck = new Stack<>();
    Card[] tradeRow = new Card[5];
    public List<Card> trash;
    private int explorerQuantity;

    /** Пополнить торговый ряд */
    public void Refill()
    {
        for (int i = 0; i < 5; i++)
        {
            if (tradeRow[i] == null)
            {
                final Card card = tradeDeck.pop();
                tradeRow[i] = card;
                card.setPosition(200 + i * 240, 540, Align.center);

                card.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        buy(card);
                    }
                });
                StarRealms.stage.addActor(card);
            }
        }

    }

    /** Удалить карту с торгового ряда */
    public void RemoveFromCardRow(Card card)
    {
        for (int i = 0; i < 5; i ++)
        {
            if (tradeRow[i] == card)
            {
                tradeRow[i] = null;
                break;
            }
        }
        Refill();
    }

    public void buy(Card card) {
        PlayerState playerState = StarRealms.playerState;
        if (card.getCost() <= playerState.getMoney()) {
            StarRealms.gameState.RemoveFromCardRow(card);

            playerState.Discard(card);
            playerState.setMoney(playerState.getMoney() - card.getCost());
        }
    }

    public int getExplorerQuantity() {
        return explorerQuantity;
    }
    public void setExplorerQuantity(int explorerQuantity) {
        this.explorerQuantity = explorerQuantity;
    }
}
