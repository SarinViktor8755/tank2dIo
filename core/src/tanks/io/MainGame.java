package tanks.io;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import tanks.io.Assets.AssetsManagerGame;
import tanks.io.ClientApi.MainClient;
import tanks.io.Screens.GamePlayScreen;
import tanks.io.Screens.MenuScreen;
import tanks.io.Units.NikName;
import tanks.io.adMod.AdAds;

public class MainGame extends Game {
    public AssetManager assetManager;
    public AssetsManagerGame assetsManagerGame;
    private MainClient mainClient;
    private GamePlayScreen gsp;

    private Screen mainMenu;

    //public AssetsManagerGame assetsManagerGame;

    static public boolean ANDROID;      // андроид
    private AdAds adMod;                // реклама
    public String tokken;

    static public final int WHIDE_SCREEN = 555;
    static public final int HIDE_SCREEN = 315;

    public MainGame(int tip) {
        assetManager = new AssetManager();
        assetsManagerGame = new AssetsManagerGame(assetManager);

       // this.getAllAssets();

        if (tip == 1) ANDROID = true;
        else ANDROID = false;
        mainClient = new MainClient(this);

    }

    public void setMainClient(MainClient mainClient) {
        this.mainClient = mainClient;
    }

    @Override
    public void create() {
       // tokken = NikName.getTokken();
        assetsManagerGame.loadAllAssetMenu();
        mainMenu = new MenuScreen(this);
        this.setScreen(mainMenu);


    }

    public void startGamePley(){
        mainMenu.dispose();
        assetsManagerGame.loadAllAsseGame();
        this.gsp = new GamePlayScreen(this);
        this.setScreen(this.gsp);

    }

    public void switchingFromGameMenu(){
    //    playScreen.dispose();
        //assetsManagerGame.loadAllAsseGame();
       //this.gsp.dispose();
        mainMenu = new MenuScreen(this);
        this.setScreen(mainMenu);

    }

    public static boolean isANDROID() {
        return ANDROID;
    }

    public MainClient getMainClient() {
        return mainClient;
    }


    public void getAllAssets(){
        this.assetManager = new AssetManager();
        this.assetManager.update();
        this.assetManager.finishLoading();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void updateClien(){
        this.getMainClient().upDateClient();
    }


}

