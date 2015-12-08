package logic;

public class Floor extends Map {
    private int floor;
    private int buildingID;

    public Floor(int id, String path, double pixelToFeetRatio, int buildingID, int floor){
        super(id, path, pixelToFeetRatio);
        this.floor = floor;
        this.buildingID = buildingID;

    }

    public Floor(IMap map) {//this might need to be IMap
        super(map);
    }

    public Floor(){}

    public String getFloorName(){
        if (floor == 0){
            return "the basement";
        } else if (floor == -1){
            return "the sub-basement";
        } else return "floor" + Integer.toString(floor);
    }
    public int getFloor(){ return this.floor; }
    public void setFloor(int floor){  this.floor = floor; }
    public boolean inside() { return  true;}
    public int getBuildingID() {
        return buildingID;
    }
    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }
}
