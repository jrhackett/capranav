package logic;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import logic.Node;
import logic.Graph;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Henry on 11/15/2015.
 */
public class GraphTesting {
    Graph testGraph1;
    Graph testGraph2;
    Graph freshGraph;
    HashMap<Integer, INode> hash1;
    LinkedList<Interest> nodes2 = new LinkedList<Interest>();
    Interest node1, node2, node3, node4;

    @Before
    public void setUp() throws Exception {
        node1 = new Interest(0, 1, 2, 3, 4, 5, 6, "Generic Interest");
        node2 = new Interest(1, 1, 2, 3, 4, 5, 6, 99, "Generic ID'd Interest");
        node3 = new Interest(2, 1, 2, 3, 4, 5, 6, "Generic Interest 2");
        node4 = new Interest(3, 1, 2, 3, 4, 5, 6, 99, "Generic ID'd Interest 2");

        hash1 = new HashMap<Integer, INode>();
        hash1.put(1, node1);
        hash1.put(2, node2);
        hash1.put(3, node3);
        hash1.put(4, node4);

        nodes2.add(node1);
        nodes2.add(node2);
        nodes2.add(node3);
        nodes2.add(node4);

        freshGraph = new Graph();
        testGraph1 = new Graph(hash1);
        testGraph2 = new Graph();
    }

    @Test
    public void toStringTest1(){
        assertEquals("toString on an empty graph returned incorrect value", "", freshGraph.toString());
    }

    @Test
    public void toStringTest2(){
        String str = "0\n 1\n 2\n 3\n ";
        assertEquals("toString on a non empty graph returned incorrect value", str, testGraph1.toString());
    }

    @Test
    public void getNodesTest1(){
        assertEquals("getNodes on an empty graph returned incorrect value", new HashMap<Integer, Node>(), freshGraph.getNodes());
    }

    @Test
    public void getNodesTest2(){
        assertEquals("getNodes on a non empty graph returned incorrect value", hash1, testGraph1.getNodes());
    }

    @Test
    public void getTest2(){
        assertEquals("get on a non empty graph returned incorrect value", nodes2, testGraph1.get());
    }

}
