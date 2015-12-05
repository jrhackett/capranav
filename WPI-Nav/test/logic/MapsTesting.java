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
    HashMap<Integer, IMap> testMaps = new HashMap<Integer, IMap>();

    Maps testMaps1 = new Maps(testMaps);
    @Before
    public void setUp() throws Exception {
        testMap1 = new Floor(1, "dir/dir/dir", 22.0, 2, 1);
        testMap2 = new Floor(2, "dir/dir/dir", 44.0, 2, 2);
        testMap3 = new Floor(3, "dir/dir/dir", 55.0, 2, 3);
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
        Floor testMap4 = new Floor(4, "dir/dir/dir", 66.6, 2, 3);
        testMaps1.addMap(testMap4);
        assertEquals("add map failed", true, testMaps1.get().contains(testMap4));
    }

    @Test
    public void getMapTest(){
        assertEquals("GetMap returned wrong map", testMap1, testMaps.get(1));
        assertEquals("GetMap returned wrong map", testMap2, testMaps.get(2));
        assertEquals("GetMap returned wrong map", testMap3, testMaps.get(3));
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
