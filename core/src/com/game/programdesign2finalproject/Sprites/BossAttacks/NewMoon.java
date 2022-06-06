package com.game.programdesign2finalproject.Sprites.BossAttacks;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sounds.SoundManager;
import com.game.programdesign2finalproject.Sprites.Boss0;
import com.game.programdesign2finalproject.Sprites.Character;

public class NewMoon extends Boss0Attack{
    private TmxMapLoader mapLoader = new TmxMapLoader();
    private TiledMap newMoon = mapLoader.load("NewMoon.tmx");
    private TextureAtlas atlas;
    private int newMoonWidth = 82;
    private int newMoonHeight = 64;
    private float stateTime ;
    protected Animation<TextureRegion> boss0AttackAnimation;

    public NewMoon(PlayScreen screen, Boss0 boss, Character player) {
        super(screen, boss, player);
        atlas = new TextureAtlas("NewMoon.pack");
        stateTime = 0;
        for (int i = 0;i<3;i++){
            frames.add(new TextureRegion(atlas.findRegion("bossAttack1-1"),newMoonWidth*i,0, newMoonWidth, newMoonHeight));
        }
        boss0AttackAnimation = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        defineBoss0Attack();

        velocity.set(player.b2body.getPosition().x-boss.b2body.getPosition().x,player.b2body.getPosition().y-boss.b2body.getPosition().y);
        velocity.setLength(4.f);
        b2body.setLinearVelocity(velocity);
        if (velocity.x>0){
            setRegion(boss0AttackAnimation.getKeyFrame(0));
        }
        else {
            b2body.setTransform(boss.b2body.getPosition().x, boss.b2body.getPosition().y+24/PPM,3.14f);
            for (int i = 0; i<3;i++ )
            boss0AttackAnimation.getKeyFrame(0.1f*i).flip(true,false);
            setRegion(boss0AttackAnimation.getKeyFrame(0));
        }
        setBounds(boss.b2body.getPosition().x, boss.b2body.getPosition().y , 30 / PPM, 30 / PPM);
    }

    @Override
    public void defineBoss0Attack() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(boss.b2body.getPosition().x, boss.b2body.getPosition().y);
        bdef.type = BodyDef.BodyType.KinematicBody;
        if(!world.isLocked())
            b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = getPolygon((PolygonMapObject) newMoon.getLayers().get(1).getObjects().get(0));
        fdef.filter.categoryBits = ProgramDesign2FinalProject.BOSS_ATTACK_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.CHARACTER_BIT|
                ProgramDesign2FinalProject.FIREBALL_BIT;
        fdef.isSensor = true;
        fdef.shape = shape;
        fdef.friction = 0;
        b2body.setLinearVelocity(velocity);
        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();
        if (boss.b2body.getPosition().x>56&&!player.isDead())
        SoundManager.getInstance().soundPowerUp.setVolume(SoundManager.getInstance().soundNewMoon.play(),0.3f);
    }

    private PolygonShape getPolygon(PolygonMapObject polygonObject) {

        Polygon polygon = polygonObject.getPolygon();
        PolygonShape polygonShape = new PolygonShape();
        float[] vertices = polygon.getVertices();

        for (int i = 0; i < vertices.length; i += 2) {
            vertices[i]   = (polygon.getX() + vertices[i])   /PPM;
            vertices[i+1] = (polygon.getY() + vertices[i+1]) /PPM;
        }

        polygonShape.set(vertices,0,vertices.length);
        return polygonShape;
    }

    public void update(float dt){
        super.update(dt);
        if (destroyed) return;
        stateTime += dt;
        setRegion( boss0AttackAnimation.getKeyFrame(stateTime,true));
        if (b2body.getLinearVelocity().x > 0)
            setCenter(b2body.getPosition().x +12/PPM, b2body.getPosition().y+12/PPM );
        else
            setCenter(b2body.getPosition().x - 12/PPM , b2body.getPosition().y- 12/PPM);
        b2body.setLinearVelocity(velocity);
        if(stateTime > 3 || toDestroy || boss.isDestroyed()) {
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
