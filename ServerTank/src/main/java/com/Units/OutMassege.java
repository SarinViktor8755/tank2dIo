package main.java.com.Units;

import java.util.ArrayDeque;
import java.util.Objects;

import main.java.com.Network;

public class OutMassege {
    Network.StockMess sm;
    public boolean actual;
    int timePick;
    int forPlayer;
    
    long timeMomenIn; // время прихода сообщения

    public OutMassege() {
        this.sm = new Network.StockMess();
        this.actual = true;
        this.timePick = 0;
        this.forPlayer = - 1;
    }

    public void setParametors(Network.StockMess sm, int timePick, int forPlayer) {
        this.sm = sm;
        this.actual = true;
        this.timePick = timePick;
        this.forPlayer = forPlayer;
        this.timeMomenIn = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "OutMassege{" +
                "actual=" + actual +
                ", timePick=" + timePick +
                ", forPlayer=" + forPlayer +
                '}';
    }


    @Override
    public int hashCode() {
        return Objects.hash(sm, actual, timePick, forPlayer, timeMomenIn);
    }

}
