package logic;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import logic.Node;

import java.util.ArrayList;

/**
 * Created by Henry on 11/15/2015.
 */
public class NodeTesting {
    Interest i1;
    Interest i2;
    Transition t1;
    Transition t2;
    Transition t1c;

    Edge e1;
    Edge e2;

    @Before
    public void setUp() throws Exception {
        i1 = new Interest(0, 1, 2, 3, 4, 5, 6, "Generic Interest");
        i2 = new Interest(1, 2, 3, 4, 5, 6, 7, 77, "Generic ID'd Interest");

        t1 = new Transition(1, 2, 3, 4, 5, 6, 7);
        t2 = new Transition(2, 3, 4, 5, 6, 7, 8, 77);

        t1c = new Transition(1, 2, 3, 4, 5, 6, 7);

        e1 = new Edge(0, 22.2);
        e2 = new Edge(1, 33.3);
    }

    @Test
    public void getScoresTest() throws Exception {
        i1.setF(4.4);
        i1.setG(5.5);
        assertEquals("getF returned incorrect value", 4.4, i1.getF(), 0.01);
        assertEquals("getG returned incorrect value", 5.5, i1.getG(), 0.01);
        assertEquals("getH returned incorrect value", 0.0, i1.getH(), 0.01);

    }

    @Test
    public void setParentTest() throws Exception {
        i2.setParent(t1);
        assertEquals("getParent returned incorrect value", t1, i2.getParent());
    }

    @Test
    public void getMapIdTest1() throws Exception {
        assertEquals("getMap_id returned incorrect value", 0, i1.getMap_id());
    }

    @Test
    public void getMapIdTest2() throws Exception {
        assertEquals("getMap_id returned incorrect value", 77, i2.getMap_id());
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("toString should return the node id as a string", "0\n", i1.toString());
    }

    @Test
    public void equalsTest() throws Exception{
        assertEquals("equals returned incorrect value", true, t1.equals(t1));
    }

    @Test
    public void equalsTest2() throws Exception{
        assertEquals("equals returned incorrect value", false, t1.equals(t2));

    }

    @Test
    public void equalsTest3() throws Exception{
        assertEquals("equals returned incorrect value", false, t1.equals(t1c));
    }

    @Test
    public void adjaceniesTest() throws Exception{
        ArrayList<Edge> ae = new ArrayList<Edge>();
        ae.add(e1);
        ae.add(e1);
        i1.setAdjacencies(ae);
        assertEquals("getAdjecencies did not return correct value", ae, i1.getAdjacencies());
    }
}
