package ru.mirea.inbo05.project.logic;

import ru.mirea.inbo05.project.logic.cards.Base;
import ru.mirea.inbo05.project.logic.cards.Card;

import java.util.List;

public class PlayerState {
    private int health;
    private int money;
    private int attack;
    public List<Card> deck;
    public List<Card> discard;
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


}
