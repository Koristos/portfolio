package ru.fefelov.mech.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.util.LinkedList;
import java.util.List;

import ru.fefelov.math.Rect;
import ru.fefelov.mech.Gun;
import ru.fefelov.pools.impl.BulletPool;
import ru.fefelov.sprite.Sprite;

public class PlasmGun extends Gun {

    private final float bulletSpeed = 0.4f;


    public PlasmGun(BulletPool pool, boolean isAlly, TextureAtlas atlas, Rect worldbounds, Sprite owner){
        setDamage(1);
        setSpeed(new Vector2(0f, isAlly ? bulletSpeed : -bulletSpeed));
        setRows(4);
        setCols(5);
        setFrames(20);
        setWorldBounds(worldbounds);
        setBulletHeight(0.15f);
        setOwner(owner);
        setPool(pool);
        setTextureRegion(atlas.findRegion("plasm"));
        setFirstFrame(1);
        setSound(Gdx.audio.newSound(Gdx.files.internal("music/plasm.mp3")));
        setBlowSizeCoef(1);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (!readyToShoot){
                    readyToShoot = true;
                }
            }
        }, 0, 0.3f);
        setTimer(timer);
        setHitSound(Gdx.audio.newSound(Gdx.files.internal("music/plasmDamage.mp3")));
    }
}
