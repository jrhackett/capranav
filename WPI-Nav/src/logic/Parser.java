package logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import com.google.gson.*;
import com.google.gson.stream.*;
import java.io.FileWriter;
import java.io.FileReader;

/* A Parser object is used to do translations from JSON files and Nodes.
 * Be sure to use "nodes.json" as the filename upon instantiation.
 */
public class Parser
{
	private String filename;        //File for reading/writing Nodes
	private JsonWriter writer;
	private JsonStreamParser parser;

	public static void main(String args[]) {
		Parser test = new Parser("nodes.json");
		HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
		Node node1 = new Node("five", 1, 1, 1, 1, 1);
		Node node2 = new Node("two", 2, 2, 2, 2, 2);
		nodes.put(0, node1);
		nodes.put(1, node2);
		Graph graph = new Graph(nodes);
		test.toFile(graph);
	}

    /**
     * Parser is used to communicate between the database and the rest of the program
     * @param name: filename of the database to observe
     * @return void
     */
	public Parser(String name) {
		this.filename = name;
		try {
			this.parser = new JsonStreamParser(new FileReader(filename));
		}
		catch (FileNotFoundException e) {
			System.out.println("nodes.json doesn't exist (opened for reading)");
		}
	}

    /**
     * toFile(Node) is used to append a single node to the JSON database file
     * @param node: Node to add to the database
     * @return void
     */
	public void toFile(Node node) {
		try { //Create a JsonWriter to append the file with graph nodes
			writer = new JsonWriter(new FileWriter(filename, true));
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
		new Gson().toJson(node, Node.class, writer); //Write to file
		close(); //Close the writer
	}


	/**
	 * toFile(Graph) is used to write an entire graph out to a JSON database file
	 * WARNING: This version will OVERWRITE the given file
	 *
	 * @param collection: Graph to write to the database
     */

	public void toFile(ICollection collection) {
		try { //Create a JsonWriter to append the file with graph nodes
			writer = new JsonWriter(new FileWriter(filename, false));
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Gson gson = new Gson();
		if(this.filename == "nodes.json")
		{
			Collection<Node> nodes = collection.get();
			for(Node n : nodes) {
				gson.toJson(n, Node.class, writer); //Write to file
			}
		}
		else {
			Collection<Map> maps = collection.get();
			for(Map n : maps) {
				gson.toJson(n, Map.class, writer); //Write to file
			}
		}
		close(); //Close the writer
	}

    /**
     * fromFile reads in all Nodes or Maps from database file nodes.json or maps.json
     * @return ICollection - either a Graph or a Maps
     */
	public ICollection fromFile() {
		if(this.filename == "nodes.json") {
			HashMap<Integer, Node> graph = new HashMap<Integer, Node>();
			Gson gson = new Gson();
			Node temp;
			while(parser.hasNext()){
				temp = gson.fromJson(parser.next(), Node.class);
				graph.put(temp.getID(), temp); //Add Node to the map under its ID
			}
			System.out.println(new Graph(graph).toString());
			return new Graph(graph);
		}
		else {
			HashMap<Integer, Map> maps = new HashMap<Integer, Map>();
			Gson gson = new Gson();
			Map temp;
			while(parser.hasNext()){
				temp = gson.fromJson(parser.next(), Map.class);
				maps.put(temp.getID(), temp); //Add Node to the map under its ID
			}
			return new Maps(maps);
		}
	}

    /**
     * close is used to close the FileWriter
	 * Class methods handle opening/closing of FileWriter
	 * DO NOT (try to) CALL THIS OUTSIDE OF PARSER CLASS
     * @return void
     */
	private void close() {
		try {
			this.writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
