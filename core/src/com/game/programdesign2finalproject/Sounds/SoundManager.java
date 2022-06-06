package com.game.programdesign2finalproject.Sounds;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;




public class SoundManager {
    public Music bgm;
    public Music bgmInConv;
    public Music soundBoss;
    public Music Victory;
    public Sound soundCoin;
    public Sound soundBrick;
    public Sound soundBump;
    public Sound soundSpawn;
    public Sound soundPowerUp;
    public Sound soundPowerDown;
    public Sound soundStomp;
    public Sound soundWryyy;
    public Sound soundCharacterDie;
    public Sound soundStopTime;
    public Sound soundDragonYell;
    public Sound soundBossEnter;
    public Sound soundExplosion;
    public Sound[] soundShout = new Sound[4];
    public Sound soundDragonDie;
    public Sound soundNewMoon;
    public Sound soundSpirit;


    private static SoundManager singleInstance = null;

    public SoundManager(){
        bgm = Gdx.audio.newMusic(Gdx.files.internal(ProgramDesign2FinalProject.MUSIC_PATH));
        bgmInConv = Gdx.audio.newMusic(Gdx.files.internal(ProgramDesign2FinalProject.MUSIC_PATH_1));
        soundBoss = Gdx.audio.newMusic(Gdx.files.internal(ProgramDesign2FinalProject.MUSIC_PATH_SEPHIROTH));
        Victory = Gdx.audio.newMusic(Gdx.files.internal(ProgramDesign2FinalProject.MUSIC_PATH_2));
        soundCoin = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_COIN));
        soundBrick = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_BRICK));
        soundBump = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_BUMP));
        soundSpawn = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_SPAWN));
        soundPowerUp = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_POWERUP));
        soundPowerDown = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_POWERDOWN));
        soundStomp = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_STOMP));
        soundWryyy = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_WRYYY));
        soundCharacterDie = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_CHARACTERDIE));
        soundStopTime = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_TheWorld));
        soundDragonYell = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_DRAGON));
        soundBossEnter = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_BOSSENTER));
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_EXPLSION));
        soundShout[0] =  Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_SHOUT01));
        soundShout[1] =  Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_SHOUT02));
        soundShout[2] =  Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_SHOUT03));
        soundShout[3] =  Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_SHOUT04));
        soundDragonDie = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_DRAGONDIE));
        soundNewMoon = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_NEWMOON));
        soundSpirit = Gdx.audio.newSound(Gdx.files.internal(ProgramDesign2FinalProject.SOUND_PATH_SPIRIT));






    }

    public static SoundManager getInstance(){
        if(singleInstance == null) {
            singleInstance = new SoundManager();
        }
        return singleInstance;
    }

}
