package tanks.io.Utils;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class VectorUtils {
    private static Vector2 tempVector2;
    private static Vector2 tempVector21;
    private static Vector3 tempVector3;


    public static Vector2 tempVector2(float x, float y) {
       return tempVector2(x,y);
    }

    public static Vector3 tempVector3(float x, float y) {
        return tempVector3(x,y).set(x,y,0);
    }

    public static Vector2 takeTargetVector2Through(Vector2 sorce, Vector2 target){
        return sorce.cpy().sub(target);
    }

    public static float getLen2(Vector2 pos,Vector2 pos2){
       return pos.cpy().sub(pos2).len2();
    }

    public static float getLen(Vector2 pos,Vector2 pos2){
        return pos.cpy().sub(pos2).len();
    }

    public static float getLen2(Vector2 pos,float x, float y){
        return pos.cpy().sub(x,y).len2();
    }

    public static Vector2 v3tov2(Vector3 v3){
        return tempVector2(v3.x,v3.y);
    }

    public static float getLen2(float x, float y, float x1, float y1){
        return tempVector2.set(x,y).sub(x1,y1).len2();
    }



}
