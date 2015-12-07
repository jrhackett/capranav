package MapBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.*;

import java.util.*;

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
	private static final Double WINDOW_WIDTH = 1425.0;
	private static final Double WINDOW_HEIGHT = 600.0;

	/* visual component */
	private MapBuilderDisplay myDisplay;

	/* information variables */
	private HashMap<Integer, Building> buildings;
	private HashMap<Integer, INode> masterNodeList;
	private HashMap<Integer, INode> nodeList;
	private HashMap<Integer, IMap> maps;

	private ArrayList<INode> potentialEdgeNodes;

	private int currentMapID = 0;

	private int nextNodeID;
	private int nextMapID = 7;// TODO UNIQUE

	/* current node information */
	private int selectedNodeID;

	/* map two stuff */
	private int currentMapIDTwo = 0;
	private HashMap<Integer, INode> secondaryMapNodeList;


	public boolean SNAPPING = true;


	@Override
	public void start(Stage s) throws Exception {
		this.nextNodeID = 0; // TODO FIX THIS ID INCREMENT THING
		potentialEdgeNodes = new ArrayList<>();

		mapsFromFile();
		nodesFromFile();
		buildingsFromFile();

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


	public void  setNodeInformation(INode node){
		this.myDisplay.setNodeLabel(node.getClass().toString() + "  |  " + node.toString() + "  |  " + node.getID());
	}

	public void setEdgeInformation(String s){
		this.myDisplay.setEdgeLabel(s);
	}


	public static boolean ugh = true;

	/**
	 * sets the next node id to be the largest value
	 */
	private void setNextNodeID() {
		/*
		if (ugh) {
			nextNodeID = 4000;
			ugh = false;
	*/

//		if (ugh) {
//			nextNodeID = 3000;
//			ugh = false;
//		}
//		else {

		masterNodeList.forEach((k, v) -> {
			nextNodeID = (k > nextNodeID) ? k : nextNodeID;
		});
		nextNodeID++;

	}
	//}

	/**
	 * Given a mouse event -> gets
	 *
	 * @return ID of new Node
	 */
	public int newPathNodeAtLocation(double x, double y) {
		// TODO: get UNIQUE or next number - look into singelton
		INode newNode = new Path(this.nextNodeID, x, y, 0, x, y, 0, this.currentMapID);
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

	/**
	 * return the HashMap of Nodes [to display][of the current map]
	 *
	 * @param id
	 * @return
	 */
	public HashMap<Integer, INode> getNodesOfBuilding(int id) {

		HashMap<Integer, INode> value = new HashMap<>();

		masterNodeList.forEach((k, v) -> {
			if (maps.get(v.getMap_id()).getBuildingID() == id) {
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

		//cleanNodeList(this.nodeList);

		setNextNodeID();

	}


	public HashMap<Integer, INode> getSecondaryNodeList(){
		return secondaryMapNodeList;
	}

	public void setCurrentMapTwo(int id) {

		this.currentMapIDTwo = id;
		this.secondaryMapNodeList =	new HashMap<>();

		masterNodeList.forEach((k,v) ->{
			if (v.isTransition() && v.getMap_id() == id){
				secondaryMapNodeList.put(k,v);
			}
		});

		//cleanNodeList(this.nodeList);
		//setNextNodeID();
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
	 * This method saves the nodes in nodeList to masterNodeList
	 */
	public void saveNodesToMaster(){
		if(nodeList != null){



			// First, remove all the nodes that were deleted
			// AKA: are present in the old list but not in the new one
			Integer[] keyList = masterNodeList.keySet().toArray(new Integer[masterNodeList.keySet().size()]);
			for (int i = 0; i < keyList.length; i++) {
				if (masterNodeList.containsKey(keyList[i]) && masterNodeList.get(keyList[i]).getMap_id() == currentMapID
						&&!nodeList.containsKey(keyList[i])) {
					masterNodeList.remove(keyList[i]);
				}
			}

			// Remove any rouge edges in the masterNodeList
			// cleanNodeList(masterNodeList);

			// Update all the nodes that could have been changed
			nodeList.forEach((k, v) -> {
				masterNodeList.put(k, v);

				if(v.isTransition()){
					//System.out.println("Transition node found");
				}
			});
		}
	}


	/**
	 * Write nodes to file
	 * 
	 * @param
	 */
	public void nodesToFile() {
		saveNodesToMaster();

		for (java.util.Map.Entry<Integer, INode> nodeEntry : masterNodeList.entrySet()){
			if (nodeEntry.getValue().isTransition()){
				for(Edge e : nodeEntry.getValue().getAdjacencies()){
					try {
						if (masterNodeList.get(e.getTarget()).isTransition()) {
							((Transition) nodeEntry.getValue()).setBuildingID(maps.get(masterNodeList.get(e.getTarget()).getMap_id()).getBuildingID());
							((Transition) nodeEntry.getValue()).setToFloor(maps.get(masterNodeList.get(e.getTarget()).getMap_id()).getFloor());

							System.out.println("Node Id: " + nodeEntry.getKey() + ", Builder Id:" + ((Transition) nodeEntry.getValue()).getBuildingID() +  ",  Floor number: " +((Transition) nodeEntry.getValue()).getToFloor());
							break;
						}
					} catch (NullPointerException z){
						System.out.println("ERROR WITH NODES, SOME EDGES LOST IN GIT");
					}
				}
			}
		}


		/*this.buildings.forEach((k,v) -> {
			v.translateBuilding(getNodesOfBuilding(k), masterNodeList).forEach((key,value) ->{
				masterNodeList.put(key, value);
			});
		});/*/

		Parser parser = new Parser<INode>();
		parser.toFile(masterNodeList);
	}

	private void buildingsFromFile(){
		buildings = new Parser<Building>().fromFileBuilding();
	}


	/**
	 * Load nodes to file
	 * 
	 * @param
	 */
	public void nodesFromFile() {
		this.masterNodeList = new Parser<INode>().fromFileGraph();
		
		//int firstKey = masterNodeList.keySet()[0];
		masterNodeList.remove(0);

		/*
		
		System.out.println("All nodes form file are shown below:");
		
		masterNodeList.forEach((k,v) -> {
			System.out.println(v + ", " + v.getAdjacencies().size());
			
		});
		
		System.out.println("Nodes complete");
		System.out.println("");*/
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

	// This adds a given node to the nodeList hashmap
	public void addNode(INode iNode){
		nodeList.put(iNode.getID(), iNode);
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
