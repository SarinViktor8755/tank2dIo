package tanks.io.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import tanks.io.MainGame;

public class DesktopLauncher2 {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Tanks2D.io";
        config.width = 960;
        config.height = 640;

        new LwjglApplication(new MainGame(2), config);
    }
}
