package main.java.com;

import java.io.IOException;


import static main.java.com.Network.register;

import com.badlogic.gdx.math.MathUtils;

import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import main.java.com.Units.ListPlayers;
import main.java.com.Units.OutMassegeCollection;
import main.java.com.Units.Player;

public class GameServer extends Listener {

    public final long timerTact = 50; //ms поток таймер циклов , рассылвает координаты ботов ))
    public final long timerLogic = (long) (this.timerTact / 3f); // таймер поведения ботов

    Server server;
    MainGame mainGame;

    Network.Answer answerTemp = new Network.Answer();
    Network.PleyerPositionNom ppn = new Network.PleyerPositionNom();

    ListPlayers lp = new ListPlayers(this);
    public OutMassegeCollection outMassegeCollection;

    long previousStepTime;


//    public void sendMassege(int idPlayer, Network.StockMess stockMess){
//        this.outMassegeCollection.addMasssage(idPlayer,stockMess){};
//    }

    public GameServer(String[] args, ServerLauncher serverLauncher) throws IOException {


        outMassegeCollection = new OutMassegeCollection(lp, server);
        server = new Server();
        register(server);
        server.bind(Network.tcpPort, Network.udpPort);
        server.start();

//        lp.getPlayerForId(4343);
//        System.out.println(lp.getPlayerForId(4343) + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        previousStepTime = System.currentTimeMillis();
        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) { // подключение
                lp.addPlayer(connection.getID());

            }

            @Override
            public void disconnected(Connection connection) { // отключение
                System.out.println("disconnected : " + connection.getID());
                lp.getPlayerForId(connection.getID()).setStatus(Player.STATUS_DISCONECT);
                outMassegeCollection.sendPlayerDiscount(connection.getID());
            }

            public void received(Connection connection, Object object) { // приход
                //System.out.println(lp.getSize());
                //outMassegeCollection.sendingQueue(server); // отправка всех сообщений


                if (object instanceof Network.PleyerPosition) {
                    // System.out.print("p ");

                    Player player = lp.getPlayerForId(connection.getID());
                    Network.PleyerPosition pp = (Network.PleyerPosition) object;
                    player.setXp(pp.xp);
                    player.setYp(pp.yp);
                    // player.setR(pp.rot);
                    player.setRotTower(pp.roy_tower);
                    //////////////////////////////////
                    ppn.nom = connection.getID();
                    ppn.xp = pp.xp;
                    ppn.yp = pp.yp;
                    //ppn.rot = pp.rot;
                    ppn.roy_tower = pp.roy_tower;
                    ////////////////////////////////
                    server.sendToAllExceptUDP(connection.getID(), ppn);
                    //  System.out.println(Gdx.graphics.getDeltaTime());

                    return;
                }

                if (object instanceof Integer) {// полученеи ответа
                    //   System.out.println(  ((Network.Answer)object).nomber / );
                    int nom = (int) object;
                    //  System.out.print("Integer (" + nom + " )" );
                    outMassegeCollection.mark_the_sent(nom);
                    //   System.out.println();
                    //  outMassegeCollection.sendingQueue(server);
                    return;
                }

                if (object instanceof Network.Answer) {// полученеи ответа
                    //   System.out.print("Answer ");
                    // outMassegeCollection.sendingQueue(server);
                    //   System.out.println(  ((Network.Answer)object).nomber / );
                    outMassegeCollection.mark_the_sent(((Network.Answer) object).nomber);
                    return;
                }

                if (object instanceof Network.StockMess) {
                    System.out.print("SM _____<<<" + connection.getID());
                    Network.StockMess p = (Network.StockMess) object;
                   // System.out.println("time_even:: " + p.time_even);
                    answerTemp.nomber = p.time_even;
                  //  System.out.println(answerTemp);
                    //System.out.println("______________>>>>>>>>>>>>>>>>>");
                    server.sendToUDP(connection.getID(), answerTemp); // ответ
                    if(outMassegeCollection.isExists(p.time_even)) return;
                    //if(g)p.time_even
                    // System.out.print(" >pSm> ");
                    try {
                        ///  if(!lp.getPlayerForId(connection.getID()).checkIntterMess(p.nomer_pley)) return;
                        /// проверка на входящие сообщения

                        if (!lp.getPlayerForId(connection.getID()).addMass(p.nomer_pley)) return;
                    } catch (NullPointerException e) {
                        lp.addPlayer(connection.getID());
                        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    } finally {
                        //   System.out.println("22222");
                        lp.getPlayerForId(connection.getID()).clearQuerety(p.nomer_pley); // странная строка
                        lp.routerMassege(connection, p); /// отправляем сообщение в маршрутезатор

                        //outMassegeCollection.sendingQueue(server);
                    }
                    return;
                }
            }

            @Override
            public void idle(Connection connection) {
            }
        });

        mainGame = new MainGame(this,parsBotSize(args[0]));
        startUpdateThread();
        startUpdateBehaviourThread();
       // System.out.println("args " + parsBotSize(args[0]) + "  " + args[0]);
    }

    private void updateGame() { // выполняется каждые 25 мс
        outMassegeCollection.sendingQueue(this.server); // отправит соообщения
        long deltaTime = getDeltaTime();
        //System.out.println(deltaTime + " ms  ");
        float time = (float) (deltaTime * .001);



        mainGame.bot.sendsCoordinatBots(server);  // отдать координаты ботов
        mainGame.bot.updateBot(time); // Боты обносить
        mainGame.bullets.updateBulets(deltaTime);   // обносить пули

//        if (mainGame.bullets.iSNullActiveBullets() && MathUtils.randomBoolean(.01f))
//            mainGame.bullets.addBullet(new Vector2(10, 10), new Vector2(66, 0), 1);


        if (MathUtils.randomBoolean(.005f) && (lp.getPlayers().size() > 0)) {
            outMassegeCollection.sendPlayerHP(lp.getPlayerForId(MathUtils.random(lp.getPlayers().size() - 1)).getId(), MathUtils.random(80));
        }

    }


    private void moveBotToPlayerList() {
        for (int i = 0; i < this.mainGame.bot.getBotList().size(); i++) {
            //  mainGame.gameServer.lp.addPlayer();

        }

        //
        //this.mainGame
    }


    private void startUpdateThread() { // выполняется каждые 50 мс
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        //updateGame();
                        Thread.sleep(getMainGame().gameServer.timerTact);

                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }


    private void startUpdateBehaviourThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        updateGame();
//                        System.out.print("! ");
//                        System.out.println();
                        Thread.sleep(getMainGame().gameServer.timerLogic);
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }


    public MainGame getMainGame() {
        return mainGame;
    }

    public Network.Answer getAnswerTemp() {
        return answerTemp;
    }

    public ListPlayers getLp() {
        return lp;
    }

    public OutMassegeCollection getOutMassegeCollection() {
        return outMassegeCollection;
    }

    public Network.PleyerPositionNom getPpn() {
        return ppn;
    }

    public Server getServer() {
        return server;
    }

//    public void updateBulletPosition() {
//        long tackt = canIStep();
//        if(tackt < 15) return;
//        System.out.println("updateBulletPosition "+ tackt)
//
//    }


    public long getDeltaTime() {
        long time = System.currentTimeMillis();
        long raz = (time - previousStepTime);
        previousStepTime = time;
        return raz;
    }

    public Integer parsBotSize(String args){
        try {
            return Integer.valueOf(args);
        }catch (ArrayIndexOutOfBoundsException e){
            return MathUtils.random(100,170);
        }
    }




}
