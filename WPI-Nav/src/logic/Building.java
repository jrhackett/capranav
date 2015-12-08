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

    ArrayList<String> names; //the names of the building

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
    public int    getFloorID(int i){
        return floorMap.get(i);
    }

    public boolean containsFloor(int floor){
        floorMap.forEach((k,v) -> {
            System.out.println(k);
            System.out.println(v);

        });
        return this.floorMap.containsKey(floor);
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

    public HashMap<Integer, INode>  translateBuilding(HashMap<Integer, INode> nodes, HashMap<Integer,INode> master){

        for (Edge e : master.get(localA).getAdjacencies()){
            if (master.get(e.getTarget()).getMap_id() != master.get(localA).getMap_id()){
                universalA = master.get(e.getTarget()).getID();
            }
        }

        for (Edge e : master.get(localB).getAdjacencies()){
            if (master.get(e.getTarget()).getMap_id() != master.get(localB).getMap_id()){
                universalB = master.get(e.getTarget()).getID();
            }
        }

        Translate translate = new Translate(master.get(localA), master.get(localB), master.get(universalA), master.get(universalB));
        return translate.setUniversalCoordinates(nodes);
    }
}
