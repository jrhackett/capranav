package logic;


import org.junit.Before;
import org.junit.Test;
import visuals.Instructions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by henry on 12/1/2015.
 */
public class DirectionsTesting {

    Landmark nA, nB, nC, nD, nE, nF, nG, nH, nI, nZ;

    Campus camp;
    Floor floo;
    HashMap<Integer, INode> nodes;
    HashMap<Integer, IMap> mmap, nmap;
    HashMap<Integer, Building> build;

    @Before
    public void setUp() throws Exception{
        camp = new Campus(1, "dir/dir/dir", 1.0);
        floo = new Floor(2, "dir/dir/dir", 2.0, 2, 2);
        mmap = new HashMap<Integer, IMap>();
        nmap = new HashMap<Integer, IMap>();
        build = new HashMap<Integer, Building>();

        mmap.put(camp.getID(), camp);

        nmap.put(camp.getID(), floo);

        // Nodes are set up like this:
        // A - B . C
        // | . | \ |
        // D . E - F
        // . / | . |
        // G . H - I
        // . . . . |
        // . . . . Z

        nA = new Landmark(1, 0, 0, 0, 0, 0, 0, 1, "A");
        nB = new Landmark(2, 100, 0, 0, 100, 0, 0, 1, "B");
        nC = new Landmark(3, 200, 0, 0, 200, 0, 0, 1, "C");
        nD = new Landmark(4, 0, 100, 0, 0, 100, 0, 1, "D");
        nE = new Landmark(5, 100, 100, 0, 100, 100, 0, 1, "E");
        nF = new Landmark(6, 200, 100, 0, 200, 100, 0, 1, "F");
        nG = new Landmark(7, 0, 200, 0, 0, 200, 0, 1, "G");
        nH = new Landmark(8, 100, 200, 0, 100, 200, 0, 2, "H");
        nI = new Landmark(9, 200, 200, 0, 200, 200, 0, 1, "I");
        nZ = new Landmark(10,200, 300, 0, 200, 300, 0, 1, "Z");

        nA.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(nB.getID(), 1), new Edge(nD.getID(), 1))));
        nB.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(nA.getID(), 1), new Edge(nE.getID(), 1), new Edge(nF.getID(), 1))));
        nC.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(nF.getID(), 1))));
        nD.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(nA.getID(), 1))));
        nE.setAdjacencies(
                new ArrayList<Edge>(Arrays.asList(new Edge(nB.getID(), 1), new Edge(nF.getID(), 1), new Edge(nG.getID(), 1), new Edge(nH.getID(), 1))));
        nF.setAdjacencies(
                new ArrayList<Edge>(Arrays.asList(new Edge(nB.getID(), 1), new Edge(nC.getID(), 1), new Edge(nE.getID(), 1), new Edge(nI.getID(), 1))));
        nG.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(nE.getID(), 1))));
        nH.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(nE.getID(), 1), new Edge(nI.getID(), 1))));
        nI.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(nF.getID(), 1), new Edge(nH.getID(), 1), new Edge(nZ.getID(), 1))));
        nZ.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(nI.getID(), 1))));

        nodes = new HashMap<>();
        nodes.put(nA.getID(), nA);
        nodes.put(nB.getID(), nB);
        nodes.put(nC.getID(), nC);
        nodes.put(nD.getID(), nD);
        nodes.put(nE.getID(), nE);
        nodes.put(nF.getID(), nF);
        nodes.put(nG.getID(), nG);
        nodes.put(nH.getID(), nH);
        nodes.put(nI.getID(), nI);
        nodes.put(nZ.getID(), nZ);
    }

    @Test
    public void stepByStepTesting(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nA, nB, nodes);
        ArrayList<ArrayList<Instructions>> aI = Directions.stepByStep(As, mmap, build);
        ArrayList<ArrayList<Instructions>> act = new ArrayList<ArrayList<Instructions>>();
        act.add(new ArrayList<Instructions>());
        act.get(0).add(new Instructions("Face East, and walk 100 feet.", As.get(0)));
        act.get(0).add(new Instructions("You have reached your destination", As.get(1)));
        assertEquals("stepByStep did not output correct output", act.get(0).get(0).getInstruction_string(), aI.get(0).get(0).getInstruction_string());
    }

    @Test
    public void getTotalDistance(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nA, nB, nodes);
        Directions d = new Directions();
        d.stepByStep(As, mmap, build);
        assertEquals("Total distance is not properly calculated", 100,  d.getTotalDistance(), 0.05);

    }

    @Test
    public void getTotalDistance2(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nA, nE, nodes);
        Directions d = new Directions();
        d.stepByStep(As, mmap, build);
        assertEquals("Total distance is not properly calculated", 200,  d.getTotalDistance(), 0.05);

    }

    @Test
    public void getTotalDistance3(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nA, nI, nodes);
        Directions d = new Directions();
        d.stepByStep(As, mmap, build);
        assertEquals("Total distance is not properly calculated", 341,  d.getTotalDistance(), 0.5);

    }

    @Test
    public void getTotalDistance4(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nA, nG, nodes);
        Directions d = new Directions();
        d.stepByStep(As, mmap, build);
        assertEquals("Total distance is not properly calculated", 341,  d.getTotalDistance(), 0.5);

    }

    @Test
    public void getTotalDistance5(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nA, nB, nodes);
        Directions d = new Directions();
        d.stepByStep(As, nmap, build);
        assertEquals("Total distance is not properly calculated", 100,  d.getTotalDistance(), 0.05);

    }
    @Test
    public void stepByStepTesting2(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nC, nI, nodes);
        Directions d = new Directions();
        ArrayList<ArrayList<Instructions>> aI = d.stepByStep(As, mmap, build);
        Instructions i1 = new Instructions("Face South, and walk 200 feet.", As.get(0));
        Instructions i3 = new Instructions("You have reached your destination.", As.get(1));
        assertEquals("stepByStep did not output correct output", i1.getInstruction_string(), aI.get(0).get(0).getInstruction_string());
        assertEquals("stepByStep did not output correct output", i3.getInstruction_string(), aI.get(0).get(1).getInstruction_string());
    }

    @Test
    public void stepByStepTesting3(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nC, nZ, nodes);
        Directions d = new Directions();
        ArrayList<ArrayList<Instructions>> aI = d.stepByStep(As, mmap, build);
        Instructions i1 = new Instructions("Face South, and walk 300 feet.", As.get(0));
        Instructions i3 = new Instructions("You have reached your destination.", As.get(1));
        assertEquals("stepByStep did not output correct output", i1.getInstruction_string(), aI.get(0).get(0).getInstruction_string());
        assertEquals("stepByStep did not output correct output", i3.getInstruction_string(), aI.get(0).get(1).getInstruction_string());

    }

    @Test
    public void stepByStepTesting4(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nA, nE, nodes);
        Directions d = new Directions();
        ArrayList<ArrayList<Instructions>> aI = d.stepByStep(As, mmap, build);
        Instructions i1 = new Instructions("Face East, and walk 100 feet.", As.get(0));
        Instructions i2 = new Instructions("Make a right at B, and walk 100 feet.", As.get(1));
        Instructions i3 = new Instructions("You have reached your destination.", As.get(2));
        assertEquals("stepByStep did not output correct output", i1.getInstruction_string(), aI.get(0).get(0).getInstruction_string());
        assertEquals("stepByStep did not output correct output", i2.getInstruction_string(), aI.get(0).get(1).getInstruction_string());
        assertEquals("stepByStep did not output correct output", i3.getInstruction_string(), aI.get(0).get(2).getInstruction_string());
    }

    @Test
    public void stepByStepTesting5(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nD, nG, nodes);
        Directions d = new Directions();
        ArrayList<ArrayList<Instructions>> aI = d.stepByStep(As, mmap, build);

        Instructions i1 = new Instructions("Face North, and walk 100 feet.", As.get(0));
        Instructions i2 = new Instructions("Turn hard right at A, and walk 200 feet.", As.get(1));
        Instructions i2b = new Instructions("Turn hard right at B, and walk 200 feet.", As.get(1));
        Instructions i2c = new Instructions("Turn right at E, and walk 283 feet.", As.get(1));

        Instructions i3 = new Instructions("You have reached your destination.", As.get(2));


        assertEquals("stepByStep did not output correct output", i1.getInstruction_string(), aI.get(0).get(0).getInstruction_string());
        assertEquals("stepByStep did not output correct output", i2.getInstruction_string(), aI.get(0).get(1).getInstruction_string());
        assertEquals("stepByStep did not output correct output", i2b.getInstruction_string(), aI.get(0).get(2).getInstruction_string());
        assertEquals("stepByStep did not output correct output", i2c.getInstruction_string(), aI.get(0).get(3).getInstruction_string());

        assertEquals("stepByStep did not output correct output", i3.getInstruction_string(), aI.get(0).get(4).getInstruction_string());
    }

    @Test
    public void getTotalDistanceTesting2(){
        ArrayList<INode> As = AStarShortestPath.AStarSearch(nA, nA, nodes);
        Directions d = new Directions();
        assertEquals("Total distance not calculated correctly", 0, d.getTotalDistance(), 0.5);
    }
}
