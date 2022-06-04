package com.game.programdesign2finalproject.Screens;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;
import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.V_HEIGHT;
import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.V_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Menu implements Screen {
    private ProgramDesign2FinalProject game;
    int i=0;
    public Texture background;
    public Texture black;
    private SpriteBatch batch;
    private Viewport gamePort;
    private OrthographicCamera gamecam;
    private boolean changeScreenCountDown = false;
    private float clearCount = 0;
    public Menu(ProgramDesign2FinalProject game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(800,450,gamecam);
        gamecam.position.set(gamePort.getWorldWidth() / 2,gamePort.getWorldHeight() / 2,0);
        background = new Texture("start.png");
        black = new Texture("full_black.jpg");
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.justTouched()) {
            changeScreenCountDown = true;
        }
        if(clearCount >= 3) {
            game.setScreen(new StartConversation(game));
            dispose();
        }
        if(changeScreenCountDown == true) {
            clearCount += delta;
        }
        batch.begin();
        //batch.setColor(1,1,1,1-clearCount);
<<<<<<< HEAD
        batch.draw(background, 0, 0,650,300);
        batch.setColor(1,1,1,1 - clearCount / 2);
=======
        batch.draw(background, 0, 0,660,470);
        batch.setColor(1,1,1,1-clearCount / 2);
>>>>>>> d4b1d46 (6/5 dots map update)
        //batch.draw(black, 0, 0,650,300);
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
