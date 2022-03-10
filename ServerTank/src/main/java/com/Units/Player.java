package main.java.com.Units;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;

import main.java.com.Units.Bots.BehaviourBot;

public class Player { /// все характеристики играков
    public static final int STATUS_MENU = 1;
    public static final int STATUS_IN_GAME = 2;
    public static final int STATUS_DISCONECT = 6;

    private static int RED_COMMAND = 1;
    private static int BLUE_COMMAND = 2;

    BehaviourBot behaviourBot;

    // общие
    int status = STATUS_MENU;
    // игровые
    float xp, yp, r, rotTower; // коодинаты
    int hp, frags, death, command, id; //ХП

    String tokken, nikName;
    HashSet<Integer> inMass;

    public Player(int id) {
        this.id = id;
        this.inMass = new HashSet<Integer>();
        hp = 100;
        death = 1;
        command = MathUtils.random(1, 2);
        nikName = String.valueOf("Player no." + this.id);

        this.behaviourBot = new BehaviourBot();

    }

    public void settPosition(Vector2 p) {
        this.setXp(p.x);
        this.setYp(p.y);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getXp() {
        return xp;
    }

    public void setXp(float xp) {
        this.xp = xp;
    }

    public static int getStatusInGame() {
        return STATUS_IN_GAME;
    }

    public float getYp() {
        return yp;
    }

    public void setYp(float yp) {
        this.yp = yp;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
//        if (this.r > 360) this.r = 0;
//        if (this.r < 0) this.r = 360;
    }

    public float getRotTower() {
        return rotTower;
    }

    public void setRotTower(float rotTower) {
        this.rotTower = rotTower;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getFrags() {
        return frags;
    }

    public void setFrags(int frags) {
        this.frags = frags;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTokken() {
        return tokken;
    }

    public void setTokken(String tokken) {
        this.tokken = tokken;
    }

    public String getNikName() {
        return nikName;
    }

    public void setNikName(String nikName) {
        this.nikName = nikName;

    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public boolean addMass(int nMass) { //Добавляет указанный элемент в этот набор, если он еще не присутствует. Более формально, добавляет указанный элемент e к этому набору, если этот набор не содержит такого элемента e2 , что (e == null? E2 == null: e.equals (e2)) . Если этот набор уже содержит элемент, вызов оставляет набор без изменений и возвращает false .
        return this.inMass.add(nMass);

    }

    public void clearQuerety(Integer nom) { // ужалить все которые отличаюстся на 5 )))
        for (Integer s : inMass) {
            if (Math.abs(s - nom) > 15) inMass.remove(s);
        }
    }

    public int getSize() {
        return this.inMass.size();
    }

    @Override
    public String toString() {
        return "Player{" +
                "behaviourBot=" + behaviourBot +
                ", status=" + status +
                ", xp=" + xp +
                ", yp=" + yp +
                ", r=" + r +
                ", rotTower=" + rotTower +
                ", hp=" + hp +
                ", frags=" + frags +
                ", death=" + death +
                ", command=" + command +
                ", id=" + id +
                ", tokken='" + tokken + '\'' +
                ", nikName='" + nikName + '\'' +
                ", inMass=" + inMass +
                '}';
    }
}
