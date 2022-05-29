package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
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

public class Character extends Sprite {

    public enum State{FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion characterStand;
    private Animation<TextureRegion> characterRun;
    private Animation<TextureRegion> characterJump;
    private TextureRegion bigCharacterStand;
    private TextureRegion bigCharacterJump;
    private Animation<TextureRegion> bigCharacterRun;
    private Animation<TextureRegion> growCharacter;
    private TextureRegion characterDead;

    private float stateTimer;
    private boolean runnungRight;
    private boolean characterIsBig;
    public boolean runGrowAnimation;
    private boolean timeToDefineBigCharacter;
    private TextureAtlas atlas2;
    private boolean timeToRedefineCharacter;
    private boolean characterIsDead;


    public Character(PlayScreen screen){
        atlas2 = new TextureAtlas("jotaro_walking.pack");
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runnungRight = true;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        int characterHeight = 32;
        int characterWidth = 16;
        int bigCharacterHeight = 32;
        int bigCharacterWidth = 16;
        int jotaroHeight = 240;
        int jotaroWidth = 240;
        for (int i = 2; i < 5; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Vega_Picture"),i* characterWidth,0, characterWidth, characterHeight));
        characterRun = new Animation(0.1f, frames);
        frames.clear();

        //for (int i = 1; i < 4; i++)
        //  frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),i* bigCharacterWidth,0, bigCharacterWidth, bigCharacterHeight));
        for(int i=0;i<16;i++) {
            frames.add(new TextureRegion(atlas2.findRegion("00"), i * jotaroWidth, 0, jotaroWidth, jotaroHeight));
        }
        /*for(int i=0;i<4;i++) {
            frames.add(new TextureRegion(atlas2.findRegion("jotaro_walking-0"),i* jotaroWidth,0, jotaroWidth, jotaroHeight));
        }
        for(int i=0;i<4;i++) {
            frames.add(new TextureRegion(atlas2.findRegion("jotaro_walking-4"),i* jotaroWidth,0, jotaroWidth, jotaroHeight));
        }
        for(int i=0;i<4;i++) {
            frames.add(new TextureRegion(atlas2.findRegion("jotaro_walking-8"),i* jotaroWidth,0, jotaroWidth, jotaroHeight));
        }
        for(int i=0;i<4;i++) {
            frames.add(new TextureRegion(atlas2.findRegion("jotaro_walking-12"),i* jotaroWidth,0, jotaroWidth, jotaroHeight));
        }*/
        bigCharacterRun = new Animation(0.1f, frames);
        frames.clear();

        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),15 * bigCharacterWidth,0, bigCharacterWidth, bigCharacterHeight));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0, bigCharacterWidth, bigCharacterHeight));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),15 * bigCharacterWidth,0, bigCharacterWidth, bigCharacterHeight));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0, bigCharacterWidth, bigCharacterHeight));
        growCharacter = new Animation(0.2f, frames);
        frames.clear();

        for (int i = 0;i < 2;i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("Vega_Picture"),0,0, characterWidth, characterHeight));
        characterJump = new Animation(0.1f, frames);
        frames.clear();
        //bigCharacterJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"),  5*bigCharacterWidth,0, bigCharacterWidth, bigCharacterHeight);
        bigCharacterJump = new TextureRegion(atlas2.findRegion("11"),  0,0, jotaroWidth, jotaroHeight);


        characterStand = new TextureRegion(screen.getAtlas().findRegion("Vega_Picture"),  5*characterWidth,0, characterWidth, characterHeight);

        //bigCharacterStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"),  0,0, bigCharacterWidth, bigCharacterHeight);
        bigCharacterStand = new TextureRegion(atlas2.findRegion("02"),  0,0, jotaroWidth, jotaroHeight);

        characterDead = new TextureRegion(screen.getAtlas().findRegion("little_mario"),  96,0, 16, 16);

        defineCharacter();
        setBounds(1,1, characterWidth / PPM, characterHeight / PPM);
        //setBounds(1,1, jotaroWidth / PPM, jotaroHeight / PPM);
        setRegion(characterStand);
    }

    public void update(float dt){
        if (characterIsBig)
            setCenter(b2body.getPosition().x, b2body.getPosition().y + 10/PPM);
        else{
            setCenter(b2body.getPosition().x, b2body.getPosition().y - 6/PPM);
        }
        setRegion(getFrame(dt));
        if (timeToDefineBigCharacter)
            defineBigCharacter();
        if (timeToRedefineCharacter){
            redefineCharacter();
        }
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
                region = characterIsBig ? (TextureRegion) bigCharacterJump : (TextureRegion) characterJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = characterIsBig ? (TextureRegion) bigCharacterRun.getKeyFrame(stateTimer,true) : (TextureRegion) characterRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = characterIsBig ? bigCharacterStand : characterStand;
                break;
        }


        if((b2body.getLinearVelocity().x > 0 || !runnungRight) && !region.isFlipX()) {
            region.flip(true, false);
            runnungRight = false;
        }
        else if((b2body.getLinearVelocity().x < 0 || runnungRight) && region.isFlipX()){
                region.flip(true,false);
                runnungRight = true;
        }

            stateTimer = (currentState == previousState)? stateTimer + dt :0;
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
        runGrowAnimation = true;
        characterIsBig = true;
        timeToDefineBigCharacter = true;
        setBounds(getX(),getY(),1.2f,1.2f);
        SoundManager.getInstance().soundPowerUp.play();
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
        if (characterIsBig){
            characterIsBig = false;
            timeToRedefineCharacter = true;
            setBounds(getX(),getY(),16/PPM,32/PPM);
            SoundManager.getInstance().soundPowerDown.play();
        }
        else {
            SoundManager.getInstance().bgm.stop();
            SoundManager.getInstance().soundCharacterDie.play();
            characterIsDead = true;
            Filter filter = new Filter();
            filter.maskBits = ProgramDesign2FinalProject.NOTHING_BIT;
            for (Fixture fixture : b2body.getFixtureList())
                fixture.setFilterData(filter);
            b2body.applyLinearImpulse(new Vector2(0,4f), b2body.getWorldCenter(), true);
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
        fdef.filter.maskBits = ProgramDesign2FinalProject.GROUND_BIT |
                ProgramDesign2FinalProject.COIN_BIT |
                ProgramDesign2FinalProject.BRICK_BIT|
                ProgramDesign2FinalProject.ENEMY_BIT|
                ProgramDesign2FinalProject.OBJECT_BIT|
                ProgramDesign2FinalProject.ENEMY_HEAD_BIT|
                ProgramDesign2FinalProject.ITEM_BIT;


        fdef.shape = shape;
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
        fdef.filter.maskBits = ProgramDesign2FinalProject.GROUND_BIT |
                ProgramDesign2FinalProject.COIN_BIT |
                ProgramDesign2FinalProject.BRICK_BIT|
                ProgramDesign2FinalProject.ENEMY_BIT|
                ProgramDesign2FinalProject.OBJECT_BIT|
                ProgramDesign2FinalProject.ENEMY_HEAD_BIT|
                ProgramDesign2FinalProject.ITEM_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/ PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 8 / PPM), new Vector2(2/ PPM, 8 / PPM));
        fdef.filter.categoryBits = ProgramDesign2FinalProject.CHARACTER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        timeToDefineBigCharacter = false;

    }
    public void defineCharacter(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / PPM, 32 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.CHARACTER_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.GROUND_BIT |
                ProgramDesign2FinalProject.COIN_BIT |
                ProgramDesign2FinalProject.BRICK_BIT|
                ProgramDesign2FinalProject.ENEMY_BIT|
                ProgramDesign2FinalProject.OBJECT_BIT|
                ProgramDesign2FinalProject.ENEMY_HEAD_BIT|
                ProgramDesign2FinalProject.ITEM_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/ PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 10 / PPM), new Vector2(2/ PPM, 10 / PPM));
        fdef.filter.categoryBits = ProgramDesign2FinalProject.CHARACTER_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);


    }

}
