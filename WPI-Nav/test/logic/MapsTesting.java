package logic;
import static org.junit.Assert.assertEquals;

import logic.Maps;
import org.junit.Before;
import org.junit.Test;
import logic.Map;

import java.util.HashMap;

/**
 * Created by Henry on 11/15/2015.
 */
public class MapsTesting {
    Map testMap1;
    Map testMap2;
    Map testMap3;
    HashMap<Integer, Map> testMaps = new HashMap<Integer, Map>();

    Maps testMaps1 = new Maps(testMaps);
    @Before
    public void setUp() throws Exception {
        testMap1 = new Map(1, 22.5, 21.5, "New Map 1", "dir/dir/dir", 2.0);
        testMap2 = new Map(2, 22.5, 21.5, "New Map 2 ", "dir/dir/dir", 3.0);
        testMap3 = new Map(3, 22.5, 21.5, "New Map 3", "dir/dir/dir", 4.0);
        testMaps.put(1, testMap1);
        testMaps.put(2, testMap2);
        testMaps.put(3, testMap3);
    }

    @Test
    public void getTest1() {
        assertEquals("get returned incorrect value", testMaps.values(), testMaps1.get());
    }

    @Test
    public void addMapTest1(){
        Map testMap4 = new Map(4, 4.4, 4.4, "New Map 4", "dir/dir/dir", 4.4);
        testMaps1.addMap(testMap4);
        assertEquals("add map failed", true, testMaps1.get().contains(testMap4));
    }

    @Test
    public void checkTest1(){
        assertEquals("check test failed", false, testMaps1.check("dir/dir/dir"));
    }

    @Test
    public void checkTest2(){
        assertEquals("check test failed", true, testMaps1.check("fir/fir/fir"));
    }
}
