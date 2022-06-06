package com.game.programdesign2finalproject.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Sounds.SoundManager;

public class GameClearScreen implements Screen{
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Music music;
    public GameClearScreen(ProgramDesign2FinalProject game) {
        this.game = game;
        viewport = new FitViewport(ProgramDesign2FinalProject.V_WIDTH, ProgramDesign2FinalProject.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((ProgramDesign2FinalProject)game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME CLEAR, CONGRAGULATION", font);

        table.add(gameOverLabel).expandX();
        table.row();

        stage.addActor(table);
        music = SoundManager.getInstance().Victory;
        music.setLooping(true);
        music.play();
        music.setVolume(0.2f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        if(Gdx.input.justTouched()) {
            music.stop();
            game.setScreen(new Menu((ProgramDesign2FinalProject) game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
