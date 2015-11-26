package testing;

import logic.Campus;
import logic.IMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Created by Henry on 11/15/2015.
 */
public class MapTesting {
    IMap newMap;
    IMap copyMap;

    @Before
    public void setUp() throws Exception {
        newMap = new Campus(0, "dir/dir/dir", 2.0);
        copyMap = new Campus(newMap);
    }

    @Test
    public void setPathTest1() {
        copyMap.setPath("foo/foo/foo");
        assertEquals("getPath returned incorrect value from setPath", "foo/foo/foo", copyMap.getPath());
    }
}
