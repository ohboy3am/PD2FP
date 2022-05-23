package com.game.programdesign2finalproject.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Sprites.Enemy;
import com.game.programdesign2finalproject.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if("head".equals(fixA.getUserData()) || "head".equals(fixB.getUserData())){
            Fixture head = "head".equals(fixA.getUserData())? fixA : fixB;
            Fixture object = fixA ==head ? fixB : fixA;

            if(object.getUserData() != null && (object.getUserData() instanceof InteractiveTileObject) ){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }

        }

        switch (cDef){

            //主角採到敵人頭上
            case ProgramDesign2FinalProject.ENEMY_HEAD_BIT | ProgramDesign2FinalProject.CHARACTER_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.ENEMY_HEAD_BIT)
                    ((Enemy)fixB.getUserData()).hitOnHead();
                break;

                //敵人碰到物件
            case ProgramDesign2FinalProject.ENEMY_BIT | ProgramDesign2FinalProject.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.ENEMY_BIT){
                    Gdx.app.log("goomba","hit wall");
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                }

                break;

            case ProgramDesign2FinalProject.CHARACTER_BIT | ProgramDesign2FinalProject.ENEMY_BIT:
                Gdx.app.log("goomba","Kill you");
                break;

                //敵人撞到敵人
            case ProgramDesign2FinalProject.ENEMY_BIT | ProgramDesign2FinalProject.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
            default:
                break;
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
