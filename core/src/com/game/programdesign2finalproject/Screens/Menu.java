package com.game.programdesign2finalproject.Screens;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;
import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.V_HEIGHT;
import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.V_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Game;
import com.game.programdesign2finalproject.Screens.PlayScreen;

public class Menu implements Screen {
    private ProgramDesign2FinalProject game;
    public Texture background;
    private SpriteBatch batch;
    private Viewport gamePort;
    private PlayScreen playscreen;
    public Menu(ProgramDesign2FinalProject game) {
        this.game = game;
        gamePort = new FitViewport(ProgramDesign2FinalProject.V_WIDTH / PPM,ProgramDesign2FinalProject.V_HEIGHT / PPM);
        background = new Texture("menu_adjust.png");
        batch = new SpriteBatch();
    }
    public void update(float dt) {
        handleInput();
    }
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new PlayScreen(game));
            dispose();
        }
        batch.begin();
        batch.draw(background, 0, 0,V_WIDTH,V_HEIGHT);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
        background.dispose();
    }
}
