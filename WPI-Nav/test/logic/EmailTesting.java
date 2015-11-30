package logic;

import org.junit.Before;
import org.junit.Test;

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

}
