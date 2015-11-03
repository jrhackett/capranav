/*
 * An implementation of a weighted edge to be used in a graph
 */
public class Edge implements Comparable<Edge>
{
	/*
	 * from and to are vertices
	 * weight is distance between the vertices
	 */
	private Node from;
	private Node to;
	private double weight;
	
	/*
	 * Constructor initializes Edge vertices from and to and weight weight
	 */
	public Edge(Node from, Node to, double weight)
	{
		/*
		 * might want to validate from, to, and weight to make sure they are non-negative
		 * in the future if we see this is a problem
		 */
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	/*
	 * Returns the vertex from
	 */
	public Node getFrom()
	{
		return this.from;
	}
	
	/*
	 * Returns the vertex to
	 */
	public Node getTo()
	{
		return this.to;
	}
	
	/*
	 * Returns the weight
	 */
	public double getWeight()
	{
		return this.weight;
	}
	
	/*
	 * Returns a String describing this Edge
	 */
	public String toString()
	{
		return String.format("%d-%d %.5f", from, to, weight);
	}
	
	/*
	 * Compares weights of this edge and Edge o
	 * Returns -1 if this weight is greater
	 * Returns 1 if this weight is less
	 * Returns 0 if weights are equal
	 */
	public int compareTo(Edge o) 
	{
		if(this.weight > o.weight) return -1;
		if(this.weight < o.weight) return 1;
		else return 0;
	}
}