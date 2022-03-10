package main.java.com.Units.Bots;

import java.util.HashMap;
import java.util.Map;

import main.java.com.Units.Player;

public class BotBehavior {

    private HashMap<Integer, BehaviourBot> behaviourBot;
    private HashMap<Integer, Player> botList;
    private HashMap<Integer, Player> allPlayers;

    public BotBehavior(HashMap<Integer, Player> bl) {
        this.behaviourBot = new HashMap<>();
        this.botList = bl;
  //      this.allPlayers = p;
    }

    public BehaviourBot getBehaviourBotForID(int id) {
        try {
            return this.behaviourBot.get(id);
        } catch (NullPointerException e) {
            BehaviourBot bb = new BehaviourBot();
            this.behaviourBot.put(id, bb);
            return bb;
        }
    }

    public void updateTimeBB(float dt){ /// бновит временные такты
        for (Map.Entry<Integer, BehaviourBot> bt : behaviourBot.entrySet()) {
            getBehaviourBotForID(bt.getKey()).updateDT(dt);
        }
    }

    public void updateBehavior(float dt){ /// обновить поведение
        for (Map.Entry<Integer, BehaviourBot> bt : behaviourBot.entrySet()) {
            getBehaviourBotForID(bt.getKey()).update(dt);
        }
    }

    public boolean isRedyToAttack(int id){
        if(!behaviourBot.containsKey(id)) return false;
        return getBehaviourBotForID(id).isAtack();
    }

    private Integer FindNearestTarget(){ //
        float ras = Float.MIN_VALUE; // asdasd
        int id = Integer.MIN_VALUE;

        for (Map.Entry<Integer, BehaviourBot> bt : behaviourBot.entrySet()) {

        }

        for (Map.Entry<Integer, Player> bt : allPlayers.entrySet()) {


        }


        return 1;
    }

    /// поведение башни








}
