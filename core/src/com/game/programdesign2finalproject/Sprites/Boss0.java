package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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

public class Boss0 extends Boss{

    private TextureRegion bossStand;
    private  Character player;

    public Boss0(PlayScreen screen, float x, float y, Character player){
        super(screen, x, y, player);
        this.player = player;
        this.screen = screen;
        this.world = screen.getWorld();

        Array<TextureRegion> frames = new Array<TextureRegion>();

        int dioHeight = 80;
        int dioWidth = 60;
        bossStand= new TextureRegion( new Texture("dio.png"));
        setBounds(1,1, dioWidth / PPM, dioHeight / PPM);
        setCenter(b2body.getPosition().x,b2body.getPosition().y+12/PPM);
        setRegion(bossStand);

    }



    public void draw(Batch batch){
        super.draw(batch);

    }

    @Override
    protected void defineBoss() {
        BodyDef bdef = new BodyDef();
        bdef.position.set( 4, 40 / PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.NOTHING_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.CHARACTER_BIT;


        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/ PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 10 / PPM), new Vector2(2/ PPM, 10 / PPM));
        fdef.filter.categoryBits = ProgramDesign2FinalProject.NOTHING_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        velocity.set(player.b2body.getPosition().x-b2body.getPosition().x,player.b2body.getPosition().y-b2body.getPosition().y);
        b2body.setLinearVelocity(velocity);
        setCenter(b2body.getPosition().x , b2body.getPosition().y+12/PPM);

    }
}