package com.game.programdesign2finalproject.Items;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sprites.Character;

public abstract class Item extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Vector2 velocity;
    protected boolean toDestroyed;
    protected boolean destroyed;
    protected Body body;

    public Item(PlayScreen screen, float x, float y){
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x,y);
        setBounds(getX(),getY(),16/PPM,16/PPM);
        defineItem();
        toDestroyed = false;
        destroyed = false;
    }

    public abstract void defineItem();
    public abstract void use(Character character);


    public  void update(float dt){
        if (toDestroyed && !destroyed){
            world.destroyBody(body);
            destroyed = true;
            Gdx.app.log("ITEM_UPDATE","SHIT");
        }
    }


    public void draw(Batch batch){
        if(destroyed) return;

        super.draw(batch);
        Gdx.app.log("ITEM_DRAW","SHIT");
    }

    public void destroy(){
        toDestroyed = true;
    }

    public void reverseVelocity(boolean x, boolean y){
        if (x)
            velocity.x *= -1;
        if (y)
            velocity.y *= -1;
    }
}
