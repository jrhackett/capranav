package logic;

public class Node {

	public String name;
	public double x_coord;
	public double y_coord;
	public double z_coord;
	public double g_scores;
	public double h_scores;
	public double f_scores = 0;
	public Edge[] adjacencies;
	public Node parent;

	public Node(String val, double x, double y, double z) {
		name = val;
		x_coord = x;
		y_coord = y;
		z_coord = z;
	}

	public String getName() {
		return name;
	}
	
	public double getX() {
		return x_coord;
	}
	
	public double getY() {
		return x_coord;
	}
	
	public double getZ() {
		return x_coord;
	}

	public void addEdge(Edge edge) {
		// TODO Auto-generated method stub
		
	}

}