package logic;
import java.util.Collection;
import java.util.HashMap;

public class Graph implements ICollection
{
	//MG: Changed to private, did this break anything?
	private HashMap<Integer, Node> nodes;

	/**
	 * Simple constructor : Empty Hashmap
	 */
	public Graph() {
		this.nodes = new HashMap<Integer, Node>();
	}

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
	 * @return String: a string describing the graph
	 */
	//HW: Made this actually return something
	public String toString()
	{
		String str = "";
		for(Node node : this.nodes.values())
		{
			str = str + (node.toString() + " ");
			System.out.println(node.toString());
		}
		return str;
	}

	public HashMap<Integer, Node> getNodes() {
		return nodes;
	}

	public Collection<Node> get() {
		return this.nodes.values();
	}
}
