package logic;

import java.util.ArrayList;

public class Building {
    int id;
    int floors;
    ArrayList<Integer> maps; //Floor would be easier but easier to just do int for parsing
    ArrayList<String> names;

    public Building(int id, int floors){
        this.id = id;
        this.floors = floors;
    };

    public int getID() { return id; }
    public String getName(){ return this.names.get(0);}
    public ArrayList<String> getNames(){ return this.names;}

    public void   addName(String s){ this.names.add(s);}
}
