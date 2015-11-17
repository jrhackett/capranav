
package logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StepByStepTesting {
	// This initializes the 9 nodes
	Node nA, nB, nC, nD, nE, nF, nG, nH, nI;

	// This sets up all 9 nodes with edges
	@Before
	public void setUp() {
		// Nodes are set up like this:
		// A - B . C
		// | . | \ |
		// D . E - F
		// . / | . |
		// G . H - I

		nA = new Node("A", 1, 0, 0, 0);
		nB = new Node("B", 2, 100, 0, 0);
		nC = new Node("C", 3, 200, 0, 0);
		nD = new Node("D", 4, 0, 100, 0);
		nE = new Node("E", 5, 100, 100, 0);
		nF = new Node("F", 6, 200, 100, 0);
		nG = new Node("G", 7, 0, 200, 0);
		nH = new Node("H", 8, 100, 200, 0);
		nI = new Node("I", 9, 200, 200, 0);

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
		nI.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(nF.getID(), 1), new Edge(nH.getID(), 1))));
	}

	// These test cases check that the directions return the correct number of
	// strings

	@Test
	public void DirectionsTestLengthOfSteps1() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 1 for 2 node path", path.size(), steps.size());
	}

	@Test
	public void DirectionsTestLengthOfSteps2() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 2 for 3 node path", path.size(), steps.size());
	}

	@Test
	public void DirectionsTestLengthOfSteps3() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 3 for 4 node path", path.size(), steps.size());
	}

	@Test
	public void DirectionsTestLengthOfSteps4() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 4 for 5 node path", path.size(), steps.size());
	}

	@Test
	public void DirectionsTestLengthOfSteps5() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI, nH));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 5 for 6 node path", path.size(), steps.size());
	}

	@Test
	public void DirectionsTestLengthOfSteps6() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI, nH, nE));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 6 for 7 node path", path.size(), steps.size());
	}

	@Test
	public void DirectionsTestLengthOfSteps7() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI, nH, nE, nG));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Test length is 7 for 8 node path", path.size(), steps.size());
	}

	// These test cases test whether the first string has the correct initial
	// terms:
	// "Face " + either "East", "South-East", "South", "South-West", "West",
	// "North-West", "North", or "North-East"

	// This test looks for East
	@Test
	public void FirstStringTest1() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nE, nF));
		ArrayList<String> steps = Directions.stepByStep(path);
		String testPhrase = "Face " + "East";
		assertEquals("First phrase says East", testPhrase, steps.get(0).substring(0, testPhrase.length()));
	}

	// This test looks for South-East
	@Test
	public void FirstStringTest2() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nE, nI));
		ArrayList<String> steps = Directions.stepByStep(path);
		String testPhrase = "Face " + "South-East";
		assertEquals("First phrase says East", testPhrase, steps.get(0).substring(0, testPhrase.length()));
	}

	// This test looks for South
	@Test
	public void FirstStringTest3() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nE, nH));
		ArrayList<String> steps = Directions.stepByStep(path);
		String testPhrase = "Face " + "South";
		assertEquals("First phrase says East", testPhrase, steps.get(0).substring(0, testPhrase.length()));
	}

	// This test looks for South-West
	@Test
	public void FirstStringTest4() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nE, nG));
		ArrayList<String> steps = Directions.stepByStep(path);
		String testPhrase = "Face " + "South-West";
		assertEquals("First phrase says East", testPhrase, steps.get(0).substring(0, testPhrase.length()));
	}

	// This test looks for West
	@Test
	public void FirstStringTest5() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nE, nD));
		ArrayList<String> steps = Directions.stepByStep(path);
		String testPhrase = "Face " + "West";
		assertEquals("First phrase says East", testPhrase, steps.get(0).substring(0, testPhrase.length()));
	}

	// This test looks for North-West
	@Test
	public void FirstStringTest6() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nE, nA));
		ArrayList<String> steps = Directions.stepByStep(path);
		String testPhrase = "Face " + "North-West";
		assertEquals("First phrase says East", testPhrase, steps.get(0).substring(0, testPhrase.length()));
	}

	// This test looks for North
	@Test
	public void FirstStringTest7() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nE, nB));
		ArrayList<String> steps = Directions.stepByStep(path);
		String testPhrase = "Face " + "North";
		assertEquals("First phrase says East", testPhrase, steps.get(0).substring(0, testPhrase.length()));
	}

	// This test looks for North-East
	@Test
	public void FirstStringTest8() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nE, nC));
		ArrayList<String> steps = Directions.stepByStep(path);
		String testPhrase = "Face " + "North-East";
		assertEquals("First phrase says East", testPhrase, steps.get(0).substring(0, testPhrase.length()));
	}

	// These test cases test that the string returns the right strings

	@Test
	public void CorrectStringsTest1() {
		ArrayList<Node> path = new ArrayList<Node>(Arrays.asList(nD, nA, nB, nF, nI, nH, nE, nG));
		ArrayList<String> steps = Directions.stepByStep(path);
		assertEquals("Message from Node 1 to Node 2", "Face North, and walk 100 feet.", steps.get(0));
		assertEquals("Message from Node 2 to Node 3", "Turn hard right, and walk 100 feet.", steps.get(1));
		assertEquals("Message from Node 3 to Node 4", "Turn right, and walk 141 feet.", steps.get(2));
		assertEquals("Message from Node 4 to Node 5", "Turn right, and walk 100 feet.", steps.get(3));
		assertEquals("Message from Node 5 to Node 6", "Turn hard right, and walk 100 feet.", steps.get(4));
		assertEquals("Message from Node 6 to Node 7", "Turn hard right, and walk 100 feet.", steps.get(5));
		assertEquals("Message from Node 7 to Node 8", "Turn a near U-turn left, and walk 141 feet.", steps.get(6));
		assertEquals("Final message", "You have reached your destination", steps.get(7));
	}

}
