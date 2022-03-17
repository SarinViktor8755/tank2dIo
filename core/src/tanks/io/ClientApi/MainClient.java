package tanks.io.ClientApi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import tanks.io.MainGame;

public class MainClient {


    public float aplphaConnect;
    private Client client;   //клиент
    private boolean onLine;
    private MainGame mg;

    private NetworkPacketStock networkPacketStock;
    public TreeMap<Integer, Network.PleyerPositionNom> otherPlayer;
    public HashMap<Integer, Boolean> frameUpdates; //Обновления кадра для играков
    public ArrayDeque<PacketModel> inDequePacket; // входящие пакеты для обработки;

    private Network.PleyerPosition pp;
    public int myIdConnect; //Мой ИД

    public MainClient(MainGame mainGame) {
        aplphaConnect = 0;
        this.mg = mainGame;
        this.networkPacketStock = new NetworkPacketStock();

        this.startClirnt();
        coonKtToServer();

        pp = new Network.PleyerPosition();
        otherPlayer = new TreeMap<>();
        frameUpdates = new HashMap<>();
        inDequePacket = new ArrayDeque<>();
        onLine = true;
    }

    private void startClirnt() {
        this.client = new Client();
        Network.register(client);
        this.client.start();
        //   this.client.setName(NikName.getTokken());
    }

    public void updateAlphaW() { // обновление альфа канала фона
        if (client.isConnected())
            this.aplphaConnect += Gdx.graphics.getDeltaTime() / 10;
        else this.aplphaConnect -= Gdx.graphics.getDeltaTime() / 10;
        aplphaConnect = MathUtils.clamp(aplphaConnect, .45f, 1);
        System.out.println("aplphaConnect" + aplphaConnect);

    }


    public MainClient setMyIdConnect(int myIdConnect) {
        this.networkPacketStock.setMyId(myIdConnect);
        this.myIdConnect = myIdConnect;
        return this;
    }
    //////////////////

    public void router(Object object) {
        if (!onLine) return;
        if (object instanceof Network.PleyerPositionNom) { // полученеи позиции играков
            // System.out.println("PleyerPositionNom");
            Network.PleyerPositionNom pp = (Network.PleyerPositionNom) object;
            frameUpdates.put(pp.nom, true);
            if (pp.nom == client.getID()) return;
            otherPlayer.put(pp.nom, pp);
            return;
        }

        if (object instanceof Network.Answer) {// полученеи ответа
            //  System.out.println("!!!!!!!!!!Network.Answer " + ((Network.Answer) object).nomber);
            getNetworkPacketStock().markAsSent(((Network.Answer) object).nomber);
            System.out.println("<<----  " + ((Network.Answer) object).nomber + "  :: otvet");
            return;
        }

        if (object instanceof Integer) {
            // System.out.println("int "+((Integer) object).intValue());
            // System.out.println("Integer  "+((Integer) object).intValue());
            getNetworkPacketStock().markAsSent(((Integer) object).intValue());
            System.out.println("<<  " + object);
            return;
        }


        if (object instanceof Network.StockMess) {// полученеи сообщения

            //  System.out.println((((Network.StockMess) object).tip == Heading_type.MY_NIK) + "  NIK__ " + ((Network.StockMess) object).time_even);
            int nom = ((Network.StockMess) object).time_even;
            // System.out.println("!!!  " + ((Network.StockMess) object).tip);
            client.sendUDP(nom); // ответ отправка
            //   System.out.println("StockMess");
            // client.sendUDP(((Network.StockMess) object).time_even);
            ////////////////////
            Network.StockMess ns = (Network.StockMess) object;
            // System.out.println("          ----*------    "+ns.textM);
            if (networkPacketStock.inList.get(nom) != null) return; // проверка сообщения на повтор
            System.out.println(nom + " - incoming package number");
            // System.out.println("No!");
            //  System.out.println("!!!!!!!!!_--------------------------!!!!!!!!!!!!!!!!!!!!!11" + ns.textM);
            /// БЕРЕМ ПАКЕТ И ЗАОЛНЯЕМ
            PacketModel pm = networkPacketStock.getFreePacketModel();
            pm.setParametrs(ns.tip, ns.p1, ns.p2, ns.p3, ns.p4, ns.p5, ns.p6, ns.textM);
            this.networkPacketStock.inList.put(nom, pm); // доавбатиь во входящий пакеты - журнал входящих сообщений

            this.inDequePacket.offerLast(pm); // обавляем на пакет

            return;
        }

        if (object instanceof Network.PleyerPosition) {// полученеи позиции - скореевсего даже не применим
            // System.out.println("Network.PleyerPosition");
            //  mg.getMainClient().getNetworkPacketStock().markAsSent(((Network.Answer)object).nomber);
            return;
        }


    }

    public boolean coonectToServer() {
        // if(!onLine)  return false;
        client.start();
        //  client.connect(5000, Network.ip, Network.tcpPort, Network.udpPort);
        //client.start();
        //  client.setName(NikName.getNikName());

        new Thread("Connect") {
            public void run() {
                try {
                    client.connect(5000, Network.ip, Network.tcpPort, Network.udpPort);
                    client.addListener(new Listener() {
                        public void connected(Connection connection) {
                            setMyIdConnect(connection.getID());
                            //           networkPacketStock.toSendMyTokken();
                        }

                        public void received(Connection connection, Object object) {
                            router(object);
                        }

                        public void disconnected(Connection connection) {
                            System.out.println("disconnected!!!!!!!!!");
                            new Exception().printStackTrace();
                            //mg.switchingFromGameMenu();
                        }
                    });

                } catch (IOException | NullPointerException ex) {
                    ex.printStackTrace();
                }

            }
        }.start();

        return true;
    }

    public boolean coonKtToServer() {
        System.out.println("Connect to server");
        this.onLine = coonectToServer();
        return client.isConnected();
    }

    public Client getClient() {
        return client;
    }

    public boolean isConnect() {
        System.out.println("onLine " + this.onLine);
        return client.isConnected();
    }

    public void upDateClient() {

        if (!onLine) return;
//        if(!client.isConnected()) {
//            if(MathUtils.randomBoolean(.005f)) coonKtToServer();
//            return;}

        if (!client.isConnected()) {
            try {

                if (MathUtils.randomBoolean(.005f))
                    client.reconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            return;
        }

        sendOutgoingQueue();
        processIncomingQueue();
        //checkMainParametors();

    }

    public int getPing() {
        client.updateReturnTripTime();
        return client.getReturnTripTime();
    }

    private void checkMainParametors() {
    }

    private void processIncomingQueue() { //обработать очередь входящ
        //System.out.println("processIncomingQueue");
        networkPacketStock.updatInLint();
    }

    private void sendOutgoingQueue() { //отправить очеред исходящих
        networkPacketStock.updatOutLint(getClient());

    }

    public void sendMuCoordinat(float x, float y, float rot, float rotTower) {
        if (!onLine) return;
        // if(MathUtils.randomBoolean(.85f)) return;
        sendOutgoingQueue();
        /// фильтрация по дельте
        try {
            if (pp.xp == (int) x && pp.yp == (int) y) {
                if (pp.roy_tower == (int) rotTower) {
                    if (MathUtils.randomBoolean(.9f)) return;
                    else if (MathUtils.randomBoolean(.5f)) return;
                }
            }

        } catch (NullPointerException e) {
            System.out.println("!");
        }

        pp.xp = (int) x;
        pp.yp = (int) y;
        pp.roy_tower = (int) rotTower;
        client.sendUDP(pp); /// отправка координат
        sendOutgoingQueue();
    }


    public NetworkPacketStock getNetworkPacketStock() {
        return networkPacketStock;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
        this.networkPacketStock.setOnline(onLine);
    }
}






