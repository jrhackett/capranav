package logic;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
 * An implementation for a Node describing a location to be used in Graph
 */
public class Node 
{
	/*
	 * name is the name of the location
	 * x is the x coordinate on the map
	 * y is the y coordinate on the map
	 * edges is a minimum PriorityQueue of edges, organized by edge weight 
	 */
	private String name;
	private double x;
	private double y;
	Comparator<Edge> comparator = new EdgeComparator(); 
	PriorityQueue<Edge> edges = new PriorityQueue<Edge>(10, comparator);
	
	/*
	 * Constructor initializes the Node
	 */
	public Node(String name, double x, double y)
	{
		/*
		 * Might want to do bounds checking on these in the future
		 */
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Returns name of the node
	 */
	public String getName()
	{
		return this.name;
	}
	
	/*
	 * Returns the x coordinate of the node
	 */
	public double getX()
	{
		return this.x;
	}
	
	/*
	 * Return the y coordinate of the node
	 */
	public double getY()
	{
		return this.y;
	}
	
	/*
	 * Adds an edge to this node
	 */
	public void addEdge(Edge e)
	{
		this.edges.add(e);
	}
	
	/*
	 * Returns a String describing this Node
	 */
	public String toString()
	{
		return String.format("%s: %.5f, %.5f", this.name, this.x, this.y);
	}
	
	/*
	 * Methods to add:
	 * 
	 */
}
