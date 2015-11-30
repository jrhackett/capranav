package logic;

/**
 * Created by Xenocidist on 11/26/15.
 */
public class Campus extends Map {

    private int buildingID;

    public Campus(int id, String path, double pixelToFeetRatio){
        super(id, path, pixelToFeetRatio);
    }

    public Campus(IMap map) {//this might need to be IMap
        super(map);
    }

    public Campus(){}


    public boolean inside() { return false;}


}
