package com.game.programdesign2finalproject.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game.programdesign2finalproject.Screens.PlayScreen;

public abstract class Boss extends Sprite {
    protected World world;
    protected PlayScreen screen;
    protected float stateTime;
    public Body b2body;
    public Vector2 velocity;
    protected boolean setToDestroy;
    protected boolean destroyed;


    protected boolean vanished;


    public Boss(PlayScreen screen, float x, float y, Character player) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineBoss();
        velocity = new Vector2(-1,0);
        setToDestroy = false;
        destroyed = false;
        //b2body.setActive(false);
    }

    protected abstract void defineBoss();
    public abstract  void update(float dt);

    public void reverseVelocity(boolean x, boolean y){
        if (x)
            velocity.x *= -1;
        if (y)
            velocity.y *= -1;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isVanished() {
        return vanished;
    }
}
