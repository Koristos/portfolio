package ru.fefelov.sprite.impl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.fefelov.math.Rect;
import ru.fefelov.screen.impl.GameScreen;
import ru.fefelov.sprite.BaseButton;

public class ButtonPlay extends BaseButton {

    private final float HEIGHT = 0.2f;
    private final float MARGIN = 0.05f;
    private final Game game;

    public ButtonPlay (TextureAtlas atlas, Game game){
        super(atlas.findRegion("play1"), atlas.findRegion("play2"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setLeft(worldBounds.getLeft()+ MARGIN);
        setBottom(worldBounds.getBottom()+ MARGIN);
        super.resize(worldBounds);
    }

    @Override
    public void action() {
        game.getScreen().dispose();
        game.setScreen(new GameScreen());
    }
}
