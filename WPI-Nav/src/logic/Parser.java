package logic;

import java.io.IOException;
import java.util.HashMap;
import com.google.gson.*;
import com.google.gson.stream.*;
import java.io.FileWriter;
import java.io.FileReader;

/* A Parser object is used to do translations from JSON files and Nodes.
 *
 * NOTES
 * Be sure to use "nodes.json" as the filename upon instantiation.
 * DO NOT FORGET to call Parser.close() when done with the object; otherwise, changes will not be written to file
 */
public class Parser
{
	private JsonWriter writer;
	private JsonStreamParser parser;

	//TODO Remove when done testing
	//Just for testing Parser class
	public static void main(String []args) {
		Parser parser = new Parser("nodes.json");
		Node node1 = new Node("here", 1, 5, 6, 7);
		Node node2 = new Node("there", 2, 1, 2, 3);
		Edge edge = new Edge(node2, 5);
		node1.addEdge(edge);
		parser.toFile(node1);
		parser.toFile(node2);
		/*
		Graph graph = parser.fromFile();
		graph.toString();
		*/
		parser.close();
	}

	public Parser(String filename) {
		try{
			this.writer = new JsonWriter(new FileWriter(filename, true));
			this.parser = new JsonStreamParser(new FileReader(filename));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	// Write a new Node out to file
	public void toFile(Node node) {
		//Create a JsonWriter to append the file with graph nodes
		new Gson().toJson(node, Node.class, writer);
		System.out.println(new Gson().toJson(node, Node.class));
	}

	// Generate Graph of nodes from JSON file.
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

	//Close JsonWriter (Necessary to flush changes to file)
	public void close() {
		try {
			this.writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
