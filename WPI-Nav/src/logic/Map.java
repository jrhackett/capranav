package logic;

import java.util.ArrayList;

//basic map class
public class Map {
    private int ID;
    private double WIDTH;
    private double HEIGHT;
   // private String name;
    private String path;
    private double pixelToFeetRatio;
    private ArrayList<String> names;
    private int floor;

    public Map(int id, double width, double height, String name, String path, double pixelToFeetRatio){
        this.ID = id;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.names.add(0, name);
        this.path = path;
        this.pixelToFeetRatio = pixelToFeetRatio;
    }

    public Map(Map map) {
        this.ID = map.getID();
        this.WIDTH = map.getWIDTH();
        this.HEIGHT = map.getHEIGHT();
        this.names.add(0, map.getName());
        this.path = map.getPath();
        //TODO: fix this
        this.pixelToFeetRatio = 0;
    }

    public Map(){}

    public String toString() { return names.get(0); }

    public double getWIDTH() {
        return WIDTH;
    }

    public String getName() { return names.get(0);}

    public ArrayList<String> getNames() { return names;}

    public void addNames(ArrayList<String> arrayList){ names.addAll(arrayList); }

    public int getID() {
        return ID;
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getPixelToFeetRatio() { return this.pixelToFeetRatio;}

}
