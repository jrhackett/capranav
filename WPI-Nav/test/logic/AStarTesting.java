package logic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class AStarTesting {
	Node t1, t2, t3, t4;
	@Before
	public void setUp() throws Exception {
		t1 = new Node("Origin", 1, 0, 0, 0);
		t2 = new Node("X", 2, 5, 0, 0);
		t3 = new Node("Y", 3, 0, 5, 0);
		t4 = new Node("Slope", 4, 5, 5, 0);
	}

	@Test
	public void test() {

	}

	@Test
	public void AStarHueristics() {
		assertEquals("Tests heuristic", 5, AStarShortestPath.getHeuristic(t1, t2), 0);
	}

}
