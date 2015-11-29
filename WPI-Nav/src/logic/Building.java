package logic;

import java.util.ArrayList;
import java.util.HashMap;

//These will be mapped to transition nodes somehow!!?
public class Building {
    int id;
    int floors; //number of floors
    //<Integer> maps; //Floor would be easier but easier to just do int for parsing
    private HashMap<Integer, Integer> floorMap; //int -> mapID

    ArrayList<String> names;



    public Building(int id, int floors){
        this.id = id;
        this.floors = floors;
    };

    public String getName(){ return this.names.get(0);}
    public ArrayList<String> getNames(){ return this.names;}

    public void   addName(String s){ this.names.add(s);}
    public HashMap<Integer, Integer> getFloorMap(){
        return floorMap;
    }
}
