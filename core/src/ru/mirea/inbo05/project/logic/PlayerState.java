package ru.mirea.inbo05.project.logic;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import ru.mirea.inbo05.project.StarRealms;
import ru.mirea.inbo05.project.logic.cards.Base;
import ru.mirea.inbo05.project.logic.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
    /** Здоровье игрока */
    private int health = 50;
    /** Очки торговли игрока */
    private int money = 0;
    /** Очки атаки игрока */
    private int attack = 0;
    /** Колода игрока */
    public List<Card> deck = new ArrayList<>();
    /** Сброс игрока */
    public List<Card> discardDeck = new ArrayList<>();
    /** Рука игрока */
    public List<Card> hand = new ArrayList<>();
    /** Разыгранные карты игрока */
    public List<Card> playedCards = new ArrayList<>();
    /** Разыгранные базы игрока */
    public List<Base> bases = new ArrayList<>();

    /** Группа элементов в руке */
    public Group handGroup;
    /** Группа элементов среди разыгранных карт */
    public Group playedCardsGroup;
    /** Группа элементов среди баз */
    public Group basesGroup;

    public PlayerState() {
        handGroup = new Group();
        playedCardsGroup = new Group();
        basesGroup = new Group();
        StarRealms.stage.addActor(handGroup);
        StarRealms.stage.addActor(playedCardsGroup);
        StarRealms.stage.addActor(basesGroup);
    }

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
        card.remove();
    }

    /** Перемешать сброс */
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

    /** Вытянуть карту из колоды */
    public void draw() {
        if (deck.isEmpty()) {
            shuffle();
            deck.addAll(discardDeck);
            discardDeck.clear();
        }
        final Card card = deck.get(0);
        card.setScale(0.8f);
        hand.add(card);
        deck.remove(0);

        card.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                card.play();
                repositionCardsInHand();
            }
        });

        repositionCardsInHand();

        handGroup.addActor(card);
    }

    /** Разместить карты в руке по порядку */
    void repositionCardsInHand()
    {
        int index = 0;
        for (Card card : hand)
        {
            card.setPosition(index * card.getWidth() * card.getScaleX(), 0, Align.bottomLeft);
            index++;
        }
    }
}
