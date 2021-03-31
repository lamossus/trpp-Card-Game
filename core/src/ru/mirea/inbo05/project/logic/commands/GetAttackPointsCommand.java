package ru.mirea.inbo05.project.logic.commands;

/** Команда получения очков атаки */
public class GetAttackPointsCommand implements Command {
    /** Количество получаемых очков атаки*/
    private int attackPoints;

    public int getAttackPoints() {
        return attackPoints;
    }

    @Override
    public void execute() {

    }
}
