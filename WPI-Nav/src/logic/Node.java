package logic;

import java.util.ArrayList;

public abstract class Node implements INode {

	private int id;
	private int map_id;
	private double x_coord;
	private double y_coord;
	private double z_coord;
	private double x_univ;
	private double y_univ;
	private double z_univ;
	private double g_scores;
	private double h_scores;
	private double f_scores = 0;
	private ArrayList<Edge> adjacencies;
	private INode parent;
	private String picturePath = null;

	/**
	 * Nodes are used to represent a location and to hold the Edges to all other
	 * locations
	 *
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
	public Node(int id, double x, double y, double z, double x_univ, double y_univ, double z_univ) {
		this.id = id;
		this.x_coord = x;
		this.y_coord = y;
		this.z_coord = z;
		this.x_univ = x_univ;
		this.y_univ = y_univ;
		this.z_univ = z_univ;
		this.adjacencies = new ArrayList<Edge>();
	}

	/**
	 * Alternate Constructor: includes map_id
	 * Nodes are used to represent a location and to hold the Edges to all other locations
	 * @param id: identification number of the node
	 * @param x: x-coordinate of the node
	 * @param y: y-coordinate of the node
	 * @param z: z-coordinate of the node
	 * @param map_id: map id number
	 * @return void
	 */
	public Node(int id, double x, double y, double z, double x_univ, double y_univ, double z_univ, int map_id) {
		this.id = id;
		this.x_coord = x;
		this.y_coord = y;
		this.z_coord = z;
		this.x_univ = x_univ;
		this.y_univ = y_univ;
		this.z_univ = z_univ;
		this.map_id = map_id;
		this.adjacencies = new ArrayList<Edge>();
	}

	/**
	 * Creates a node from an old node
	 * @param node
     */
	public Node(INode node){
		this.id = node.getID();
		this.x_coord = node.getX();
		this.y_coord = node.getY();
		this.z_coord = node.getZ();
		this.x_univ = node.getX_univ();
		this.y_univ = node.getY_univ();
		this.z_univ = node.getZ_univ();
		this.map_id = node.getMap_id();
		this.adjacencies = node.getAdjacencies();
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
	public INode getParent() {
		return parent;
	}

	/**
	 * setParent is used to return the parent of this node
	 * 
	 * @return Node: parent of the node
	 */
	public void setParent(INode parent) {
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
	public String toString() {return String.format("%d\n", this.id);}

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

	public double getX_univ() {
		return this.x_univ;
	}

	public double getY_univ() {
		return y_univ;
	}

	public double getZ_univ() {
		return z_univ;
	}

	public void setX_univ(double x_univ) {
		this.x_univ = x_univ;
	}

	public void setY_univ(double y_univ) {
		this.y_univ = y_univ;
	}

	public void setZ_univ(double z_univ) {
		this.z_univ = z_univ;
	}

	public boolean isInteresting() {
		return false;
	}

	public boolean isTransition() {
		return false;
	}

	public boolean isRoom() {return false; }

	public ArrayList<String> getNames() {return new ArrayList<>();}

	public void removeEdge(int id){
		for(Edge e : adjacencies){
			if (e.getTarget() == id){
				adjacencies.remove(e);
			}
		}
	}

	public String getPicturePath(){
		return this.picturePath;
	}
	public void setPicturePath(String s){
		this.picturePath = s;
	};
    public void addNames(ArrayList<String> l) {};

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Node)) return false;

		Node node = (Node) o;

		if (id != node.id) return false;
		if (getMap_id() != node.getMap_id()) return false;
		if (Double.compare(node.x_coord, x_coord) != 0) return false;
		if (Double.compare(node.y_coord, y_coord) != 0) return false;
		if (Double.compare(node.z_coord, z_coord) != 0) return false;
		if (Double.compare(node.getX_univ(), getX_univ()) != 0) return false;
		if (Double.compare(node.getY_univ(), getY_univ()) != 0) return false;
		return Double.compare(node.getZ_univ(), getZ_univ()) == 0;

	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = id;
		temp = Double.doubleToLongBits(x_coord);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y_coord);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z_coord);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(getX_univ());
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(getY_univ());
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(getZ_univ());
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}


// Delete this later its for Travis <3