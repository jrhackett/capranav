package logic;

/**
 * Created by jacobhackett on 11/24/15.
 */
public class Transition extends Node {

    public Transition(int id, double x, double y, double z, double x1, double y1, double z1){
        super(id, x, y, z, x1, y1, z1);
    }

    public Transition(int id, double x, double y, double z, double x1, double y1, double z1, int map_id){
        super(id, x, y, z, x1, y1, z1, map_id);
    }


    public boolean isTransition() {
        return true;
    }
}
