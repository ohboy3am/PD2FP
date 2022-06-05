package com.game.programdesign2finalproject.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Sounds.SoundManager;

public class StartConversation implements Screen {
    private int conversationCount = -1;
    public Texture mainCharacter;
    public Texture whitebox;
    private SpriteBatch batch;
    private Label myStoryTalk;
    private OrthographicCamera gamecam;
    public Stage stage;
    public Viewport viewport;
    private ProgramDesign2FinalProject game;
    private Table table;
    private Music music;
    public  StartConversation(ProgramDesign2FinalProject game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        mainCharacter = new Texture("main_character_dialog.PNG");
        whitebox = new Texture("whitebox.PNG");
        batch = new SpriteBatch();
        viewport = new FitViewport(ProgramDesign2FinalProject.V_WIDTH, ProgramDesign2FinalProject.V_HEIGHT,gamecam);
        stage = new Stage(viewport, batch);
        this.table = new Table();
        table.bottom();
        gamecam.position.set(viewport.getWorldWidth() / 2,viewport.getWorldHeight() / 2,0);
        //table.setFillParent(true);

        music = SoundManager.getInstance().bgmInConv;
        music.setLooping(true);
        music.play();
        music.setVolume(0.1f);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(whitebox, 0, 0,600,120);
        batch.draw(mainCharacter, 30, 90,200,200);
        batch.end();
        stage.draw();
        if(Gdx.input.justTouched()) {
            conversationCount++;
            changeConversation();
        }
        if(conversationCount == 24) {
            music.stop();
            game.setScreen(new PlayScreen(game));
        }
    }

    //Conversation Start; dots verified.
    String sentence0 = "";
    String sentence1 = "I'm an ordinary freshman in NCKU_CSIE.";
    String sentence2 = "I live a stable life. Such trival things as coding, writing exercises,\n having lunch with friends consist a day of mine.";
    String sentence3 = "However, a dream makes my after life much more exciting (not wet dream).";
    String sentence4 = "In the dream, an adorable pretty girl talked with me.";
    String sentence5 = "After made a brief self introduction, she appeared depressed.";
    String sentence6 = "She was the princess of the D.R.M. Kindom in Isekai,\n as well as the most powerful magician in the kindom.";
    String sentence7 = "Dangerous and broken, her kindom was invaded by the demon corps.";
    String sentence8 = "Under such critical circumstance that was going to be out of control,\n she spent planty of energy casting a rare spell.";
    String sentence9 = "The consequence of the spell was that she could summon a hero to save the kindom.";
    String sentence10 = "I was the chosen person.";
    String sentence11 = "She asked me if I wanted to respond the spell.";
    String sentence12 = "I felt bored with college daily life,\n so I immediately promised her to help saving her kindom without any consideration.";
    String sentence13 = "However, the princess was highly fatigue and left little magic power\n due to defence against the demon corps.";
    String sentence14 = "She, therefore, failed to complete the whole process of spell casting.";
    String sentence15 = "Namely, she was disable to transport me to her world directly.";
    String sentence16 = "She could only sent me to the \"Chaotic space\".";
    String sentence17 = "\"Chaotic space\" was the place where multiple different worlds overlapped.";
    String sentence18 = "I had to cross there on my own to get to the princess' world.";
    String sentence19 = "Finally, I psyched myself up and let princess enchant me.\n I felt I dreamed a weird dream in a dream.";
    String sentence20 = "......";
    String sentence21 = "I woke up in the \"Chaotic space\" and looked around.\n There were no decent roads to pass, without principle and full of danger.";
    String sentence22 = "I couldn't help trembling.\n In spite of that, I still had to keep my promise to princess.";
    String sentence23 = "\"A journey of a thousand miles begins with single step.\"\n Taking a deep breath, I began my first step.";

    public  void tableApply(String sentence, int padBottom){
        table.removeActor(myStoryTalk);
        myStoryTalk = new Label(sentence, new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        table.add(myStoryTalk).expandX().padBottom(padBottom);
        table.add(myStoryTalk).expandX();
        stage.addActor(table);
    }

    public void changeConversation() {
        switch(conversationCount) {
            case 0:
                tableApply(sentence0, 80);
                table.add(myStoryTalk).expandX().padRight(60);
                break;
            case 1:
                tableApply(sentence1, 80);
                break;
            case 2:
                tableApply(sentence2, 70);
                break;
            case 3:
                tableApply(sentence3, 80);
                break;
            case 4:
                tableApply(sentence4, 80);
                break;
            case 5:
                tableApply(sentence5, 80);
                break;
            case 6:
                tableApply(sentence6, 70);
                break;
            case 7:
                tableApply(sentence7, 80);
                break;
            case 8:
                tableApply(sentence8, 70);
                break;
            case 9:
                tableApply(sentence9, 80);
                break;
            case 10:
                tableApply(sentence10, 80);
                break;
            case 11:
                tableApply(sentence11, 80);
                break;
            case 12:
                tableApply(sentence12, 70);
                break;
            case 13:
                tableApply(sentence13, 70);
                break;
            case 14:
                tableApply(sentence14, 80);
                break;
            case 15:
                tableApply(sentence15, 80);
                break;
            case 16:
                tableApply(sentence16, 80);
                break;
            case 17:
                tableApply(sentence17, 80);
                break;
            case 18:
                tableApply(sentence18, 80);
                break;
            case 19:
                tableApply(sentence19, 70);
                break;
            case 20:
                tableApply(sentence20, 80);
                break;
            case 21:
                tableApply(sentence21, 70);
                break;
            case 22:
                tableApply(sentence22, 70);
                break;
            case 23:
                tableApply(sentence23, 70);
                break;
        }
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
