package ru.mirea.inbo05.project.logic.cards;

import com.badlogic.gdx.graphics.g2d.Sprite;
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

    /*
    Тут должны быть конструкторы, наверно...
     */

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public Faction getFaction() {
        return faction;
    }
}
