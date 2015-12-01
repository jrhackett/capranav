package logic;

/**
 * Created by Xenocidist on 11/26/15.
 */
public class Campus extends Map {

    public Campus(int id, String path, double pixelToFeetRatio){
        super(id, path, pixelToFeetRatio);
    }

    public Campus(IMap map) {//this might need to be IMap
        super(map);
    }

    public int getBuildingID(){
        return 0;
    }

    public int getFloor(){
        return  0;
    }

    public boolean inside() { return false;}

}
