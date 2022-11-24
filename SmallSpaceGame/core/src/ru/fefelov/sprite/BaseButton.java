package ru.fefelov.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButton extends Sprite{

    private static final float SCALE = 0.9f;

    private int pointer;
    private boolean pressed;
    private final TextureRegion freeRegion;
    private final TextureRegion pressedRegion;

    public BaseButton(TextureRegion freeRegion, TextureRegion pressedRegion) {
        super(freeRegion);
        this.freeRegion = freeRegion;
        this.pressedRegion = pressedRegion;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (isMe(touch)){
            this.pointer = pointer;
            this.pressed = true;
            this.scale = SCALE;
            this.regions[0] = pressedRegion;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed){
            return false;
        }
        if (isMe(touch)){
            action();
        }
        pressed = false;
        scale = 1f;
        this.regions[0] = freeRegion;
        return false;
    }

    public abstract void action();
}
