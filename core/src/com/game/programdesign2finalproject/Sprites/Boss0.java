package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sounds.SoundManager;
import com.game.programdesign2finalproject.Sprites.BossAttacks.Boss0Attack;
import com.game.programdesign2finalproject.Sprites.BossAttacks.NewMoon;
import com.game.programdesign2finalproject.Sprites.BossAttacks.TrackingBomb;

public class Boss0 extends Boss{

    public enum State{ATTACK1, ATTACK2, MOVING1, MOVING2, DEAD}
    private TextureAtlas atlas;
    private Animation<TextureRegion> bossFly;
    private  Character player;
    private float stateTime;
    private float firstAttackTime;
    private float secondAttackTime;
    private Array<Boss0Attack> attacks;
    private boolean runningRight;
    private boolean phase2;

    public Boss0(PlayScreen screen, float x, float y, Character player){
        super(screen, x, y, player);
        this.player = player;
        this.screen = screen;
        this.world = screen.getWorld();
        firstAttackTime = 0;
        secondAttackTime = 0;
        stateTime = 0;
        attacks = new Array<Boss0Attack>();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        hp = 20;
        runningRight = false;

        int bossWidth = 180;
        int bossHeight = 160;

        atlas = new TextureAtlas("bahamut_flying.pack");

        for (int i = 0;i<4;i++){
            frames.add(new TextureRegion(atlas.findRegion("VVkF3FI-0"),bossWidth*i,0, bossWidth, bossHeight));
        }
        bossFly = new Animation(0.1f, frames);
        frames.clear();
        setBounds(1,1, bossWidth / PPM, bossHeight / PPM);
        setCenter(b2body.getPosition().x,b2body.getPosition().y+12/PPM);
        setRegion( bossFly.getKeyFrame(0,true));

    }

    public void firstAttack(){

            attacks.add(new NewMoon(screen, this,player));
            firstAttackTime = 0;

    }

    public void secondAttack(){
        if (secondAttackTime > 1){
            attacks.add(new TrackingBomb(screen, this,player));
            secondAttackTime = 0;
        }
    }

    public void draw(Batch batch){
        if (destroyed)return;
        super.draw(batch);
        for(Boss0Attack attack : attacks){
            if (attack.isDestroyed()) continue;
            attack.draw(batch);
        }

    }


    @Override
    protected void defineBoss() {
        BodyDef bdef = new BodyDef();
        bdef.position.set( 4, 40 / PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.BOSS_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.CHARACTER_BIT|
        ProgramDesign2FinalProject.FIREBALL_BIT;


        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }

    @Override
    public void hit() {
        SoundManager.getInstance().soundStomp.play();
        hp--;
    }


    public void die(){
        SoundManager.getInstance().soundWryyy.setVolume(SoundManager.getInstance().soundWryyy.play(),0.5f);
        toDestroy = true;
        Filter filter = new Filter();
        filter.maskBits = ProgramDesign2FinalProject.NOTHING_BIT;
        for (Fixture fixture : b2body.getFixtureList())
            fixture.setFilterData(filter);
        b2body.applyLinearImpulse(new Vector2(0,4f), b2body.getWorldCenter(), true);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (destroyed)return;
        stateTime += dt;
        firstAttackTime += dt;
        secondAttackTime +=dt;
        velocity.set(player.b2body.getPosition().x-b2body.getPosition().x,player.b2body.getPosition().y-b2body.getPosition().y);
        b2body.setLinearVelocity(velocity);


        if (firstAttackTime > 1){
            firstAttack();
        }

        if (hp<10 && !phase2){
            SoundManager.getInstance().soundWryyy.setVolume(SoundManager.getInstance().soundWryyy.play(),0.5f);
            hp += 5;
            phase2 = true;
        }

        if (secondAttackTime > 5 && phase2){
            secondAttack();
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !bossFly.getKeyFrame(stateTime,true).isFlipX()) {
            bossFly.getKeyFrame(stateTime,true).flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && bossFly.getKeyFrame(stateTime,true).isFlipX()){
            bossFly.getKeyFrame(stateTime,true).flip(true,false);
            runningRight = true;
        }

        setRegion( bossFly.getKeyFrame(stateTime,true));
        setCenter(b2body.getPosition().x , b2body.getPosition().y+12/PPM);

        Array<Boss0Attack> newAttackFound = new Array<Boss0Attack>();
        for(Boss0Attack attack : attacks) {
            if(attack.isDestroyed()){
                newAttackFound.add(attack);
                continue;
            }
            if (!attack.isDestroyed())
                attack.update(dt);

        }
        attacks.removeAll(newAttackFound,true);

        if (hp<=0){
            die();
        }
    }
}
