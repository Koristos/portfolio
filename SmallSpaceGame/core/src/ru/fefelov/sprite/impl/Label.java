package ru.fefelov.sprite.impl;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.sprite.Sprite;

public class Label extends Sprite {

    private final float HEIGHT = 0.15f;
    private final float INIT_HEIGHT = 0.5f;
    private boolean actiive = false;
    private Vector2 position;
    private Sound sound;

    public Label(TextureRegion region, Vector2 position, Sound sound) {
        super(region);
        this.position = position;
        this.sound = sound;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(INIT_HEIGHT);
        this.pos.set(position);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (actiive){
            if (getHeight() > HEIGHT){
                setHeightProportion(getHeight()-((INIT_HEIGHT-HEIGHT)/20));
            }
            super.draw(batch);
        }
    }

    public void activate(){
        this.actiive = true;
        sound.play();
    }

    public void  disable() {
        this.actiive = false;
        setHeightProportion(INIT_HEIGHT);
    }
}
