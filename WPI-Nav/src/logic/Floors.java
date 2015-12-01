package logic;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Xenocidist on 11/26/15.
 */

/**
 * TODO: this is for parsing! I copied Maps code and changed it to Floor
 */
public class Floors implements ICollection {
    HashMap<Integer, Floor> maps;

    public Floors() { this.maps = new HashMap<Integer, Floor>(); }

    public Floors(HashMap<Integer, Floor> maps) { this.maps = maps; }

    public HashMap<Integer, Floor> getMaps() {
        return this.maps;
    }

    public Collection<Floor> get() {
        return this.maps.values();
    }

    public void addMap(Floor map) {
        this.maps.put(map.getID(), map);
    }

    public Map getMap(int id) {return this.maps.get(id);}

    public boolean check(String path) {
        boolean check = true;
        try {
            for (Map m : this.maps.values()) {
                if (m.getPath().equals(path))
                    return false;
            }
            return check;
        } catch (NullPointerException e) {
            return true;
        }
    }

}
