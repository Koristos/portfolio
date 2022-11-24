package ru.fefelov.sprite.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.fefelov.math.Rect;
import ru.fefelov.screen.impl.GameScreen;
import ru.fefelov.sprite.BaseButton;

public class ButtonNewGame extends BaseButton {

    private final float HEIGHT = 0.18f;
    private final float MARGIN = 0.15f;
    private GameScreen screen;
    private boolean active = false;

    public ButtonNewGame(TextureRegion freeRegion, TextureRegion pressedRegion, GameScreen screen) {
        super(freeRegion, pressedRegion);
        this.screen = screen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom()+ MARGIN);
        super.resize(worldBounds);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (active){
            super.draw(batch);
        }
    }

    @Override
    public void action() {
        if (active){
            screen.newGame();
        }
    }

    public void enable(){
        this.active = true;
    }

    public void disable(){
        this.active = false;
    }
}
