package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.logic.cards.Base;

/** Команда для атаки вражеской базы */
public class AttackBaseCommand implements Command {
    /** Атакуемая база */
    private Base target;

    public Base getTarget() {
        return target;
    }

    @Override
    public void execute() {

    }
}
