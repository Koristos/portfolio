package ru.fefelov.pools.impl;

import ru.fefelov.pools.SpritePool;
import ru.fefelov.sprite.impl.Bullet;

public class BulletPool extends SpritePool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}

