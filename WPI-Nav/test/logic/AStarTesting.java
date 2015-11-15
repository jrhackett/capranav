package logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class AStarTesting {
	Node t1, t2, t3, t4;
	Node n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13;
	@Before
	public void setUp() throws Exception {
		t1 = new Node("Origin", 1, 0, 0, 0);
		t2 = new Node("X", 2, 5, 0, 0);
		t3 = new Node("Y", 3, 0, 5, 0);
		t4 = new Node("Slope", 4, 5, 5, 0);

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
		n1.adjacencies = new ArrayList<Edge> (Arrays.asList( new Edge(n2, 1), new Edge(n5, 1)));

		// RecCenter
		n2.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n1, 1), new Edge(n4, 1)));

		// Field
		n3.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n4, 1)));

		// Harrington
		n4.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n3, 1), new Edge(n2, 1), new Edge(n5, 1)));

		// Quad
		n5.adjacencies = new ArrayList<Edge>(
				Arrays.asList(new Edge(n1, 1), new Edge(n4, 1), new Edge(n6, 1), new Edge(n8, 1)));

		// Morgan
		n6.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n5, 1), new Edge(n7, 1)));

		// Riley
		n7.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n6, 1)));

		// Higgins Labs
		n8.adjacencies = new ArrayList<Edge>(
				Arrays.asList(new Edge(n5, 1), new Edge(n9, 1), new Edge(n10, 1), new Edge(n11, 1)));

		// Campus Center
		n9.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n8, 1)));

		// Fountain
		n10.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n8, 1), new Edge(n13, 1), new Edge(n12, 1)));

		// Alden
		n11.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n8, 1), new Edge(n12, 1)));

		// West Street
		n12.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n11, 1), new Edge(n10, 1), new Edge(n13, 1)));

		// Library
		n13.adjacencies = new ArrayList<Edge>(Arrays.asList(new Edge(n10, 1), new Edge(n12, 1)));
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

	// Testing~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// AStar~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/*
	 * @Test public void AStarTest1() { assertEquals("Tests AStar",
	 * ArrayList<Node>, AStarShortestPath.AStarSearch(n1, n13), 0); }
	 */

}
