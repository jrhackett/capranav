package logic;
import static org.junit.Assert.assertEquals;

import logic.Graph;
import logic.Node;
import logic.Parser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import logic.*;


/**
 * Created by Henry on 11/15/2015.
 */
public class ParserTesting {
    Parser testParser1;
    Parser testParser2;
    Node node1, node2;
    Graph graph;
    HashMap<Integer, Node> nodes;

    @Before
    public void setUp() throws Exception {
        testParser1 = new Parser("nodes.json");
        testParser2 = new Parser("nodes.json");

        nodes = new HashMap<Integer, Node>();
        node1 = new Node("one", 1, 1, 1, 1, 1);
        node2 = new Node("two", 2, 2, 2, 2, 2);

        nodes.put(0, node1);
        nodes.put(1, node2);

        graph = new Graph(nodes);
    }
/*
    @Test
    public void fromFileTest1() throws Exception{
        testParser1.toFile(graph);
        assertEquals("fromFile did not return a graph of nodes", new Graph(nodes), testParser1.fromFile());
    }

    @Test
    public void toFileTest1() throws Exception {
        testParser2.toFile(graph);
        assertEquals("toFile did not output the proper graph", new Graph(nodes), testParser2.fromFile());
    }
    */
}
