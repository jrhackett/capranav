package logic;

import java.util.ArrayList;

public class Node {

	private String name;
	private int    id;
	private double x_coord;
	private double y_coord;
	private double z_coord;
	private double g_scores;
	private double h_scores;
	private double f_scores = 0;
	ArrayList<Edge> adjacencies;
	private Node parent;


	/**
	 * Nodes are used to represent a location and to hold the Edges to all other locations
	 * @param val: Name of the node
	 * @param id: identification number of the node
	 * @param x: x-coordinate of the node
	 * @param y: y-coordinate of the node
	 * @param z: z-coordinate of the node
	 * @return void
	 */
	public Node(String val, int id, double x, double y, double z) {
		this.name = val;
		this.id = id;
		this.x_coord = x;
		this.y_coord = y;
		this.z_coord = z;
		this.adjacencies = new ArrayList<Edge>();
	}

	/**
	 * getName is used to return the name of the node
	 * @param void
	 * @return String: name of the node
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * getID is used to return the id of the node
	 * @param void
	 * @return double: id of the node
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * getX is used to return the x-coordinate of the node
	 * @param void
	 * @return double: x-coordinate of the node
	 */
	public double getX() {
		return this.x_coord;
	}

	/**
	 * getY is used to return the y-coordinate of the node
	 * @param void
	 * @return double: y-coordinate of the node
	 */
	public double getY() {
		return this.y_coord;
	}

	/**
	 * getZ is used to return the z-coordinate of the node
	 * @param void
	 * @return double: z-coordinate of the node
	 */
	public double getZ() {
		return this.z_coord;
	}

	/**
	 * getG is used to return the g score of the node
	 * @param void
	 * @return double: g score of the node
	 */
	public double getG() { return this.g_scores; }

	/**
	 * getH is used to return the h score of the node
	 * @param void
	 * @return double: h score of the node
	 */
	public double getH() { return this.h_scores; }

	/**
	 * getF is used to return the f score of the node
	 * @param void
	 * @return double: f score of the node
	 */
	public double getF() { return this.f_scores; }

	/**
	 * getAdjacencies is used to return the ArrayList of edges for this node
	 * @param void
	 * @return ArrayList: edge ArrayList of the node
	 */
	public ArrayList<Edge> getAdjacencies() { return this.adjacencies; }

	/**
	 * getParent is used to return the parent of this node
	 * @param void
	 * @return Node: parent of the node
	 */
	public Node getParent() { return this.parent; }

	/**
	 * setG is used to change the g score of the node
	 * @param g: a new g score
	 * @return void
	 */
	public void setG(double g) { this.g_scores = g; }

	/**
	 * setH is used to change the h score of the node
	 * @param h: a new h score
	 * @return void
	 */
	public void setH(double h) { this.h_scores = h; }

	/**
	 * setF is used to change the f score of the node
	 * @param f: a new f score
	 * @return void
	 */
	public void setF(double f) { this.f_scores = f; }

	/**
	 * setParent is used to change the parent of this node
	 * @param p: a new parent
	 * @return void
	 */
	public void setParent(Node p) { this.parent = p; }

	/**
	 * addEdge is used to add an Edge to the ArrayList of edges
	 * @param edge: a new Edge to add
	 * @return void
	 */
	public void addEdge(Edge edge) {
		this.adjacencies.add(edge);
	}

	/**
	 * toString is used to print the node in a readable format
	 * @param void
	 * @return String: a string describing the node
	 */
	public String toString() { return String.format("%s: %f, %f, %f\n", this.name, this.x_coord, this.y_coord, this.z_coord);}

	/**
	 * @warning do not generate hashcode/equals, it creates a Stack Overflow in
	 *          JUnit
	 */


}