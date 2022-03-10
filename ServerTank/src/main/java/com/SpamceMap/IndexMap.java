package main.java.com.SpamceMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class IndexMap {
    private TiledMap map;

    private int width_map; // ширина карты
    private int height_map; // высота карты

    private ArrayList<Figure> allfigure; // набор колизий

    public IndexMap() {
        allfigure = new ArrayList<>();
        readFile("desert.json");

        JSONObject obj = new JSONObject(readFile("desert.json"));
        // System.out.println(obj);
        String firstName = String.valueOf(obj.getInt("height"));
        // System.out.println(firstName);

        width_map = obj.getInt("width") * obj.getInt("tilewidth"); // ширина карты
        height_map = obj.getInt("height") * obj.getInt("tilewidth");  // высота карты
//        System.out.println(width_map);

        // JSONArray arr = obj.getJSONArray("layers").getJSONObject(1).getJSONArray("objects");
        // JSONObject arr = obj.getJSONArray("layers").get
//        System.out.println("!!!!!!!!!!!!!!!!!");
//        System.out.println(obj.getJSONArray("layers").getJSONObject(2).getJSONArray("objects"));
//        System.out.println("!!!!!!!!!!!!!!!!!");

        JSONArray arr = obj.getJSONArray("layers").getJSONObject(2).getJSONArray("objects");

        for (int i = 0; i < arr.length(); i++) {
            ceateObjectmap((JSONObject) arr.get(i));
        }

    }

    ////////////////
    public void ceateObjectmap(JSONObject obj) {
        if (obj.optBoolean("ellipse")) createEllipse(obj);
        else createRectangle(obj);
    }


    private void createEllipse(JSONObject obj) {
        Ellipse e = new Ellipse(new Vector2(obj.getInt("x"), obj.getInt("y")), obj.getInt("width"), obj.getInt("height"));
        this.allfigure.add(e);
       // System.out.println("createEllipse position " + e.getPositionCenter()+"  redius " + e.getRadius() + "  redius2 " + e.getRadius2());
        //  System.out.println(this.allfigure);
    }

    private void createRectangle(JSONObject obj) {
        Rectangle r = new Rectangle(obj.getInt("x"), height_map - obj.getInt("y") , obj.getInt("width"), obj.getInt("height"), this.height_map);
        this.allfigure.add(r);
        // System.out.println(" Rectangle: position" + r.getPosition() + "  WH" + r.getPositionWH());

    }
//////////////

    public String readFile(String name) {

        try {
            FileInputStream fis = null;
            fis = new FileInputStream(name);
            byte[] buffer = new byte[10];
            StringBuilder sb = new StringBuilder();
            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));
                buffer = new byte[10];
            }
            fis.close();
            String content = sb.toString();
            return content;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "{}";
        } catch (IOException e) {
            e.printStackTrace();
            return "{}";
        }

    }


    public boolean isPointWithinMmap(Vector2 p) {
        return isPointWithinMmap(p.x, p.y);
    }

    public boolean isPointWithinMmap(float x, float y) {
        //System.out.println(" width_map" + width_map + "  height_map" + height_map);
        if (x > width_map - 7) return false;
        if (x < 7) return false;
        if (y > height_map - 7) return false;
        if (y < 7) return false;
        return true;
    }

    public boolean isPointInCollision(Vector2 p) {
        // System.out.println(isPointInCollision(p.x, p.y));
        return isPointInCollision(p.x, p.y);
    }

    public boolean isPointInCollision(float x, float y) {
        for (int i = 0; i < allfigure.size(); i++) {
            if (allfigure.get(i) instanceof Rectangle) {
                Rectangle r = (Rectangle) allfigure.get(i);

                /// true если касается
                if (r.isPointCollision((int) x, (int) y)) return true;
            }

            if (allfigure.get(i) instanceof Ellipse) {
                Ellipse e = (Ellipse) allfigure.get(i);
                if (e.isPointCollision((int) x, (int) y)) return true;
            }

        }
        return false;
    }


    public int getWidth_map() {
        return width_map;
    }

    public int getHeight_map() {
        return height_map;
    }
}
