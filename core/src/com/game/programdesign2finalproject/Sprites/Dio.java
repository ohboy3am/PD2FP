package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;

public class Dio extends Sprite {

    public Body b2body;
    public World world;

    private TextureRegion dioStand;

    private PlayScreen screen;

    public Dio(PlayScreen screen){
        this.screen = screen;
        this.world = screen.getWorld();

        Array<TextureRegion> frames = new Array<TextureRegion>();

        int dioHeight = 80;
        int dioWidth = 60;
        dioStand= new TextureRegion( new Texture("dio.png"));
        defineDio();
        setBounds(1,1, dioWidth / PPM, dioHeight / PPM);
        setCenter(b2body.getPosition().x,b2body.getPosition().y+12/PPM);
        setRegion(dioStand);

    }

    public void defineDio(){
        BodyDef bdef = new BodyDef();
        bdef.position.set( 2, 40 / PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.NPC_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.CHARACTER_BIT;


        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/ PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / PPM, 10 / PPM), new Vector2(2/ PPM, 10 / PPM));
        fdef.filter.categoryBits = ProgramDesign2FinalProject.NPC_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);


    }

    public void touch(){
        screen.dialog.setDialog(true);
    }

    public void draw(Batch batch){
        super.draw(batch);

    }
}
