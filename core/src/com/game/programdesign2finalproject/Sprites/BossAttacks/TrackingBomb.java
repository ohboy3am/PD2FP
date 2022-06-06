package com.game.programdesign2finalproject.Sprites.BossAttacks;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sounds.SoundManager;
import com.game.programdesign2finalproject.Sprites.Boss0;
import com.game.programdesign2finalproject.Sprites.Character;

public class TrackingBomb extends Boss0Attack{
    private TmxMapLoader mapLoader = new TmxMapLoader();
    private TiledMap newMoon = mapLoader.load("NewMoon.tmx");
    private TextureAtlas atlas;
    private int trackingBombWidth = 160;
    private int trackingBombHeight = 130;
    private float stateTime ;
    private float explodeTime ;
    protected Animation<TextureRegion> boss0AttackAnimation;
    protected Animation<TextureRegion> explosionAnimation;
    private boolean explode;

    public TrackingBomb(PlayScreen screen, Boss0 boss, Character player) {
        super(screen, boss, player);
        explode = false;
        atlas = new TextureAtlas("explosion.pack");
        stateTime = 0;
        for (int i = 0;i<2;i++){
            frames.add(new TextureRegion(atlas.findRegion("1761246774explosion-animation-5-00"), trackingBombWidth *i,0, trackingBombWidth, trackingBombHeight));
        }
        boss0AttackAnimation = new Animation(0.1f, frames);
        frames.clear();
        for (int i = 2;i<17;i++){
            frames.add(new TextureRegion(atlas.findRegion("1761246774explosion-animation-5-02"), trackingBombWidth *i,0, trackingBombWidth, trackingBombHeight));
        }
        explosionAnimation = new Animation(0.1f, frames);
        frames.clear();
        defineBoss0Attack();
        setRegion(boss0AttackAnimation.getKeyFrame(0));
        setBounds(boss.b2body.getPosition().x, boss.b2body.getPosition().y, 100/ PPM, 100 / PPM);
        velocity.set(player.b2body.getPosition().x-boss.b2body.getPosition().x,player.b2body.getPosition().y-boss.b2body.getPosition().y);
        velocity.setLength(4.f);
        b2body.setLinearVelocity(velocity);

    }

    @Override
    public void defineBoss0Attack() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(boss.b2body.getPosition().x, boss.b2body.getPosition().y);
        bdef.type = BodyDef.BodyType.KinematicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(24 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.BOSS_ATTACK_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.CHARACTER_BIT|
                ProgramDesign2FinalProject.FIREBALL_BIT;
        fdef.isSensor = true;
        fdef.shape = shape;
        fdef.friction = 0;
        b2body.setLinearVelocity(velocity);
        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();
    }


    public void update(float dt){
        super.update(dt);
        if (destroyed) return;
        stateTime += dt;

        setCenter(b2body.getPosition().x , b2body.getPosition().y );
        if (explode){
            setRegion( explosionAnimation.getKeyFrame(explodeTime,true));
            explodeTime += dt;
            velocity.set(0,0);
        }
        else {
            setRegion( boss0AttackAnimation.getKeyFrame(stateTime,true));
            velocity.set(player.b2body.getPosition().x-b2body.getPosition().x,player.b2body.getPosition().y-b2body.getPosition().y);
            velocity.setLength(1.5f);
        }
        b2body.setLinearVelocity(velocity);
        if((stateTime > 2.5 || toDestroy) ) {
            stateTime = 0;
            explode = true;
        }
        if (explode&&explodeTime == 0){
            SoundManager.getInstance().soundPowerUp.setVolume(SoundManager.getInstance().soundExplosion.play(),0.5f);
        }
        if(explodeTime>0.7|| boss.isDestroyed()){
            world.destroyBody(b2body);
            destroyed = true;
        }




        // if((fireRight && b2body.getLinearVelocity().x < 0) || (!fireRight && b2body.getLinearVelocity().x > 0))
        //setToDestroy();
    }

    public void draw(Batch batch){
        if (destroyed) return;

        super.draw(batch);
    }
}
