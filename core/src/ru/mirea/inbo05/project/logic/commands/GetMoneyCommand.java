package ru.mirea.inbo05.project.logic.commands;

/** Команда получения очков торговли */
public class GetMoneyCommand implements Command {
    /** Количество получаемых очков торговли */
    private int money;

    public int getMoney() {
        return money;
    }

    @Override
    public void execute() {

    }
}