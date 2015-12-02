package logic;

import java.util.ArrayList;
import java.util.HashMap;

//These will be mapped to transition nodes somehow!!?
public class Building {
    private int id;
    private int floors; //number of floors

    private int universalA;
    private int universalB;
    private int localA;
    private int localB;


    //<Integer> maps; //Floor would be easier but easier to just do int for parsing
    private HashMap<Integer, Integer> floorMap; //int -> mapID

    ArrayList<String> names;

    public Building(int id, int floors){
        this.id = id;
        this.floors = floors;
        this.floorMap = new HashMap<>();
        this.names = new ArrayList<>();
    };

    public int getID() { return id; }
    public String getName(){ return this.names.get(0);}
    public ArrayList<String> getNames(){ return this.names;}

    public void   addName(String s){ this.names.add(s);}
    public HashMap<Integer, Integer> getFloorMap(){ //floor -> id map
        return floorMap;
    }

    public void addFloor(int floor, int mapid){
        floorMap.put(floor, mapid);
    }
    public void setNames(ArrayList<String> names){
        this.names = names;
    }

    public void setTranslateNodes(int a, int b, int c, int d){
        this.universalA = a;
        this.universalB = b;
        this.localA     = c;
        this.localB     = d;
    }

    public void  translateBuilding(HashMap<Integer, INode> nodes, HashMap<Integer,INode> master){
        Translate translate = new Translate(master.get(localA), master.get(localB), master.get(universalA), master.get(universalB));
        translate.setUniversalCoordinates(nodes);
    }


}
