package logic;

import java.io.IOException;
import java.util.HashMap;
import com.google.gson.*;
import com.google.gson.stream.*;
import java.io.FileWriter;
import java.io.FileReader;

public class Parser
{
	private String filename;
	private JsonWriter writer;
	private JsonStreamParser parser;

	public static void main(String []args)
	{
		Parser parser = new Parser("nodes.json");
		/*Node node1 = new Node("here", 1, 5, 6, 7);
		Node node2 = new Node("there", 2, 1, 2, 3);
		Edge edge = new Edge(node2, 5);
		node1.addEdge(edge);
		parser.toFile(node1);*/
		Graph graph = parser.fromFile();
		graph.toString();
		parser.close();
	}

	public Parser(String name) {
		filename = name;
		try{
			writer = new JsonWriter(new FileWriter(filename, true));
			parser = new JsonStreamParser(new FileReader(filename));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	// Write a new node out to file (hard-coded filename in method -- could change)
	public void toFile(Node node) {
		//Create a JsonWriter to append the file with graph nodes
		new Gson().toJson(node, Node.class, writer);
		System.out.println(new Gson().toJson(node, Node.class));
	}

	// Generate HashMap of nodes from JSON file.
	public Graph fromFile() {
		HashMap<Integer, Node> graph = new HashMap<Integer, Node>();
		Gson gson = new Gson();
		Node temp;
		while(parser.hasNext()){
			temp = gson.fromJson(parser.next(), Node.class);
			graph.put(temp.id, temp); //Add Node to the map under its ID
		}
		return new Graph(graph);
	}

	public void close() {
		try {
			writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}


	/*
	 * Parses nodes and edges from nodes.txt and edges.txt
	 * into a Graph, returns the graph
	 * Note this is the old parsing method
	 */
	//TODO maybe remove this
//	public static Graph parsing()
//	{
//		String inputNodes = "nodes.txt";
//		String inputEdges = "edges.txt";
//		Scanner scanner = null;
//		Scanner scanner2 = null;
//		HashMap<Integer, Node> hashmap = new HashMap<Integer, Node>();
//		int i = 0;
//
//		try
//		{
//			scanner = new Scanner(new File(inputNodes));
//		}
//		catch(FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//
//		while (scanner.hasNextLine())
//		{
//            Scanner s1 = new Scanner(scanner.nextLine());
//            String name = s1.next();
//            int x = Integer.parseInt(s1.next());
//            int y = Integer.parseInt(s1.next());
//            int z = 0; //TODO: change this value later
//            Node node = new Node(name, 7, x, y, z);
//            hashmap.put(i, node);
//            i++;
//		}
//
//		try
//		{
//			scanner2 = new Scanner(new File(inputEdges));
//		}
//		catch(FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//
//		while(scanner2.hasNextLine())
//		{
//			Scanner s2 = new Scanner(scanner2.nextLine());
//			String firstNodeName = s2.next();
//			String secondNodeName = s2.next();
//			Node node1 = null;
//			Node node2 = null;
//
//			for(Node node : hashmap.values())
//			{
//				if(node.getName().equals(firstNodeName))
//				{
//					node1 = node;
//				}
//				else if(node.getName().equals(secondNodeName))
//				{
//					node2 = node;
//				}
//			}
//
//			double weight = Math.sqrt(Math.pow(node1.getX() - node2.getX(),2.0) + Math.pow(node1.getY() - node2.getY(), 2.0));
//			Edge edge = new Edge(node1, weight); //TODO: check this...
//
//			for(Node node : hashmap.values())
//			{
//				if(node.getName().equals(firstNodeName) || node.getName().equals(secondNodeName))
//				{
//					node.addEdge(edge);
//				}
//			}
//		}
//		return new Graph(hashmap);
//	}
}
