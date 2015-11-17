package logic;

import java.util.ArrayList;

public class Node {

	private String name;
	private int id;
	private int map_id;
	private double x_coord;
	private double y_coord;
	private double z_coord;
	private double g_scores;
	private double h_scores;
	private double f_scores = 0;
	private ArrayList<Edge> adjacencies;
	private Node parent;


	/**
	 * Nodes are used to represent a location and to hold the Edges to all other
	 * locations
	 * 
	 * @param val:Name
	 *            of the node
	 * @param id:
	 *            identification number of the node
	 * @param x:
	 *            x-coordinate of the node
	 * @param y:
	 *            y-coordinate of the node
	 * @param z:
	 *            z-coordinate of the node
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
	 * Alternate Constructor: includes map_id
	 * Nodes are used to represent a location and to hold the Edges to all other locations
	 * @param val: Name of the node
	 * @param id: identification number of the node
	 * @param x: x-coordinate of the node
	 * @param y: y-coordinate of the node
	 * @param z: z-coordinate of the node
	 * @param map_id: map id number
	 * @return void
	 */
	public Node(String val, int id, double x, double y, double z, int map_id) {
		this.name = val;
		this.id = id;
		this.x_coord = x;
		this.y_coord = y;
		this.z_coord = z;
		this.map_id = map_id;
		this.adjacencies = new ArrayList<Edge>();
	}

	/**
	 * getName is used to return the name of the node
	 * @param
	 * @return String: name of the node
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * getID is used to return the id of the node
	 * @param
	 * @return double: id of the node
	 */
	public int getID() {
		return this.id;
	}

	/**
	 * setID is used to return the id of the node
	 * 
	 * @param
	 * @return double: id of the node
	 */
	public void setID(int id) {
		this.id = id;
	}



	/**
	 * getX is used to return the x-coordinate of the node
	 * @param
	 * @return double: x-coordinate of the node
	 */
	public double getX() {
		return x_coord;
	}

	public void setX(double x_coord) {
		this.x_coord = x_coord;
	}

	/**
	 * getY is used to return the y-coordinate of the node
	 * @param
	 * @return double: y-coordinate of the node
	 */
	public double getY() {
		return y_coord;
	}

	public void setY(double y_coord) {
		this.y_coord = y_coord;
	}

	/**
	 * getZ is used to return the z-coordinate of the node
	 * @return double: z-coordinate of the node
	 */
	public double getZ() {
		return z_coord;
	}

	public void setZ(double z_coord) {
		this.z_coord = z_coord;
	}

	/**
	 * getG is used to return the g score of the node
	 * @return double: g score of the node
	 */
	public double getG() {
		return g_scores;
	}

	public void setG(double g_scores) {
		this.g_scores = g_scores;
	}

	/**
	 * getH is used to return the h score of the node
	 * @return double: h score of the node
	 */
	public double getH() {
		return h_scores;
	}

	/**
	 * setH is used to change the h score of the node
	 * 
	 * @param h_scores:
	 *            a new h score
	 * @return void
	 */
	public void setH(double h_scores) {
		this.h_scores = h_scores;
	}

	/**
	 * getF is used to return the f score of the node
	 * 
	 * @return double: f score of the node
	 */
	public double getF() {
		return f_scores;
	}


	public void setF(double f_scores) {
		this.f_scores = f_scores;
	}

	public ArrayList<Edge> getAdjacencies() {
		return adjacencies;
	}


	public void setAdjacencies(ArrayList<Edge> adjacencies) {
		this.adjacencies = adjacencies;
	}

	/**
	 * getParent is used to return the parent of this node
	 * 
	 * @return Node: parent of the node
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * setParent is used to return the parent of this node
	 * 
	 * @return Node: parent of the node
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}


	public void addEdge(Edge edge) {
		this.adjacencies.add(edge);
	}

	/**
	 * toString is used to print the node in a readable format
	 * @return String: a string describing the node
	 */
	//public String toString() { return String.format("%s: %f, %f, %f\n", this.name, this.x_coord, this.y_coord, this.z_coord);}
	public String toString() {return this.name;}

	/**
	 * getMap_id is used to get the map id of the node
	 * @return int: map id of node
	 */
	public int getMap_id() {
		return this.map_id;
	}

	/**
	 * setF is used to change the map_id score of the node
	 * @param map_id: a new map id score
	 * @return void
	 */
	public void setMap_id(int map_id) {
		this.map_id = map_id;
	}

	/**
	 * sets the name of the node
	 * @param name
     */
	public void setName(String name) {this.name = name;}


	/**
	 * @warning do not generate hashcode/equals, it creates a Stack Overflow in
	 *          JUnit
	 */


}