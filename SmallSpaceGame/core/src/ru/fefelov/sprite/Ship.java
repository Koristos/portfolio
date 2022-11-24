package ru.fefelov.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.fefelov.math.Rect;
import ru.fefelov.mech.Gun;

public class Ship extends Sprite {

    protected int hp;
    protected Rect worldBounds;
    protected Gun gun;
    protected boolean isBlowing;
    protected final Sound EXPLOSION_SOUND = Gdx.audio.newSound(Gdx.files.internal("music/explosion.mp3"));
    protected TextureRegion [] explosions;

    public Ship() {
    }

    public Ship (TextureRegion region) {
      super(region);
    }

    public void shoot (){
        this.gun.shoot();
    }

    public void makeDamage (int damage){
        this.hp = this.hp - damage;
        if (this.hp <= 0){
            this.hp = 0;
            this.blow();
        }
    }

    public void blow(){
        this.isBlowing = true;
        this.regions = explosions;
        this.frame = 0;
        this.EXPLOSION_SOUND.play();
    };

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setWorldBounds(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }

    public int getHp() {
        return hp;
    }

    public boolean isBlowing() {
        return isBlowing;
    }

}
