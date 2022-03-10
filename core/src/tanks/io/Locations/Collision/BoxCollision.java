package tanks.io.Locations.Collision;

import com.badlogic.gdx.math.Vector2;

public class BoxCollision {
    Vector2 lb; // леывй нижний край
    Vector2 ru; // правый верхний

    public BoxCollision(Vector2 lb, Vector2 ru) {
        this.lb = lb;
        this.ru = ru;
        System.out.println(" Rectangle: position" + lb + "  WH" + ru);
    }

    public boolean isCollisionTank(Vector2 p){
        if (p.x + 15 < this.lb.x) return true;
        if (p.x - 15 > this.ru.x) return true;
        if (p.y + 15 < this.lb.y) return true;
        if (p.y - 15 > this.ru.y) return true;
        return false;
    }

    @Override
    public String toString() {
        return "Box{" +
                "lb=" + lb +", ru=" + ru +
                '}';
    }
}
