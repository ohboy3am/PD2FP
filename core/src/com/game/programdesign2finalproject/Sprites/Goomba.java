package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Scenes.Hud;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sounds.SoundManager;

public class Goomba extends Enemy{
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private int goombaHeight = 16;
    private int goombaWidth = 16;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();


        for (int i = 0; i<2;i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"),i * goombaWidth,0,goombaWidth,goombaHeight));
        }
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), goombaWidth / PPM, goombaHeight / PPM);


    }

    public void update(float dt){
        stateTime += dt;
        if(destroyed && stateTime >=1) {
            vanished = true;
        }
        if(destroyed) return;
        if (setToDestroy){
            destroyed = true;
            world.destroyBody(b2body);
            stateTime = 0;
        }
        else {
            b2body.setLinearVelocity(velocity);
            setCenter(b2body.getPosition().x , b2body.getPosition().y);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }



    }



    @Override
    protected void defineEnemy() {

        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.ENEMY_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.GROUND_BIT |
                ProgramDesign2FinalProject.COIN_BIT |
                ProgramDesign2FinalProject.BRICK_BIT|
                ProgramDesign2FinalProject.OBJECT_BIT|
                ProgramDesign2FinalProject.ENEMY_BIT|
                ProgramDesign2FinalProject.CHARACTER_BIT|
                ProgramDesign2FinalProject.FIREBALL_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5,9).scl(1/PPM);
        vertice[1] = new Vector2(5,9).scl(1/PPM);
        vertice[2] = new Vector2(-3,3).scl(1/PPM);
        vertice[3] = new Vector2(3,3).scl(1/PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = ProgramDesign2FinalProject.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if (destroyed && stateTime < 1){
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), goombaWidth * 2, 0, goombaWidth, goombaHeight+1));
            super.draw(batch);
        }
        if(!destroyed ){
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
        Hud.addScore(100);
        SoundManager.getInstance().soundStomp.play();
    }
}
