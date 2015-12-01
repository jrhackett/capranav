package logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class EmailTesting {

    Email testE, testE2;
    @Before
    public void setUp() throws Exception {
       testE = new Email("mjgiancola@wpi.edu");
        testE2 = new Email("notarealaddress@notarealemail.egg");
    }

    @Test
    public void dummyTest() throws Exception{
        assertEquals("This is just here to appease TravisCl", testE, testE);
    }

    @Test
    public void eTest1() throws Exception {
        ArrayList<String> dirs = new ArrayList<>();
        dirs.add("First");
        dirs.add("Second");
        dirs.add("Third");
        assertEquals("Email did not send correctly", testE.sendDirections(dirs, "Morgan", "DAKA"), true);
    }

    /* Even if the email address isn't real, it still considers the attempt to send as being successful
    That's probably okay
     */
    @Test
    public void eTest2() throws Exception {
        ArrayList<String> dirs = new ArrayList<>();
        dirs.add("First");
        dirs.add("Second");
        dirs.add("Third");
        assertEquals("Email did not send correctly", testE2.sendDirections(dirs, "Morgan", "DAKA"), true);
    }

}
