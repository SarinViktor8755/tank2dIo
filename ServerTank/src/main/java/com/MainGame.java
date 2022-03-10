package main.java.com;

import java.util.HashMap;
import java.util.Map;

import main.java.com.SpamceMap.IndexMap;
import main.java.com.Units.Bots.IndexBot;
import main.java.com.Units.Bullet.IndexBullets;
import main.java.com.Units.Player;

public class MainGame {
    GameServer gameServer;
    IndexBullets bullets;
    IndexMap mapSpace;
    IndexBot bot;

    HashMap<Integer, Player> allPlayers;

    float timer;
    public MainGame(GameServer gameServer, int number_of_bots) {

        this.bot = new IndexBot(this,number_of_bots);
        this.gameServer = gameServer;
        this.bullets = new IndexBullets(this.gameServer);
        this.mapSpace = new IndexMap();
        this.allPlayers = new HashMap<>();
    }

    public void updateGameStatus(){

    }

    public HashMap<Integer, Player> getListplayerUpdate(){ // генерация листов всех играков
        this.allPlayers.clear();
        //allPlayers.putAll(gameServer.lp);
        allPlayers.putAll(bot.getBotList());
        return this.allPlayers;
    }

    public IndexBot getBot() {
        return bot;
    }

    public float getTimer() {
        return timer;
    }

    private boolean getTimeUpdate(){
        return true;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public IndexBullets getBullets() {
        return bullets;
    }

    public void setBullets(IndexBullets bullets) {
        this.bullets = bullets;
    }

    public IndexMap getMapSpace() {
        return mapSpace;
    }

    public void setMapSpace(IndexMap mapSpace) {
        this.mapSpace = mapSpace;
    }
}
