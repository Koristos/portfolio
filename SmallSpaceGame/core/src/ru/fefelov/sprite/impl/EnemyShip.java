package ru.fefelov.sprite.impl;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.mech.Gun;
import ru.fefelov.sprite.Ship;

public class EnemyShip extends Ship {

    private Vector2 speed;
    private Vector2 move;
    private int moveType;

    public EnemyShip(Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.move = new Vector2(0,0);
        this.regions = new TextureRegion[1];

    }

    @Override
    public void update(float delta) {
        if (isBlowing){
            if (this.frame < this.explosions.length-1){
                frame++;
            } else {
               this.destroy();
            }
        }else {
            calculateMove();
            this.pos.mulAdd(move, delta);
            if (isOutside(worldBounds)) {
                destroy();
                this.gun.disable();
            }
            if (this.getTop()< worldBounds.getTop()){
                shoot();
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public void setProp(Vector2 speed,
                        int moveType,
                        TextureRegion region,
                        float height,
                        int hp,
                        Gun gun,
                        TextureRegion[] explosion
    ) {
        this.speed = speed;
        this.moveType = moveType;
        this.regions = new TextureRegion[1];
        this.regions[0] = region;
        this.frame = 0;
        setHeightProportion(height);
        this.hp = hp;
        this.gun = gun;
        this.explosions = explosion;
        this.isBlowing = false;
    }

    private void calculateMove(){
        switch (moveType){
            case 1:
                if (this.pos.x < (worldBounds.getLeft() + this.getWidth()+0.01) ||
                        this.pos.x > (worldBounds.getRight() - this.getWidth()-0.01)) {
                    this.speed.x = this.speed.x * (-1);
                }
                if (!speed.equals(move)){
                    move.set(speed);
                }
                break;
            case 2:
                if (this.pos.x < (worldBounds.getLeft() + this.getWidth()+0.01) ||
                        this.pos.x > (worldBounds.getRight() - this.getWidth()-0.01)) {
                    this.speed.x = this.speed.x * (-1);
                }
                float y = speed.y;
                if (this.pos.x <0){
                    y = y * ((worldBounds.getLeft()/speed.x));
                    if (y>0){
                        y= y*(-1);
                    }
                }else if (this.pos.x > 0){
                    y = y * ((worldBounds.getRight()/speed.x));
                    if (y>0){
                        y= y*(-1);
                    }
                }
                move.set(speed.x, y);
                break;
        }

        if (this.getTop() > worldBounds.getTop()){
            move.y = -0.2f;
        }
    }
}
