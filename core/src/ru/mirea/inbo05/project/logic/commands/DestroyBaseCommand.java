package ru.mirea.inbo05.project.logic.commands;

import com.badlogic.gdx.Gdx;
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


public class DestroyBaseCommand implements Command {

    @Override
    public boolean execute() {
        for (Actor actor : StarRealms.stage.getActors())
            actor.setTouchable(Touchable.disabled);

        Table scrollTable = new Table();
        final ScrollPane scrollPane = new ScrollPane(scrollTable, StarRealms.assets.getSkin());
        for (final CardInfo cardInfo : StarRealms.enemyState.bases) {
            final ImageButton card = new ImageButton(new TextureRegionDrawable(StarRealms.assets.getTexture(cardInfo.textureName)));
            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    StarRealms.enemyState.discardDeck.add(cardInfo);
                    StarRealms.enemyState.bases.remove(cardInfo);

                    StarRealms.placeBases();

                    scrollPane.remove();
                    for (Actor actor : StarRealms.stage.getActors())
                        actor.setTouchable(Touchable.enabled);
                }
            });
            scrollTable.add(card);
            scrollTable.row();
        }

        scrollPane.setHeight(Gdx.graphics.getHeight());
        scrollPane.setWidth(Gdx.graphics.getWidth());
        StarRealms.stage.addActor(scrollPane);
        return true;
    }
}