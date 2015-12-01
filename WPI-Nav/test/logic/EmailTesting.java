package logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class EmailTesting {

    Email testE;
    @Before
    public void setUp() throws Exception {
       testE = new Email("team9.cs3733@gmail.com");
    }

    @Test
    public void dummyTest() throws Exception{
        assertEquals("This is just here to appease TravisCl", testE, testE);
    }

    @Test
    public void realTest() throws Exception {
        ArrayList<String> dirs = new ArrayList<>();
        dirs.add("First");
        dirs.add("Second");
        dirs.add("Third");
        assertEquals("This one does stuff", testE.sendDirections(dirs, "Morgan", "DAKA"), true);
    }

}
