package ru.mirea.inbo05.project.logic.commands;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import ru.mirea.inbo05.project.StarRealms;
import ru.mirea.inbo05.project.logic.cards.CardInfo;

import java.util.HashMap;
import java.util.Map;

public class ScrapCommand implements Command {
    int mask; // 1 - рука, 2 - сброс, 4 - торговый ряд
    boolean may;

    @Override
    public boolean execute() {
        Map<CardInfo, Integer> cardsToChooseFrom = new HashMap<>();
        if ((mask & 1) == 1)
        {
            for (CardInfo cardInfo : StarRealms.playerState.hand)
                cardsToChooseFrom.put(cardInfo, 1);
        }
        if ((mask & 2) == 2)
        {
            for (CardInfo cardInfo : StarRealms.playerState.discardDeck)
                cardsToChooseFrom.put(cardInfo, 2);
        }
        if ((mask & 4) == 4)
        {
            for (CardInfo cardInfo : StarRealms.gameState.tradeRow)
                cardsToChooseFrom.put(cardInfo, 4);
        }

        if (cardsToChooseFrom.size() == 0)
            return true;

        for (Actor actor : StarRealms.stage.getActors())
            actor.setTouchable(Touchable.disabled);

        Table scrollTable = new Table();
        final ScrollPane scrollPane = new ScrollPane(scrollTable, StarRealms.assets.getSkin());
        for (final Map.Entry<CardInfo, Integer> cardInfo : cardsToChooseFrom.entrySet())
        {
            final ImageButton card = new ImageButton(new TextureRegionDrawable(StarRealms.assets.getTexture(cardInfo.getKey().textureName)));
            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    StarRealms.gameState.trash.add(cardInfo.getKey());
                    if (cardInfo.getValue() == 1)
                    {
                        StarRealms.playerState.hand.remove(cardInfo.getKey());
                        cardInfo.getKey().instance.remove();
                        StarRealms.playerState.repositionCardsInHand();
                    }
                    if (cardInfo.getValue() == 2)
                    {
                        StarRealms.playerState.discardDeck.remove(cardInfo.getKey());
                    }
                    if (cardInfo.getValue() == 4)
                    {
                        StarRealms.gameState.removeFromCardRow(cardInfo.getKey());
                    }
                    scrollPane.remove();
                    for (Actor actor : StarRealms.stage.getActors())
                        actor.setTouchable(Touchable.enabled);
                }
            });
            scrollTable.add(card);
            scrollTable.row();
        }
        scrollPane.setHeight(1080f);
        scrollPane.setWidth(1920f);
        StarRealms.stage.addActor(scrollPane);
        return true;
    }
}
