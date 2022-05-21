package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;

public class Character extends Sprite {
    public enum State{FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion characterStand;
    private Animation characterRun;
    private Animation characterJump;
    private float stateTimer;
    private boolean runnungRight;

    public Character(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runnungRight = true;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        int characterHeight = 16;
        int characterWidth = 16;
        for (int i = 1; i < 4; i++)
            frames.add(new TextureRegion(getTexture(),i* characterWidth,0, characterWidth, characterHeight));
        characterRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 4;i < 6;i++)
            frames.add(new TextureRegion(getTexture(),i* characterWidth,0, characterWidth, characterHeight));
        characterJump = new Animation(0.1f, frames);


        characterStand = new TextureRegion(getTexture(), 1,1, characterWidth, characterHeight);

        defineCharacter();
        setBounds(1,1, characterWidth / PPM, characterHeight / PPM);
        setRegion(characterStand);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = (TextureRegion) characterJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = (TextureRegion) characterRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = characterStand;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runnungRight) && !region.isFlipX()) {
            region.flip(true, false);
            runnungRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runnungRight) && region.isFlipX()){
                region.flip(true,false);
                runnungRight = true;
        }

            stateTimer = (currentState == previousState)? stateTimer + dt :0;
            previousState = currentState;
            return region;
        }


    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
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
        fdef.filter.maskBits = ProgramDesign2FinalProject.DEFAULT_BIT | ProgramDesign2FinalProject.COIN_BIT | ProgramDesign2FinalProject.BRICK_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef);
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 6 / PPM), new Vector2(2/ PPM, 6 / PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");

    }
}
