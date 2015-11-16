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

/**
 * This will be a strictly 'PHASE-BASED' application
 *
 * #1: Choose Map
 *      -> loads in Nodes
 * [ADD NODE PHASE]: Clicks add node locations, "no naming now"
 * [CHANGE NAME PHASE]: Clicks selects node, allows user to set name
 * -> WRITES NODE
 *
 * [ADD EDGE PHASE]
 *
 */

public class MapBuilderController extends   Application {
    /* visual constants */
    private static final Double WINDOW_WIDTH = 1000.0;
    private static final Double WINDOW_HEIGHT = 1000.0;

    /* visual component */
    private MapBuilderDisplay myDisplay;

    /* information variables */
    private HashMap<Integer, Node> nodes;   //all nodes
    //private HashMap<Integer, Map> maps;     //all maps
    private Maps maps;

    private ArrayList<Node> potentialEdgeNodes;

    private int currentMap;
    private int currentNode;
    private int nextNodeID;
    private int nextMapID;



    /* current node information */
    public boolean SELECTED = false; //Describes if a node has been selected
    public Node selectedNode;



    @Override
    public void start(Stage s) throws Exception {
        this.nextNodeID = 17; //TODO FIX THIS ID INCREMENT thING
        potentialEdgeNodes = new ArrayList<>();

        mapsFromFile();
        //nodesFromFile();
        loadNodesFromFile();

		/* basic layout */
        s.setResizable(false);

		/* setup */
        this.myDisplay = new MapBuilderDisplay(WINDOW_WIDTH, WINDOW_HEIGHT, this);    //creates scene

        Scene scene = new Scene(myDisplay, WINDOW_WIDTH, WINDOW_HEIGHT);
        s.setScene(scene); //sets scene to display
        scene.getStylesheets().add(getClass().getResource("buttonbar.css").toExternalForm());

        s.show();   //shows scene
    }


    /**
     * Given a mouse event -> gets
     * @param e
     * @return ID of new Node
     */
    public int newNodeAtLocation(MouseEvent e){
        double x = e.getX() - 3.5;
        double y = e.getY() - 2.5;
        System.out.println("X: " + x);
        System.out.println("Y: " + y);
        //TODO: get UNIQUE or next number - look into singelton
        Node newNode = new Node("ENTER TEXT", this.nextNodeID, x, y, 0, this.currentMap);
        nodes.put(this.nextNodeID, newNode);
        return this.nextNodeID++;
    }

    /**
     *
     * @param name
     * @param path
     * @param ratio
     * @return
     */
    public boolean createAndWriteNewMap(String name, String path, double ratio){
        boolean validate = validatePath(path);
        if (validate){
            Map newMap = new Map(nextMapID, 0, 0, name, path, ratio);
            maps.addMap(newMap);
            myDisplay.chooseMap.addMapToMaps(newMap);
            nextMapID++;
            return  true;
        } else {
            return false;
        }
    }

    /**
     * sets the selected node name
     * @param name
     */
    public void setNodeName(String name){
        if (SELECTED) {
            selectedNode.setName(name);
        }
    }


    /**
     * Make sure that the file exists and its unique
     * @param path
     * @return true if it exists
     */
    private boolean validatePath(String path){
        System.out.println("VALIDATING PATH!");
        this.maps.check(path);

        try {
            //TODO make this better
            //This throws an exception if user tries to load a map that DNE
            System.out.println("PATH ATTEMPT: " + path);
            Image mapI = new Image(getClass().getResourceAsStream("../images/" + path + ".png"));
            ImageView mapV = new ImageView(mapI);
        } catch (NullPointerException e){
            System.out.println(e);
            return false;
        }

        return true;
    }


    /**
     * loads maps from file
     */
    public void loadMapsFromFile(){
        //TODO complete this!
        //gets and creates maps currently written



        //garb tester:
        this.maps = new Maps();
        maps.addMap(new Map (11,1,2,"Campus Center","wpi-campus-map", 1));
        maps.addMap(new Map (99,1,2,"Project Center Floor 1","project_center_floor_1_redo_1024", 1));

        mapsFromFile();
    }

    /**
     * get the maps [to display]
     * @return
     */
    public Maps getMaps(){
        return this.maps;
    }

    /**
     * load Nodes from file
     */
    public void loadNodesFromFile(){
        //gets and creates maps currently written
        //TODO: uncomment this block and delete dummy stuff to work with file
        /*
        Parser test = new Parser("nodes.json");
        Graph graph = test.fromFile();
         */

        //below is tester garb
        nodes = new HashMap<Integer, Node>();
        //Node n1 = new Node("Institute",0, 0, 0, 0, 11);
        //Node n2 = new Node("RecCenter",1, 10, 10, 0, 11);
        //Node n3 = new Node("Field",2, 0, 20, 10, 99);
        //Node n4 = new Node("Harrington",3, 3, 10, 20, 11);
        Node n5 = new Node("Quad",4, 55, 34, 76, 11);
        Node n6 = new Node("Morgan",5, 55, 44, 66, 99);
        Node n7 = new Node("Riley",6, 11, 88, 55, 11);
        Node n8 = new Node("Higgins Labs",7, 90, 53, 0, 99);
        Node n9 = new Node("Campus Center",8, 100, 250, 0, 99);
        Node n10 = new Node("Fountain",9, 106, 170, 0, 11);
        Node n11 = new Node("Alden",10, 166, 53, 0, 99);
        Node n12 = new Node("West Street",11, 168, 8, 0, 11);
        Node n13 = new Node("Library",12, 260, 260, 0, 11);
        Node[] tester = {n5, n6, n7, n8, n9, n10, n11, n12, n13};
        for (Node n : tester){
            nodes.put(n.getID(), n);
        }

    }

    /**
     * return the HashMap of Nodes [to display][of the current map]
     * @param id
     * @return
     */
    public HashMap<Integer, Node> getNodesOfMap(int id){
        HashMap<Integer, Node> value = new HashMap<>();

        nodes.forEach((k,v) -> {
            if(v.getMap_id() == id){
                value.put(k,v);
            }
        });
        return value;
    }


    /**
     * Sets the current map to id
     * @param id
     */
    public void setCurrentMap(int id){

        this.currentMap = id;
        //getNodesOfMap(id);
    }

    /**
     * Gets the node given a key
     * @param id
     * @return node
     */
    public Node getNode(int id){ return nodes.get(id);}

    /**
     * adds a node to the arraylist of node for potential edges
     */
    public void addPotentialEdge(Node node){
        //validate
        node.getAdjacencies().contains(node);
        potentialEdgeNodes.contains(node);
        potentialEdgeNodes.add(node);
    }

    /**
     * resets the potential edges
     */
    public void resetPotentialEdges(){
        this.potentialEdgeNodes = new ArrayList<>();
    }

    /**
     * Adds edges. NOTE: currently weight is defaulted to 1
     */
    public boolean addEdges(){
        for (Node n : potentialEdgeNodes){
            nodes.get(selectedNode.getID()).addEdge(new Edge(n, 1));
            //selectedNode.getID().addEdge(new Edge(n, 1));
        }
        return true;
    }

    /**
     * Write nodes to file
     * @param
     */
    public void nodesToFile(){
        Parser parser = new Parser("nodes.json");
        parser.toFile(new Graph(this.nodes));
    }

    /**
     * Load nodes to file
     * @param
     */
    public void nodesFromFile(){
        Parser test = new Parser("nodes.json");
        Graph graph = (Graph)test.fromFile();
        this.nodes = graph.getNodes();
    }

    /**
     * writes maps to file
     */
    public void mapsToFile() {
        Parser parser = new Parser("maps.json");
        parser.toFile(this.maps);
    }

    /**
     * load maps from file
     */
    public void mapsFromFile() {
        Parser parser = new Parser("maps.json");
        this.maps = (Maps)parser.fromFile();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
