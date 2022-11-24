package ru.fefelov.sprite.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.sprite.Sprite;

public class UfoMoveFlame extends Sprite {

    private final float MIN_HEIGHT = 0.06f;
    private final float MAX_HEIGHT = 0.08f;

    private final DIRECTION direction;
    private float currentSize;
    private boolean isGrowing = true;
    private Rect worldBounds;

    public UfoMoveFlame(TextureAtlas atlas, DIRECTION direction) {
        super(atlas.findRegion("comet"));
        this.direction = direction;
        setAngle(direction.value);
        this.currentSize = MIN_HEIGHT;
    }

    @Override
    public void resize(final Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(worldBounds.getHeight() * MIN_HEIGHT);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void draw(SpriteBatch batch, Vector2 move) {
        switch (this.direction) {
            case LEFT:
                if (move.x > 0 && Math.abs(move.x/move.y)>0.5f) {
                    draw(batch);
                }
                break;
            case RIGHT:
                if (move.x < 0 && Math.abs(move.x/move.y)>0.5f) {
                    draw(batch);
                }
                break;
            case CENTER:
                if (move.y > 0) {
                    draw(batch);
                }
                break;
        }
        flameResize();
    }

    public void setPosition(Vector2 position) {
        switch (direction) {
            case LEFT:
                setRight(position.x);
                break;
            case RIGHT:
                setLeft(position.x);
                break;
            case CENTER:
                this.pos.x = position.x;
                break;
        }
        setTop(position.y);
    }

    private void flameResize() {
        if (isGrowing) {
            currentSize = currentSize + (MAX_HEIGHT - MIN_HEIGHT) / 20;
            if (currentSize > MAX_HEIGHT) {
                isGrowing = false;
            }
        } else {
            currentSize = currentSize - (MAX_HEIGHT - MIN_HEIGHT) / 20;
            if (currentSize < MIN_HEIGHT) {
                isGrowing = true;
            }
        }
        setHeightProportion(worldBounds.getHeight() * currentSize);
    }

    enum DIRECTION {
        RIGHT(-70),
        LEFT(190),
        CENTER(-120);

        private final float value;

        DIRECTION(int value) {
            this.value = value;
        }
    }
}


