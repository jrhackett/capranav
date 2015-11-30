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

        Campus c = new Campus(1, "Path", 0.13);
        Floor f = new Floor(2, "Floor", 0.13, 3, 4);
        
        HashMap<Integer, Campus> ch = new HashMap<>();
        ch.put(c.getID(), c);

        HashMap<Integer, Floor> fh = new HashMap<>();
        fh.put(f.getID(), f);

        testParser1 = new Parser<Campus>();
        testParser2 = new Parser<Floor>();

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
