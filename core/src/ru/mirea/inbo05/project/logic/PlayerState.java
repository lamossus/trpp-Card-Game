package ru.mirea.inbo05.project.logic;

import ru.mirea.inbo05.project.logic.cards.Base;
import ru.mirea.inbo05.project.logic.cards.Card;

import java.util.List;

public class PlayerState {
    private int health;
    private int money;
    private int attack;
    private List<Card> deck;
    private List<Card> discard;
    private List<Card> hand;
    private List<Card> playedCards;
    private List<Base> bases;
}
