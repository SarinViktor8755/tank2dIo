package tanks.io.ClientApi;


import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.kryonet.Client;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;

import tanks.io.Units.NikName;

public class NetworkPacketStock { /// входящие сообщения
    BlockingDeque<PacketModel> packetDeque;
    HashMap<Integer, PacketModel> outList; // лист выходных сообщений
    HashMap<Integer, PacketModel> inList; // лист входных сообщений
    CopyOnWriteArrayList<Integer> inListMass;
    private boolean online;


    int myId = -1;

    int nomerOut; // это номера исходящего сообщения

    @SuppressWarnings("NewApi")
    public NetworkPacketStock() {
        this.online = true;
        this.packetDeque = new LinkedBlockingDeque<>();
        for (int i = 0; i < 150; i++) {
            this.packetDeque.add(new PacketModel());
        }
        outList = new HashMap<>();
        inList = new HashMap<>();

        nomerOut = 0;
    }

    public void addOutgoingPackage(PacketModel packet) {
        ///мсразу сделать отправку )))
        this.outList.put(packet.getTime_even(), packet);
    }

    private void addIncomingPackage(Network.StockMess packet) {
        //this.inList.put(packet.getTime_even(),packet);
    }


    public void updatOutLint(Client client) { // проверка расылка сообщений
        if(!online){
            this.outList.clear();
            return;
        }
        Integer key;
        PacketModel temp;
        Iterator<Integer> in = outList.keySet().iterator(); // отправка сообщений на сервер

      //  System.out.println(outList);
        // System.out.println("-------------------");
        while (in.hasNext()) {
            try {

                key = in.next();
                temp = outList.get(key);
                temp.sendId(this.myId);
                temp.getP().nomer_pley = client.getID();
                //System.out.println("--" + outList.size());
                if (!temp.isActual()) continue;
                client.sendUDP(temp.getP()); // отправка пакетов
                checkByTime(temp);

                //System.out.print(" >>> "+ temp.getTime_even());
                System.out.println(" >>>>>> otpravil paket " + temp.getTime_even() + " tip: " + temp.getTip());
            } catch (ConcurrentModificationException e) {
            }
        }
    }

    public void updatInLint() { // проверка входящих сообщений
        Integer key;
        PacketModel temp;
        Iterator<Integer> in = inList.keySet().iterator(); // входящие сообщения
        while (in.hasNext()) {
            try {
                key = in.next();
                temp = inList.get(key);
                checkByTime(temp);// проверить по времени
            } catch (ConcurrentModificationException e) {
            }
        }
    }


    //////////////////////////////////////////////

    public void setMyId(int myId) {
        this.myId = myId;
    }


    public void checkByTime(PacketModel pm) { // проверка по времени пакета
        int rTime = (int) System.currentTimeMillis();
        if (rTime - pm.getTime_even() > 4_000) pm.setActualFalse();
    }


    public void toSendMyShot(float x, float y, float alignShoot) { // мой выстрел
        PacketModel pm = getFreePacketModel();
        outList.put(pm.getTime_even(), pm);
        pm.setParametrs(Heading_type.MY_SHOT, (int) x, (int) y, (int) alignShoot, (int) (5000 + MathUtils.random(9999) + x - y), null, null, NikName.getNikName());
        //this.updatOutLint();
        System.out.println("----------->>>> toSendMyShot" );
    }

    public void toSendMyTokken() {
        PacketModel pm = getFreePacketModel();
        outList.put(pm.getTime_even(), pm);
        pm.setParametrs(Heading_type.MY_TOKKEN, null, null, null, null, null, null, NikName.getTokken());

    }

    public void toSendMyNik() {

        //System.out.println("adding Packet Tokken");
        PacketModel pm = getFreePacketModel();
        outList.put(pm.getTime_even(), pm);
        pm.setParametrs(Heading_type.MY_NIK, null, null, null, null, null, null, NikName.getNikName());
    }

    public void toSendButtonStartClick() {
        //System.out.println("start button");
        PacketModel pm = getFreePacketModel();
        outList.put(pm.getTime_even(), pm);
        pm.setParametrs(Heading_type.BUTTON_STARTGAME, null, null, null, null, null, null, NikName.getNikName());
        // pm.setParametrs(Heading_type.MY_TOKKEN, null, null, null, null, null, null, NikName.getTokken());
    }

    public void toSendParametersOfPlayer(int id) { // скажи парамтры игрока )) по ID
        PacketModel pm = getFreePacketModel();
        outList.put(pm.getTime_even(), pm);
        pm.setParametrs(Heading_type.PARAMETERS_PLAYER, null, null, null, null, null, null, NikName.getNikName());
    }


    public void toSendParametersOfPlayer() { // скажи парамтры игрока )) по ID
        //System.out.println("start button");
        PacketModel pm = getFreePacketModel();
        // System.out.println("NIK  " +  NikName.getNikName());
        pm.setParametrs(Heading_type.PARAMETERS_PLAYER, null, null, null, null, null, null, NikName.getNikName());
    }


    public void toSendMYParameters(int hp) {
        PacketModel pm = getFreePacketModel();
        System.out.println("MY_PARAMETERS >>>> ");
        pm.setParametrs(Heading_type.MY_PARAMETERS, hp, null, null, null, null, null, NikName.getNikName());
    }

    /////////////////////////////////////////
    private Integer getNomderForMasseg() { // придумать номер для исходящео сообщения
        this.nomerOut++;
        if (this.nomerOut > Integer.MAX_VALUE - 100) this.nomerOut = 1;
        outList.clear();
        return this.nomerOut;
    }

    public PacketModel getFreePacketModel() {
        int n = getNomderForMasseg();
        //System.out.println("get: " + n);
        PacketModel pm = this.packetDeque.pollLast();
        pm.getP().time_even = n;
        this.packetDeque.offerFirst(pm);
        pm.getP().time_even = n;
        //this.outList.put(pm.getP().time_even, pm);
        // System.out.println(outList);
        pm.getP().nomer_pley = pm.getP().time_even;
        // System.out.println(pm.getP().nomer_pley + "  !!!!!!!!");

        return pm;
    }

    public void markAsSent(int nom) { // ullPointerException
        try {
            outList.get(nom).setActualFalse();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException markAsSent " + nom);
        }

    }

    public HashMap<Integer, PacketModel> getOutList() {
        return outList;
    }

    //
//    public void routingInMassage(PacketModel m) {
//        // System.out.println("routingInMassage  tip:" + m.getP().tip);
//        if (m.getP().tip == Heading_type.MY_NIK) {
//
//
//            return;
//        }
//
//        if (m.getP().tip == Heading_type.MY_SHOT) {
//            //System.out.println("MY_SHOT <<< return");
//            return;
//        }
//
//        if (m.getP().tip == Heading_type.MY_TOKKEN) {
//            toSendMyNik();
//            return;
//        }
//
//        if (m.getP().tip == Heading_type.PARAMETERS_PLAYER) {
//
//            return;
//        }
//
//        if (m.getP().tip == Heading_type.STATUS_GAME) {
//
//
//            return;
//        }
//
//
//    }


    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
