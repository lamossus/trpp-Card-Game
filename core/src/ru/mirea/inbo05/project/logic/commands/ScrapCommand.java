package ru.mirea.inbo05.project.logic.commands;

public class ScrapCommand implements Command {
    int mask; // 1 - рука, 2 - сброс, 4 - торговый ряд
    boolean may;

    @Override
    public boolean execute() {
        return true;
    }
}
