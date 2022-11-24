package ru.fefelov.sprite.impl;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.fefelov.math.Rect;
import ru.fefelov.sprite.Sprite;

public class Title extends Sprite {

    private final float HEIGHT = 0.3f;
    private final float MARGIN = 0.05f;

    public Title(TextureAtlas atlas) {
        super(atlas.findRegion("title"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setTop(worldBounds.getTop()-MARGIN);
    }
}
