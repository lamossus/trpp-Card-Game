package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.StarRealms;

/** Команда восстановления очков влияния игрока */
public class HealCommand implements Command {
    /** Количество восстанавливаемых очков здоровья */
    private int healAmount;

    /**
     * Метод добавляет очки здоровья игроку
     */
    @Override
    public boolean execute() {
        StarRealms.setHealth(StarRealms.playerState.getHealth() + healAmount);
        return true;
    }
}