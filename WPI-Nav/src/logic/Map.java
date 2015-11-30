package logic;

//basic map class
abstract public class Map implements IMap{
    private int ID;
    private String path;
    private double pixelToFeetRatio;


    public Map(int id, String path, double pixelToFeetRatio){
        this.ID = id;
        this.path = path;
        this.pixelToFeetRatio = pixelToFeetRatio;
    }

    public Map(IMap map) {
        this.ID = map.getID();
        this.path = map.getPath();
        this.pixelToFeetRatio = 0;
    }

    public Map(){}

    public int getID() {
        return ID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getPixelToFeetRatio() { return this.pixelToFeetRatio;}

}
