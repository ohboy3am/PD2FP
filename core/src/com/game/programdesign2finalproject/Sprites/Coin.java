package com.game.programdesign2finalproject.Sprites;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.programdesign2finalproject.Sprites.Items.ItemDef;
import com.game.programdesign2finalproject.Sprites.Items.Mushroom;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Scenes.Hud;
import com.game.programdesign2finalproject.Screens.PlayScreen;
import com.game.programdesign2finalproject.Sounds.SoundManager;

public class Coin extends InteractiveTileObject{

    private static TiledMapTileSet tileset;
    private final  int BLANK_COIN = 27+1;

    public Coin(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        tileset = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(ProgramDesign2FinalProject.COIN_BIT);
    }

    @Override
    public void onHeadHit(Character character) {


        if (getCell().getTile().getId() == BLANK_COIN){
            SoundManager.getInstance().soundBump.play();
        }
        else if(getCell().getTile().getId() == 24+1){
            SoundManager.getInstance().soundCoin.play();
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 /PPM), Mushroom.class));
            SoundManager.getInstance().soundSpawn.play();
            getCell().setTile(tileset.getTile(BLANK_COIN));
            Hud.addScore(100);
        }


    }

}
