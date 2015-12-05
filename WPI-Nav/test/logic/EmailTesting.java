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
       testE2 = new Email("%%%%%%");
    }

    @Test
    public void eTest1() throws Exception {
        ArrayList<String> dirs = new ArrayList<>();
        dirs.add("First");
        dirs.add("Second");
        dirs.add("Third");
        assertEquals("Email did not send!", true, testE.sendDirections(dirs, "Morgan", "DAKA"));
    }

    @Test
    public void eTest2() throws Exception {
        ArrayList<String> dirs = new ArrayList<>();
        dirs.add("First");
        dirs.add("Second");
        dirs.add("Third");

        assertEquals("Email sent when it shouldn't have!", false, testE2.sendDirections(dirs, "Morgan", "DAKA"));

    }
}
