package logic;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import logic.Map;
/**
 * Created by Henry on 11/15/2015.
 */
public class MapTesting {
    Map newMap;
    Map copyMap;

    @Before
    public void setUp() throws Exception {
        newMap = new Floor(1, "dir/dir/dir", 22.0, 2, 1);
        copyMap = new Floor(newMap);
    }

    @Test
    public void setPathTest1() {
        copyMap.setPath("foo/foo/foo");
        assertEquals("getPath returned incorrect value from setPath", "foo/foo/foo", copyMap.getPath());
    }
}
