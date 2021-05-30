package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.StarRealms;
import ru.mirea.inbo05.project.logic.PlayerState;
import ru.mirea.inbo05.project.logic.cards.Base;

/** Команда для атаки вражеской базы */
public class AttackBaseCommand implements Command {
    /** Атакуемая база */
    private Base target;

    public Base setTarget() {
        return target;
    }

    @Override
    public boolean execute() {
        PlayerState enemyState = StarRealms.enemyState;
        PlayerState playerState = StarRealms.playerState;

        for (int i = 0; i < enemyState.bases.size(); i++)
        {
            Base base = enemyState.bases.get(i);
            if (base.isTaunt() && base != target)
                return false;
        }

        if (target.getHealth() <= playerState.getAttack())
        {
            target.remove();
            enemyState.bases.remove(target);
            enemyState.discard(target);

            playerState.setAttack(playerState.getAttack() - target.getHealth());
            return true;
        }

        return false;
    }
}
