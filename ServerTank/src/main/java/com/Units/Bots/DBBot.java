package main.java.com.Units.Bots;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DBBot {
    private final float ZERO_ATTACK = 1;

    // таймеры
    private float timer_attack = 0;
    private float timer_tact = 0;
    private float timer_stop = 0;

    private Vector2 target;
    private Vector2 directionOfFollowing;

    private Vector2 dublication_rotation;
    private Vector2 dublication_position;
    private boolean rotationKapkan = MathUtils.randomBoolean();

    public DBBot() {
        this.timer_attack = 3;
        this.timer_tact = 0;
        this.timer_stop = 0;
        this.directionOfFollowing = new Vector2(0, 0);
        this.target = new Vector2(Integer.MIN_VALUE, -1);
        this.directionOfFollowing = new Vector2(1, 1);
        this.dublication_rotation = new Vector2(Vector2.X);
        this.dublication_position = new Vector2(Vector2.X);
        this.directionOfFollowing.setAngleDeg(MathUtils.random(365));
    }

    public void updateTimer(float dt) {
        this.timer_attack -= dt;
        this.timer_tact -= dt;
        this.timer_stop -= dt;
    }

    public boolean isRedyAttack() {
        if (timer_attack < 0) return true;
        else return false;
    }

    public void newAttackZero() {
        this.timer_attack = ZERO_ATTACK;
    }

    public float getZERO_ATTACK() {
        return ZERO_ATTACK;
    }

    public float getTimer_attack() {
        return timer_attack;
    }

    public void setTimer_attack(float timer_attack) {
        this.timer_attack = timer_attack;
    }

    public float getTimer_tact() {
        return timer_tact;
    }

    public void setTimer_tact(float timer_tact) {
        this.timer_tact = timer_tact;
    }

    public float getTimer_stop() {
        return timer_stop;
    }

    public void setTimer_stop(float timer_stop) {
        this.timer_stop = timer_stop;
    }

    public Vector2 getTarget() {
        return target;
    }

    public void setTarget(Vector2 target) {
        this.target = target;
    }

    public Vector2 getDirectionOfFollowing() {
        return directionOfFollowing;
    }

    public void setDirectionOfFollowing(Vector2 directionOfFollowing) {
        this.directionOfFollowing = directionOfFollowing;
    }

    public boolean tackEnding() {
        if (timer_tact < 0) return true;
        else return false;
    }

    public boolean isRotationKapkan() {
        return rotationKapkan;
    }

    public void setRotationKapkan(boolean rotationKapkan) {
        this.rotationKapkan = rotationKapkan;
    }

    public void setTarget(float v, float v1) {
        this.getTarget().set(v, v1);
    }

    public boolean isZeroTarget() {
        if (this.target.x == Integer.MIN_VALUE) return true;
        return false;
    }



}
