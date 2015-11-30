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

/** A Parser object is used to do translations to/from JSON files and Nodes/Maps/Buildings.
 *
 * USAGE: Struct should be INode, IMap, or Building
 * 		  new Parser<INode>().fromFileGraph();       - Returns HashMap<Integer, INode>
 * 		  new Parser<IMap>().fromFileMap();          - Returns HashMap<Integer, IMap>
 * 		  new Parser<Building>().fromFileBuilding(); - Returns HashMap<Integer, Building>
 * 		  new Parser<Struct>().toFile(HashMap<Integer, Struct> collection); - Stores into files based on type
 */
public class Parser<Struct>
{
	//Don't need to set any of these anymore - empty constructor
	private String           filename; //File for reading/writing Nodes
	private JsonWriter       writer;
	private JsonStreamParser parser;

	//These two sets of arrays must be in parallel !
	private static final Class[]  mTypes = { Campus.class, Floor.class };
	private static final String[] mNames = { "campus.json", "floor.json" };
	private static final Class[]  nTypes = { Bathroom.class, Elevator.class, Food.class, Landmark.class, Path.class,
											 Room.class, Stairs.class, TStairs.class  };
	private static final String[] nNames = { "bathroom.json", "elevator.json", "food.json", "landmark.json", "path.json",
											 "room.json", "stair.json", "tstair.json" };

	public Parser() {}

	public static void main(String args[]) {
		Campus c = new Campus(1, "Path", 0.13);
		Floor f = new Floor(2, "Floor", 0.13, 3, 4);

		Bathroom b = new Bathroom(5, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, "Bathroom");
		Elevator e = new Elevator(12, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0);
		Food foo = new Food(19, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, "Food");
		Landmark l = new Landmark(26, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, "Landmark");
		Path p = new Path(33, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0);
		Room r = new Room(40, 41.0, 42.0, 43.0, 44.0, 45.0, 46.0, "Room");
		Stairs s = new Stairs(47, 48.0, 49.0, 50.0, 51.0, 52.0, 53.0);
		TStairs t = new TStairs(54, 55.0, 56.0, 57.0, 58.0, 59.0, 60.0);

		HashMap<Integer, Campus> ch = new HashMap<>();
		ch.put(c.getID(), c);

		HashMap<Integer, Floor> fh = new HashMap<>();
		fh.put(f.getID(), f);

		HashMap<Integer, Bathroom> bh = new HashMap<>();
		bh.put(b.getID(), b);

		HashMap<Integer, Elevator> eh = new HashMap<>();
		eh.put(e.getID(), e);

		HashMap<Integer, Food> fooh = new HashMap<>();
		fooh.put(foo.getID(), foo);

		HashMap<Integer, Landmark> lh = new HashMap<>();
		lh.put(l.getID(), l);

		HashMap<Integer, Path> ph = new HashMap<>();
		ph.put(p.getID(), p);

		HashMap<Integer, Room> rh = new HashMap<>();
		rh.put(r.getID(), r);

		HashMap<Integer, Stairs> sh = new HashMap<>();
		sh.put(s.getID(), s);

		HashMap<Integer, TStairs> th = new HashMap<>();
		th.put(t.getID(), t);
		/*
		new Parser<Campus>().toFile(ch);
		new Parser<Floor>().toFile(fh);
		new Parser<Bathroom>().toFile(bh);
		new Parser<Elevator>().toFile(eh);
		new Parser<Food>().toFile(fooh);
		new Parser<Landmark>().toFile(lh);
		new Parser<Path>().toFile(ph);
		new Parser<Room>().toFile(rh);
		new Parser<Stairs>().toFile(sh);
		new Parser<TStairs>().toFile(th);*/

		HashMap<Integer, IMap> maps = new Parser<>().fromFileMap();
		HashMap<Integer, INode> nodes = new Parser<>().fromFileGraph();
		System.out.println(maps.toString());
		System.out.println(nodes.toString());
		System.out.println("SUCCESS !");
	}

	/**
	 * toFile() is used to write an entire HashMap of Struct out to a JSON database file
	 * WARNING: This will OVERWRITE the given file
	 *
	 * @param collection: HashMap of Nodes/Maps/Buildings to write to the database
     */
	public void toFile(HashMap<Integer, Struct> collection) {
		Gson gson = new Gson();

		Collection<Struct> vals = collection.values();
		for (Struct s : vals) {
			int m = Arrays.asList(mTypes).indexOf(s.getClass());
			int n = Arrays.asList(nTypes).indexOf(s.getClass());

			//Set filename to the correct thing, based on Struct s
			if      (m != -1) filename = mNames[m];
			else if (n != -1) filename = nNames[n];
			else 			  filename = "building.json";

			//Write s out to the correct file
			try { writer = new JsonWriter(new FileWriter(filename, false)); }
			catch (IOException e) { return; } //Bad bad bad
			gson.toJson(s, s.getClass(), writer);
			close(); //Close the writer
		}
	}

	/* Each of the below methods return what's expected... */

	public HashMap<Integer, IMap> fromFileMap() {
		Gson gson = new Gson();
		HashMap<Integer, IMap> maps = new HashMap<>();
		IMap temp;

		for (int i = 0; i < mTypes.length; i++) {
			try { parser = new JsonStreamParser(new FileReader(mNames[i])); }
			catch (FileNotFoundException e) { return null; } //This is bad - don't let this happen

			while(parser.hasNext()) {
				temp = (IMap)gson.fromJson(parser.next(), mTypes[i]);
				maps.put(temp.getID(), temp);
			}
		}
		return maps;
	}

	public HashMap<Integer, Building> fromFileBuilding() {
		Gson gson = new Gson();
		HashMap<Integer, Building> builds = new HashMap<>();
		Building temp;

		try {
			parser = new JsonStreamParser(new FileReader("building.json"));
		}
		catch (IOException e) {
			e.printStackTrace();
			return null; //Bad bad bad
		}

		while (parser.hasNext()) {
			temp = gson.fromJson(parser.next(), Building.class);
			builds.put(temp.getID(), temp);
		}
		return builds;
	}


	public HashMap<Integer, INode> fromFileGraph() {
		Gson gson = new Gson();
		HashMap<Integer, INode> graph = new HashMap<>();
		INode temp;

		for (int i = 0; i < nTypes.length; i++) {
			try { parser = new JsonStreamParser(new FileReader(nNames[i])); }
			catch (FileNotFoundException e) { return null; } //This is bad - don't let this happen

			while(parser.hasNext()) {
				temp = (INode)gson.fromJson(parser.next(), nTypes[i]);
				graph.put(temp.getID(), temp);
			}
		}
		return graph;
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
