package tanks.io.ClientApi;


public class PacketModel {
    private boolean actual;
    private Network.StockMess p;
    //private float timelive;

    public Network.StockMess packatToSend() {
        p = new Network.StockMess();
        return new Network.StockMess();
    }

    public PacketModel(Integer time_even, Integer tip, Integer p1, Integer p2, Integer p3, Integer p4, Integer p5, Integer p6, Integer nomer_pley, String textM, boolean actual) {
        p.time_even = time_even;
        p.tip = tip;
        p.p1 = p1;
        p.p2 = p2;
        p.p3 = p3;
        p.p4 = p4;
        p.p5 = p5;
        p.p6 = p6;
        p.nomer_pley = nomer_pley;
        p.textM = textM;
        this.actual = actual;
    }


    public PacketModel(Network.StockMess sm, boolean actual) {
        this.p = sm;
        this.actual = actual;
    }

    public PacketModel() {
        p = new Network.StockMess();
        p.time_even = null;
        p.tip = null;
        p.p1 = null;
        p.p2 = null;
        p.p3 = null;
        p.p4 = null;
        p.p5 = null;
        p.p6 = null;
        p.nomer_pley = null;
        p.textM = null;
        actual = false;
        // timelive = 0;
    }

    public int getTip(){
        return this.p.tip;
    }

    public Integer getTime_even() {
        return this.p.time_even;
    }

    public void setParametrs(int tip, Integer p1, Integer p2, Integer p3, Integer p4, Integer p5, Integer p6, String text) {
        actual = true;
        p.tip = tip;
        p.p1 = p1;
        p.p2 = p2;
        p.p3 = p3;
        p.p4 = p4;
        p.p5 = p5;
        p.p6 = p6;
        p.nomer_pley = 0;
        p.textM = text;
        actual = true;
        //timelive = 0;
    }


    public void sendId(int myId){
        p.nomer_pley = myId;
    }

    public boolean isActual() {
        return actual;
    }

    public Network.StockMess getP() {
        return p;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public void setActualFalse() {
        this.actual = false;
    }

    public void printPaket() {
        System.out.println("-------------Start PACK-------------");
        System.out.println("nom_pack  " + getP().time_even);
        System.out.println("tip  " + getP().tip);
        System.out.println("1 " + getP().p1);
        System.out.println("2 " + getP().p2);
        System.out.println("3 " + getP().p3);
        System.out.println("4 " + getP().p4);
        System.out.println("5 " + getP().p5);
        System.out.println("6 " + getP().p6);
        System.out.println("np " + getP().nomer_pley);
        System.out.println("text "+ getP().textM);
        System.out.println("-------------END PACK-------------");
    }

}
