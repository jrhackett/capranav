package logic;

import java.util.ArrayList;

public class Node {

	private String name;
	private int    id;
	private int    map_id;
	private double x_coord;
	private double y_coord;
	private double z_coord;
	private double g_scores;
	private double h_scores;
	private double f_scores = 0;
	private ArrayList<Edge> adjacencies;
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
	 * getX is used to return the x-coordinate of the node
	 * @param
	 * @return double: x-coordinate of the node
	 */
	public double getX() {
		return this.x_coord;
	}

	/**
	 * getY is used to return the y-coordinate of the node
	 * @param
	 * @return double: y-coordinate of the node
	 */
	public double getY() {
		return this.y_coord;
	}

	/**
	 * getZ is used to return the z-coordinate of the node
	 * @return double: z-coordinate of the node
	 */
	public double getZ() {
		return this.z_coord;
	}

	/**
	 * getG is used to return the g score of the node
	 * @return double: g score of the node
	 */
	public double getG() { return this.g_scores; }

	/**
	 * getH is used to return the h score of the node
	 * @return double: h score of the node
	 */
	public double getH() { return this.h_scores; }

	/**
	 * getF is used to return the f score of the node
	 * @return double: f score of the node
	 */
	public double getF() { return this.f_scores; }

	/**
	 * getAdjacencies is used to return the ArrayList of edges for this node
	 * @return ArrayList: edge ArrayList of the node
	 */
	public ArrayList<Edge> getAdjacencies() { return this.adjacencies; }

	/**
	 * getParent is used to return the parent of this node
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adjacencies == null) ? 0 : adjacencies.hashCode());
		long temp;
		temp = Double.doubleToLongBits(f_scores);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(g_scores);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(h_scores);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		temp = Double.doubleToLongBits(x_coord);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y_coord);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z_coord);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (adjacencies == null) {
			if (other.adjacencies != null)
				return false;
		} else if (!adjacencies.equals(other.adjacencies))
			return false;
		if (Double.doubleToLongBits(f_scores) != Double.doubleToLongBits(other.f_scores))
			return false;
		if (Double.doubleToLongBits(g_scores) != Double.doubleToLongBits(other.g_scores))
			return false;
		if (Double.doubleToLongBits(h_scores) != Double.doubleToLongBits(other.h_scores))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (Double.doubleToLongBits(x_coord) != Double.doubleToLongBits(other.x_coord))
			return false;
		if (Double.doubleToLongBits(y_coord) != Double.doubleToLongBits(other.y_coord))
			return false;
		if (Double.doubleToLongBits(z_coord) != Double.doubleToLongBits(other.z_coord))
			return false;
		return true;
	}

}