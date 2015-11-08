package logic;

import java.util.ArrayList;

public class Node {

	public String name;
	public int    id;
	public double x_coord;
	public double y_coord;
	public double z_coord;
	public double g_scores;
	public double h_scores;
	public double f_scores = 0;
	public ArrayList<Edge> adjacencies;
	public Node parent;

	public Node(String val, int id, double x, double y, double z) {
		this.name = val;
		this.id = id;
		this.x_coord = x;
		this.y_coord = y;
		this.z_coord = z;
		this.adjacencies = new ArrayList<Edge>();
	}

	public String getName() {
		return this.name;
	}
	
	public double getX() {
		return this.x_coord;
	}
	
	public double getY() {
		return this.x_coord;
	}
	
	public double getZ() {
		return this.x_coord;
	}

	public void addEdge(Edge edge) {
		this.adjacencies.add(edge);
	}

	public String toString()
	{
		return String.format("%s: %f, %f, %f\n", this.name, this.x_coord, this.y_coord, this.z_coord);
	}

}