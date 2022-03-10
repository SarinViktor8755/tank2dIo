package tanks.io.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import tanks.io.MainGame;

public class DesktopLauncher5 {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Tanks2D.io";
        config.height = 1920;
        config.width = 1080;

        new LwjglApplication(new MainGame(2), config);
    }
}
