package com.game.programdesign2finalproject;

import com.badlogic.gdx.Game;

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
	public static final short BOSS_BIT = 2048;
	public static final short BOSS_ATTACK_BIT = 4096;



	public static final String MUSIC_PATH = "audio/music/mario_music_minor.mp3";
	public static final String MUSIC_PATH_1 = "Chopin_Sonata_Doppio_movimento.mp3";
	public static final String MUSIC_PATH_2 = "Victory_Screech.mp3";
	public static final String MUSIC_PATH_SEPHIROTH = "angel.ogg";
	public static final String SOUND_PATH_COIN = "audio/sounds/coin.wav";
	public static final String SOUND_PATH_BRICK = "audio/sounds/breakblock.wav";
	public static final String SOUND_PATH_BUMP = "audio/sounds/bump.wav";
	public static final String SOUND_PATH_SPAWN = "audio/sounds/spawn.wav";
	public static final String SOUND_PATH_POWERUP = "audio/sounds/powerup.wav";
	public static final String SOUND_PATH_STOMP = "audio/sounds/stomp.wav";
	public static final String SOUND_PATH_POWERDOWN = "audio/sounds/powerdown.wav";
	public static final String SOUND_PATH_WRYYY = "audio/sounds/wryyy.wav";
	public static final String SOUND_PATH_CHARACTERDIE = "audio/sounds/wasted.wav";
	public static final String SOUND_PATH_TheWorld = "audio/sounds/TheWorld.wav";
	public static final String SOUND_PATH_DRAGON = "dragonYell.wav";
	public static final String SOUND_PATH_BOSSENTER = "bossenter.wav";
	public static final String SOUND_PATH_EXPLSION = "explosion.wav";
	public static final String SOUND_PATH_SHOUT01 = "shout01.wav";
	public static final String SOUND_PATH_SHOUT02 = "shout02.wav";
	public static final String SOUND_PATH_SHOUT03 = "shout03.wav";
	public static final String SOUND_PATH_SHOUT04 = "shout04.wav";
	public static final String SOUND_PATH_DRAGONDIE = "dragondie.wav";
	public static final String SOUND_PATH_NEWMOON = "newmoon.wav";
	public static final String SOUND_PATH_SPIRIT = "Die sound.mp3";
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
