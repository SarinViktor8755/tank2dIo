package tanks.io.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import tanks.io.MainGame;

public class DesktopLauncher0 {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Tanks2D.io";
        config.width = MainGame.WHIDE_SCREEN / 2;
        config.height = MainGame.HIDE_SCREEN / 2;

        new LwjglApplication(new MainGame(2), config);
    }
}
