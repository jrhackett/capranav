package logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class EmailTesting {

    Email testE, testE2;
    @Before
    public void setUp() throws Exception {
       testE = new Email("team9.cs3733@gmail.com");
       testE2 = new Email("notarealaddress@notarealemail.egg");
    }

    @Test
    public void eTest1() throws Exception {
        ArrayList<String> dirs = new ArrayList<>();
        dirs.add("First");
        dirs.add("Second");
        dirs.add("Third");
        assertEquals("Email sent successfully", testE.sendDirections(dirs, "Morgan", "DAKA"), true);
    }

    @Test
    public void eTest2() throws Exception {
        ArrayList<String> dirs = new ArrayList<>();
        dirs.add("First");
        dirs.add("Second");
        dirs.add("Third");
        assertEquals("Email address was invalid - sending fails", testE2.sendDirections(dirs, "Morgan", "DAKA"), false);
    }
}
