package ru.mirea.inbo05.project.logic.commands;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Интерфейс команд.
 * Командами являются все действия игрока (Розыгрыш или покупка карты, завершение хода и т.д.), а также эффекты разыгранных карт.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
public interface Command {
    /** Метод, исполняющий команду */
    boolean execute();
}
