package tanks.io.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import tanks.io.MainGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Tanks2D.io";
        config.width = MainGame.WHIDE_SCREEN ;
        config.height = MainGame.HIDE_SCREEN;


        //System.out.println(com.badlogic.gdx.Version.VERSION);

        new LwjglApplication(new MainGame(2), config);
    }
}
