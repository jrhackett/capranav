package logic;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Henry on 11/15/2015.
 */
public class EdgeTesting {
    Edge e1;
    Edge e2;
    Interest n1;
    Interest n2;

    @Before
    public void setUp() throws Exception {
        n1 = new Food(0, 1, 2, 3, 4, 5, 6, "Generic Interest");
        n2 = new Food(1, 2, 3, 4, 5, 6, 7, 99, "Generic ID'd Interest");
        e1 = new Edge(n1.getID(), 55);
        e2 = new Edge(n2.getID(), 25);
    }

    @Test
    public void edgeWeightTest1(){
        assertEquals("getWeight returned incorrect value", 55, e1.getWeight(), 0.01);
    }

    @Test
    public void edgeWeightTest2(){
        assertEquals("getWeight returned incorrect value", 25, e2.getWeight(), 0.01);
    }

    @Test
    public void edgeTargetTest1(){
        assertEquals("getTarget returned incorrect value", n1.getID(), e1.getTarget());
    }

    @Test
    public void edgeTargetTest2(){
        assertEquals("getTarget returned incorrect value", n2.getID(), e2.getTarget());
    }

}
