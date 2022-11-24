package ru.fefelov.sprite.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.fefelov.math.Rect;
import ru.fefelov.sprite.BaseButton;

public class ButtonQuit extends BaseButton {

    private final float HEIGHT = 0.2f;
    private final float MARGIN = 0.05f;

    public ButtonQuit (TextureAtlas atlas){
        super(atlas.findRegion("quit1"), atlas.findRegion("quit2"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setRight(worldBounds.getRight()- MARGIN);
        setBottom(worldBounds.getBottom()+ MARGIN);
        super.resize(worldBounds);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
