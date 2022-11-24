package ru.fefelov.mech.impl;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import ru.fefelov.math.Rect;
import ru.fefelov.mech.Gun;
import ru.fefelov.pools.impl.BulletPool;
import ru.fefelov.sprite.Sprite;
import ru.fefelov.sprite.impl.Bullet;
import ru.fefelov.utils.Regions;

public class BossFireGun extends Gun {

    private final Vector2 bulletOneSpeed = new Vector2(0, -0.14f);
    private final Vector2 bulletTwoSpeed = new Vector2(0.05f, -0.1f);
    private final Vector2 bulletThreeSpeed = new Vector2(-0.05f, -0.1f);


    public BossFireGun (BulletPool pool, boolean isAlly, TextureAtlas atlas, Rect worldbounds, Sprite owner, Sound shootSound, Sound hitSound, int damageMul){
        setDamage(5+damageMul);
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

    @Override
    public void shoot() {
        if (readyToShoot){
            Bullet bulletOne = pool.obtain();
            bulletOne.set(owner, Regions.split(textureRegion, rows, cols, frames), owner.getPosition(),
                    bulletOneSpeed, bulletHeight, worldBounds, damage, firstFrame, blowSizeCoef, hitSound);
            Bullet bulletTwo = pool.obtain();
            bulletTwo.set(owner, Regions.split(textureRegion, rows, cols, frames), owner.getPosition(),
                    bulletTwoSpeed, bulletHeight, worldBounds, damage, firstFrame, blowSizeCoef, hitSound);
            Bullet bulletThree = pool.obtain();
            bulletThree.set(owner, Regions.split(textureRegion, rows, cols, frames), owner.getPosition(),
                    bulletThreeSpeed, bulletHeight, worldBounds, damage, firstFrame, blowSizeCoef, hitSound);
            sound.play();
            this.readyToShoot = false;
        }
    }
}