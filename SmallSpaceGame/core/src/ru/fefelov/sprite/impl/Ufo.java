package ru.fefelov.sprite.impl;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

import ru.fefelov.math.Rect;
import ru.fefelov.screen.impl.GameScreen;
import ru.fefelov.sprite.Ship;
import ru.fefelov.utils.Regions;

public class Ufo extends Ship {

    private final float SPEED = 0.004f;
    private final float MENU_HEIGHT = 0.2f;
    private final float GAME_MODE_HEIGHT = 0.08f;
    private final float SCALE = 0.99f;
    private final int HP = 100;

    private Vector2 destination = new Vector2();
    static Vector2 move = new Vector2();
    private boolean enabled = false;
    private boolean gameMode;
    private Map<String, UfoMoveFlame> flames = new HashMap<>();
    private boolean forwardPressed = false;
    private boolean backPressed = false;
    private boolean goLeftPressed = false;
    private boolean goRightPressed = false;
    private boolean shootingPressed = false;
    private final GameScreen screen;
    private final TextureRegion [] mainVew;


    public Ufo(TextureAtlas atlas, boolean isGameMode, GameScreen screen) {
        super(atlas.findRegion("ufo"));
        mainVew = this.regions;
        this.screen = screen;
        this.gameMode = isGameMode;
        this.flames.put("left", new UfoMoveFlame(atlas, UfoMoveFlame.DIRECTION.LEFT));
        this.flames.put("right", new UfoMoveFlame(atlas, UfoMoveFlame.DIRECTION.RIGHT));
        this.flames.put("center", new UfoMoveFlame(atlas, UfoMoveFlame.DIRECTION.CENTER));
        this.isBlowing = false;
        this.setHp(HP);
    }

    public void setExplosions(TextureRegion explosion){
        this.explosions = Regions.split(explosion, 6, 8, 48);
    }

    @Override
    public void resize(final Rect worldBounds) {
        this.worldBounds = worldBounds;
        if (!enabled) {
            setHeightProportion(worldBounds.getHeight() * MENU_HEIGHT);
        } else {
            setHeightProportion(worldBounds.getHeight() * GAME_MODE_HEIGHT);
        }
        if (gameMode && !enabled) {
            destination.set(0, worldBounds.getBottom() + GAME_MODE_HEIGHT);
        }

        for (Map.Entry<String, UfoMoveFlame> entry : flames.entrySet()) {
            entry.getValue().resize(worldBounds);
        }
        if (this.gun != null){
            this.gun.setWorldBounds(worldBounds);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (!enabled || isDestroyed()) return false;
        if (isMe(touch)){
            shoot();
        }
        this.destination.set(touch);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (this.isDestroyed()){
            return;
        }
        if (isBlowing){
            if (this.frame < this.explosions.length-1){
                frame++;
            } else {
               this.screen.gameOver();
               destroy();
            }
        }else {
            calculateMovement(this.pos, this.destination);
            if (shootingPressed){
                shoot();
                shootingPressed = false;
            }
            for (Map.Entry<String, UfoMoveFlame> entry : flames.entrySet()) {
                entry.getValue().setPosition(this.pos);
                entry.getValue().draw(batch, this.move);
            }
            if (!enabled && gameMode) {
                resizeToGameMode();
                chekStartPosition();
            }
        }
        super.draw(batch);
    }

    public boolean keyDown(int keycode) {
        if (enabled){
            switch (keycode) {
                case Input.Keys.DPAD_UP:
                    this.forwardPressed = true;
                    break;
                case Input.Keys.DPAD_DOWN:
                    this.backPressed = true;
                    break;
                case Input.Keys.DPAD_LEFT:
                    this.goLeftPressed = true;
                    break;
                case Input.Keys.DPAD_RIGHT:
                    this.goRightPressed = true;
                    break;
                case Input.Keys.SPACE:
                    this.shootingPressed = true;
                    break;
            }
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.DPAD_UP:
                this.forwardPressed = false;
                break;
            case Input.Keys.DPAD_DOWN:
                this.backPressed = false;
                break;
            case Input.Keys.DPAD_LEFT:
                this.goLeftPressed = false;
                break;
            case Input.Keys.DPAD_RIGHT:
                this.goRightPressed = false;
                break;
            case Input.Keys.SPACE:
                this.shootingPressed = false;
                break;
        }
        return false;
    }

    private void calculateMovement(Vector2 position, Vector2 destination) {
        if (!gameMode){
            return;
        }
        if (forwardPressed || backPressed || goLeftPressed || goRightPressed) {
            destination.set(position);
            if (forwardPressed) {
                destination.y = destination.y + SPEED;
            }
            if (backPressed) {
                destination.y = destination.y - SPEED;
            }
            if (goLeftPressed) {
                destination.x = destination.x - SPEED;
            }
            if (goRightPressed) {
                destination.x = destination.x + SPEED;
            }
        }
        checkBorder(destination);
        move.set(destination);
        move.sub(position);
        if (move.len() > SPEED) {
            move.nor().scl(SPEED);
        }
        if (destination.x - position.x < 0) {
            this.screen.setUfoXmove(-SPEED);
        }else if (destination.x - position.x > 0){
            this.screen.setUfoXmove(SPEED);
        }else if (destination.x - position.x == 0){
            this.screen.setUfoXmove(0);
        }
        position.add(move);
    }

    private void resizeToGameMode() {
        if (getHeight() > GAME_MODE_HEIGHT) {
            float nextHeight = getHeight() * SCALE;
            if (nextHeight > GAME_MODE_HEIGHT) {
                this.setHeightProportion(worldBounds.getHeight() * nextHeight);
            } else {
                setHeightProportion(worldBounds.getHeight() * GAME_MODE_HEIGHT);
            }
        }
    }

    private void chekStartPosition() {
        if (getHeight() == GAME_MODE_HEIGHT && this.pos.y == destination.y) {
            enabled = true;
        }
    }

    private void checkBorder(Vector2 destination) {
        if (destination.x > worldBounds.getRight() - getHalfWidth()) {
            this.destination.x = worldBounds.getRight() - getHalfWidth();
        } else if (destination.x < worldBounds.getLeft() + getHalfWidth()) {
            this.destination.x = worldBounds.getLeft() + getHalfWidth();
        } else {
            this.destination.x = destination.x;
        }
        if (destination.y > worldBounds.getTop() - getHeight() * 3) {
            this.destination.y = worldBounds.getTop() - getHeight() * 3;
        } else if (destination.y < worldBounds.getBottom() + getHalfHeight()) {
            this.destination.y = worldBounds.getBottom() + getHalfHeight();
        } else {
            this.destination.y = destination.y;
        }
    }

    public void renew(){
        setHp(HP);
        this.destroyed = false;
        this.isBlowing = false;
        this.enabled = false;
        this.regions = mainVew;
        this.frame = 0;
        this.pos.set(0,worldBounds.getBottom()-getHeight());
        this.destination.set(0, worldBounds.getBottom() + GAME_MODE_HEIGHT);
    }

    public static Vector2 getMove() {
        return move;
    }
}
