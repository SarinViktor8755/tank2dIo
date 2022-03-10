package main.java.com.Units.Bullet;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import main.java.com.GameServer;

public class IndexBullets {

    private final Array<Bullet> activeBullets = new Array<>();
    private final BulletPool bp = new BulletPool();
    private GameServer gameServer;

    private long previousStepTime = 0;
    private static int BULLET_SPEED = 400;


    public IndexBullets(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public void addBullet(Vector2 pos, Vector2 vel, int nom,int nomAuthor) { // координаты, навправление, номер снаряда
        vel.clamp(BULLET_SPEED,BULLET_SPEED);
        // получи пулю из нашего бассейна
        Bullet b = bp.obtain();
        /// стреляйте пулей с того места, на которое мы нажимаем, в направлении прямо вверх
        b.fireBullet(pos.x, pos.y, vel.x, vel.y, nom,nomAuthor);
        // добавьте в наш массив маркеры, чтобы мы могли получить к ним доступ в нашем методе визуализации
        activeBullets.add(b);
//        System.out.println("activeBullets.size " + activeBullets.size);
//        System.out.println("++ bullet " + nom);

        gameServer.outMassegeCollection.tellAboutShootBot((int)pos.x,(int)pos.y,(int)vel.angleDeg(),nom,nomAuthor);
    }

    private boolean checkingGoingAbroad(float x, float y) {
       // System.out.println(!gameServer.getMainGame().getMapSpace().isPointInCollision(x, y));
        if (gameServer.getMainGame().getMapSpace().isPointInCollision(x, y)) return true;
        if (!gameServer.getMainGame().getMapSpace().isPointWithinMmap(x, y)) return true;
        return false;
    }

    public boolean iSNullActiveBullets() {
        return activeBullets.size < 1;
    }

    public void updateBulets(float dt) {
        Bullet bullet;

        for (int i = 0; i < activeBullets.size; i++) {
            bullet = activeBullets.get(i);
            bullet.update(dt);
            System.out.println("  " + activeBullets.get(i));
            if (checkingGoingAbroad(bullet.position.x, bullet.position.y)) {
                freeBullet(i,bullet);
                System.out.println("вышел за границу карты x: " + bullet.position.x +"  y: " + bullet.position.y);
            }
        }
           /// System.out.println(activeBullets.size);
    }

    public synchronized void freeBullet(int index, Bullet bullet){
       /// System.out.println("!!!!!!!!!!!!!!!!!!!BOOOOOOOOOOOOOOOOOOOM !!!!!!!!!!!!!!!!!!!  " + bullet.position);
        gameServer.outMassegeCollection.tellShellRupture(bullet.position,bullet.getNom());
        bp.free(bullet);
        activeBullets.removeIndex(index);

    }


}
