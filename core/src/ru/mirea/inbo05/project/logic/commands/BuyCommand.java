package ru.mirea.inbo05.project.logic.commands;

import ru.mirea.inbo05.project.StarRealms;
import ru.mirea.inbo05.project.logic.PlayerState;
import ru.mirea.inbo05.project.logic.cards.Card;

public class BuyCommand implements Command{
    private int cost;
    private Card owner;

    public BuyCommand(Card card)
    {
        owner = card;
    }

    @Override
    public void execute() {
        PlayerState playerState = StarRealms.playerState;
        if (owner.getCost() <= playerState.getMoney()) {
            StarRealms.gameState.RemoveFromCardRow(owner);

            playerState.Discard(owner);
            playerState.setMoney(playerState.getMoney() - owner.getCost());
        }
    }
}
