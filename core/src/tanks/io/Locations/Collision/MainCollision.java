package tanks.io.Locations.Collision;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

import tanks.io.Screens.GamePlayScreen;

public class MainCollision {
    GamePlayScreen gsp;

    private ArrayList<BoxCollision> box = new ArrayList<>();
    private ArrayList<CircleCollision> circle = new ArrayList<>();


    public MainCollision(GamePlayScreen gsp) {
        this.gsp = gsp;
        this.circle = new ArrayList<>();
        box= new ArrayList<>();
    }

    public void addRectangleMapObject(Vector2 rn, Vector2 lu) {
        this.box.add( new BoxCollision(rn, lu));

    }

    public boolean isCollisionsRectangle(Vector2 pos) {
       // System.out.println(box);
        for (BoxCollision b : box) {
            if (!b.isCollisionTank(pos)) return false;
        }
        return true;
    }

    ////////////////////////

    public void addCircleeMapObject(Vector2 c, float r) {
        this.circle.add(new CircleCollision(c, r));
    }

    public boolean isCircleCircle(Vector2 pos) {
       // System.out.println(circle);
        for (CircleCollision c : circle) {
           if (!c.isCollisionCircle(pos)) return false;
        }
        return true;
    }

}




