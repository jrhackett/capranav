package MapBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * This will be a strictly 'PHASE-BASED' application
 *
 * #1: Choose Map -> loads in Nodes [ADD NODE PHASE]: Clicks add node locations,
 * "no naming now" [CHANGE NAME PHASE]: Clicks selects node, allows user to set
 * name -> WRITES NODE
 *
 * [ADD EDGE PHASE]
 *
 */

public class MapBuilderController extends Application {
	/* visual constants */
	private static final Double WINDOW_WIDTH = 1000.0;
	private static final Double WINDOW_HEIGHT = 800.0;

	/* visual component */
	private MapBuilderDisplay myDisplay;

	/* information variables */
	private HashMap<Integer, INode> masterNodeList;
	private HashMap<Integer, INode> nodeList;
	private HashMap<Integer, IMap> maps;

	private ArrayList<INode> potentialEdgeNodes;

	private int currentMapID = -1;

	private int nextNodeID;
	private int nextMapID = 3335;// TODO UNIQUE

	/* current node information */
	private int selectedNodeID;

	@Override
	public void start(Stage s) throws Exception {
		this.nextNodeID = 0; // TODO FIX THIS ID INCREMENT THING
		potentialEdgeNodes = new ArrayList<>();

		mapsFromFile();
		nodesFromFile();

		// loadNodesFromFile();

		/* basic layout */
		s.setResizable(true);

		/* setup */
		// Creats the scene
		this.myDisplay = new MapBuilderDisplay(s, WINDOW_WIDTH, WINDOW_HEIGHT, this);

		Scene scene = new Scene(myDisplay, WINDOW_WIDTH, WINDOW_HEIGHT);
		s.setScene(scene); // sets scene to display
		scene.getStylesheets().add(getClass().getResource("buttonbar.css").toExternalForm());

		s.show(); // shows scene
	}

	/**
	 * sets the next node id to be the largest value
	 */
	private void setNextNodeID() {
		masterNodeList.forEach((k, v) -> {
			nextNodeID = (k > nextNodeID) ? k : nextNodeID;
			nextNodeID++;
		});
	}

	/**
	 * Given a mouse event -> gets
	 * 
	 * @param e
	 * @return ID of new Node
	 */
	public int newPathNodeAtLocation(double x, double y) {
		// TODO: get UNIQUE or next number - look into singelton
		Node newNode = new Path(this.nextNodeID, x, y, 0, x, y, 0, this.currentMapID);
		nodeList.put(this.nextNodeID, newNode);
		return this.nextNodeID++;
	}

	/**
	 * This method takes in the name, path, and pixel to foot ratio and creates
	 * a new map
	 *
	 * @param name
	 * @param path
	 * @param ratio
	 * @return
	 */
	public boolean createAndWriteNewMap(String name, String path, double ratio) {
		// TODO: Fix this to work with new classes

		/*
		 * boolean validate = validatePath(path); if (validate) { Map newMap =
		 * new Map(nextMapID, 0, 0, name, path, ratio); maps.addMap(newMap);
		 * myDisplay.chooseMap.addMapToMaps(newMap); nextMapID++; return true; }
		 * else { return false; }
		 */

		return false;
	}

	/**
	 * sets the selected node name
	 * 
	 * @param name
	 */
	public void setNodeName(String name) {
		// TODO: Fix this to work with new classes

		/*
		 * if (this.isNodeSelected()) {
		 * nodeList.get(selectedNodeID).setName(name);
		 * changeNameToIncludeMap(selectedNodeID); }
		 */
	}

	/**
	 * sets the selected node name
	 * 
	 * @param n
	 */
	public void changeNameToIncludeMap(int id) {
		// TODO: Fix this to work with new classes

		/*
		 * String mapPrefix = new String(); mapPrefix =
		 * maps.getMap(nodeList.get(id).getMap_id()).getName();
		 * nodeList.get(id).setName(mapPrefix + " " +
		 * nodeList.get(id).getName());
		 */
	}
	
	/**
	 * This takes in two nodes, the original with x, y, and edge information and the new with type specific info
	 * and combines them to form an updated node. This node is then added to the nodeList
	 * 
	 * @param originalNode ID of original node to modify type and info
	 * @param newNode New node to add with type and specific info
	 * 
	 */
	public void updateNodeType(int originalNodeID, INode newNode){
		INode originalNode = nodeList.get(originalNodeID);
		
		// set the coordinates to be the same
		newNode.setX(originalNode.getX());
		newNode.setX(originalNode.getY());
		newNode.setX(originalNode.getZ());
		
		// set the ID
		newNode.setID(originalNodeID);
		
		newNode.setAdjacencies(originalNode.getAdjacencies());
		
		nodeList.put(originalNodeID, newNode);
		
	}

	/**
	 * Make sure that the file exists and its unique
	 * 
	 * @param path
	 * @return true if it exists
	 */
	private boolean validatePath(String path) {
		// this.maps.get(currentMapID).check(path);

		try {
			// TODO make this better
			// This throws an exception if user tries to load a map that DNE
			System.out.println("PATH ATTEMPT: " + path);
			Image mapI = new Image(getClass().getResourceAsStream("../images/" + path + ".png"));
			ImageView mapV = new ImageView(mapI);
		} catch (NullPointerException e) {
			try {
				Image mapI = new Image(getClass().getResourceAsStream("/images/" + path + ".png"));
			} catch (NullPointerException f) {
				return false;
			}
		}

		return true;
	}

	/**
	 * get the maps [to display]
	 * 
	 * @return
	 */
	public HashMap<Integer, IMap> getMaps() {

		return this.maps;
	}

	/**
	 * return the HashMap of Nodes [to display][of the current map]
	 * 
	 * @param id
	 * @return
	 */
	public HashMap<Integer, INode> getNodesOfMap(int id) {

		HashMap<Integer, INode> value = new HashMap<>();

		masterNodeList.forEach((k, v) -> {
			if (v.getMap_id() == id) {
				value.put(k, v);
			}
		});
		return value;
	}

	// Same version as above, but assumes the selected map ID
	public HashMap<Integer, INode> getNodesOfMap() {
		return getNodesOfMap(this.currentMapID);
	}

	public HashMap<Integer, INode> getCurrentNodeList() {
		return nodeList;
	}

	/**
	 * Sets the current map to id
	 * 
	 * @param id
	 */
	public void setCurrentMap(int id) {

		this.currentMapID = id;
		this.nodeList = getNodesOfMap();
		cleanNodeList(this.nodeList);

		setNextNodeID();

	}

	/**
	 * Gets the node given a key
	 * 
	 * @param id
	 * @return node
	 */
	public INode getNode(int id) {
		return nodeList.get(id);
	}

	/**
	 * Write nodes to file
	 * 
	 * @param
	 */
	public void nodesToFile() {
		// First, remove all the nodes that were deleted
		// AKA: are present in the old list but not in the new one
		Integer[] keyList = masterNodeList.keySet().toArray(new Integer[masterNodeList.keySet().size()]);
		for (int i = 0; i < keyList.length; i++) {
			if (masterNodeList.containsKey(keyList[i]) && !nodeList.containsKey(keyList[i])) {
				masterNodeList.remove(keyList[i]);
			}
		}

		// Remove any rouge edges in the masterNodeList
		cleanNodeList(masterNodeList);

		// Update all the nodes that could have been changed
		nodeList.forEach((k, v) -> {
			masterNodeList.put(k, v);
		});

		// send this masterNodeList to the file
		Parser parser = new Parser();
		parser.toFile(masterNodeList);
	}

	/**
	 * Load nodes to file
	 * 
	 * @param
	 */
	public void nodesFromFile() {
		this.masterNodeList = new Parser<INode>().fromFileGraph();
		
		
	}

	/**
	 * writes maps to file
	 */
	public void mapsToFile() {
		/*
		 * Parser parser = new Parser("maps.json"); parser.toFile(this.maps);
		 */
	}

	/**
	 * load maps from file
	 */
	public void mapsFromFile() {
		this.maps = new Parser<IMap>().fromFileMap();

	}

	public static void main(String[] args) {
		launch(args);
	}

	// Select a certain node for adding edges or modifying information
	public void selectNode(int id) {
		this.selectedNodeID = id;
	}

	// Deselect the current node
	public void deselectNode() {
		this.selectedNodeID = -1;
	}

	// Returns true if a node is currently selected
	// Otherwise return false
	public boolean isNodeSelected() {
		if (this.selectedNodeID == -1) {
			return false;
		} else {
			return true;
		}
	}

	// This gets the selected node
	public INode getSelectedNode() {
		return getNode(this.selectedNodeID);
	}

	// This gets the selected node
	public int getSelectedNodeID() {
		return this.selectedNodeID;
	}

	// This deletes a given node
	public void deleteNode(int id) {

		// Remove the given node
		nodeList.remove(id);

		// Then clean up the lingering edges
		cleanNodeList(nodeList);
	}

	// This method cleans the orphan edges who's target is not a node in the
	// hashmap
	private void cleanNodeList(HashMap<Integer, INode> input) {

		// Iterate through the entire hashmap
		// If any edge in any node points to a node that isn't in the hashmap
		// Remove that edge.
		Iterator<HashMap.Entry<Integer, INode>> nodeIterator = input.entrySet().iterator();
		while (nodeIterator.hasNext()) {
			HashMap.Entry<Integer, INode> nodeEntry = nodeIterator.next();

			for (int i = 0; i < getNode(nodeEntry.getKey()).getAdjacencies().size(); i++) {
				Edge currentEdge = getNode(nodeEntry.getKey()).getAdjacencies().get(i);

				if (!input.containsKey(currentEdge.getTarget())) {
					nodeEntry.getValue().getAdjacencies().remove(currentEdge);
				}
			}
		}
	}
}
