package ru.fefelov.sprite.impl;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.screen.impl.GameScreen;

public class TrackingStar extends Star{

    private final Vector2 sumSpeed = new Vector2();
    private final Vector2 speed = new Vector2();
    private final GameScreen screen;

    public TrackingStar(TextureAtlas atlas, String[] textureArray, GameScreen screen) {
        super(atlas, textureArray);
        this.screen = screen;
    }

    @Override
    public void update(float delta) {
        speed.set(screen.getUfoXmove(), 0);
        sumSpeed.setZero().mulAdd(speed, 14f).rotateDeg(180f).add(v);
        pos.mulAdd(sumSpeed, delta);
        checkBounds();
    }
}
