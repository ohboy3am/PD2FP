package com.game.programdesign2finalproject.Screens;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.game.programdesign2finalproject.Scenes.Dialog;

import com.game.programdesign2finalproject.Sprites.Goomba;
import com.game.programdesign2finalproject.Sprites.Items.Item;
import com.game.programdesign2finalproject.Sprites.Items.ItemDef;
import com.game.programdesign2finalproject.Sprites.Items.Mushroom;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;

import com.game.programdesign2finalproject.Scenes.Hud;
import com.game.programdesign2finalproject.Sounds.SoundManager;
import com.game.programdesign2finalproject.Sprites.Character;
import com.game.programdesign2finalproject.Sprites.Enemy;
import com.game.programdesign2finalproject.Tools.B2WorldCreator;
import com.game.programdesign2finalproject.Tools.WorldContactListener;

import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    //遊戲的reference
    private ProgramDesign2FinalProject game;
    private TextureAtlas atlas;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    public Dialog dialog;

    //地圖變數
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d變數
    private World world;
    private Box2DDebugRenderer b2dr;
    private  B2WorldCreator creator;

    //sprites
    private Character player;

    private Music music;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;
    private boolean IsPaused = false;

    public PlayScreen(ProgramDesign2FinalProject game){

        atlas = new TextureAtlas("NEW_Character_and_Enemies.pack");
        this.game = game;
        //遊戲中的視角
        gamecam = new OrthographicCamera();
        //Fitviewport用來維持螢幕比例
        gamePort = new FitViewport(ProgramDesign2FinalProject.V_WIDTH / PPM,ProgramDesign2FinalProject.V_HEIGHT / PPM,gamecam);
        //HUD顯示 分數 時間 等級
        hud = new Hud(game.batch);
        dialog = new Dialog();

        //加載地圖以及設定如何繪製地圖
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1 / PPM);

        //初始化gamecam
        gamecam.position.set(gamePort.getWorldWidth() / 2,gamePort.getWorldHeight() / 2,0);

        //5
        //初始化world
        world = new World(new Vector2(0,-10),true);

        //初始化Box2DDebugRenderer
        b2dr = new Box2DDebugRenderer();
        //讓程式可以畫出debug線
        creator = new B2WorldCreator(this);

        //初始化角色
        player = new Character(this);

        world.setContactListener(new WorldContactListener());

        //設置音樂
        music = SoundManager.getInstance().bgm;
        music.setLooping(true);
        //music.play();

        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();


    }

    public void spawnItem(ItemDef idef){
        itemsToSpawn.add(idef);
    }

    public void handleSpawningItems() {
        while (!itemsToSpawn.isEmpty()){
            ItemDef idef = itemsToSpawn.poll();


            if (idef.type == Mushroom.class){

                items.add(new Mushroom(this, idef.position.x, idef.position.y));

            }

        }

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(player.currentState == Character.State.DEAD) return;
        //角色移動
        if (player.jumpTime <3){
            if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
                player.b2body.applyLinearImpulse(new Vector2(0,3f), player.b2body.getWorldCenter(), true);
                player.jumpTime+=1;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2)
        player.b2body.applyLinearImpulse(new Vector2(-0.1f,0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.justTouched()){
            player.fire();
        }

    }

    public void update(float dt){
        //先處裡input
        if(IsPaused == true) return;
        handleInput(dt);

        //物理模擬中的每一步 (每秒60次)
        world.step(1/60f,6,2);

        player.update(dt);

        Array<Enemy> enemyFound = new Array<Enemy>();
        for (Enemy enemy : creator.getGoombas()){

            if (enemy instanceof Goomba){
                if (enemy.isVanished()) {
                    enemyFound.add(enemy);
                    continue;
                }
            }

                enemy.update(dt);

                if (enemy.isDestroyed()) continue;

                if (enemy.getX() < player.getX() + 224 / PPM)
                    enemy.b2body.setActive(true);

            //224個像素內敵人醒來

        }
        creator.getGoombas().removeAll(enemyFound, true);

        Array<Item> itemFound = new Array<Item>();
        for (Item item :items){

            if (item.isDestroyed())
            {
                itemFound.add(item);
                continue;
            }

             item.update(dt);
        }
        items.removeAll(itemFound, true);

        handleSpawningItems();


        hud.update(dt);

        //讓gamecam到player.x的位置
        if (player.currentState != Character.State.DEAD)
        gamecam.position.x = player.b2body.getPosition().x;

        //更新gamecam
        gamecam.update();
        //讓renderer只畫gamecam看到的東西
        renderer.setView(gamecam);

    }

    @Override
    public void render(float delta) {
        //世界!!! 時間停止!!!
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            if(IsPaused == false) {
                IsPaused = true;
                SoundManager.getInstance().soundStopTime.play();
            }else {
                IsPaused = false;
            }
        }
        //分隔update logic 跟render
        update(delta);
        //清理螢幕
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //繪製地圖
        renderer.render();
        //畫出debug線
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for (Enemy enemy : creator.getGoombas()){
            if (enemy.isDestroyed()) continue;
            enemy.draw(game.batch);
        }
        for (Item item :items){
            if (item.isDestroyed()) continue;
            item.draw(game.batch);
        }

        game.batch.end();

        //畫出Hud camera看到的東西
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        dialog.draw();
        if (gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public boolean gameOver(){
        if (player.currentState == Character.State.DEAD && player.getStateTimer() > 3){
            return true;
        }
        else return false;
    }
    @Override
    public void resize(int width, int height) {
        //更新gamePort
        gamePort.update(width,height);
    }

    public TiledMap getMap(){
        return map;
    }
    public World getWorld(){
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }
}
