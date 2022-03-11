package tanks.io.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import tanks.io.AudioEngine.AudioEngine;
import tanks.io.CameraGame;
import tanks.io.InputProcessor.InputProcessorPC;
import tanks.io.Locations.GameSpace;
import tanks.io.MainGame;
import tanks.io.ParticleEffect.ParticleCustum;
import tanks.io.Screens.Controll.Controller;
import tanks.io.Units.Bullets;
import tanks.io.Units.Tanks.Tank;
import tanks.io.Units.Tanks.TanksOther;

public class GamePlayScreenSP extends GamePlayScreen{
    private MainGame mainGame;
    private CameraGame cameraGame;
    private SpriteBatch batch;
    private float timeInGame = 0; // время в игре
    private GameSpace gameSpace; // карта_ локация
    private AudioEngine audioEngine;// игравой движок
    private InputProcessorPC inputProcessorPC;
    private Controller controller;
    private Tank tank;
    public Vector2 pos;
    private Bullets bullets;
    public ParticleCustum pc;
    private TanksOther tanksOther;

    public GamePlayScreenSP(MainGame mainGame) {

        this.mainGame = mainGame;
        this.batch = new SpriteBatch();
        this.timeInGame = 0;
        this.gameSpace = new GameSpace(this);
        this.audioEngine = new AudioEngine(this);
        this.tanksOther = new TanksOther(this);
        this.inputProcessorPC = new InputProcessorPC(this);
        Gdx.input.setInputProcessor(inputProcessorPC);
        this.pos = new Vector2(150, 150);
        this.cameraGame = new CameraGame(MainGame.WHIDE_SCREEN * 1.2f, MainGame.HIDE_SCREEN * 1.2f, gameSpace.getSizeLocationPixel(), gameSpace.WITH_LOCATION, gameSpace.HEIHT_LOCATION);
        this.cameraGame.jampCameraToPoint(pos.x, pos.y);

//        this.controller = new Controller(this, mainGame.getAssetManager().get("flatDark26.png", Texture.class), mainGame.getAssetManager().get("flatDark261.png", Texture.class));
        this.controller = new Controller(this);

        tank = new Tank(this);

        bullets = new Bullets(this);
        pc = new ParticleCustum(this, mainGame.getAssetManager().get("particle1.png", Texture.class), mainGame.getAssetManager().get("fire.png", Texture.class), mainGame.getAssetManager().get("iron.png", Texture.class),mainGame.getAssetManager().get("de.pack", TextureAtlas.class));

    }


}
