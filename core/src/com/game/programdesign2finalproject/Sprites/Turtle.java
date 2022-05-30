package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.game.programdesign2finalproject.Screens.PlayScreen;

import javax.print.attribute.standard.PagesPerMinute;

public class Turtle extends Enemy{

    public enum State{WALKING, SHELL}
    public Turtle.State currentState;
    public Turtle.State previousState;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private TextureRegion shell;

    public Turtle(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"),0,0, 16, 24));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"),16,0, 16, 24));
        shell = new TextureRegion(screen.getAtlas().findRegion("turtle"),0,0, 16, 24);
        walkAnimation = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        currentState = previousState = State.WALKING;

        setBounds(getX(), getY(), 16/ PPM, 24/PPM);
    }

    @Override
    protected void defineEnemy() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void hitOnHead() {

    }
}
