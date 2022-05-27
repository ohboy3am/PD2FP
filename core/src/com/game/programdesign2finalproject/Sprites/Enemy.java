package com.game.programdesign2finalproject.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.game.programdesign2finalproject.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    protected float stateTime;
    public Body b2body;
    public Vector2 velocity;
    protected boolean setToDestroy;
    protected boolean destroyed;


    protected boolean vanished;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-1,-2);
        setToDestroy = false;
        destroyed = false;
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();
    public abstract  void update(float dt);
    public abstract void hitOnHead();

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
