package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.logic.cards.CardInfo;

import java.util.ArrayList;

public class ScrapCommand implements Command {
    int mask; // 1 - рука, 2 - сброс, 4 - торговый ряд
    boolean may;

    @Override
    public boolean execute() {
        ArrayList<CardInfo> cardsToChooseFrom = new ArrayList<>();

        return true;
    }
}
