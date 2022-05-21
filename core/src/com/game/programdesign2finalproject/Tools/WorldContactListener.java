package com.game.programdesign2finalproject.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.programdesign2finalproject.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        if("head".equals(fixA.getUserData()) || "head".equals(fixB.getUserData())){
            Fixture head = fixA.getUserData().equals("head")? fixA : fixB;
            Fixture object = fixA ==head ? fixB : fixA;

            if(object.getUserData() != null && (object.getUserData() instanceof InteractiveTileObject) ){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
