package ru.mirea.inbo05.project.logic;

import ru.mirea.inbo05.project.logic.cards.Base;
import ru.mirea.inbo05.project.logic.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
    private int health = 50;
    private int money = 0;
    private int attack = 0;
    public List<Card> deck;
    private List<Card> discardDeck = new ArrayList<>();
    public List<Card> hand;
    public List<Card> playedCards;
    public List<Base> bases;

    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }

    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /** Сбросить карту */
    public void discard(Card card)
    {
        card.clearListeners();
        discardDeck.add(card);
    }

    private void shuffle() {
        int index;
        Card temp;
        for (int i = 0; i < discardDeck.size(); i++) {
            index = (int) (Math.random() * (discardDeck.size() - i)) + i;
            temp = discardDeck.get(i);
            discardDeck.set(i, discardDeck.get(index));
            discardDeck.set(index, temp);
        }
    }

    public void draw() {
        if (deck.isEmpty()) {
            shuffle();
            deck.addAll(discardDeck);
            discardDeck.clear();
        }
        hand.set(hand.size(), deck.get(0));
        deck.remove(0);
    }
}
