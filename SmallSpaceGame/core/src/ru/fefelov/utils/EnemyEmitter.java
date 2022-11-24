package ru.fefelov.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import ru.fefelov.math.Rect;
import ru.fefelov.mech.impl.BigFireGun;
import ru.fefelov.mech.impl.BossFireGun;
import ru.fefelov.mech.impl.FireGun;
import ru.fefelov.pools.impl.BulletPool;
import ru.fefelov.pools.impl.EnemyPool;
import ru.fefelov.sprite.impl.EnemyShip;

public class EnemyEmitter {
    private final Rect worldBounds;
    private final EnemyPool enemyPool;
    private TextureAtlas enemyAtlas;
    private BulletPool bulletPool;
    private TextureAtlas bulletAtlas;
    private TextureRegion [] explosions;

    private final float GENERATE_INTERVAL = 4f;
    private final float LEVEL_COEF = 0.05f;
    private float generateTimer;
    private boolean working;
    private int level;

    private final Sound BOSS_SHOOT = Gdx.audio.newSound(Gdx.files.internal("music/fire2.mp3"));
    private final Sound ENEMY_SHOOT = Gdx.audio.newSound(Gdx.files.internal("music/fire1.mp3"));
    private final Sound ENEMY_HIT = Gdx.audio.newSound(Gdx.files.internal("music/fireDamage.mp3"));

    public EnemyEmitter(TextureAtlas enemyAtlas, Rect worldBounds, EnemyPool enemyPool, BulletPool bulletPool, TextureAtlas bulletAtlas, TextureRegion explosion) {
        this.level = 1;
        this.worldBounds = worldBounds;
        this.enemyPool = enemyPool;
        this.bulletPool = bulletPool;
        this.enemyAtlas = enemyAtlas;
        this.bulletAtlas = bulletAtlas;
        this.explosions = Regions.split(explosion, 6, 8, 48);
        this.working = true;
    }

    public void generate(float delta) {
        if (!working){
            return;
        }
        if (level < 3){
            generateTimer += (delta-delta/10*level);
        }else {
            generateTimer += (delta+delta/10*(level-2));
        }
        if (generateTimer > GENERATE_INTERVAL) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            float type = (float) Math.random();
            if (level<4){
                type = type - 0.6f + 0.2f*level;
            }
            float speedMul = 1+(LEVEL_COEF*(level-1));
            int hpDamageMul = MathUtils.floorPositive(level/2);

            if (type < 0.2f) {
                enemyShip.setProp(
                        new Vector2(0, -0.09f*speedMul),
                        1,
                        enemyAtlas.findRegion("enemy1"),
                        0.08f,
                        1+hpDamageMul,
                        new FireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT,
                                hpDamageMul),
                        explosions
                );
            } else if (type < 0.4f) {
                enemyShip.setProp(
                        new Vector2(0.06f*speedMul, -0.06f*speedMul),
                        1,
                        enemyAtlas.findRegion("enemy2"),
                        0.08f,
                        1+hpDamageMul,
                        new FireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT,
                                hpDamageMul),
                        explosions
                );
            } else if (type < 0.6f){
                enemyShip.setProp(
                        new Vector2(0.1f*speedMul, -0.025f*speedMul),
                        2,
                        enemyAtlas.findRegion("enemy4"),
                        0.08f,
                        2+hpDamageMul,
                        new FireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT,
                                hpDamageMul),
                        explosions
                );
            }else if (type < 0.75f) {
                enemyShip.setProp(
                        new Vector2(0, -0.04f*speedMul),
                        1,
                        enemyAtlas.findRegion("enemy5"),
                        0.11f,
                        3+hpDamageMul,
                        new BigFireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT,
                                hpDamageMul),
                        explosions
                );
            } else if (type < 0.9f) {
                enemyShip.setProp(
                        new Vector2(0.05f*speedMul, -0.02f*speedMul),
                        1,
                        enemyAtlas.findRegion("enemy6"),
                        0.11f,
                        3+hpDamageMul,
                        new BigFireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                ENEMY_SHOOT,
                                ENEMY_HIT,
                                hpDamageMul),
                        explosions
                );
            } else {
                enemyShip.setProp(
                        new Vector2(0.03f*speedMul, -0.001f*speedMul),
                        2,
                        enemyAtlas.findRegion("enemy7"),
                        0.2f,
                        7+hpDamageMul,
                        new BossFireGun(bulletPool,
                                false,
                                bulletAtlas,
                                worldBounds,
                                enemyShip,
                                BOSS_SHOOT,
                                ENEMY_HIT,
                                hpDamageMul),
                        explosions
                );
            }
            enemyShip.setBottom(worldBounds.getTop());
            enemyShip.pos.x = MathUtils.random(
                    worldBounds.getLeft() + enemyShip.getWidth(),
                    worldBounds.getRight() - enemyShip.getWidth()
            );
        }
    }

    public void stop(){
        this.working = false;
    }

    public void start(){
        generateTimer = 0f;
        this.level = 1;
        this.working = true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel (int level){
        this.level = level;
    }
}
