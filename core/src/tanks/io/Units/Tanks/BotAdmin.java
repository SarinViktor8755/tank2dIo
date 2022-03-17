package tanks.io.Units.Tanks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import tanks.io.MainGame;
import tanks.io.Screens.GamePlayScreen;

public class BotAdmin {/// локальные боты
    private GamePlayScreen gsp;

    public BotAdmin(GamePlayScreen gsp) {
        this.gsp = gsp;
    }

    public void upDateBotBehaviour() { // поведение бота
        float del = Gdx.graphics.getDeltaTime();
        // создание локальных ботов
        if (gsp.getTanksOther().listOpponents.size() < 50) {
            gsp.getTanksOther().createOponent(50, 50, MathUtils.random(45), MathUtils.random(45));
        }
        for (int i = 0; i < gsp.getTanksOther().listOpponents.size(); i++) {

        }
//        if(gsp.getTanksOther().listOpponents.size() <= 0){
//            gsp.getTanksOther().
//        }


//        OpponentsTanks r = new OpponentsTanks(
//                new Vector2(p.xp, p.yp),
//                new Vector2(1, 0),
//                0, true,
//                p.nom, listOpponents, gsp
//        );


    }


    static String getNikNameGen() {
        ArrayList<String> names = new ArrayList<>();
        names.add("Bubba");
        names.add("Honey");
        names.add("Bo");
        names.add("Sugar");
        names.add("Doll");
        names.add("Peach");
        names.add("Snookums");
        names.add("Queen");
        names.add("Ace");
        names.add("Punk");
        names.add("Sugar");
        names.add("Gump");
        names.add("Rapunzel");
        names.add("Teeny");
        names.add("MixFix");
        names.add("BladeMight");
        names.add("Rubogen");
        names.add("Lucky");
        names.add("Tailer");
        names.add("IceOne");
        names.add("Sugar");
        names.add("Gump");
        names.add("Rapunzel");
        names.add("Teeny");
        names.add("MixFix");
        names.add("BladeMight");
        names.add("Rubogen");
        names.add("Lucky");
        names.add("Tailer");
        names.add("IceOne");
        names.add("TrubochKa");
        names.add("HihsheCKA");
        names.add("R2-D2");
        names.add("Breha Organa");
        names.add("Yoda");
        names.add("Obi-Wan Kenob");
        names.add("C-3PO");
        names.add("Darth Sidious");
        names.add("Darth Vader");
        names.add("Boba Fett");
        names.add("Sarin");
        names.add("Sasha");
        return names.get(MathUtils.random(names.size() - 1)) + "@Bot";
    }

}
