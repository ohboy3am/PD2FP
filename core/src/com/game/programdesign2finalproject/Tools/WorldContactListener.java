package com.game.programdesign2finalproject.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.game.programdesign2finalproject.Sprites.Dio;
import com.game.programdesign2finalproject.Sprites.Attacks.FireBall;
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
            case ProgramDesign2FinalProject.CHARACTER_BIT | ProgramDesign2FinalProject.COIN_BIT:
            case ProgramDesign2FinalProject.CHARACTER_BIT | ProgramDesign2FinalProject.BRICK_BIT:
            case ProgramDesign2FinalProject.CHARACTER_BIT | ProgramDesign2FinalProject.OBJECT_BIT:
            case ProgramDesign2FinalProject.CHARACTER_BIT | ProgramDesign2FinalProject.GROUND_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.CHARACTER_BIT){
                    ((Character) fixA.getUserData()).jumpTime = 0;
                }

                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.CHARACTER_BIT)
                    ((Character) fixB.getUserData()).jumpTime = 0;
                break;

                //主角頭部撞擊
            case ProgramDesign2FinalProject.CHARACTER_HEAD_BIT | ProgramDesign2FinalProject.BRICK_BIT:
            case ProgramDesign2FinalProject.CHARACTER_HEAD_BIT | ProgramDesign2FinalProject.COIN_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.CHARACTER_HEAD_BIT){
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Character) fixA.getUserData());
                }

                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.CHARACTER_HEAD_BIT)
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Character) fixB.getUserData());
                break;


            case ProgramDesign2FinalProject.ENEMY_BIT | ProgramDesign2FinalProject.FIREBALL_BIT:
            case ProgramDesign2FinalProject.ENEMY_HEAD_BIT | ProgramDesign2FinalProject.FIREBALL_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.FIREBALL_BIT)
                    ((Enemy)fixB.getUserData()).hitOnHead();
                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.FIREBALL_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead();
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
            case ProgramDesign2FinalProject.ENEMY_HEAD_BIT | ProgramDesign2FinalProject.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.OBJECT_BIT) {
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                }
                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.OBJECT_BIT){
                        ((Enemy)fixA.getUserData()).reverseVelocity(true,false);
                }
                break;

                //主角撞到敵人
            case ProgramDesign2FinalProject.CHARACTER_BIT | ProgramDesign2FinalProject.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.CHARACTER_BIT){
                    ((Character) fixA.getUserData()).hit();
                }

                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.CHARACTER_BIT)
                    ((Character) fixB.getUserData()).hit();
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

                //主角跟NPC接觸
            case ProgramDesign2FinalProject.NPC_BIT | ProgramDesign2FinalProject.CHARACTER_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.NPC_BIT)
                    ((Dio)fixA.getUserData()).touch(Gdx.input.justTouched());
                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.NPC_BIT)
                    ((Dio)fixB.getUserData()).touch(Gdx.input.justTouched());
                break;

            case ProgramDesign2FinalProject.FIREBALL_BIT | ProgramDesign2FinalProject.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == ProgramDesign2FinalProject.FIREBALL_BIT)
                    ((FireBall)fixA.getUserData()).setToDestroy();
                else if(fixB.getFilterData().categoryBits == ProgramDesign2FinalProject.FIREBALL_BIT)
                    ((FireBall)fixB.getUserData()).setToDestroy();
                break;

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
