package logic;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import logic.Map;

import logic.*;

/**
 * Created by Henry on 11/15/2015.
 */
public class MapTesting {
    Map newMap;
    Map copyMap;

    @Before
    public void setUp() throws Exception {
        newMap = new Map(1, 22.5, 21.5, "New Map", "dir/dir/dir", 2.0);
        copyMap = new Map(newMap);
    }

    @Test
    public void setPathTest1() {
        copyMap.setPath("foo/foo/foo");
        assertEquals("getPath returned incorrect value from setPath", "foo/foo/foo", copyMap.getPath());
    }
}
