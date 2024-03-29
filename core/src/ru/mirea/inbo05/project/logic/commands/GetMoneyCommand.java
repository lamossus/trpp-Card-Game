package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.StarRealms;

/** Команда получения очков торговли */
public class GetMoneyCommand implements Command {
    /** Количество получаемых очков торговли */
    private int money;

    /**
     *
     * Добавляет игроку монетки
     */
    @Override
    public boolean execute() {
        StarRealms.setMoney(StarRealms.playerState.getMoney() + money);
        return true;
    }
}