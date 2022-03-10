package main.java.com;

import java.io.IOException;

public class ServerLauncher {
    public static GameServer server;
    public static void main(String[] args) throws IOException {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("(========Start server Tank2D========)");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        server = new GameServer(args,new ServerLauncher());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10L);
                } catch (Exception e) {

                }
            }
        }).start();

    }
}

