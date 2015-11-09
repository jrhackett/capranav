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
    //Note must close parser after toFile but NOT after fromFile
	public static void main(String []args) {
		Parser parser = new Parser("nodes.json");
		/*Node node1 = new Node("here", 1, 5, 6, 7);
		Node node2 = new Node("there", 2, 1, 2, 3);
		Edge edge = new Edge(node2, 5);
		node1.addEdge(edge);
		parser.toFile(node1);
		parser.toFile(node2);*/

		Graph graph = parser.fromFile();
		graph.toString();

		//parser.close();
	}

    /**
     * Parser is used to communicate between the database and the rest of the program
     * @param filename: filename of the database to observe
     * @return void
     */
	public Parser(String filename) {
		try{
			this.writer = new JsonWriter(new FileWriter(filename, true));
			this.parser = new JsonStreamParser(new FileReader(filename));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

    /**
     * toFile is used to append a node to the JSON database file nodes.json
     * @param node: Node to add to the database
     * @return void
     */
	public void toFile(Node node) {
		//Create a JsonWriter to append the file with graph nodes
		new Gson().toJson(node, Node.class, writer);
		System.out.println(new Gson().toJson(node, Node.class));
	}

    /**
     * fromFile is used to read in all Nodes from database file nodes.json
     * @param void
     * @return Graph - returns Graph representing contents of nodes.json
     */
	public Graph fromFile() {
		HashMap<Integer, Node> graph = new HashMap<Integer, Node>();
		Gson gson = new Gson();
		Node temp;
		while(parser.hasNext()){
			temp = gson.fromJson(parser.next(), Node.class);
			graph.put(temp.getID(), temp); //Add Node to the map under its ID
		}
		return new Graph(graph);
	}

    /**
     * close is used to close the FileWriter
     * @param void
     * @return void
     */
	public void close() {
		try {
			this.writer.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
