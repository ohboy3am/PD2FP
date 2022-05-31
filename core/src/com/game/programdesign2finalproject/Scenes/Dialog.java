package com.game.programdesign2finalproject.Scenes;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.V_HEIGHT;
import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.V_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private boolean showDialog = false;
    private boolean show = false;
    public Dialog() {
    }
    public void draw() {
        if(! showDialog ) return;
        npchead = new Texture("main_character_dialog.png");
        b = new SpriteBatch();
        b.begin();
        b.draw(npchead, 0, 0,V_WIDTH,V_HEIGHT);
        b.end();
    }
    public void setDialog(boolean dialogShow) {
        showDialog = dialogShow;
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
