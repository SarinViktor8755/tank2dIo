package tanks.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;

import tanks.io.Utils.VectorUtils;

import static tanks.io.MainGame.HIDE_SCREEN;
import static tanks.io.MainGame.WHIDE_SCREEN;

public class CameraGame{
    OrthographicCamera camera;
    private FillViewport viewport;
    private boolean floatCamera;

    private int sizeMap;

    private Vector2 speed; // определяемая скосроть с назначением новойо точки
    private Vector2 targetPoint; // точка к которой двиать камеру

    private final int hl, wl;

    public CameraGame(float HIDE_SCREEN,float WHIDE_SCREEN, int sm, int hl,int wl) {
        this.camera = new OrthographicCamera();
        this.viewport = new FillViewport(HIDE_SCREEN, WHIDE_SCREEN, camera);
        floatCamera = false;
        targetPoint = new Vector2();
        this.sizeMap = sm;

        this.wl = wl;
        this.hl = hl;

    }

    public void moveFloatCameraToPoint(float x , float y, float speed){
        this.targetPoint.set(x,y);
        this.camera.position.sub(this.camera.position.cpy().sub(targetPoint.x,targetPoint.y,0).scl(Gdx.graphics.getDeltaTime() * speed));
        fixBounds();
    }

    public void jampCameraToPoint(float x , float y){
        //this.camera.position.set(x,y,0);
        this.camera.position.set(x,y,0);
        fixBounds();
    }


//    public void jampCameraToPoint(Vector2 toPoint, speed){
//        //this.camera.position.set(x,y,0);
//        this.camera.position.set(x,y,0);
//        fixBounds();
//    }

    public void updateCamera(){

    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public FillViewport getViewport() {
        return viewport;
    }


    public void fixBounds() {
        float scaledViewportWidthHalfExtent = WHIDE_SCREEN * 1.2f  * 0.5f;
        float scaledViewportHeightHalfExtent = HIDE_SCREEN  * 1.2f* 0.5f;

        // Horizontal
        if (camera.position.x < scaledViewportWidthHalfExtent)
            camera.position.x = scaledViewportWidthHalfExtent;
        else if (camera.position.x > wl - scaledViewportWidthHalfExtent)
            camera.position.x = wl - scaledViewportWidthHalfExtent;

        // Vertical
        if (camera.position.y < scaledViewportHeightHalfExtent)
            camera.position.y = scaledViewportHeightHalfExtent;
        else if (camera.position.y > hl - scaledViewportHeightHalfExtent)
            camera.position.y = hl - scaledViewportHeightHalfExtent;
    }

    public Vector3 getCameraPosition(){
        return camera.position;
    }
}
