package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.StarRealms;

/** Команда получения очков атаки */
public class GetAttackPointsCommand implements Command {
    /** Количество получаемых очков атаки*/
    private int attackPoints;

    @Override
    public void execute() {
        StarRealms.playerState.setAttack(StarRealms.playerState.getAttack() + attackPoints);
    }
}
