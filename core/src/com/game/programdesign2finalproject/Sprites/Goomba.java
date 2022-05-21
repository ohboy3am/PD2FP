package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;

public class Goomba extends Enemy{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
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
        setToDestroy = false;
        destroyed = false;

    }

    public void update(float dt){
        stateTime += dt;
        if (setToDestroy && !destroyed){
            Gdx.app.log("goomba","Collision");
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba"), goombaWidth * 2, 0, goombaWidth, goombaHeight));
        }
        else if(!destroyed){
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
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
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.ENEMY_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.GROUND_BIT |
                ProgramDesign2FinalProject.COIN_BIT |
                ProgramDesign2FinalProject.BRICK_BIT|
                ProgramDesign2FinalProject.OBJECT_BIT|
                ProgramDesign2FinalProject.ENEMY_BIT|
                ProgramDesign2FinalProject.CHARACTER_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef);

        //Create the Head here:
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5,8).scl(1/PPM);
        vertice[1] = new Vector2(5,8).scl(1/PPM);
        vertice[2] = new Vector2(-3,3).scl(1/PPM);
        vertice[3] = new Vector2(3,3).scl(1/PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = ProgramDesign2FinalProject.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }
}
