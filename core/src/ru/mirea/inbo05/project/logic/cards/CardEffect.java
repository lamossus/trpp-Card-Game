package ru.mirea.inbo05.project.logic.cards;

import ru.mirea.inbo05.project.logic.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class CardEffect {
    /** Список выполняемых комманд */
    private List<Command> commands = new ArrayList<>();
    /** Использован ли этот эффект */
    private boolean isActive = true;
    /** Текст, отображаемый при активации */
    private String effectText = "Test effect";

    public void activate() {
        for (Command command : commands)
        {
            boolean success = false;
            while (!success)
            {
                success = command.execute();
            }
        }
    }
    public boolean isActive() {
        return isActive;
    }
    public String getEffectText() {
        return effectText;
    }
}
