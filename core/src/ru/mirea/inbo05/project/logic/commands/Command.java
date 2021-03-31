package ru.mirea.inbo05.project.logic.commands;

/**
 * Интерфейс команд.
 * Командами являются все действия игрок (Розыгрыш или покупка карты, завершение хода и т.д.), а также эффекты разыгранных карт.
 */
public interface Command {
    /** Метод, исполняющий команду */
    void execute();
}
