package ru.mirea.inbo05.project.logic.cards;

import ru.mirea.inbo05.project.logic.commands.Command;

import java.util.List;

public class Card {
    private int cost;
    private String name;
    private Sprite image;
    private Faction faction;
    private List<Command> mainEffect;
    private List<Command> allyEffect;
    private List<Command> trashEffect;
    private boolean isBase;

    /*
    Тут должны быть конструкторы, наверно...
     */

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public Sprite getImage() {
        return image;
    }

    public Faction getFaction() {
        return faction;
    }

    public List<Command> getMainEffect() {
        return mainEffect;
    }
    public List<Command> getAllyEffect() {
        return allyEffect;
    }

    public List<Command> getTrashEffect() {
        return trashEffect;
    }

    public boolean isBase() {
        return isBase;
    }
}
