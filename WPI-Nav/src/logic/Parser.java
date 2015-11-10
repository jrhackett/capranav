package logic;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    /**
     * Parser is used to communicate between the database and the rest of the program
     * @param filename: filename of the database to observe
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
     * toFile is used to append a node to the JSON database file nodes.json
     * @param node: Node to add to the database
     * @return void
     */
	public void toFile(Node node) {
		try { //Create a JsonWriter to append the file with graph nodes
			writer = new JsonWriter(new FileWriter(filename, true));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		new Gson().toJson(node, Node.class, writer); //Write to file
		close(); //Close the writer
	}

    /**
     * fromFile reads in all Nodes from database file nodes.json
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
	 * Class methods handle opening/closing of FileWriter
	 * DO NOT (try to) CALL THIS OUTSIDE OF PARSER CLASS
     * @param void
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
