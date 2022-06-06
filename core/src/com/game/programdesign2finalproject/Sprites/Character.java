package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sounds.SoundManager;

import com.game.programdesign2finalproject.Sprites.Attacks.FireBall;

public class Character extends Sprite {

    public enum State{FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD}
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;

    private TextureRegion characterStand;
    private Animation<TextureRegion> characterRun;
    private TextureRegion characterJump;
    private TextureRegion bigCharacterStand;
    private TextureRegion bigCharacterJump;
    private Animation<TextureRegion> bigCharacterRun;
    private Animation<TextureRegion> growCharacter;
    private TextureRegion characterDead;

    private float stateTimer;
    private float hitTime;
    private boolean runningRight;
    private boolean characterIsBig;
    public boolean runGrowAnimation;
    private boolean timeToDefineBigCharacter;
    private TextureAtlas atlas2;
    private boolean timeToRedefineCharacter;
    private boolean characterIsDead;
    private PlayScreen screen;
    public int jumpTime;
    private float fireTime;
    private boolean fightBoss = false;
    private Array<FireBall> fireBalls;
    private Vector2 bossRoomPosition= new Vector2(61,1);


    private int hp;
    public int getHp() {
        return hp;
    }


    public Character(PlayScreen screen){
        this.screen = screen;
        atlas2 = new TextureAtlas("jotaro_walking.pack");
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        hitTime = 0;
        runningRight = false;
        jumpTime = 0;
        fireTime = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        hp = 1;

        int characterHeight = 32;
        int characterWidth = 16;
        int bigCharacterHeight = 32;
        int bigCharacterWidth = 16;
        int jotaroHeight = 240;
        int jotaroWidth = 240;
        //for (int i = 2; i < 5; i++)
            //frames.add(new TextureRegion(screen.getAtlas().findRegion("Vega_Picture"),i* characterWidth,0, characterWidth, characterHeight));
        frames.add(new TextureRegion( new Texture("running_animation/3.PNG")));
        frames.add(new TextureRegion( new Texture("running_animation/3.PNG")));
        frames.add(new TextureRegion( new Texture("running_animation/4.PNG")));
        frames.add(new TextureRegion( new Texture("running_animation/5.PNG")));
        frames.add(new TextureRegion( new Texture("running_animation/5.PNG")));
        characterRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i=0;i<16;i++) {
            frames.add(new TextureRegion(atlas2.findRegion("00"), i * jotaroWidth, 0, jotaroWidth, jotaroHeight));
        }
        bigCharacterRun = new Animation(0.1f, frames);
        frames.clear();

        frames.add(new TextureRegion( new Texture("running_animation/1.PNG")));
        frames.add(new TextureRegion(atlas2.findRegion("02"),  0,0, jotaroWidth, jotaroHeight));
        growCharacter = new Animation(0.2f, frames);
        frames.clear();


        characterJump = new TextureRegion( new Texture("running_animation/2.PNG"));
        frames.clear();
        //bigCharacterJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"),  5*bigCharacterWidth,0, bigCharacterWidth, bigCharacterHeight);
        bigCharacterJump = new TextureRegion(atlas2.findRegion("11"),  0,0, jotaroWidth, jotaroHeight);


        characterStand = new TextureRegion( new Texture("running_animation/1.PNG"));

        //bigCharacterStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"),  0,0, bigCharacterWidth, bigCharacterHeight);
        bigCharacterStand = new TextureRegion(atlas2.findRegion("02"),  0,0, jotaroWidth, jotaroHeight);

        characterDead = new TextureRegion( new Texture("slime.PNG"),32,0,16,16);

        defineCharacter();
        setBounds(1,1, characterWidth / PPM, characterHeight / PPM);
        //setBounds(1,1, jotaroWidth / PPM, jotaroHeight / PPM);
        setRegion(characterStand);

        fireBalls = new Array<FireBall>();
    }

    public void transport(){
        b2body.setTransform(bossRoomPosition,0);


            hp = 6;
    }

    public void update(float dt){
        if (characterIsBig){
            setCenter(b2body.getPosition().x, b2body.getPosition().y - 2 /PPM);
        }

        else{
            setCenter(b2body.getPosition().x, b2body.getPosition().y - 6/PPM);
        }

        setRegion(getFrame(dt));

        if (timeToDefineBigCharacter)
            defineBigCharacter();

        if (timeToRedefineCharacter){
            redefineCharacter();
        }
        if (b2body.getPosition().y < -48/PPM && !characterIsDead){
            die();
        }
        //56是地圖邊界
        if(b2body.getPosition().x > 2 && fightBoss == false) {
            transport();
            fightBoss = true;
            screen.BossGenerate();

        }
        hitTime += dt;
        fireTime += dt;

        if (hp<=0&&!isDead())
        {
            die();
        }

        if (screen.getHud().isTimeUp() && !isDead()) {
            die();
        }

        Array<FireBall> fireBallFound = new Array<FireBall>();
        for(FireBall ball : fireBalls) {
            if(ball.isDestroyed()){
                fireBallFound.add(ball);
                continue;
            }
            if (!ball.isDestroyed())
            ball.update(dt);

        }
        fireBalls.removeAll(fireBallFound,true);

    }


    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case DEAD:
                region = characterDead;
                break;
            case GROWING:
                region = (TextureRegion)growCharacter.getKeyFrame(stateTimer);
                if (growCharacter.isAnimationFinished(stateTimer))
                    runGrowAnimation = false;
                break;
            case JUMPING:
                region = characterIsBig ? (TextureRegion) bigCharacterJump : (TextureRegion) characterJump;
                break;
            case RUNNING:
                region = characterIsBig ? (TextureRegion) bigCharacterRun.getKeyFrame(stateTimer,true) : (TextureRegion) characterRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
                region = characterIsBig ? bigCharacterStand:(TextureRegion) characterJump;
                break;
            case STANDING:
            default:
                region = characterIsBig ? bigCharacterStand : characterStand;
                break;
        }


        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
                region.flip(true,false);
                runningRight = true;
        }

            if (currentState == previousState){
                stateTimer += dt;
            }
            else {
                stateTimer = 0;
            }
            previousState = currentState;
            return region;
        }


    public State getState(){
        if (characterIsDead)
            return State.DEAD;
        else if(runGrowAnimation)
            return State.GROWING;
        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void grow(){
        hitTime = 0;
        runGrowAnimation = true;
        characterIsBig = true;
        timeToDefineBigCharacter = true;
        setBounds(getX(),getY(),1.2f,1.2f);
    }

    public boolean isBig() {
        return characterIsBig;
    }

    public boolean isDead() {
        return characterIsDead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void hit(){
        SoundManager.getInstance().soundStomp.setVolume(SoundManager.getInstance().soundShout[(int)(Math.random()*4)].play(),0.3f);
        b2body.setLinearVelocity(b2body.getLinearVelocity().x,4f);
        if (characterIsBig&&hitTime > 0.5){
            characterIsBig = false;
            timeToRedefineCharacter = true;
            setBounds(getX(),getY(),16/PPM,32/PPM);
            SoundManager.getInstance().soundPowerDown.setVolume(SoundManager.getInstance().soundPowerDown.play(),0.5f);
            hitTime = 0;
        }
        else if (hitTime > 0.5){

                hp--;
        }
    }

    public void die(){
        SoundManager.getInstance().bgm.stop();
        SoundManager.getInstance().soundBoss.stop();
        SoundManager.getInstance().soundCharacterDie.setVolume(SoundManager.getInstance().soundSpirit.play(),0.5f);
        characterIsDead = true;
        Filter filter = new Filter();
        filter.maskBits = ProgramDesign2FinalProject.NOTHING_BIT;
        for (Fixture fixture : b2body.getFixtureList())
            fixture.setFilterData(filter);
        setBounds(getX(),getY(),16/PPM,32/PPM);
        b2body.applyLinearImpulse(new Vector2(0, .08f), b2body.getWorldCenter(), true);
    }

    public void fire(){

        if (fireTime > 1){
            fireBalls.add(new FireBall(screen, b2body.getPosition().x, b2body.getPosition().y, runningRight));
            fireTime = 0;
        }


    }

    public void redefineCharacter(){
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.CHARACTER_BIT;
        fdef.filter.maskBits = mask();


        fdef.shape = shape;
        fdef.friction = 0.5f;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/ PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 10 / PPM), new Vector2(2/ PPM, 10 / PPM));
        fdef.filter.categoryBits = ProgramDesign2FinalProject.CHARACTER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        timeToRedefineCharacter = false;
    }

    public void defineBigCharacter() {
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);
        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(0,10 / PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.CHARACTER_BIT;
        fdef.filter.maskBits = mask();


        fdef.shape = shape;
        fdef.friction = 0.5f;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/ PPM));
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,-26/ PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 22 / PPM), new Vector2(2/ PPM, 22 / PPM));
        fdef.filter.categoryBits = ProgramDesign2FinalProject.CHARACTER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBigCharacter = false;

    }
    public void defineCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(55 / PPM, 165 / PPM); //dots changed
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.CHARACTER_BIT;
        fdef.filter.maskBits = mask();


        fdef.shape = shape;
        fdef.friction = 0.5f;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/ PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 10 / PPM), new Vector2(2/ PPM, 10 / PPM));
        fdef.filter.categoryBits = ProgramDesign2FinalProject.CHARACTER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        shape.dispose();


    }


    public void draw(Batch batch){
        super.draw(batch);
        for(FireBall ball : fireBalls){
            if (ball.isDestroyed()) continue;
            ball.draw(batch);
        }

    }

    public short mask(){
        return ProgramDesign2FinalProject.GROUND_BIT |
                ProgramDesign2FinalProject.COIN_BIT |
                ProgramDesign2FinalProject.BRICK_BIT|
                ProgramDesign2FinalProject.ENEMY_BIT|
                ProgramDesign2FinalProject.ENEMY_HEAD_BIT|
                ProgramDesign2FinalProject.ITEM_BIT|
                ProgramDesign2FinalProject.BOSS_ATTACK_BIT|
                ProgramDesign2FinalProject.OBJECT_BIT;
    }

    public void heal(){
        hp++;
    }


}
