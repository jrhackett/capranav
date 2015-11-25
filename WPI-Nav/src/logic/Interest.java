package logic;

import java.util.ArrayList;

/**
 * Created by jacobhackett on 11/24/15.
 */
public class Interest  extends Node{

    private ArrayList<String> name;

    public Interest(int id, double x, double y, double z, double x1, double y1, double z1, String name){
        super(id, x, y, z, x1, y1, z1);
        this.name.indexOf(name);
    }

    public Interest(int id, double x, double y, double z, double x1, double y1, double z1, int map_id, String name){
        super(id, x, y, z, x1, y1, z1, map_id);
        this.name.indexOf(name);
    }

    public boolean isInteresting() {
        return true;
    }
}
