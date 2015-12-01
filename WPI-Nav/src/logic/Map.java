package logic;

//basic map class
abstract public class Map implements IMap{
    private int ID;
    private String filePath;
    private double pixelToFeetRatio;


    public Map(int id, String filePath, double pixelToFeetRatio){
        this.ID = id;
        this.filePath = filePath;
        this.pixelToFeetRatio = pixelToFeetRatio;
    }

    public Map(IMap map) {
        this.ID = map.getID();
        this.filePath = map.getFilePath();
        this.pixelToFeetRatio = 0;
    }

    public Map(){}

    public int getID() {
        return ID;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public double getPixelToFeetRatio() { return this.pixelToFeetRatio;}

}
