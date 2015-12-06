package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarShortestPath {

	/**
	 * AStarSearch is used to find the shortest path between two different Nodes 
	 * @param start: starting Node
	 * @param destination: final Node
	 * @return void - path 
	 */
	public static ArrayList<INode> AStarSearch(INode start, INode destination, HashMap<Integer, INode> map) {

		Set<INode> explored = new HashSet<INode>(); // Create Set for explored
		// nodes

		PriorityQueue<INode> queue = new PriorityQueue<INode>(10, // Create PriorityQueue for nodes, initial capacity of 10
				new Comparator<INode>() { // compare nodes based on comparator function
					@Override
					public int compare(INode o1, INode o2) { // compare two nodes based on f_scores
						if (o1.getF() > o2.getF()) {
							return 1; // return 1 if i is greater than j
						}

						else if (o1.getF() < o2.getF()) {
							return -1; // return -1 if i is less than j
						}

						else {
							return 0; // return 0 if both nodes have same f score
						}
					}
				}
		);

		// cost from start
		double savedG = start.getG();
		start.setG(0);

		queue.add(start); // add the starting Node to the priority queue

		boolean found = false; // set found boolean to false, will change to
		// true once path is found
		int i = 0;
		while ((!queue.isEmpty()) && (!found)) { // continue to loop as long as
			// queue is not empty AND
			// path isn't found yet
			// Retrieve and remove the Node in queue with the lowest f_score
			// value
			INode current = queue.poll();

			// add current Node to explored queue
			explored.add(current);

			// check if current value is the destination value
			if (current.getID() == (destination.getID())) {
				found = true; // if current Node = destination Node, set found
				// boolean to true and exit while loop when done
			}

			// check children of current Node
			for (Edge e : current.getAdjacencies()) {
				INode child = map.get(e.getTarget()); // set child Node =
				// to edge of
				// current Node
				double movement_cost = getCost(current, e, map); // obtain movement_cost from edge
				double temp_g_scores = current.getG() + movement_cost; // calculate g_scores
				double temp_h_scores = 0; //getHeuristic(child, destination); // get heuristic cost from current node and destination node
				double temp_f_scores = temp_g_scores + temp_h_scores; // get f score
				// if child has been evaluated already and the new f_score is higher, skip
				if ((explored.contains(child)) && (temp_f_scores >= child.getF())) {
					continue; // continue through for loop
				}
				// if child is not in the queue or the new f_score is lower:
				else if ((!queue.contains(child)) || (temp_f_scores < child.getF())) {
					child.setParent(current); // Set child's parent to the current Node to establish path
					child.setG(temp_g_scores); // update child's g_scores
					child.setH(temp_h_scores); // update childs h_scores
					child.setF(temp_f_scores); // update childs f_scores
					// remove child from queue to avoid doubles
					if (queue.contains(child)) {
						queue.remove(child);
					}

					queue.add(child); // add child to queue, while loop will set child to current next and explore adjacencies
				}

			}

		}
		ArrayList<INode> path = printPath(destination); // create a list of
		// Nodes, tracing from
		// the destination Node
		// back to the start


		return path;
	}
	/**
	 * getCost calculates the weighted distance cost between a Node and one of its given edges based on the coordinate values of the Nodes
	 * @param currentNode - Node whose edges you are currently exploring
	 * @param adjacentEdge - one of the current Node's edges
	 * @return the movement cost between two adjacent nodes
	 */
	public static double getCost(INode currentNode, Edge adjacentEdge, HashMap<Integer, INode> map) {
		INode child = map.get(adjacentEdge.getTarget());
		double multiplier = adjacentEdge.getWeight();
		double cost = Math.sqrt(
				Math.pow((child.getX_univ() - currentNode.getX_univ()), 2) + Math.pow((child.getY_univ() - currentNode.getY_univ()), 2)
						+ Math.pow((child.getZ_univ() - currentNode.getZ_univ()), 2));
		cost *= multiplier;
		return cost;
	}

	/**
	 * getHeuristic calculates the unweighted distance between two given Nodes based on coordinate values of the Nodes
	 * @param currentNode - current Node you are measuring from
	 * @param endNode - destination Node
	 * @return heuristic value, AKA as the crow flies distance between Nodes
	 */
	public static double getHeuristic(INode currentNode, INode endNode) {
		double x1 = currentNode.getX_univ();
		double x2 = endNode.getX_univ();
		double y1 = currentNode.getY_univ();
		double y2 = endNode.getY_univ();
		double z1 = currentNode.getZ_univ();
		double z2 = endNode.getZ_univ();
		double heuristic = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
		return heuristic;
	}

	/**
	 * printPath generates a list of Nodes out the path derived from an AStar Search using the parent/child attribute of each Node, starting from the goal 
	 * @param target - the final destination
	 * @return path - a List of Nodes from start to finish
	 * @warning only run inside AStarSearch
	 */
	public static ArrayList<INode> printPath(INode target) {
		ArrayList<INode> path = new ArrayList<INode>();
		int i = 0;
		for (INode node = target; node != null; node = node.getParent()) {
			if(!path.contains(node)) path.add(node);
			else
				break;
		}
		Collections.reverse(path);
		return path;
	}
}
