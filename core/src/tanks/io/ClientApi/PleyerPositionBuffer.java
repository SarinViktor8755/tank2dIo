package tanks.io.ClientApi;

public class PleyerPositionBuffer {
    int x,y,r;  // позиция кузова
    int rotTower; // угол башни

    public PleyerPositionBuffer(int x, int y, int r, int rotTower) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.rotTower = rotTower;
    }
}
