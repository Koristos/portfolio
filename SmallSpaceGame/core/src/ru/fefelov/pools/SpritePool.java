package ru.fefelov.pools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import ru.fefelov.sprite.Sprite;

public abstract class SpritePool <T extends Sprite> {

    protected final List<T> activeObjects = new ArrayList<>();
    protected final List<T> freeObjects = new ArrayList<>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        System.out.println(getClass().getSimpleName() + " active/free : " + activeObjects.size() + "/" + freeObjects.size());
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (T item : activeObjects) {
            if (!item.isDestroyed()) {
                item.update(delta);
            }
        }
    }

    public void freeAllDestroyed() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T object = activeObjects.get(i);
            if (object.isDestroyed()) {
                free(object);
                i--;
                object.flushDestroy();
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {
        for (T item : activeObjects) {
            if (!item.isDestroyed()) {
                item.draw(batch);
            }
        }
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }

    private void free(T object) {
        if (activeObjects.remove(object)) {
            freeObjects.add(object);
        }
        System.out.println(getClass().getSimpleName() + " active/free : " + activeObjects.size() + "/" + freeObjects.size());
    }

    public void stop() {
        for (T item: getActiveObjects()) {
            if (!item.isDestroyed()){
                item.destroy();
            }
        }
        freeAllDestroyed();
    }
}
