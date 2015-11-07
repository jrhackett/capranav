package logic;

import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class AStarShortestPath {
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	/*				//below code is only for testing AStar, remove later//
	public static void main(String[] args) {

		//Initialize arbitrary Nodes
		Node n1 = new Node("Institute", 0, 0, 0);
		Node n2 = new Node("RecCenter", 0, 10, 0);
		Node n3 = new Node("Field", 0, 20, 0);
		Node n4 = new Node("Harrington", 3, 16, 0);
		Node n5 = new Node("Quad", 5, 5, 0);
		Node n6 = new Node("Morgan", 6, 1, 0);
		Node n7 = new Node("Riley", 11, 2, 0);
		Node n8 = new Node("Higgins Labs", 10, 13, 0);
		Node n9 = new Node("Campus Center", 10, 20, 0);
		Node n10 = new Node("Fountain", 16, 17, 0);
		Node n11 = new Node("Alden", 16, 3, 0);
		Node n12 = new Node("West Street", 18, 8, 0);
		Node n13 = new Node("Library", 20, 20, 0);

		// initialize the edges

		// Institute
		n1.adjacencies = new Edge[] { new Edge(n2, 1), new Edge(n5, 1) };

		// RecCenter
		n2.adjacencies = new Edge[] { new Edge(n1, 1), new Edge(n4, 1) };

		// Field
		n3.adjacencies = new Edge[] { new Edge(n4, 1) };

		// Harrington
		n4.adjacencies = new Edge[] { new Edge(n3, 1), new Edge(n2, 1), new Edge(n5, 1) };

		// Quad
		n5.adjacencies = new Edge[] { new Edge(n1, 1), new Edge(n4, 1), new Edge(n6, 1), new Edge(n8, 1) };

		// Morgan
		n6.adjacencies = new Edge[] { new Edge(n5, 1), new Edge(n7, 1)};

		// Riley
		n7.adjacencies = new Edge[] { new Edge(n6, 1) };

		// Higgins Labs
		n8.adjacencies = new Edge[] { new Edge(n5, 1), new Edge(n9, 1), new Edge(n10, 1), new Edge(n11, 1) };

		// Campus Center
		n9.adjacencies = new Edge[] { new Edge(n8, 1) };

		// Fountain
		n10.adjacencies = new Edge[] { new Edge(n8, 1), new Edge(n13, 1), new Edge(n12, 1) };

		// Alden
		n11.adjacencies = new Edge[] { new Edge(n8, 1), new Edge(n12, 1) };

		// West Street
		n12.adjacencies = new Edge[] { new Edge(n11, 1), new Edge(n10, 1), new Edge(n13, 1) };

		// Library
		n13.adjacencies = new Edge[] { new Edge(n10, 1), new Edge(n12, 1) };

		

		AStarSearch(n1, n13);


	}
					//above code is only for testing remove later//
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/**
	 * AStarSearch is used to find the shortest path between two different Nodes 
	 * @param start: starting Node
	 * @param destination: final Node
	 * @return void - path 
	 */
	public static void AStarSearch(Node start, Node destination) {

		Set<Node> explored = new HashSet<Node>(); // Create Set for explored
													// nodes

		PriorityQueue<Node> queue = new PriorityQueue<Node>(10, // Create PriorityQueue for nodes, initial capacity of 10
				new Comparator<Node>() { // compare nodes based on comparator function
					@Override
					public int compare(Node o1, Node o2) { // compare two nodes based on f_scores
						if (o1.f_scores > o2.f_scores) {
							return 1; // return 1 if i is greater than j
						}

						else if (o1.f_scores < o2.f_scores) {
							return -1; // return -1 if i is less than j
						}

						else {
							return 0; // return 0 if both nodes have same f score
						}
					}
				}
		);

		// cost from start
		start.g_scores = 0;

		queue.add(start); // add the starting Node to the priority queue

		boolean found = false; // set found boolean to false, will change to
								// true once path is found

		while ((!queue.isEmpty()) && (!found)) { // continue to loop as long as
													// queue is not empty AND
													// path isn't found yet

			// Retrieve and remove the Node in queue with the lowest f_score
			// value
			Node current = queue.poll();

			// add current Node to explored queue
			explored.add(current);

			// check if current value is the destination value
			if (current.name.equals(destination.name)) {
				found = true; // if current Node = destination Node, set found
								// boolean to true and exit while loop when done
			}

			// check children of current Node
			for (Edge e : current.adjacencies) {
				Node child = e.target; // set child Node = to edge of current Node
				double movement_cost = getCost(current, e); // obtain movement_cost from edge
				double temp_g_scores = current.g_scores + movement_cost; // calculate g_scores
				double temp_h_scores = getHeuristic(child, destination); // get heuristic cost from current node and destination node
				double temp_f_scores = temp_g_scores + temp_h_scores; // get f score

				// if child has been evaluated already and the new f_score is higher, skip
				if ((explored.contains(child)) && (temp_f_scores >= child.f_scores)) {
					continue; // continue through for loop
				}

				// if child is not in the queue or the new f_score is lower:
				else if ((!queue.contains(child)) || (temp_f_scores < child.f_scores)) {
					child.parent = current; // Set child's parent to the current Node to establish path
					child.g_scores = temp_g_scores; // update child's g_scores
					child.h_scores = temp_h_scores; // update childs h_scores
					child.f_scores = temp_f_scores; // update childs f_scores

					// remove child from queue to avoid doubles
					if (queue.contains(child)) {
						queue.remove(child);
					}

					queue.add(child); // add child to queue, while loop will set child to current next and explore adjacencies
				}

			}

		}
	
		List<Node> path = printPath(destination); //create a list of Nodes, tracing from the destination Node back to the start
		System.out.println("Path: " + path); //print out the List of Nodes from start to finish

	}
	/**
	 * getCost calculates the weighted distance cost between a Node and one of its given edges based on the coordinate values of the Nodes
	 * @param currentNode - Node whose edges you are currently exploring
	 * @param adjacentEdge - one of the current Node's edges
	 * @return the movement cost between two adjacent nodes
	 */
	public static double getCost(Node currentNode, Edge adjacentEdge) {
		Node child = adjacentEdge.target;
		double multiplier = adjacentEdge.weight;
		double cost = Math.sqrt(
				Math.pow((child.x_coord - currentNode.x_coord), 2) + Math.pow((child.y_coord - currentNode.y_coord), 2)
						+ Math.pow((child.z_coord - currentNode.z_coord), 2));
		cost *= multiplier;
		return cost;
	}
	
	/**
	 * getHeuristic calculates the unweighted distance between two given Nodes based on coordinate values of the Nodes
	 * @param currentNode - current Node you are measuring from
	 * @param endNode - destination Node
	 * @return heuristic value, AKA as the crow flies distance between Nodes
	 */
	public static double getHeuristic(Node currentNode, Node endNode) {
		double heuristic = Math.sqrt(Math.pow((endNode.x_coord - currentNode.x_coord), 2)
				+ Math.pow((endNode.y_coord - currentNode.y_coord), 2)
				+ Math.pow((endNode.z_coord - currentNode.z_coord), 2));
		return heuristic;
	}

	/**
	 * printPath generates a list of Nodes out the path derived from an AStar Search using the parent/child attribute of each Node, starting from the goal 
	 * @param target - the final destination
	 * @return path - a List of Nodes from start to finish
	 * @warning only run inside AStarSearch
	 */
	public static List<Node> printPath(Node target) {
		List<Node> path = new ArrayList<Node>();

		for (Node node = target; node != null; node = node.parent) {
			path.add(node);
		}
		Collections.reverse(path);
		return path;
	}
}
