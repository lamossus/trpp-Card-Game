package ru.mirea.inbo05.project.logic;

import ru.mirea.inbo05.project.logic.cards.Card;

import java.util.List;

public class GameState {
    private List<Card> tradeDeck;
    private Card[] tradeRow = new Card[5];
    private List<Card> trash;
    private int explorerQuantity;
}
