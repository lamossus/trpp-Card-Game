package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.StarRealms;
import ru.mirea.inbo05.project.logic.PlayerState;
import ru.mirea.inbo05.project.logic.cards.Base;
import ru.mirea.inbo05.project.logic.cards.BaseInfo;

// TODO командами должны быть только возможные эффекты карт, соответственно это всё надо переделать в метод GameState или PlayerState

/** Команда для атаки вражеской базы */
public class AttackBaseCommand implements Command {
    /** Атакуемая база */
    private BaseInfo target;

    public void setTarget(BaseInfo baseInfo) {
        target = baseInfo;
    }

    @Override
    public boolean execute() {
        PlayerState enemyState = StarRealms.enemyState;
        PlayerState playerState = StarRealms.playerState;

        for (int i = 0; i < enemyState.bases.size(); i++)
        {
            BaseInfo base = enemyState.bases.get(i);
            if (base.isTaunt && base != target)
                return false;
        }

        if (target.health <= playerState.getAttack())
        {
            target.instance.remove();
            enemyState.bases.remove(target);
            enemyState.discard(target.instance);

            playerState.setAttack(playerState.getAttack() - target.health);
            return true;
        }

        return false;
    }
}
