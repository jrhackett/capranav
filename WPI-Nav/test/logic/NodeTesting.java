package logic;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import logic.Node;

import logic.*;
import controller.*;
import images.*;
import MapBuilder.*;
import visuals.*;

/**
 * Created by Henry on 11/15/2015.
 */
public class NodeTesting {
    Node testNode1, testNode2, testNode3, testNode1Copy;

    @Before
    public void setUp() throws Exception {
        testNode1 = new Node("Test Node 1", 1, 22.2, 33.3, 44.4);
        testNode2 = new Node("Test Node 2", 2, 33.3, 44.4, 55.5, 77);
        testNode3 = new Node("Test Node 3", 3, 44.4, 55.5, 66.6, 88);
        testNode1Copy = new Node("Test Node 1", 1, 22.2, 33.3, 44.4);
    }

    @Test
    public void getScoresTest() throws Exception {
        testNode1.setF(4.4);
        testNode1.setG(5.5);
        assertEquals("getF returned incorrect value", 4.4, testNode1.getF(), 0.01);
        assertEquals("getG returned incorrect value", 5.5, testNode1.getG(), 0.01);
        assertEquals("getH returned incorrect value", 0.0, testNode1.getH(), 0.01);

    }

    @Test
    public void setParentTest() throws Exception {
        testNode2.setParent(testNode3);
        assertEquals("getParent returned incorrect value", testNode3, testNode2.getParent());
    }

    @Test
    public void getMapIdTest1() throws Exception {
        assertEquals("getMap_id returned incorrect value", 0, testNode1.getMap_id());
    }

    @Test
    public void getMapIdTest2() throws Exception {
        assertEquals("getMap_id returned incorrect value", 77, testNode2.getMap_id());
    }

    @Test
    public void toStringTest() throws Exception {
        assertEquals("toString and getName should return the same thing", testNode3.getName(), testNode3.toString());
    }

    @Test
    public void hashCodeTest() throws Exception {
        assertEquals("hashCode returned incorrect value", 580291872, testNode3.hashCode());
    }

    @Test
    public void equalsTest() throws Exception{
        assertEquals("equals returned incorrect value", true, testNode1.equals(testNode1));
    }

    @Test
    public void equalsTest2() throws Exception{
        assertEquals("equals returned incorrect value", false, testNode1.equals(testNode2));

    }

    @Test
    public void equalsTest3() throws Exception{
        assertEquals("equals returned incorrect value", true, testNode1.equals(testNode1Copy));
    }

}
