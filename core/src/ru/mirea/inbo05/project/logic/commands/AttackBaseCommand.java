package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.logic.cards.Base;

public class AttackBaseCommand implements Command {
    private Base target;

    public Base getTarget() {
        return target;
    }

    @Override
    public void execute() {

    }
}
