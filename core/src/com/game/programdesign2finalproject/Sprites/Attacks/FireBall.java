package com.game.programdesign2finalproject.Sprites.Attacks;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;


public class FireBall extends Sprite {

    PlayScreen screen;
    World world;
    Array<TextureRegion> frames;
    Animation<TextureRegion> fireAnimation;
    float x;
    float y;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    boolean fireRight;
    private Vector2 velocity;
    private TmxMapLoader mapLoader = new TmxMapLoader();
    private TiledMap newMoon = mapLoader.load("NewMoon.tmx");
    private final Vector3 mouseInWorld3D;



    Body b2body;
    public FireBall(PlayScreen screen, float x, float y, boolean fireRight){

        this.x = x;
        this.y = y;
        this.fireRight = fireRight;
        this.screen = screen;
        this.world = screen.getWorld();
        mouseInWorld3D = new Vector3();
        mouseInWorld3D.x = Gdx.input.getX();
        mouseInWorld3D.y = Gdx.input.getY();
        mouseInWorld3D.z = 0;
        screen.getGamecam().unproject(mouseInWorld3D);
        velocity = new Vector2( mouseInWorld3D.x - x , mouseInWorld3D.y - y).setLength(4.f);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 4; i++){
            frames.add(new TextureRegion( new Texture("fireball.PNG"),8*i,0,8,8));
        }
        fireAnimation = new Animation<TextureRegion>(0.2f, frames);
        frames.clear();
        setRegion(fireAnimation.getKeyFrame(0));
        setBounds(x, y, 30 / PPM, 30 / PPM);
        defineFireBall();
    }

    public void defineFireBall(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();

        CircleShape shape = new CircleShape();
        shape.setRadius(18 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.FIREBALL_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.GROUND_BIT |
                ProgramDesign2FinalProject.COIN_BIT |
                ProgramDesign2FinalProject.BRICK_BIT |
                ProgramDesign2FinalProject.ENEMY_BIT |
                ProgramDesign2FinalProject.BOSS_BIT|
                ProgramDesign2FinalProject.BOSS_ATTACK_BIT;

        fdef.shape = shape;
        fdef.restitution = 1;
        fdef.friction = 0;
        b2body.createFixture(fdef).setUserData(this);
        b2body.setLinearVelocity(velocity);

        shape.dispose();
    }





    public void update(float dt){
        if (destroyed) return;
        stateTime += dt;
        setRegion( fireAnimation.getKeyFrame(stateTime,true));
        setCenter(b2body.getPosition().x , b2body.getPosition().y );
        if((stateTime > 3 || setToDestroy) ) {
            world.destroyBody(b2body);
            destroyed = true;
        }

       // b2body.setLinearVelocity(velocity);


       // if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
            //setToDestroy();
    }

    public void draw(Batch batch){
        if (destroyed) return;

            super.draw(batch);
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }


}