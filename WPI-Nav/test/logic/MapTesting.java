package logic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        copyMap.setFilePath("foo/foo/foo");
        assertEquals("getFilePath returned incorrect value from setFilePath", "foo/foo/foo", copyMap.getFilePath());
    }
}
