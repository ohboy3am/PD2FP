package com.game.programdesign2finalproject.Sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;

public class SoundManager {
    public Music bgm;
    public Sound soundCoin;
    public Sound soundBrick;
    public Sound soundBump;
    public Sound soundSpawn;
    public Sound soundPowerUp;
    public Sound soundPowerDown;
    public Sound soundStomp;
    public Sound soundWryyy;
    public Sound soundCharacterDie;
    public Sound soundYakyu;

    private static SoundManager singleInstance = null;

    public SoundManager(){
        bgm = Gdx.audio.newMusic(Gdx.files.internal(ProgramDesign2FinalProject.MUSIC_PATH));
        soundCoin = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_COIN));
        soundBrick = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_BRICK));
        soundBump = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_BUMP));
        soundSpawn = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_SPAWN));
        soundPowerUp = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_POWERUP));
        soundPowerDown = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_POWERDOWN));
        soundStomp = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_STOMP));
        soundWryyy = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_WRYYY));
        soundCharacterDie = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_CHARACTERDIE));
        soundYakyu = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_YAKYU));
    }

    public static SoundManager getInstance(){
        if(singleInstance == null) {
            singleInstance = new SoundManager();
        }
        return singleInstance;
    }

}
