package tanks.io.Units.Tanks;

import tanks.io.Screens.GamePlayScreen;

public class TanksOtherSP extends TanksOther{
    BotAdmin botAdmin;

    public TanksOtherSP(GamePlayScreen gsp) {
        super(gsp);
        System.out.println("TanksOtherSP");
        botAdmin = new BotAdmin(gsp);
    }

    public void updateClienOtherTank() {
        botAdmin.upDateBotBehaviour();
    }


}
