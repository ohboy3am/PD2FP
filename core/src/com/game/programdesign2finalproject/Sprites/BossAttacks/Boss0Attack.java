package com.game.programdesign2finalproject.Sprites.BossAttacks;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sprites.Boss0;
import com.game.programdesign2finalproject.Sprites.Character;

public abstract class Boss0Attack extends Sprite {
    protected PlayScreen screen;
    protected World world;
    protected Array<TextureRegion> frames;
    protected Animation<TextureRegion> boss0AttackAnimation;
    protected Boss0 boss;
    protected Character player;
    protected Vector2 velocity;
    protected Body b2body;
    protected boolean toDestroy;
    protected boolean destroyed;

    public Boss0Attack(PlayScreen screen, Boss0 boss, Character player){
        this.screen = screen;
        this.world = screen.getWorld();
        this.boss = boss;
        this.player = player;
        velocity = new Vector2();
        frames = new Array<TextureRegion>();
        toDestroy = false;
        destroyed = false;
    }

    public abstract void defineBoss0Attack();

    public void destroy(){
        toDestroy = true;
    }
    public boolean isDestroyed() {
        return destroyed;
    }

    public void update(float dt){
        if(!toDestroy) return;
        if (destroyed) return;

        world.destroyBody(b2body);
        destroyed = true;

    }

    public void draw(Batch batch){
        if (destroyed) return;
        super.draw(batch);
    }




}
