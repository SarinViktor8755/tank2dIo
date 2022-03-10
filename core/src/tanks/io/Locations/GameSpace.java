package tanks.io.Locations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import tanks.io.Locations.Collision.MainCollision;
import tanks.io.Locations.UtilsLighting.b2d.BodyBuilder;
import tanks.io.Screens.GamePlayScreen;

public class GameSpace {
    GamePlayScreen gps;

    private TiledMap map;
    private OrthogonalTiledMapRenderer rendererMap;
    private LightingBox2D lighting;
    private OrthographicCamera camera;
    private Radspurens radspurens;
    // private static ArrayList<String> maps = new ArrayList<>();
    public final int WITH_LOCATION;
    public final int HEIHT_LOCATION;

    private MapLayer obstacles; // препятствия

    ShapeRenderer shapeRenderer;


    private TiledMapTileLayer decorations;
    private TiledMapTileLayer ground;

    MainCollision mainCollision;

    public GameSpace(GamePlayScreen gps) {
        this.gps = gps;
        radspurens = new Radspurens(gps.getAssetsManagerGame().get("sled.png", Texture.class));


        float unitScale = 1f;
        lighting = new LightingBox2D(this);


        //map = new TmxMapLoader().load(MAP_DESETRT);
        map = gps.getAssetsManagerGame().get("map/desert.tmx");

        rendererMap = new OrthogonalTiledMapRenderer(map, 1.0f, getGps().getBatch());

        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get(0);
        WITH_LOCATION = tiledLayer.getWidth() * tiledLayer.getTileWidth();
        HEIHT_LOCATION = tiledLayer.getWidth() * tiledLayer.getTileHeight();
        //System.out.println(HEIHT_LOCATION + "!!!!!!!!!!!!!!!!!!");
        shapeRenderer = new ShapeRenderer();
        //  mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / 32f);
        decorations = (TiledMapTileLayer) map.getLayers().get("decorations");
        ground = (TiledMapTileLayer) map.getLayers().get("ground");
        obstacles = map.getLayers().get("сollision");

        mainCollision = new MainCollision(gps);

        for (int i = 0; i < obstacles.getObjects().getCount(); i++) {

            if (obstacles.getObjects().get(i) instanceof RectangleMapObject) {
                RectangleMapObject a = (RectangleMapObject) obstacles.getObjects().get(i);
                Vector2 ln = new Vector2(a.getRectangle().x, a.getRectangle().y);
                Vector2 ru = new Vector2(a.getRectangle().x + a.getRectangle().getWidth(), a.getRectangle().y + a.getRectangle().getHeight());
                mainCollision.addRectangleMapObject(ln, ru);

                BodyBuilder.createBox(getLighting().getWorld(),
                        a.getRectangle().x + a.getRectangle().getWidth() / 2,
                        a.getRectangle().y + a.getRectangle().getHeight() / 2,
                        (int) a.getRectangle().getWidth() / 2,
                        (int) a.getRectangle().getHeight() / 2,
                        true, true);
            }
            if (obstacles.getObjects().get(i) instanceof EllipseMapObject) {

                EllipseMapObject ellipse = (EllipseMapObject) obstacles.getObjects().get(i);
//                System.out.println("::::::::  "+ ellipse.getEllipse().x);
//                System.out.println("::::::::  "+ ((HEIHT_LOCATION - ellipse.getEllipse().y) - (ellipse.getEllipse().height)));
                float r = ellipse.getEllipse().height / 2l;
                float x = ellipse.getEllipse().x + r;
                float y = ellipse.getEllipse().y + r;

                mainCollision.addCircleeMapObject(new Vector2(x, ((HEIHT_LOCATION - ellipse.getEllipse().y) - (ellipse.getEllipse().height))), r);
                BodyBuilder.createCircle(getLighting().getWorld(),  x, y, r); }
        }


    }

    public MainCollision getMainCollision() {
        return mainCollision;
    }

    public int getSizeLocationPixel() {
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get(0);
        return tiledLayer.getWidth() * tiledLayer.getTileWidth();
    }

    public int getWidthLocation() {
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get(0);
        return tiledLayer.getWidth() * tiledLayer.getTileWidth();
    }

    public int getHideLocation() {
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) map.getLayers().get(0);
        return tiledLayer.getHeight() * tiledLayer.getHeight();
    }

    public MapLayer getObstacles() {
        return obstacles;
    }

    public LightingBox2D getLighting() {
        return lighting;
    }

    public void renderSpace(OrthographicCamera camera) {
        rendererMap.setView(camera);

        decorations = (TiledMapTileLayer) map.getLayers().get(1);
        ground = (TiledMapTileLayer) map.getLayers().get(0);
        map.getLayers().get(2);

        rendererMap.renderTileLayer(ground);
        rendererMap.renderTileLayer(decorations);

//        for (int i = 0; i < alignedLayer.getWidth(); i++) {
//            for (int j = 0; j < alignedLayer.getHeight(); j++) {
//                if (alignedLayer.getCell(j, i) != null) {
//
//                    System.out.println(i + "  " + j);
//                    alignedLayer.
////                    stage.addActor(new TiledActor(world, j, i, alignedLayer));
////                    System.out.println();
//                    //mapRenderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get(0));
//
//                }
//            }

        //  }
        // Rectangle
        // rendererMap.render();
        // createShape();
    }

    public Radspurens getRadspurens() {
        return radspurens;
    }

    public void addSled(float x, float y, float align) {
        radspurens.addRadspurenTank(x, y, align);
    }

    public GamePlayScreen getGps() {
        return gps;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void dispouseMap() {
        map.dispose();
    }


    static String MAP_DESETRT = "map/desert.tmx";

    public boolean inPointLocation(float x, float y) {
        if ((x < 0) || (y < 0)) return false;
        if ((x > WITH_LOCATION) || (y > HEIHT_LOCATION)) return false;
        return true;
    }

    public boolean inPointLocation(Vector2 point) {
        return inPointLocation(point.x, point.y);
    }


    private void createShape() { // создание поверхностей препядствий ))).

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(100, 500, 500, 500);
//        shapeRenderer.end();

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.BLUE);
//        shapeRenderer.ellipse(600, 300, 500, 500);
//        shapeRenderer.end();

    }

    public boolean checkMapBorders(float x, float y) {
        if (x > WITH_LOCATION) return false;
        if (x < 0) return false;
        if (y > HEIHT_LOCATION) return false;
        if (y < 0) return false;
        return true;
    }

    public boolean checkMapBorders(Vector2 p) {
        if (p.x > WITH_LOCATION) return false;
        if (p.x < 0) return false;
        if (p.y > HEIHT_LOCATION) return false;
        if (p.y < 0) return false;
        return true;
    }



}
