package com.game.programdesign2finalproject.Sprites.Items;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sprites.Character;

public class Mushroom extends Item {
    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("mushroom"), 0, 0, 16, 16);
        velocity = new Vector2(0.7f, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / ProgramDesign2FinalProject.PPM);
        fdef.filter.categoryBits = ProgramDesign2FinalProject.ITEM_BIT;
        fdef.filter.maskBits = ProgramDesign2FinalProject.CHARACTER_BIT |
                ProgramDesign2FinalProject.OBJECT_BIT |
                ProgramDesign2FinalProject.GROUND_BIT |
                ProgramDesign2FinalProject.COIN_BIT |
                ProgramDesign2FinalProject.BRICK_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Character character) {
        character.grow();
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (destroyed) return;
        setCenter(body.getPosition().x , body.getPosition().y);
        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }
}
