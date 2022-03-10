package tanks.io.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.io.IOException;

import tanks.io.ClientApi.MainClient;
import tanks.io.MainGame;
import tanks.io.Units.NikName;

public class MenuScreen implements Screen {
    private MainGame mainGame;

    private SpriteBatch batch;
    private StretchViewport viewport;
    private OrthographicCamera camera;
    private MainClient mainClient;

    private Texture wallpaper;
    private Texture wallpaper1;
    private Texture logo;

    private float timeInScreen;
    private float timerStartGame;
    private boolean startgame;

    public Label statusConnetct;

    //////////////
    private Stage stageMenu;
    private Skin skinMenu;

    TextButton textButton;

    private boolean button_start_click;


    FloatArray dummyArray = new FloatArray();
    String limit = "";


    public MenuScreen(final MainGame mainGame) {

        System.out.println();
        button_start_click = false;

        this.mainGame = mainGame;
        timeInScreen = 0;
        timerStartGame = 0;
        startgame = false;

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new StretchViewport(MainGame.WHIDE_SCREEN, MainGame.HIDE_SCREEN, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        mainClient = mainGame.getMainClient();
        stageMenu = new Stage(viewport);

        wallpaper = mainGame.assetManager.get("menuAsset/wallpaper.png", Texture.class);
        wallpaper1 = mainGame.assetManager.get("menuAsset/wallpaper1.png", Texture.class);
        logo = mainGame.assetManager.get("menuAsset/logo.png", Texture.class);

        stageMenu = new Stage(viewport);

        skinMenu = mainGame.assetManager.get("skin/uiskin.json");

        final TextField textField = new TextField(limit, skinMenu);

        textField.setMaxLength(20);
        textField.setPosition(20,250);
        textField.setText(NikName.getNikName());

        statusConnetct = new Label("Connetct . . .",skinMenu);
        statusConnetct.setPosition(350,170);
        statusConnetct.setColor(Color.DARK_GRAY);
        statusConnetct.setFontScale(.6f);

      //  textField.setFillParent(true);


    //    System.out.println(viewport.getRightGutterX());

        textButton = new TextButton("Play Game", skinMenu);
        ///System.out.println(textField.getText());

        textButton.setX(350);
        textButton.setY(30);
        textButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                if(!mainGame.getMainClient().isConnect()) return false; else {
//                    try {
//                        mainGame.getMainClient().getClient().reconnect();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

                if(!button_start_click) {
                    mainClient.getNetworkPacketStock().toSendButtonStartClick();
                    button_start_click = true;
                }
                startgame = true;

                //System.out.println(textField.getText());
                if(!mainClient.getClient().isConnected()) {
                    try {
                        mainClient.getClient().reconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;}
                mainGame.assetsManagerGame.loadAllAsseGame();
                NikName.setNikName(textField.getText());

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(!mainGame.getMainClient().isConnect()) return;

                //System.out.println("StartGameDown");
                if(!mainClient.getClient().isConnected()) return;
                if(!button_start_click) {
                    mainClient.getNetworkPacketStock().toSendButtonStartClick();
                    button_start_click = true;
                }
                if(!mainClient.getClient().isConnected()) return;
                startgame = true;


            }
        });

        stageMenu.addActor(textButton);
        stageMenu.addActor(textField);
        stageMenu.addActor(statusConnetct);
        Gdx.input.setInputProcessor(stageMenu);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        upDateScreen();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();
        batch.setColor(1 - timerStartGame, 1 - timerStartGame, 1 - timerStartGame, 1);
        //batch.draw(wallpaper1,0,0,camera.viewportWidth, camera.viewportHeight,1,2,(int)camera.viewportWidth,(int) camera.viewportHeight,false,false);
        //System.out.println((MathUtils.sin(timeInScreen) + 1)/2);
        batch.draw(wallpaper1, viewport.getScreenX(), viewport.getScreenY() + ((MathUtils.sin(timeInScreen) + 1) / 2) * 20);
        batch.draw(wallpaper, viewport.getScreenX(), viewport.getScreenY() - ((Interpolation.bounce.apply((MathUtils.sin(timeInScreen) + 1) / 2) * 10)));

        batch.draw(logo, viewport.getScreenX(), viewport.getScreenY() + 14 + ((MathUtils.cos(timeInScreen * 3) + 1) / 2) * 20);
        this.batch.end();
        stageMenu.draw();


    }

    private void upDateScreen() {
        mainGame.updateClien();
       // statusConnetct.setText(mainClient.getClient().isConnected() +"");

        if(mainClient.getClient().isConnected()){
         //   System.out.println(MathUtils.sin(Gdx.graphics.getDeltaTime()));
           // statusConnetct.setText("Ping :" + mainClient.getPing());
            statusConnetct.setColor(0,0,0,1);
            textButton.setText("Play Game");

        } else {
            statusConnetct.setText("Server:disconnect");
            statusConnetct.setColor(Color.RED);
            textButton.setText("Connect");
        }
        this.timeInScreen = Gdx.graphics.getDeltaTime() + this.timeInScreen;
        if (startgame) {
            timerStartGame = timerStartGame + Gdx.graphics.getDeltaTime() * 1.5f;
        }
        if (timerStartGame > 1) mainGame.startGamePley();
        // if (timeInScreen > 3.2f) mainGame.startGamePley();
       // kefMashtab = Interpolation.bounceOut.apply(MathUtils.sin(timeInScreen) + 1);
        //    System.out.println(kefMashtab);
        //System.out.println(mainClient.getClient().isConnected());
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
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
        batch.dispose();
        stageMenu.dispose();
        wallpaper.dispose();
        //   play.dispose();
        logo.dispose();
    }

    public void setStartgame(boolean startgame) {
        this.startgame = startgame;
    }


}
