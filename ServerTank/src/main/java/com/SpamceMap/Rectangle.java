package main.java.com.SpamceMap;

import com.badlogic.gdx.math.Vector2;

public class Rectangle implements Figure {

    private Vector2 position;  /// левая нижняя точка
    private Vector2 positionWH; /// верхняя правая точка

    public Rectangle(float x, float y, float width, float height,int hm) { /// конструктор прямоугольника
   //     System.out.println("---------!!  " + x + "  " + y + " -|||-  " + width + " -  " + height);

        this.position = new Vector2(x, y-height);
        this.positionWH = new Vector2(position.x + width, position.y + height);
//
//        System.out.println(position);
//        System.out.println(positionWH);
//        System.out.println("--------------------------------");

     //   System.out.println(this);
    }

    @Override
    public boolean isPointCollision(int x, int y) {  /// это точка пересекается  с прямоугольником или нет
        if (
                (x > this.position.x - 15) && (x < this.positionWH.x +  15)
                        &&
                (y > this.position.y - 15) && (y < this.positionWH.y + 15)
        ) return true;

        return false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getPositionWH() {
        return positionWH;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "nih lev =" + getPosition() +
                ", pravi verhn =" + getPositionWH() +
                '}';
    }
}
