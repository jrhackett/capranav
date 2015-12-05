package logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AStarTesting {

    Landmark t1, t2, t3, t4, t5;
    Landmark n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14;
    HashMap<Integer, INode> nodes, tnodes;

    @Before
    public void setUp() throws Exception {
        t1 = new Landmark(1, 0, 0, 0, 0, 0, 0, "Origin");
        t2 = new Landmark(2, 0, 0, 0, 5, 0, 0, "X");
        t3 = new Landmark(3, 0, 0, 0, 0, 5, 0, "Y");
        t4 = new Landmark(4, 0, 0, 0, 5, 5, 0, "Slope");
        t5 = new Landmark(5, 0, 0, 0, 5, 5, 5, "Z");

        n1 = new Landmark(5, 0, 0, 0, 0, 0, 0, "Institute");
        n2 = new Landmark(6, 0, 0, 0, 0, 10, 0, "RecCenter");
        n3 = new Landmark(7, 0, 0, 0, 0, 20, 0, "Field");
        n4 = new Landmark(8, 0, 0, 0, 3, 16, 0, "Harrington");
        n5 = new Landmark(9, 0, 0, 0, 5, 5, 0, "Quad");
        n6 = new Landmark(10, 0, 0, 0, 6, 1, 0, "Morgan");
        n7 = new Landmark(11, 0, 0, 0, 11, 2, 0, "Riley");
        n8 = new Landmark(12, 0, 0, 0, 10, 13, 0, "Higgins Labs");
        n9 = new Landmark(13, 0, 0, 0, 10, 20, 0, "Campus Center");
        n10 = new Landmark(14, 0, 0, 0, 16, 17, 0, "Fountain");
        n11 = new Landmark(15, 0, 0, 0, 16, 3, 0, "Alden");
        n12 = new Landmark(16, 0, 0, 0, 18, 8, 0, "West Street");
        n13 = new Landmark(17, 0, 0, 0, 20, 20, 0, "Library");




        // initialize the edges

        // Institute
        n1.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(n2.getID(), 1), new Edge(n5.getID(), 1))));

        // RecCenter
        n2.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n1.getID(), 1), new Edge(n4.getID(), 1))));

        // Field
        n3.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n4.getID(), 1))));

        // Harrington
        n4.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n3.getID(), 1), new Edge(n2.getID(), 1), new Edge(n5.getID(), 1))));

        // Quad
        n5.setAdjacencies( new ArrayList<Edge>(
                Arrays.asList(new Edge(n1.getID(), 1), new Edge(n4.getID(), 1), new Edge(n6.getID(), 1), new Edge(n8.getID(), 1))));

        // Morgan
        n6.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n5.getID(), 1), new Edge(n7.getID(), 1))));

        // Riley
        n7.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n6.getID(), 1), new Edge(n11.getID(), 1))));

        // Higgins Labs
        n8.setAdjacencies( new ArrayList<Edge>(
                Arrays.asList(new Edge(n5.getID(), 1), new Edge(n9.getID(), 1), new Edge(n10.getID(), 1), new Edge(n11.getID(), 1))));

        // Campus Center
        n9.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n8.getID(), 1))));

        // Fountain
        n10.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n8.getID(), 1), new Edge(n13.getID(), 1), new Edge(n12.getID(), 1))));

        // Alden
        n11.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n8.getID(), 1), new Edge(n12.getID(), 1), new Edge(n7.getID(), 1))));

        // West Street
        n12.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n11.getID(), 1), new Edge(n10.getID(), 1), new Edge(n13.getID(), 1))));

        // Library
        n13.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(n10.getID(), 1), new Edge(n12.getID(), 1))));

        nodes = new HashMap<>();
        nodes.put(n1.getID(), n1);
        nodes.put(n2.getID(), n2);
        nodes.put(n3.getID(), n3);
        nodes.put(n4.getID(), n4);
        nodes.put(n5.getID(), n5);
        nodes.put(n6.getID(), n6);
        nodes.put(n7.getID(), n7);
        nodes.put(n8.getID(), n8);
        nodes.put(n9.getID(), n9);
        nodes.put(n10.getID(), n10);
        nodes.put(n11.getID(), n11);
        nodes.put(n12.getID(), n12);
        nodes.put(n13.getID(), n13);
        // nodes.put(n14.getID(), n14);
        tnodes = new HashMap<>();
        tnodes.put(t1.getID(), t1);
        tnodes.put(t2.getID(), t2);
        tnodes.put(t3.getID(), t3);
        tnodes.put(t4.getID(), t4);
        tnodes.put(t5.getID(), t5);

    }


    // Testing~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Heuristics~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Test
    public void AStarHueristics1() {
        assertEquals("Tests heuristic", 5, AStarShortestPath.getHeuristic(t1, t2), 0);
    }

    @Test
    public void AStarHueristics2() {
        assertEquals("Tests heuristic", 5, AStarShortestPath.getHeuristic(t1, t3), 0);
    }

    @Test
    public void AStarHueristics3() {
        assertEquals("Tests heuristic", Math.sqrt(75), AStarShortestPath.getHeuristic(t1, t5), 0);
    }

    // Testing~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Cost~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test
    public void AStarCost1() {
        assertEquals("Tests Cost", 10, AStarShortestPath.getCost(t1, new Edge(t2.getID(), 2), tnodes), 0);
    }

    @Test
    public void AStarCost2() {
        assertEquals("Tests Cost", 50, AStarShortestPath.getCost(t1, new Edge(t3.getID(), 10), tnodes), 0);
    }

    @Test
    public void AStarCost3() {
        assertEquals("Tests Cost", 2 * Math.sqrt(75), AStarShortestPath.getCost(t5, new Edge(t1.getID(), 2), tnodes), 0);
    }

    // Testing~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // PrintPath~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // implemented in AStarSearch, no further testing needed
    @Test
    public void AStarPrintPathTest1() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n1, n5, n8, n10, n13));
        AStarShortestPath.AStarSearch(n1, n13, nodes);
        assertEquals("Tests AStar", path, AStarShortestPath.printPath(n13));
    }

    // Testing~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // AStar~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test
    public void AStarTest1() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n1, n5, n8, n10, n13));
        assertEquals("Tests AStar", path, AStarShortestPath.AStarSearch(n1, n13, nodes));
    }

    @Test
    public void AStarTest2() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n1));
        assertEquals("Tests AStar", path, AStarShortestPath.AStarSearch(n1, n1, nodes));
    }

    @Test
    public void AStarTest3() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n3, n4, n2, n1));
        assertEquals("Tests AStar", path, AStarShortestPath.AStarSearch(n3, n1, nodes));
    }

    @Test
    public void AStarTest4() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n7, n11));
        assertEquals("Tests AStar", path, AStarShortestPath.AStarSearch(n7, n11, nodes));
    }

    @Test
    public void AStarTest5() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n13, n10, n8, n5, n1));
        assertEquals("Tests AStar", path, AStarShortestPath.AStarSearch(n13, n1, nodes));
    }

    @Test
    public void AStarTest6() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n5, n1, n2));
        assertEquals("Tests AStar", path, AStarShortestPath.AStarSearch(n5, n2, nodes));
    }

    @Test
    public void AStarTest7() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n2, n1, n5));
        assertEquals("Tests AStar", path, AStarShortestPath.AStarSearch(n2, n5, nodes));
    }

    @Test
    public void AStarTest8() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n13, n10));
        assertEquals("Tests AStar", path, AStarShortestPath.AStarSearch(n13, n10, nodes));
    }


    @Test
    public void AStarTest9() {
        ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(n1, n5, n8, n10, n13));
        assertEquals("Tests AStar", path, AStarShortestPath.AStarSearch(n1, n13, nodes));
    }

}
