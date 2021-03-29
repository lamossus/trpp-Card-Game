package ru.mirea.inbo05.project.logic.commands;

public class HealCommand implements Command {
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