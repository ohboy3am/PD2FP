package com.game.programdesign2finalproject.Scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.game.programdesign2finalproject.Screens.PlayScreen;

public class Dialog implements Disposable{
    public Texture npchead;
    private SpriteBatch b;
    public Stage stage;
    private boolean showDialog = false;
    public PlayScreen screen;
    public Dialog() {
        npchead = new Texture("DIO_icon.png");
        b = new SpriteBatch();
    }
    public void draw(PlayScreen screen) {
        if(! showDialog ) return;
        b.begin();
        b.draw(npchead,30,70,400,400);
        b.end();
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            showDialog = false;
            screen.IsPaused = false;
        }
    }
    public void setDialog(boolean dialogShow) {
        if(dialogShow)
        showDialog = true;
    }
    @Override
    public void dispose() {
        stage.dispose();
    }
}
