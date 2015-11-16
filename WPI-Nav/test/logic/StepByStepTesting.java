package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class StepByStepTesting {
	// This initializes the 9 nodes
	Node nA, nB, nC, nD, nE, nF, nG, nH, nI;
	
	// This sets up all 9 nodes with edges
	@Before
	public void setUp(){
		// Nodes are set up like this:
		//  A - B   C
		//  |   | \ |
		//  D   E - F
		//    / |   |
		//  G   H - I
		
		nA = new Node("A", 1, 0, 0, 0);
		nB = new Node("B", 2, 1, 0, 0);
		nC = new Node("C", 3, 2, 0, 0);
		nD = new Node("D", 4, 0, 1, 0);
		nE = new Node("E", 5, 1, 1, 0);
		nF = new Node("F", 6, 2, 1, 0);
		nG = new Node("G", 7, 0, 2, 0);
		nH = new Node("H", 8, 1, 2, 0);
		nI = new Node("I", 9, 2, 2, 0);
		
		nA.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(nB, 1), new Edge(nD, 1)));
		nB.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(nA, 1), new Edge(nE, 1), new Edge(nF, 1)));
		nC.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(nF, 1)));
		nD.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(nA, 1)));
		nE.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(nB, 1), new Edge(nF, 1), new Edge(nG, 1), new Edge(nH, 1)));
		nF.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(nB, 1), new Edge(nC, 1), new Edge(nE, 1), new Edge(nI, 1)));
		nG.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(nE, 1)));
		nH.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(nE, 1), new Edge(nI, 1)));
		nI.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(nF, 1), new Edge(nH, 1)));
	}

	
	// These test cases check that the directions return the correct number of strings
	
	@Test
	public void DirectionsTestLengthOfSteps1() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 1 for 2 node path", path.size() - 1, steps.size());
	}
	
	@Test
	public void DirectionsTestLengthOfSteps2() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 2 for 3 node path", path.size() - 1, steps.size());
	}
	
	@Test
	public void DirectionsTestLengthOfSteps3() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 3 for 4 node path", path.size() - 1, steps.size());
	}
	
	@Test
	public void DirectionsTestLengthOfSteps4() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 4 for 5 node path", path.size() - 1, steps.size());
	}
	
	@Test
	public void DirectionsTestLengthOfSteps5() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI, nH));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 5 for 6 node path", path.size() - 1, steps.size());
	}
	
	@Test
	public void DirectionsTestLengthOfSteps6() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI, nH, nE));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 6 for 7 node path", path.size() - 1, steps.size());
	}
	
	@Test
	public void DirectionsTestLengthOfSteps7() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI, nH, nE, nG));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 7 for 8 node path", path.size() - 1, steps.size());
	}
	
	// These test cases test that the string returns the right strings
	
	@Test
	public void CorrectStringsTest1() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI, nH, nE, nG));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Message from Node 1 to Node 2", "Face 270, and walk 1 feet.", steps.get(0));
		assertEquals("Message from Node 2 to Node 3", "Turn 90, and walk 1 feet.", steps.get(1));
		assertEquals("Message from Node 3 to Node 4", "Turn 45, and walk 1 feet.", steps.get(2));
		assertEquals("Message from Node 4 to Node 5", "Turn 45, and walk 1 feet.", steps.get(3));
		assertEquals("Message from Node 5 to Node 6", "Turn 90, and walk 1 feet.", steps.get(4));
		assertEquals("Message from Node 6 to Node 7", "Turn 90, and walk 1 feet.", steps.get(5));
	}
	

}
	