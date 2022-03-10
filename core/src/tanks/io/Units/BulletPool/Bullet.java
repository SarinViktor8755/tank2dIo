package tanks.io.Units.BulletPool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Bullet implements Pool.Poolable {
    // fields for bullets position and direction
    private final static int BULLET_SPEED = 400;

    public Vector2 position = new Vector2(0, 0);
    public Vector2 direction = new Vector2(0, 0);

    public int namber = 0;


    public int getNamber() {
        return namber;
    }

    @Override
    public void reset() {
        //вызывается при выстреле пули
        this.position.set(0, 0);
        this.direction.set(0, 0);
    }

    // метод, который мы можем вызвать для обновления нашей логики маркеров
    public void update() {

        this.direction.clamp(BULLET_SPEED,BULLET_SPEED);
        this.direction.setLength(BULLET_SPEED);


        position.add(direction.scl(Gdx.graphics.getDeltaTime()));
    }

    // способ задания положения и направления пуль (стрельбы)
    public void fireBullet(float xpos, float ypos, float xvel, float yvel, int n) {
        this.position.set(xpos, ypos);
        this.direction.set(xvel, yvel).clamp(BULLET_SPEED,BULLET_SPEED);
        this.direction.setLength(BULLET_SPEED);
        this.namber = n;
    }

    // то же, что и вышеописанный метод с векторами
    public void fireBullet(Vector2 pos, Vector2 dir, int nom) {
        this.position = pos;
        this.direction = dir;
        this.namber = nom;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "position=" + position +
                '}';
    }
}