package com.game.programdesign2finalproject.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;
import com.game.programdesign2finalproject.Screens.PlayScreen;



public class CharacterInformation implements Disposable {
    //Scene2D.ui Stage 跟他的 HUD Viewport
    public Stage stage;
    public Viewport viewport;

    //紀錄分數跟時間的變數
    private int playerHP;
    private int bossHP;
    private int preBossHP;

    //Scene2D 的 部件
    private Label playerHPLabel;
    private static Label scoreLabel;
    private Label bossHPLabel;

    private PlayScreen screen;

    private SpriteBatch batch;
    private Texture textureFu, textureEp, tHead;
    private Sprite sprite, spriteDoge;
    TextureRegion region;

    private int hpWidth = 360;
    private int hpHeight = 36;
    private int hpWidthC = hpWidth;
    private int healthbarPositionX = 45;
    private int healthbarPositionY = 5;

    private float regDoge = 0;
    BitmapFont font;

    public CharacterInformation(SpriteBatch sb, PlayScreen screen){


        batch = new SpriteBatch();
        this.screen = screen;
        playerHP = 6;
        bossHP = 20;
        preBossHP = 20;

        viewport = new FitViewport(ProgramDesign2FinalProject.V_WIDTH, ProgramDesign2FinalProject.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);


        playerHPLabel = new Label(String.format("PLAYER LIFE : %02d",playerHP), new Label.LabelStyle(new BitmapFont(), Color.GREEN));



        table.row();
        table.add(scoreLabel).expandX();

        table.row();

        stage.addActor(table);

        // 载图
        textureFu = new Texture(Gdx.files.internal("healthbar.png"));
        // 抗锯齿
        textureFu.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        textureEp = new Texture(Gdx.files.internal("emptyhealthbar.png"));
        textureEp.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // doge头像
        tHead = new Texture(Gdx.files.internal("bahamuthead.png"));
        spriteDoge = new Sprite(new TextureRegion(tHead, 0, 0, 244, 242));
        spriteDoge.setBounds(10, 10, 100, 100);

        //把彩条图转为可截取的TextureRegion，默认为从右向左截取；
        region = new TextureRegion(textureFu, 0, 0, hpWidth, hpHeight);

        // sprite有更强的图形控制，为了把彩条变红色
        sprite = new Sprite(region);
        sprite.setBounds(healthbarPositionX, healthbarPositionY, 360*1.5f, 18);

        //字体
        font = new BitmapFont();


    }

    public void update(float dt){
        playerHP = screen.getPlayer().getHp();
        playerHPLabel.setText(String.format("PLAYER LIFE : %02d",playerHP));

        if (!screen.isBossExsit())return;

        bossHP = screen.getBoss0().getHp();
        //血条动画
        if (hpWidth > hpWidthC && hpWidth > 0) {
            hpWidth -= dt * 20;
        }
        if (hpWidth < hpWidthC){
            hpWidth = hpWidthC;
        }
        // 头像动画
        if (regDoge > 0.05f) {
            regDoge -= 0.05f;
        }
        if (preBossHP>bossHP){
            regDoge = 0.8f;
        }
        if (hpWidthC > hpWidthC/20) {
            hpWidthC -= (preBossHP-bossHP)*18;


                // 每次攻击重置动画头像alpha

        } else {
                // hit伤害大于血量的情况处理
                hpWidthC = 0;
        }


        preBossHP = bossHP;



    }

    public void draw(PlayScreen screen) {
        batch.setProjectionMatrix(this.stage.getCamera().combined);
        batch.begin();
        font.draw(batch, " Life : " + (int) playerHP , 80, 250);
        if (!screen.isBossExsit()){
            batch.end();
            return;
        }


        // textureEp 空血条不需要动
        batch.draw(textureEp, healthbarPositionX, healthbarPositionY, 360*1.5f, 18);

        //绘制红条  = 动画减血
        sprite.setColor(1, 0, 0, 0.4f);
        sprite.setSize(hpWidth*1.5f, 18);
        sprite.setRegionWidth(hpWidth);
        sprite.draw(batch);

        // 绘制彩色条 = 瞬间减血
        region.setRegionWidth(hpWidthC);
        batch.draw(region, healthbarPositionX, healthbarPositionY, hpWidthC*1.5f, 18);

        // 画静态头像
        batch.draw(tHead, 10, 10, 100, 100);
        // 通过修改alpha值达到动画效果
        spriteDoge.setColor(1, 0, 0, regDoge);
        // 画动画头像
        spriteDoge.draw(batch);

        // 数字血量
        font.draw(batch, (int) bossHP*100 + " / 2000", 110, 40);
        font.draw(batch, "HP", 80, 40);

        batch.end();

    }


    @Override
    public void dispose() {
        stage.dispose();
    }


}
