package logic;

import com.google.gson.Gson;
import com.google.gson.JsonStreamParser;
import com.google.gson.stream.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Arrays;

// TODO IMPORTANT: A few things need to be done before this is useable : check TODOs below
// TODO IMPORTANT: Also notice that the method names changed - sorry, couldn't really avoid it. Make sure other files are updated

/* A Parser object is used to do translations from JSON files and Nodes/Maps.
 *
 * USAGE: Struct should be either INode or Map (determines what you're iterating over)
 * 		  new Parser().fromFileGraph();                - Returns Graph
 * 		  new Parser().fromFileMap();                  - Returns Map
 * 		  new Parser().toFile(ICollection collection); - Stores into files based on type
 *
 */
public class Parser<Struct>
{
	//Don't need to set any of these anymore - empty constructor
	private String           filename; //File for reading/writing Nodes
	private JsonWriter       writer;
	private JsonStreamParser parser;

	//TODO Fill in with all Node types (these arrays must be in parallel !!!)
	private static final Class[]  types = { Map.class, Node.class, Room.class };
	private static final String[] names = { "maps.json", "nodes.json", "rooms.json" };

//	public static void main(String args[]) {
	//TODO Test Parsing
//	}

	public Parser () {}

	/**
	 * toFile() is used to write an entire Graph/Maps out to a JSON database file
	 * WARNING: This will OVERWRITE the given file
	 *
	 * @param collection: Graph/Maps to write to the database
     */
	public void toFile(ICollection collection) {
		Gson gson = new Gson();
		try { //Create a JsonWriter to append the file with graph nodes
			writer = new JsonWriter(new FileWriter(filename, false));
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Collection<Struct> collect = collection.get();
		for (Struct s : collect) {
			int i = Arrays.asList(types).indexOf(s.getClass());
			filename = names[i];
			gson.toJson(s, s.getClass(), writer);
		}

		close(); //Close the writer
	}

	/**
	 * @return A Maps object
	 */
	//TODO Is this okay? (There was a previous comment about this not working)
	public Maps fromFileMap() {
		Gson gson = new Gson();
		HashMap<Integer, IMap> maps = new HashMap<>();
		Map temp;

		try { parser = new JsonStreamParser(new FileReader("maps.json")); }
		catch (FileNotFoundException e) { return null; } //This is bad - don't let this happen

		while(parser.hasNext()) {
			temp = gson.fromJson(parser.next(), Map.class);
			maps.put(temp.getID(), temp);
		}
		return new Maps(maps);
	}

	/**
	 * @return A complete Graph of all nodes from all graph files
     */
	public Graph fromFileGraph() {
		Gson gson = new Gson();
		HashMap<Integer, INode> graph = new HashMap<>();
		INode temp;

		for (int i = 0; i < types.length; i++) {
			try { parser = new JsonStreamParser(new FileReader(names[i])); }
			catch (FileNotFoundException e) { return null; } //This is bad - don't let this happen

			while(parser.hasNext()) {
				temp = (INode)gson.fromJson(parser.next(), types[i]);
				graph.put(temp.getID(), temp);
			}
		}
		return new Graph(graph);
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
