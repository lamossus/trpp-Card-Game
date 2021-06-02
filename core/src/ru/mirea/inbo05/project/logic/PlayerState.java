package ru.mirea.inbo05.project.logic;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.mirea.inbo05.project.StarRealms;
import ru.mirea.inbo05.project.logic.cards.Base;
import ru.mirea.inbo05.project.logic.cards.BaseInfo;
import ru.mirea.inbo05.project.logic.cards.Card;
import ru.mirea.inbo05.project.logic.cards.CardInfo;

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
    public ArrayList<CardInfo> deck = new ArrayList<>();
    /** Сброс игрока */
    public ArrayList<CardInfo> discardDeck = new ArrayList<>();
    /** Рука игрока */
    public ArrayList<CardInfo> hand = new ArrayList<>();
    /** Разыгранные карты игрока */
    public ArrayList<CardInfo> playedCards = new ArrayList<>();
    /** Разыгранные базы игрока */
    public ArrayList<BaseInfo> bases = new ArrayList<>();
    public boolean yourTurn = false;

    /** Группа элементов в руке */
    @JsonIgnore
    public Group handGroup;
    /** Группа элементов среди разыгранных карт */
    @JsonIgnore
    public Group playedCardsGroup;
    /** Группа элементов среди баз */
    @JsonIgnore
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
        discardDeck.add(card.getCardInfo());
        card.getCardInfo().instance = null;
        card.remove();
    }

    /** Перемешать сброс */
    private void shuffle() {
        int index;
        CardInfo temp;
        for (int i = 0; i < discardDeck.size(); i++) {
            index = (int) (Math.random() * (discardDeck.size() - i)) + i;
            temp = discardDeck.get(i);
            discardDeck.set(i, discardDeck.get(index));
            discardDeck.set(index, temp);
        }
    }

    /** Вытянуть карту из колоды */
    public void draw() {
        if (deck.isEmpty() && discardDeck.isEmpty())
            return;

        if (deck.isEmpty()) {
            shuffle();
            deck.addAll(discardDeck);
            discardDeck.clear();
        }
        CardInfo cardInfo = deck.get(0);
        final Card card = cardInfo.CreateInstance();
        hand.add(cardInfo);
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
    public void repositionCardsInHand()
    {
        int index = 0;
        for (CardInfo card : hand)
        {
            Card cardInstance = card.instance;
            cardInstance.setPosition(index * cardInstance.getWidth() * cardInstance.getScaleX(), 0, Align.bottomLeft);
            index++;
        }
    }
}
