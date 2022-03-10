package tanks.io.Locations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayDeque;

public class Radspurens { //следы
    ArrayDeque<RadspurenTank> listRadspurens;
    Texture img;

    public Radspurens(Texture img) {
        listRadspurens = new ArrayDeque<>();
        for (int i = 0; i < 1250; i++) {
            listRadspurens.addFirst(new RadspurenTank(0, 0, 0, false));
        }
        this.img = img;
        this.img.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
    }

    public void addRadspurenTank(float x, float y, float align) {
        try {
            RadspurenTank t = this.listRadspurens.pollLast();
            t.aligbDeg = align;
            t.xp = x;
            t.yp = y;
            t.life = true;
            t.timer = 0;
            listRadspurens.addFirst(t);
        }catch (NullPointerException e){}

    }

    public void randerRadspurens(SpriteBatch sb) {
        int size;
        float alpha;
        size = listRadspurens.size();
        for (RadspurenTank rt : listRadspurens) {
            if (!rt.life) {sb.setColor(1,1,1,1);return;}
            alpha = MathUtils.clamp(.0012f * (float) size,0,.6f);
            sb.setColor(1,1,1,alpha);
            sb.draw(img,
                    rt.xp - 20, rt.yp - 20,
                    20, 20,
                    20, 40,
                    1, 1,
                    rt.aligbDeg,
                    0, 0,
                    img.getWidth(), img.getHeight(),
                    false, false);
            size--;
        }
        sb.setColor(1,1,1,1);


    }


}
