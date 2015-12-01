package logic;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by jacobhackett on 11/14/15.
 */
public class Maps {

    HashMap<Integer,IMap> maps;

    public Maps() { this.maps = new HashMap<Integer, IMap>(); }

    public Maps(HashMap<Integer, IMap> maps) { this.maps = maps; }

    public HashMap<Integer, IMap> getMaps() {
        return this.maps;
    }

    public Collection<IMap> get() {
        return this.maps.values();
    }

    public void addMap(IMap map) {
        this.maps.put(map.getID(), map);
    }

    public IMap getMap(int id) {return this.maps.get(id);}

    public boolean check(String path) {
        boolean check = true;
        try {
            for (IMap m : this.maps.values()) {
                if (m.getPath().equals(path))
                    return false;
            }
            return check;
        } catch (NullPointerException e) {
            return true;
        }
    }

}
