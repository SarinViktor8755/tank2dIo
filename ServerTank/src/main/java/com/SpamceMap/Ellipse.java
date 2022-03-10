package main.java.com.SpamceMap;

import com.badlogic.gdx.math.Vector2;

import tanks.io.Utils.VectorUtils;

public class Ellipse implements Figure {
    private Vector2 positionCenter;
    private float radius;
    private float radius2;

    public Ellipse(Vector2 position, float width, float height) {
        positionCenter = new Vector2();
        this.positionCenter.set(position.x + width / 2, position.y + height / 2);
        this.radius = width / 2l;
        this.radius2 = (float) Math.pow(width / 2, 2);
    }

    @Override
    public boolean isPointCollision(int x, int y) {
        if (VectorUtils.getLen2(positionCenter, x, y) < radius2 + 230) return true;
       // System.out.println(positionCenter + "   " + radius2);
        return false;
    }

    public Vector2 getPositionCenter() {
        return positionCenter;
    }

    public float getRadius() {
        return radius;
    }

    public float getRadius2() {
        return radius2;
    }
}
