package logic;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import logic.Edge;
import logic.Node;

import logic.*;
import controller.*;
import images.*;
import MapBuilder.*;
import visuals.*;

/**
 * Created by Henry on 11/15/2015.
 */
public class EdgeTesting {
    Edge testEdge1;
    Edge testEdge2;
    Node node1;
    Node node2;
/*
    public EdgeTesting(){
        try{
            this.setUp();
        }catch(Exception e){

        }
    }
*/
    @Before
    public void setUp() throws Exception {
        node1 = new Node("X", 1, 0, 0, 0);
        node2 = new Node("Y", 2, 0, 0, 0);
        testEdge1 = new Edge(node1, 55);
        testEdge2 = new Edge(node2, 25);
    }

    @Test
    public void edgeWeightTest1(){
        assertEquals("getWeight returned incorrect value", 55, testEdge1.getWeight(), 0.01);
    }

    @Test
    public void edgeWeightTest2(){
        assertEquals("getWeight returned incorrect value", 25, testEdge2.getWeight(), 0.01);
    }

    @Test
    public void edgeTargetTest1(){
        assertEquals("getTarget returned incorrect value", node1, testEdge1.getTarget());
    }

    @Test
    public void edgeTargetTest2(){
        assertEquals("getTarget returned incorrect value", node2, testEdge2.getTarget());
    }

}
