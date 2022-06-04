package com.game.programdesign2finalproject.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;

public class StartConversation implements Screen {
    private int conversationCount = -1;
    public Texture mainCharacter;
    public Texture whitebox;
    private SpriteBatch batch;
    private Label yakyusenpai;
    private OrthographicCamera gamecam;
    public Stage stage;
    public Viewport viewport;
    private ProgramDesign2FinalProject game;
    private Table table;
    public StartConversation(ProgramDesign2FinalProject game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        mainCharacter = new Texture("main_character_dialog.PNG");
        whitebox = new Texture("whitebox.PNG");
        batch = new SpriteBatch();
        viewport = new FitViewport(ProgramDesign2FinalProject.V_WIDTH, ProgramDesign2FinalProject.V_HEIGHT,gamecam);
        stage = new Stage(viewport, batch);
        this.table = new Table();
        table.bottom();
        gamecam.position.set(viewport.getWorldWidth() / 2,viewport.getWorldHeight() / 2,0);
        //table.setFillParent(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(whitebox, 0, 0,600,120);
        batch.draw(mainCharacter, 30, 90,200,200);
        batch.end();
        stage.draw();
        if(Gdx.input.justTouched()) {
            conversationCount++;
            changeConversation();
        }
        if(conversationCount == 3) {
            game.setScreen(new PlayScreen(game));
        }
    }
    public void changeConversation() {
        switch(conversationCount) {
            case 0:

                yakyusenpai = new Label("what's your occupation?", new Label.LabelStyle(new BitmapFont(), Color.BLACK));

                table.add(yakyusenpai).expandX().padBottom(80);
                table.add(yakyusenpai).expandX().padRight(0);
                table.add(yakyusenpai).expandX();



                stage.addActor(table);

                break;
            case 1:
                table.removeActor(yakyusenpai);
                yakyusenpai = new Label("24 years old, as a student.", new Label.LabelStyle(new BitmapFont(), Color.BLACK));

                table.add(yakyusenpai).expandX().padBottom(80);
                table.add(yakyusenpai).expandX();

                stage.addActor(table);

                break;
            case 2:
                table.removeActor(yakyusenpai);
                yakyusenpai = new Label("uhuh uh ahhhhhhh!!!", new Label.LabelStyle(new BitmapFont(), Color.BLACK));

                table.add(yakyusenpai).expandX().padBottom(80);
                table.add(yakyusenpai).expandX();

                stage.addActor(table);

                break;
        }
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
