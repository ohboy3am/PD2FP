package com.game.programdesign2finalproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.game.programdesign2finalproject.Screens.PlayScreen;

public class ProgramDesign2FinalProject extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short CHARACTER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;

	public static final String MUSIC_PATH = "audio/music/mario_music.ogg";
	public static final String SOUND_PATH_COIN = "audio/sounds/coin.wav";
	public static final String SOUND_PATH_BRICK = "audio/sounds/breakblock.wav";
	public static final String SOUND_PATH_BUMP = "audio/sounds/bump.wav";

	public SpriteBatch batch;

	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();


		setScreen(new PlayScreen(this));
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
