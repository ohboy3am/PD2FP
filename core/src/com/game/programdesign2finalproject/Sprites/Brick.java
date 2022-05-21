package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Scenes.Hud;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sounds.SoundManager;

public class Brick extends InteractiveTileObject{
    public Brick(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(ProgramDesign2FinalProject.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","Collision");
        setCategoryFilter(ProgramDesign2FinalProject.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
        SoundManager.getInstance().soundBrick.play();
    }
}