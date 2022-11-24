package ru.fefelov.pools.impl;

import com.badlogic.gdx.audio.Sound;

import ru.fefelov.math.Rect;
import ru.fefelov.pools.SpritePool;
import ru.fefelov.sprite.impl.EnemyShip;

public class EnemyPool extends SpritePool<EnemyShip> {

    private final Rect worldBounds;

    public EnemyPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(worldBounds);
    }
}