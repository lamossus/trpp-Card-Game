package ru.mirea.inbo05.project.logic.commands;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ru.mirea.inbo05.project.StarRealms;
import ru.mirea.inbo05.project.logic.PlayerState;
import ru.mirea.inbo05.project.logic.cards.Card;
import ru.mirea.inbo05.project.logic.cards.CardInfo;

/**
 * Класс команды вытягивания карты
 */
public class DrawCommand implements Command{
    @Override
    public boolean execute() {
        StarRealms.playerState.draw();
        return true;
    }
}
