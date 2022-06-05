package com.game.programdesign2finalproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.game.programdesign2finalproject.Screens.Menu;
import com.game.programdesign2finalproject.Screens.PlayScreen;

public class ProgramDesign2FinalProject extends Game {
	public static final int V_WIDTH = 600;
	public static final int V_HEIGHT = 304;  // dots changed value
	//pixel per meter
	public static final float PPM =100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short CHARACTER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short CHARACTER_HEAD_BIT = 512	;
	public static final short FIREBALL_BIT = 1024;
	public static final short NPC_BIT = 2048;

	public static final String MUSIC_PATH = "Jolly - Bulikirály.mp3";
	public static final String MUSIC_PATH_1 = "Chopin_Sonata_Doppio_movimento.mp3";
	public static final String MUSIC_PATH_2 = "puyo.mp3";
	public static final String SOUND_PATH_COIN = "audio/sounds/coin.wav";
	public static final String SOUND_PATH_BRICK = "audio/sounds/breakblock.wav";
	public static final String SOUND_PATH_BUMP = "audio/sounds/bump.wav";
	public static final String SOUND_PATH_SPAWN = "audio/sounds/spawn.wav";
	public static final String SOUND_PATH_POWERUP = "audio/sounds/powerup.wav";
	public static final String SOUND_PATH_STOMP = "audio/sounds/stomp.wav";
	public static final String SOUND_PATH_POWERDOWN = "audio/sounds/powerdown.wav";
	public static final String SOUND_PATH_WRYYY = "audio/sounds/wryyy.wav";
	public static final String SOUND_PATH_CHARACTERDIE = "audio/sounds/mariodie.wav";
	public static final String SOUND_PATH_TheWorld = "audio/sounds/TheWorld.wav";

	public SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new Menu(this));

		//setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();

		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
	

}
