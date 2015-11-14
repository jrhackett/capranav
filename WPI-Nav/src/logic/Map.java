package logic;

//basic map class
public class Map {
    private int ID;
    private double WIDTH;
    private double HEIGHT;
    private String name;
    private String path;
    private double pixelToFeetRatio;

    public Map(int id, double width, double height, String name, String path, double pixelToFeetRatio){
        this.ID = id;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.name = name;
        this.path = path;
        this.pixelToFeetRatio = pixelToFeetRatio;
    }

    public String toString() { return name; }

    public double getWIDTH() {
        return WIDTH;
    }

    public String getName() {
        return name;
    }

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

}
