package ru.mirea.inbo05.project.logic.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.mirea.inbo05.project.logic.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class CardEffect {
    /** Список выполняемых комманд */
    private List<Command> commands = new ArrayList<>();
    /** Использован ли этот эффект */
    @JsonIgnore
    private boolean active = true;
    /** Текст, отображаемый при активации */
    private String effectText = "Test effect";

    /** Активировать все эффекты карты */
    public void activate() {
        for (Command command : commands) // Выполнять команду, пока она не завершится успешно
        {
            boolean success = false;
            while (!success)
            {
                success = command.execute();
            }
        }
        active = false;
    }
    public String getEffectText() {
        return effectText;
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
