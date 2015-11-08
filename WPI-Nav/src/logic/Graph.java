package logic;
import java.util.HashMap;

public class Graph 
{
	HashMap<Integer, Node> nodes;

	/**
	 * Graphs are used to represent collections of Nodes
	 * @param nodes: a hashmap of nodes, keyed by an integer id
	 * @return void
	 */
	public Graph(HashMap<Integer, Node> nodes)
	{
		this.nodes = nodes;
	}

	/**
	 * toString is used to print the graph in a readable format
	 * @param void
	 * @return String: a string describing the graph
	 */
	public String toString()
	{
		for(Node node : this.nodes.values())
		{
			System.out.println(node.toString());
		}
		return null;
	}
}
