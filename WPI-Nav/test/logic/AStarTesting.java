package logic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class AStarTesting {
	Node t1, t2, t3, t4, t5;
	Node n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14;
	HashMap<Integer, Node> nodes;

	@Before
	public void setUp() throws Exception {
		t1 = new Node("Origin", 1, 0, 0, 0);
		t2 = new Node("X", 2, 5, 0, 0);
		t3 = new Node("Y", 3, 0, 5, 0);
		t4 = new Node("Slope", 4, 5, 5, 0);
		t5 = new Node("Z", 5, 5, 5, 5);

		n1 = new Node("Institute", 5, 0, 0, 0);
		n2 = new Node("RecCenter", 6, 0, 10, 0);
		n3 = new Node("Field", 7, 0, 20, 0);
		n4 = new Node("Harrington", 8, 3, 16, 0);
		n5 = new Node("Quad", 9, 5, 5, 0);
		n6 = new Node("Morgan", 10, 6, 1, 0);
		n7 = new Node("Riley", 11, 11, 2, 0);
		n8 = new Node("Higgins Labs", 12, 10, 13, 0);
		n9 = new Node("Campus Center", 13, 10, 20, 0);
		n10 = new Node("Fountain", 14, 16, 17, 0);
		n11 = new Node("Alden", 15, 16, 3, 0);
		n12 = new Node("West Street", 16, 18, 8, 0);
		n13 = new Node("Library", 17, 20, 20, 0);


		
				
		// initialize the edges

		// Institute
		n1.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(n2, 1), new Edge(n5, 1))));

		// RecCenter
		n2.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n1, 1), new Edge(n4, 1))));

		// Field
		n3.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n4, 1))));

		// Harrington
		n4.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n3, 1), new Edge(n2, 1), new Edge(n5, 1))));

		// Quad
		n5.setAdjacencies( new ArrayList<Edge>(
				Arrays.asList(new Edge(n1, 1), new Edge(n4, 1), new Edge(n6, 1), new Edge(n8, 1))));

		// Morgan
		n6.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n5, 1), new Edge(n7, 1))));

		// Riley
		n7.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n6, 1), new Edge(n11, 1))));

		// Higgins Labs
		n8.setAdjacencies( new ArrayList<Edge>(
				Arrays.asList(new Edge(n5, 1), new Edge(n9, 1), new Edge(n10, 1), new Edge(n11, 1))));

		// Campus Center
		n9.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n8, 1))));

		// Fountain
		n10.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n8, 1), new Edge(n13, 1), new Edge(n12, 1))));

		// Alden
		n11.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n8, 1), new Edge(n12, 1), new Edge(n7, 1))));

		// West Street
		n12.setAdjacencies( new ArrayList<Edge>(Arrays.asList(new Edge(n11, 1), new Edge(n10, 1), new Edge(n13, 1))));

		// Library
		n13.setAdjacencies(new ArrayList<Edge>(Arrays.asList(new Edge(n10, 1), new Edge(n12, 1))));

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
		assertEquals("Tests Cost", 10, AStarShortestPath.getCost(t1, new Edge(t2, 2)), 0);
	}

	@Test
	public void AStarCost2() {
		assertEquals("Tests Cost", 50, AStarShortestPath.getCost(t1, new Edge(t3, 10)), 0);
	}

	@Test
	public void AStarCost3() {
		assertEquals("Tests Cost", 2 * Math.sqrt(75), AStarShortestPath.getCost(t5, new Edge(t1, 2)), 0);
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
