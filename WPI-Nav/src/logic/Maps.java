package logic;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by jacobhackett on 11/14/15.
 */
public class Maps implements ICollection {

    HashMap<Integer, Map> maps;

    public Maps() { this.maps = new HashMap<Integer, Map>(); }

    public Maps(HashMap<Integer, Map> maps) { this.maps = maps; }

    public HashMap<Integer, Map> getMaps() {
        return this.maps;
    }

    public Collection<Map> get() {
        return this.maps.values();
    }

    public void addMap(Map map) {
        this.maps.put(map.getID(), map);
    }

    public boolean check(String path) {
        boolean check = true;
        for(Map m : this.maps.values()) {
            if(m.getPath().equals(path))
                return false;
        }
        return check;
    }

}
