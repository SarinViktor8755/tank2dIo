package tanks.io.Locations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class LightingBox2D {
    private World world;

    private GameSpace gameSpace;

    private RayHandler rayHandlerHero;
    private PointLight pointLightHero;
    private ConeLight coneLightHero;
    private ConeLight coneLightTower;
    private boolean lasetOn;
    private float laserLith;
    private RayHandler rayHandler;

    private ArrayList<PointLight> pointLightsList;

    private Box2DDebugRenderer box2DDebugRenderer;

    private BuletFlash buletFlash; // вспышка от выстрела

    public LightingBox2D(GameSpace gameSpace) {
        lasetOn = true;
        this.gameSpace = gameSpace;
        this.world = new World(new Vector2(0, 0), true);
        // RayHandler.useDiffuseLight(true);
        laserLith = 0;
        this.rayHandlerHero = new RayHandler(this.world);
        this.rayHandler = new RayHandler(this.world);
        //  rayHandlerHero.setAmbientLight(0, 0, 0.15f, .15f);
        //  rayHandlerHero.setAmbientLight(.5f);
        rayHandlerHero.setShadows(true);
        //  rayHandlerHero.shadowBlendFunc.set(GL20.GL_UNSIGNED_SHORT, GL20.GL_VERTEX_SHADER);
        RayHandler.useDiffuseLight(true);
        pointLightHero = new PointLight(rayHandlerHero, 180 /4 * 3 , Color.WHITE, 850, 0, 0);
        //свитильник геро
        coneLightHero = new ConeLight(rayHandlerHero, 90, Color.ROYAL, 200, 0, 0, 90, 60);
        coneLightTower = new ConeLight(rayHandlerHero, 3, Color.RED, 450, 0, 0, 45, 2); // лазер
        buletFlash = new BuletFlash(rayHandlerHero);// вспышка от выстрелаz
    }

    public void upDateLights(float xHero, float yHero, float align) {
        pointLightHero.setPosition(xHero, yHero);
        for (PointLight p : pointLightsList) {
            p.setPosition(p.getX()+ MathUtils.random(-10,10),p.getY()+ MathUtils.random(-10,10));
            p.setPosition(MathUtils.clamp(p.getPosition().x,0,5000),MathUtils.clamp(p.getPosition().y,0,5000));
        }
    }

    public World getWorld() {
        return world;
    }

    public BuletFlash getBuletFlash() {
        return buletFlash;
    }

    public void renderLights(Camera camera) {
        rayHandlerHero.setCombinedMatrix((OrthographicCamera) camera);
        rayHandlerHero.updateAndRender();
        upDateLights();
        if (buletFlash.isLife()) buletFlash.upDate();
    }

    public void upDateLights() {
        world.step(1 / 60f, 1, 1);
        // this.coneLightTower.setActive(lasetOn);
        if (lasetOn) laserLith = laserLith + Gdx.graphics.getDeltaTime() * 250;
        else laserLith = laserLith - Gdx.graphics.getDeltaTime() * 350;

        laserLith = MathUtils.clamp(laserLith,0,400);
       // System.out.println(laserLith);
        coneLightTower.setDistance(laserLith);
    }

    public void startBulletFlash(float x, float y) {
        buletFlash.newFlesh(x, y);
    }

    public void setPointL(float x, float y) {
        this.pointLightHero.setPosition(x, y);
    }

    public void setCone(float x, float y, float align) {
        this.coneLightHero.setPosition(x, y);
        this.coneLightHero.setDirection(align);
    }

    public void setConeTower(float x, float y, float align) {
        this.coneLightTower.setPosition(x, y);
        this.coneLightTower.setDirection(align);
    }

    public void coneLightHeroAgree() {
        if (MathUtils.randomBoolean(.5f)) if (MathUtils.randomBoolean())
            coneLightHero.setConeDegree(coneLightHero.getConeDegree() - 2);
        else coneLightHero.setConeDegree(coneLightHero.getConeDegree() - 2);
        coneLightHero.setConeDegree(MathUtils.clamp(coneLightHero.getConeDegree(), 50, 65));
    }

    public void setLasetOn(boolean lasetOn) {
        this.lasetOn = lasetOn;
    }
}
