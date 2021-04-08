package ru.mirea.inbo05.project.logic.commands;

/** Команда восстановления очков влияния игрока */
public class HealCommand implements Command {
    /** Количество восстанавливаемых очков здоровья */
    private int healAmount;

    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public void execute() {

    }
}