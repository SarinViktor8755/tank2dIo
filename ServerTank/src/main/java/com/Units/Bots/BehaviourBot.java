package main.java.com.Units.Bots;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Map;

import tanks.io.Units.Tanks.OpponentsTanks;

public class BehaviourBot { // поведение бота - вектор направлениея  - логика принятия решений

    Vector2 direction; // это типа куда нажал на ждостике бот
    float timerShoot = 0;
    private Vector2 tempRotationTower;
    private Integer nomTarget; // номер цели ИД
    private float timerTackt;
    // private boolean rotation; // поворот башни


    public BehaviourBot() {
        this.direction = new Vector2(0, 0);
        this.timerShoot = 0;
        this.tempRotationTower = new Vector2(90,0);

        this.nomTarget = null;
        this.timerTackt = 0;


    }

    public void updateDT(float deltaTime) {
        this.timerShoot += deltaTime;
    }

    public void zeroTime() {
        this.timerShoot = 0;
    }

    public boolean isAtack(){
      //  System.out.println("2222222222");
        if(this.timerShoot > 1f) {this.timerShoot = 0; return true;} else return false;
    }

///////////////////////////////////

    public void update(float delta) {
        makingDecisionTower(delta);
        this.timerTackt += delta;
    }

    private float turningTower(float targetAlign, float delta, float angel) { /// повернуть башню на градус
        this.tempRotationTower.setAngleDeg(angel);
        if (!MathUtils.isEqual(tempRotationTower.angleDeg(), targetAlign, 1.2f)) {
           // rotation = true;
            if ((direction.cpy().setAngleDeg(targetAlign).angleDeg(tempRotationTower) > 180))
                tempRotationTower.rotateDeg(-120 * delta);
            else
                tempRotationTower.rotateDeg(120 * delta);
        }
        return tempRotationTower.angleDeg();
    }

    private void makingDecisionTower(float delta) { // принятие решение

//        //    System.out.println(timerTackt);
//        if (nomTarget == null) { // если нет целей
//            turningTower(this.direction.angleDeg(), delta);
//            target_tank = 0;
//            if (MathUtils.randomBoolean(.3f)) targetDetectionTower(this.myPosition); // ищем цели
//            nomTarget = selectTarget();
//        } else {    // если  цели есть
//            turningTower(returnAngle(listOpponents.get(nomTarget).getPosition(), myPosition), delta);
//            if (MathUtils.randomBoolean(.05f)) targetDetectionTower(this.myPosition); // ищем цели
//            if (checkLen()) nomTarget = null;
//        }
    }
///////////////////////////////////

    private void targetDetectionTower(Vector2 positionMy) { // обноружение цели для башни - добавляем в очередь
//        targetTreet.clear();
//        for (Map.Entry<Integer, OpponentsTanks> tank : listOpponents.entrySet()) {
//            float l = tank.getValue().getPosition().cpy().sub(positionMy).len2();
//            if (l < 2) continue; // иключение себя
//            if (l < rast_to_target) {
//                this.targetTreet.put(returnAngle(tank.getValue().getPosition(), myPosition), tank.getKey());
//            }
//        }
//        //  System.out.println(targetTreet.size());
    }

}
