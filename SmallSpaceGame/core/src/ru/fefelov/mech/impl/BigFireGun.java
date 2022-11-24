package ru.fefelov.mech.impl;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import ru.fefelov.math.Rect;
import ru.fefelov.mech.Gun;
import ru.fefelov.pools.impl.BulletPool;
import ru.fefelov.sprite.Sprite;

public class BigFireGun extends Gun {

    private final float bulletSpeed = 0.14f;


    public BigFireGun (BulletPool pool, boolean isAlly, TextureAtlas atlas, Rect worldbounds, Sprite owner, Sound shootSound, Sound hitSound, int damageMul){
        setDamage(3+damageMul);
        setSpeed(new Vector2(0f, isAlly ? bulletSpeed : -bulletSpeed));
        setRows(4);
        setCols(8);
        setFrames(29);
        setWorldBounds(worldbounds);
        setBulletHeight(0.04f);
        setOwner(owner);
        setPool(pool);
        setTextureRegion(atlas.findRegion("bullet"));
        setFirstFrame(0);
        setSound(shootSound);
        setBlowSizeCoef(1.15f);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (!readyToShoot){
                    readyToShoot = true;
                }
            }
        }, 0, 1.8f);
        setTimer(timer);
        setHitSound(hitSound);
    }
}