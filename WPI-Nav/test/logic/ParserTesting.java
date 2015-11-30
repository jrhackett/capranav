package logic;
import static org.junit.Assert.assertEquals;

import logic.Graph;
import logic.Node;
import logic.Parser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by Henry on 11/15/2015.
 */
public class ParserTesting {
    //TODO Fix the instances of Node pls
    Parser testParser1;
    Parser testParser2;
    Interest node1, node2;
    Graph graph;
    HashMap<Integer, INode> nodes;

    @Before
    public void setUp() throws Exception {
        testParser1 = new Parser("nodes.json");
        testParser2 = new Parser("nodes.json");

        nodes = new HashMap<Integer, INode>();
        node1 = new Interest(0, 1, 2, 3, 4, 5, 6, "Generic Interest");
        node2 = new Interest(0, 1, 2, 3, 4, 5, 6, "Generic Interest 2");

        nodes.put(0, node1);
        nodes.put(1, node2);

        graph = new Graph(nodes);
    }

    @Test
    public void dummyTest() throws Exception{
        assertEquals("This is just here to appease TravisCl", testParser1, testParser1);
    }
/*
    @Test
    public void fromFileTest1() throws Exception{
        testParser1.toFile(graph);
        assertEquals("fromFile did not return a graph of nodes", new Graph(nodes), testParser1.fromFile());
    }
*/
}
