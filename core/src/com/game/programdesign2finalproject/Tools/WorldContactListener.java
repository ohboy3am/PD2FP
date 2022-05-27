package com.game.programdesign2finalproject.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.programdesign2finalproject.Sprites.Items.Item;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Sprites.Character;
import com.game.programdesign2finalproject.Sprites.Enemy;
import com.game.programdesign2finalproject.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;


        switch (cDef){
                //主角頭部撞擊
            case ProgramDesign2FinalProject.CHARACTER_HEAD_BIT | ProgramDesign2FinalProject.BRICK_BIT:
            case ProgramDesign2FinalProject.CHARACTER_HEAD_BIT | ProgramDesign2FinalProject.COIN_BIT:

                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.CHARACTER_HEAD_BIT){
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Character) fixA.getUserData());
                }

                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.CHARACTER_HEAD_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Character) fixB.getUserData());
                break;

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
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                }
                break;

                //主角撞到敵人
            case ProgramDesign2FinalProject.CHARACTER_BIT | ProgramDesign2FinalProject.ENEMY_BIT:
                break;

                //敵人撞到敵人
            case ProgramDesign2FinalProject.ENEMY_BIT | ProgramDesign2FinalProject.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                break;

                //道具碰到物件
            case ProgramDesign2FinalProject.ITEM_BIT | ProgramDesign2FinalProject.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true,false);
                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.ITEM_BIT){
                    ((Item)fixB.getUserData()).reverseVelocity(true,false);
                }
                break;

                //主角撞到道具
            case ProgramDesign2FinalProject.ITEM_BIT | ProgramDesign2FinalProject.CHARACTER_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.ITEM_BIT)
                   ((Item)fixA.getUserData()).use((Character)fixB.getUserData());
                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.ITEM_BIT)
                    ((Item)fixB.getUserData()).use((Character)fixA.getUserData());
                break;


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
