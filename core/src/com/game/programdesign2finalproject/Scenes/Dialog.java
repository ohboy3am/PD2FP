package com.game.programdesign2finalproject.Scenes;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.V_HEIGHT;
import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.V_WIDTH;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;

public class Dialog implements Disposable{
    public Texture npchead;
    private SpriteBatch b;
    public Stage stage;
    public Viewport viewport;
    private boolean showDialog = false;
    public Dialog() {

    }
    public void draw() {
        if(!showDialog) return;
        viewport = new FitViewport(ProgramDesign2FinalProject.V_WIDTH/2, ProgramDesign2FinalProject.V_HEIGHT/2, new OrthographicCamera());
        npchead = new Texture("main_character_dialog.png");
        b = new SpriteBatch();
        b.begin();
        b.draw(npchead, 0, 0,V_WIDTH,V_HEIGHT);
        b.end();
    }
    public void setDialog(boolean dialogshow) {
        showDialog = dialogshow;
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
