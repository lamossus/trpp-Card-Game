package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.StarRealms;

/** Команда получения очков атаки */
public class GetAttackPointsCommand implements Command {
    /** Количество получаемых очков атаки*/
    private int attackPoints;


    /**
     * Добавление игроку очков атаки, полученных от разыгранных карт
     *
     */
    @Override
    public boolean execute() {
        StarRealms.setAttack(StarRealms.playerState.getAttack() + attackPoints);
        return true;
    }
}
