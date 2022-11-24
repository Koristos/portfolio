package ru.fefelov.screen.impl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.logging.StreamHandler;

import ru.fefelov.math.Rect;
import ru.fefelov.screen.BaseScreen;
import ru.fefelov.sprite.impl.Background;
import ru.fefelov.sprite.impl.ButtonPlay;
import ru.fefelov.sprite.impl.ButtonQuit;
import ru.fefelov.sprite.impl.Star;
import ru.fefelov.sprite.impl.Title;
import ru.fefelov.sprite.impl.Ufo;

public class MenuScreen extends BaseScreen {

    private final Game game;

    private Texture backgroundPict;
    private Vector2 position;
    private Background background;
    private Ufo ufo;

    private TextureAtlas atlas;
    private TextureAtlas ufoAtlas;

    private final String[] textureNameArray = new String[]{"Star2", "Star4", "Star6", "Star7", "Star8"};
    private Star[] stars;
    private ButtonPlay buttonPlay;
    private ButtonQuit buttonQuit;
    private Title title;
    private Music music;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        backgroundPict = new Texture("background.jpg");
        atlas = new TextureAtlas("menu.pack");
        ufoAtlas = new TextureAtlas("ufo.pack");
        position = new Vector2();
        background = new Background(backgroundPict);
        ufo = new Ufo(ufoAtlas,false, null);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas, textureNameArray);
        }
        buttonPlay = new ButtonPlay(atlas, this.game);
        buttonQuit = new ButtonQuit(atlas);
        title = new Title(atlas);
        music = Gdx.audio.newMusic(Gdx.files.internal("music/menu.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        for (Star star : stars) {
            star.update(delta);
        }
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        buttonPlay.draw(batch);
        buttonQuit.draw(batch);
        title.draw(batch);
        ufo.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundPict.dispose();
        ufoAtlas.dispose();
        atlas.dispose();
        music.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        ufo.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        buttonQuit.resize(worldBounds);
        title.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        position.set(touch);
        buttonPlay.touchDown(touch, pointer, button);
        buttonQuit.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        position.set(touch);
        buttonPlay.touchUp(touch, pointer, button);
        buttonQuit.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }
}
