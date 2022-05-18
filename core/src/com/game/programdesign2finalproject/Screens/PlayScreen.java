package com.game.programdesign2finalproject.Screens;

import static com.game.programdesign2finalproject.ProgramDesign2FinalProject.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.programdesign2finalproject.ProgramDesign2FinalProject;

import com.game.programdesign2finalproject.Scenes.Hud;
import com.game.programdesign2finalproject.Sprites.Character;
import com.game.programdesign2finalproject.Tools.B2WorldCreator;

public class PlayScreen implements Screen {
    //遊戲的reference
    private ProgramDesign2FinalProject game;
    private TextureAtlas atlas;


    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //地圖變數
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d變數
    private World world;
    private Box2DDebugRenderer b2dr;

    //sprites
    private Character player;

    public PlayScreen(ProgramDesign2FinalProject game){
        atlas = new TextureAtlas("Character_and_Enemies.pack");

        this.game = game;
        //遊戲中的視角
        gamecam = new OrthographicCamera();
        //Fitviewport用來維持螢幕比例
        gamePort = new FitViewport(ProgramDesign2FinalProject.V_WIDTH / PPM,ProgramDesign2FinalProject.V_HEIGHT / PPM,gamecam);
        //HUD顯示 分數 時間 等級
        hud = new Hud(game.batch);

        //加載地圖以及設定如何繪製地圖
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1 / PPM);

        //初始化gamecam
        gamecam.position.set(gamePort.getWorldWidth() / 2,gamePort.getWorldHeight() / 2,0);


        //初始化world
        world = new World(new Vector2(0,-10),true);

        //初始化Box2DDebugRenderer
        b2dr = new Box2DDebugRenderer();
        //讓程式可以畫出debug線
        new B2WorldCreator(world, map);

        //初始化角色
        player = new Character(world, this);

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        //角色移動
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
        player.b2body.applyLinearImpulse(new Vector2(-0.1f,0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt){
        //先處裡input
        handleInput(dt);

        world.step(1/60f,6,2);

        player.update(dt);

        gamecam.position.x = player.b2body.getPosition().x;

        //更新gamecam
        gamecam.update();
        //讓renderer只畫gamecam看到的東西
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
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
        game.batch.end();

        //畫出Hud camera看到的東西
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //更新gamePort
        gamePort.update(width,height);
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
