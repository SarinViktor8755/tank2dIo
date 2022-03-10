package tanks.io.ParticleEffect;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayDeque;

import tanks.io.Screens.GamePlayScreen;
import tanks.io.Utils.VectorUtils;

public class ParticleCustum {
    ArrayDeque<ParticleSmoke> particleDeque;// дым
    ArrayDeque<PasricalExplosion> pasricalExplosions; // мелкие взрывы
    ArrayDeque<PasricalExplosionBigParameter> pasricalExplosionsBigParam; // большие взрывы
    ArrayDeque<Garbage> pasricalGarbage; // большие взрывы
    ArrayDeque<Explosion_Death> explosion_Death; // взрыв из тотал анигилейшен
    ArrayDeque<Explosion_Death> explosion_Death_little; // взрыв из тотал анигилейшен


    private Texture t;
    private Texture f;
    private Texture iron;
    private TextureAtlas textureAtlasDeathExplosion; /// атлес текстур взрыва тотала


    GamePlayScreen gps;

    public ParticleCustum(GamePlayScreen gps, Texture t, Texture f, Texture iron, TextureAtlas de) {
        this.t = t;
        this.f = f;
        this.iron = iron;
        this.textureAtlasDeathExplosion = de;

        this.particleDeque = new ArrayDeque<>();
        this.pasricalExplosions = new ArrayDeque<>();
        this.pasricalExplosionsBigParam = new ArrayDeque<>();
        this.pasricalGarbage = new ArrayDeque<>();
        this.explosion_Death = new ArrayDeque<>(); ///  текстур взрыва тотала
        this.explosion_Death_little = new ArrayDeque<>(); ///  текстур взрыва тотала__little


        for (int i = 0; i < 4; i++) {
            Explosion_Death ed = new Explosion_Death();
            this.explosion_Death.add(ed);
        }

        for (int i = 0; i < 25; i++) {
            Explosion_Death ed = new Explosion_Death();
            this.explosion_Death_little.add(ed);
        }

        for (int i = 0; i < 350; i++) {
            this.particleDeque.add(new ParticleSmoke());
        }

        for (int i = 0; i < 8; i++) {
            this.pasricalExplosions.add(new PasricalExplosion());
        }

        for (int i = 0; i < 20; i++) {
            this.pasricalExplosionsBigParam.add(new PasricalExplosionBigParameter());
        }

        for (int i = 0; i < 450; i++) {
            this.pasricalGarbage.add(new Garbage());
        }


        this.gps = gps;
        t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
        //f.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);

    }

    public void randerGarbage(SpriteBatch spriteBatch) {
        int k = 0;
        for (Garbage g : pasricalGarbage) {  // частицы
            //if (!fd.isLife()) continue;
            k++;


            g.upDate();
            spriteBatch.setColor(1, 1, 1, 1 - (MathUtils.map(0f, pasricalGarbage.size(), 0.3f, 1f, k)));

            //System.out.println(fd.getAlpha());
            spriteBatch.draw(iron,
                    g.getPos().x - iron.getWidth() / 2, g.getPos().y - iron.getHeight() / 2,
                    f.getWidth(), f.getHeight(),
                    iron.getWidth() / 2, iron.getHeight() / 2,
                    1, 1,
                    g.getRot(),
                    0, 0,
                    iron.getWidth(), iron.getHeight(),
                    false, false);
        }
        spriteBatch.setColor(1, 1, 1, 1);

    }


    public void render(SpriteBatch sb) {
//        i = 0;
//        k = 0;

        if (MathUtils.randomBoolean(0.0005f)) {
            this.addPasricalDeath(gps.getTank().getPosition().x + MathUtils.random(-300,500) , gps.getTank().getPosition().y);

        }

//        if (MathUtils.randomBoolean(0.1f)) {
//            this.addPasricalDeath_little(gps.getTank().getPosition().x + MathUtils.random(-300,300) , gps.getTank().getPosition().y+ MathUtils.random(-300,300), MathUtils.random(2,5));
//        }


        for (ParticleSmoke u : particleDeque) {
            if (!u.isLife()) continue;
//            k++;
            u.update();
            u.setAlpha(MathUtils.clamp(u.getTime_life(), -1, .7f));
            sb.setColor(u.getColor());
            sb.draw(t,
                    u.getPosition().x - t.getWidth() / 2, u.getPosition().y - t.getHeight() / 2,
                    t.getWidth() / 2, t.getHeight() / 2,
                    t.getWidth(), t.getHeight(),
                    u.getScale(), u.getScale(),
                    u.getRotation(),
                    0, 0,
                    t.getWidth(), t.getHeight(),
                    false, false);
        }
        sb.setColor(1, 1, 1, 1);


        for (PasricalExplosion u : pasricalExplosions) {  // взрывы
            //  if(VectorUtils.getLen2(gps.getTank().getPosition(),u.getPosition()) > 90_000) u.setTime_life(0);
            if (!u.isLife()) continue;
            u.update();
            sb.setColor(1, 1, 1, MathUtils.clamp(u.getTime_life(), -1, 1) * 10);
            sb.draw(f,
                    u.getPosition().x - f.getWidth() * u.getScale() / 2, u.getPosition().y - f.getWidth() * u.getScale() / 2,
                    0, 0,
                    f.getWidth(), f.getHeight(),
                    u.getScale(), u.getScale(),
                    0,
                    0, 0,
                    f.getWidth(), f.getHeight(),
                    false, false);

        }

        for (PasricalExplosionBigParameter fd : pasricalExplosionsBigParam) {  // смерть большие
            if (!fd.isLife()) continue;
            fd.update(this);
            sb.setColor(1, 1, 1, fd.getAlpha());
            //System.out.println(fd.getAlpha());
            sb.draw(f,
                    fd.getPosition().x - t.getWidth() / 2, fd.getPosition().y - t.getHeight() / 2,
                    f.getWidth() / 2, f.getHeight() / 2,
                    f.getWidth(), f.getHeight(),
                    fd.getScale(), fd.getScale(),
                    fd.getRot(),
                    0, 0,
                    f.getWidth(), f.getHeight(),
                    false, false);
        }



        for (Explosion_Death ed : explosion_Death_little) {
            if (!ed.isLife()) continue;
            ed.update();

            /////////////////
            TextureAtlas.AtlasRegion tex =  textureAtlasDeathExplosion.findRegion(ed.getNameTextureRegion());
            float xw = MathUtils.map(100,0,100,0,tex.getRegionWidth());
            float yw = MathUtils.map(100,0,100,0,tex.getRegionHeight());
            /////////////////
            sb.draw(
                    tex,
                    ed.getPosition().x - (tex.getRegionWidth() / 2/ed.getKefm()*ed.getTime_life()), ed.getPosition().y - (tex.getRegionHeight() / 2/ed.getKefm()*ed.getTime_life()),
                    xw/ed.getKefm()*ed.getTime_life(), yw/ed.getKefm()*ed.getTime_life()
            );
        }

        for (Explosion_Death ed : explosion_Death) {
            if (!ed.isLife()) continue;
            ed.update();
            /////////////////
            TextureAtlas.AtlasRegion tex =  textureAtlasDeathExplosion.findRegion(ed.getNameTextureRegion());
            float xw = MathUtils.map(100,0,100,0,tex.getRegionWidth());
            float yw = MathUtils.map(100,0,100,0,tex.getRegionHeight());
            /////////////////
            sb.draw(
                    tex,
                    ed.getPosition().x - (tex.getRegionWidth() / 2), ed.getPosition().y - (tex.getRegionHeight() / 2),
                    xw, yw
            );
        }
    }

    public void addParticalsSmoke(int quantity, float x, float y, int hp) {
        // System.out.println(hp);
        if (VectorUtils.getLen2(gps.getTank().getPosition(), x, y) > 90_000) return;
        float black = MathUtils.map(50, 0, 0, 1, hp) + MathUtils.random(-.35f, +.35f);
        //  System.out.println(black);
        if (!checkViseble(x, y)) return;
        for (int i = 0; i < quantity; i++) {
            ParticleSmoke a = this.particleDeque.pollLast();
            a.setParameters(MathUtils.random(.5f, 2),
                    x + MathUtils.random(-7, 7),
                    y + MathUtils.random(-7, 7),
                    MathUtils.random(0, 360), MathUtils.random(-20, 20),
                    MathUtils.random(-.2f, .7f),
                    MathUtils.random(-.2f, .4f),
                    MathUtils.random(-10, 10), MathUtils.random(-10, 10),
                    1 - black, 1 - black, 1 - black, 1
            );
            this.particleDeque.offerFirst(a);
        }
    }

    public void addParticalsSmokeOne(float x, float y) {
        if (VectorUtils.getLen2(gps.getTank().getPosition(), x, y) > 90_000) return;
        ParticleSmoke a;
        a = this.particleDeque.pollLast();
        a.setParameters(1.0f,
                x, y,
                MathUtils.random(0, 360), MathUtils.random(-5, 5),
                .5f,
                1.2f,
                -.4f, -.4f,
                MathUtils.random(.7f, 1), .4f, .4f,
                .2f
        );
        this.particleDeque.offerFirst(a);
    }

    public void addParticalsSmokeOneBullet(float x, float y) {
        if (!checkViseble(x, y)) return;
        ParticleSmoke a;
        a = this.particleDeque.pollLast();
        a.setParameters(.4f,
                x, y,
                MathUtils.random(0, 360), MathUtils.random(-5, 5),
                .3f,
                00.1f,
                -.4f, -.4f,
                .4f, .4f, MathUtils.random(.7f, 1),
                1f
        );
        this.particleDeque.offerFirst(a);

    }

    public void addPasricalExplosion(float timeLife, float x, float y) {
        if (!checkViseble(x, y)) return;
        PasricalExplosion a = this.pasricalExplosions.pollLast();
        a.setParameters(timeLife, x, y);
        this.pasricalExplosions.offerFirst(a);
    }

    public void addPasricalDeath(float x, float y) {
        if (!checkViseble(x, y)) return;
        Explosion_Death a = this.explosion_Death.pollLast();
        a.setParameters(x, y);
        this.explosion_Death.offerFirst(a);
    }

    public void addPasricalDeath_little(float x, float y,float kefM) {
        if (!checkViseble(x, y)) return;
        Explosion_Death a = this.explosion_Death_little.pollLast();
        a.setParameters(x, y,kefM);
        this.explosion_Death_little.offerFirst(a);
    }

    public void addGarbage(float x, float y) {
        Garbage g = this.pasricalGarbage.pollLast();
        g.setParametors(x, y);
        this.pasricalGarbage.offerFirst(g);
    }


    public void addPasricalExplosionDeath(float x, float y) {
        if (!checkViseble(x, y)) return;
        PasricalExplosionBigParameter f;

        f = this.pasricalExplosionsBigParam.pollLast();
        f.setParameters(x, y);
        this.pasricalExplosionsBigParam.offerFirst(f);

        for (int i = 0; i < 2; i++) {
            f = this.pasricalExplosionsBigParam.pollLast();
            f.setParameters(x + MathUtils.random(-40, +40), y + MathUtils.random(-40, +40));
            this.pasricalExplosionsBigParam.offerFirst(f);
        }


    }

    private boolean checkViseble(float x, float y) {
        if (!MathUtils.isEqual(gps.getTank().getPosition().x, x, 350)) return false;
        if (!MathUtils.isEqual(gps.getTank().getPosition().y, y, 350)) return false;
        return true;
    }


    public void addParticalsSmoke(int random, float v, float v1) {
    }


}
