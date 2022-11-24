package ru.fefelov.sprite.impl;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.sprite.Sprite;

public class Bullet extends Sprite {

    private final Vector2 v = new Vector2();

    private Rect worldBounds;
    private int damage;
    private Sprite owner;
    private boolean isBlowing;
    private float blowSizeCoef;
    private Sound hitSound;

    public Bullet(){
    }

    public void set(
            Sprite owner,
            TextureRegion regions [],
            Vector2 pos,
            Vector2 v,
            float height,
            Rect worldBounds,
            int damage,
            int firstFrame,
            float blowSizeCoef,
            Sound hitSound
    ) {
        this.owner = owner;
        this.regions = regions;
        this.pos.set(pos);
        this.v.set(v);
        this.frame = firstFrame;
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
        this.isBlowing = false;
        this.blowSizeCoef = blowSizeCoef;
        this.hitSound = hitSound;
    }

    public void blow(){
        setHeightProportion(getHeight()*blowSizeCoef);
        this.hitSound.play();
        this.isBlowing = true;
    }

    @Override
    public void update(float delta) {
        if (isBlowing){
            if (this.frame < this.regions.length-1){
                this.frame++;
            }else {
                destroy();
            }
        }else {
            this.pos.mulAdd(v, delta);
            if (isOutside(worldBounds)) {
                destroy();
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }

    public boolean isBlowing(){
        return this.isBlowing;
    }

}
