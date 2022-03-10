package main.java.com.Units.Bots;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Server;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import main.java.com.MainGame;
import main.java.com.Units.Player;


public class IndexBot {
    HashMap<Integer, Player> botList;
    HashMap<Integer, DBBot> dbBots;

    private MainGame mainGame;
    private static int nom = -100;

    private final Vector2 speed_constanta = new Vector2(90, 0);

    final float SPEED_ROTATION = 180f;

    private Vector2 temp_position_vector;
    private static BotBehavior botBehavior;

    private int sizeBot = 1;

    main.java.com.Network.PleyerPositionNom temPP = new main.java.com.Network.PleyerPositionNom();

    public IndexBot(MainGame mainGame , int number_bots) {
        this.temp_position_vector = new Vector2();
        this.botList = new HashMap<Integer, Player>();
        this.mainGame = mainGame;
        this.dbBots = new HashMap<>();
        this.sizeBot = number_bots;

        this.botBehavior = new BotBehavior(botList); // поведение бота - тут вся логика  )))
        // this.allPlayers = new HashMap<Integer, TowerRotation>();
    }

    public String getNikName(int id) {
        return botList.get(id).getNikName();
    }

    private void createBuletBot(int id) {
        Vector2 pos = new Vector2(botList.get(id).getXp(), botList.get(id).getYp());
        Vector2 vel = new Vector2(1, 0).setAngleDeg(botList.get(id).getRotTower());
        int nom = (int) (botList.get(id).getRotTower() + MathUtils.random(10_0000));
        mainGame.getBullets().addBullet(pos, new Vector2(1, 1), nom,id);

    }

    private DBBot getDbBotForId(int id) {
        DBBot db = this.dbBots.get(id);
        if (db == null) {
            db = new DBBot();
            this.dbBots.put(id, db);
        }
        return db;
    }

    public void updateBot(float deltaTime) {
        //System.out.println(sizeBot+ " ---  " + botsSizeList());
        if (botsSizeList() < sizeBot) addBot(); // обавленеи ботов

        DBBot dbBot;
        HashMap<Integer, Player> allpl = mainGame.getListplayerUpdate();
        Vector2 tv1 = new Vector2();
        Vector2 tv2 = new Vector2();
        for (HashMap.Entry<Integer, Player> p : botList.entrySet()) {
            dbBot = getDbBotForId(p.getKey());
            dbBot.updateTimer(deltaTime);

            tower_rotation(deltaTime,p.getValue(),dbBot,allpl,tv1,tv2); // работа с башней
            body_rotation(deltaTime, p.getValue(), dbBot,tv1); // поворот корпуса
            tank_movement(deltaTime, p.getValue(), dbBot,tv1); // передвижение
            random_behavior(p.getValue(), dbBot); // случайные повортоы - остановка __

            if(MathUtils.randomBoolean(.005f)) createBuletBot(p.getValue().getId());// test
        }

    }

    ////////////////////////

    public void tower_rotation(float deltaTime,Player p, DBBot dbBot , HashMap<Integer, Player> allpl, Vector2 tv1,Vector2 tv2){
        //////////// поврот башни
        if (MathUtils.randomBoolean(0.04f)) {
            // vibor target
            tv1.set(p.getXp(), p.getYp());
            float ras = Float.MAX_VALUE;
            int idTarget = Integer.MAX_VALUE;
            for (HashMap.Entry<Integer, Player> targts : botList.entrySet()) {
                tv2.set(targts.getValue().getXp(), targts.getValue().getYp());
                float distant_temp = Vector2.dst2(tv1.x, tv1.y, tv2.x, tv2.y);
                if (distant_temp < 12) continue;
                if (ras > distant_temp) {
                    ras = distant_temp;
                    idTarget = targts.getKey();
                }
                if (idTarget != Integer.MAX_VALUE)
                    dbBot.setTarget(allpl.get(idTarget).getXp(), allpl.get(idTarget).getYp());
            }
            //  System.out.println(dbBot.getTarget() + "  " + p.getValue().getId() + " _ target " + dbBot.getTarget() + p.getValue().getXp());
        }

        tv1.set(dbBot.getTarget()).sub(p.getXp(), p.getYp());

//            //  System.out.println(dbBot.getTarget());
        if (!dbBot.isZeroTarget()) { // если цели нет - надо добавить поиск препятдствий )))
            //  System.out.println("target ++");
            tv2.set(dbBot.getTarget()).sub(p.getXp(), p.getYp()); // ветор направления на ЦЕЛЬ
            tv1.set(Vector2.X).setAngleDeg(p.getRotTower()); // вектор реального положения башни
            float angleBetween = tv1.angleDeg(tv2.setAngleDeg(tv2.angleDeg() + 180));
            if (!MathUtils.isEqual(tv1.angleDeg(), tv2.angleDeg(), 1.2f)) {
                // System.out.println("angleBetween:: " + angleBetween);
                if (angleBetween > 180)
                    p.setRotTower(p.getRotTower() - 120 * deltaTime);
                else
                    p.setRotTower(p.getRotTower() + 120 * deltaTime);
            }
        } else { // если цели есть
            //  System.out.println("target --");
            tv1.set(Vector2.X).setAngleDeg(p.getR()); // вектор реального положения корпуса
            tv2.set(Vector2.X).setAngleDeg(p.getRotTower()); // вектор реального положения башни
            float angleBetween = tv1.angleDeg(tv2);
            if (!MathUtils.isEqual(tv1.angleDeg(), tv2.angleDeg(), 1.2f)) {

                // System.out.println("angleBetween:: " + angleBetween);
                if (angleBetween > 180)
                    p.setRotTower(p.getRotTower() - 120 * deltaTime);
                else
                    p.setRotTower(p.getRotTower() + 120 * deltaTime);
            }

        }

        if (check_obstacles_front_tank(p, MathUtils.random(40, 110), deltaTime) && MathUtils.randomBoolean(.5f) && dbBot.tackEnding()) {
            dbBot.getDirectionOfFollowing().rotateDeg(MathUtils.random(65, 290));
            dbBot.setTimer_tact(MathUtils.random(.8f, 2f));
        }

    }

    public void random_behavior(Player p, DBBot dbBot) {
        if (MathUtils.randomBoolean(.01f)) { // случайный поворот
            dbBot.getDirectionOfFollowing().rotateDeg(MathUtils.random(65, 290));
            dbBot.setTimer_tact(MathUtils.random(.8f, 2f));
        }
        if (MathUtils.randomBoolean(.00005f) && dbBot.getTimer_stop() < -5) { // случайная остановка )))
            dbBot.setTimer_stop(MathUtils.random(.5f, 5));
        }
    }

    public void body_rotation(float deltaTime, Player p, DBBot dbBot, Vector2 tv) {
        tv.setAngleDeg(p.getR());
        if (!MathUtils.isEqual(tv.angleDeg(), dbBot.getDirectionOfFollowing().angleDeg(), 10) && dbBot.getTimer_stop() < 0) {
            if (tv.angleDeg(dbBot.getDirectionOfFollowing()) > 180) {
                tv.rotateDeg(-deltaTime * SPEED_ROTATION);
                p.setR(tv.angleDeg());
            }
            //p.getValue().setR(tv1.angleDeg() - deltaTime * SPEED_ROTATION);
            else {
                tv.rotateDeg(deltaTime * SPEED_ROTATION);
                p.setR(tv.angleDeg());
            }
        }
    }

    public void tank_movement(float deltaTime, Player p, DBBot dbBot, Vector2 mc) { // передвижение ботов
        mc.set(moveCoordinat(p, deltaTime));
        if (!mainGame.getMapSpace().isPointInCollision(mc.x, mc.y) && mainGame.getMapSpace().isPointWithinMmap(mc.x, mc.y)) {
            p.setXp(mc.x);
            p.setYp(mc.y);
        } else {
            mc = new Vector2(moveCoordinat(p, deltaTime, 45, true));
            if (!mainGame.getMapSpace().isPointInCollision(mc.x, mc.y) && mainGame.getMapSpace().isPointWithinMmap(mc.x, mc.y)) {

                dbBot.getDirectionOfFollowing().rotateDeg(dbBot.getDirectionOfFollowing().angleDeg() + 5);
            } else {
                mc = new Vector2(moveCoordinat(p, deltaTime, -45, true));
                if (!mainGame.getMapSpace().isPointInCollision(mc.x, mc.y) && mainGame.getMapSpace().isPointWithinMmap(mc.x, mc.y)) {

                    dbBot.getDirectionOfFollowing().rotateDeg(dbBot.getDirectionOfFollowing().angleDeg() - 5);
                }
            }
        }
    }

    public boolean check_obstacles_front_tank(Player tank, int distance, float dt) {
        Vector2 pos = moveCoordinat(tank, dt, distance);
        return mainGame.getMapSpace().isPointInCollision(pos) || !mainGame.getMapSpace().isPointWithinMmap(pos);
    }

    public boolean check_obstacles_front_tank(Player tank, float distance, float align) {


        return false;
    }
    ////////////////////////

    private Vector2 moveCoordinat(Player tank, float dt) { // передвижение обекта по направлению взять новые координаты
        temp_position_vector.set(tank.getXp(), tank.getYp());
        temp_position_vector.add(speed_constanta.cpy().setAngleDeg(tank.getR()).scl(dt));
        return temp_position_vector;
    }

    private Vector2 moveCoordinat(Player tank, float dt, float deg, boolean r) { // передвижение обекта по направлению взять новые координаты
        Vector2 pos = new Vector2(1, 1).set(tank.getXp(), tank.getYp());
        Vector2 vel = new Vector2(1, 1).setAngleDeg(tank.getR());

        //  pos.sub(vel);
        return pos.add(vel.rotateRad(deg));

    }


    private Vector2 moveCoordinat(Player tank, float dt, float dist) { // передвижение обекта по направлению взять новые координаты
        temp_position_vector.set(tank.getXp(), tank.getYp());
        temp_position_vector.add(speed_constanta.cpy().setAngleDeg(tank.getR()).cpy().scl(dt * dist));
        return temp_position_vector;
    }

    private boolean idCollisionApponents() {

        return false;
    }

    private Vector2 moveCoordinatRotation(Player tank, float dt, int deg) { // передвижение обекта по направлению взять новые координаты
        Vector2 position = new Vector2(tank.getXp(), tank.getYp());
        Vector2 velocity = speed_constanta.cpy().setAngleDeg(tank.getR());


        return position.add(velocity.cpy().scl(dt));
    }

    private int botsSizeList() {
        return botList.size();
    }

    private void addBot() {
        Player p = new Player(nom);
        p.setNikName(getNikNameGen());

        p.setCommand(MathUtils.random(1, 2));

        p.setXp(MathUtils.random(200, 1100));
        p.setYp(MathUtils.random(200, 1100));
        p.setHp(MathUtils.random(80));


        this.botList.put(nom, p);
        nom--;
        System.out.println("add " + nom);


        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////
//        allPlayers.put(nom,new TowerRotation(
//                new Vector2(0,1),
//                new Vector2(1,1).setAngleDeg(p.getR()),
//                new Vector2(p.getXp(),p.getYp())
//
//        ));
        ////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////


    }

    public void sendsCoordinatBots(Server server) {
        for (HashMap.Entry<Integer, Player> p : botList.entrySet()) {
            sendCoordinatBots(p.getKey(), server);
        }
    }

//    public boolean clashWithOtherPlayers(Player p1) { // Столкновени с другими ботами
//       // System.out.println("!!! " + mainGame.getAllPlayers().size());
//        HashMap<Integer,Player> ap = (HashMap<Integer, Player>) mainGame.getAllPlayers().keySet();
//        for (Integer key : ap.keySet()) {
//            //if (key == p1.getId()) continue;
//            Player pv = mainGame.getAllPlayers().get(key);
////            System.out.println("!!!-------------");
////            System.out.println(Vector2.dst(p1.getXp(), p1.getYp(), pv.getXp(), pv.getYp()));
//            if (Vector2.dst2(p1.getXp(), p1.getYp(), pv.getXp(), pv.getYp()) < 1350) return true;
//        }
//        return false;
//    }


    private void sendCoordinatBots(int idBot, Server server) {
        temPP.nom = idBot;
        temPP.xp = (int) this.botList.get(idBot).getXp();
        temPP.yp = (int) this.botList.get(idBot).getYp();
        temPP.roy_tower = (int) this.botList.get(idBot).getRotTower();
        server.sendToAllUDP(temPP);
    }


    public HashMap<Integer, Player> getBotList() {
        return botList;
    }

    static String getNikNameGen() {
        ArrayList<String> names = new ArrayList<>();
        names.add("Bubba");
        names.add("Honey");
        names.add("Bo");
        names.add("Sugar");
        names.add("Doll");
        names.add("Peach");
        names.add("Snookums");
        names.add("Queen");
        names.add("Ace");
        names.add("Punk");
        names.add("Sugar");
        names.add("Gump");
        names.add("Rapunzel");
        names.add("Teeny");
        names.add("MixFix");
        names.add("BladeMight");
        names.add("Rubogen");
        names.add("Lucky");
        names.add("Tailer");
        names.add("IceOne");
        names.add("Sugar");
        names.add("Gump");
        names.add("Rapunzel");
        names.add("Teeny");
        names.add("MixFix");
        names.add("BladeMight");
        names.add("Rubogen");
        names.add("Lucky");
        names.add("Tailer");
        names.add("IceOne");
        names.add("TrubochKa");
        names.add("HihsheCKA");
        names.add("R2-D2");
        names.add("Breha Organa");
        names.add("Yoda");
        names.add("Obi-Wan Kenob");
        names.add("C-3PO");
        names.add("Darth Sidious");
        names.add("Darth Vader");
        names.add("Boba Fett");
        names.add("Sarin");
        names.add("Sasha");
        return names.get(MathUtils.random(names.size() - 1)) + "@Bot";
    }
}
