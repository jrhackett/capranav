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
    private HashMap<Integer, Node> nodes;
    private Maps maps;

    private ArrayList<Node> potentialEdgeNodes;

    private int currentMap;

    private int nextNodeID;
    private int nextMapID  = 3335;//TODO UNIQUE



    /* current node information */
    public boolean SELECTED = false; //Describes if a node has been selected
    public Node selectedNode;



    @Override
    public void start(Stage s) throws Exception {
        this.nextNodeID = 0; //TODO FIX THIS ID INCREMENT thING
        potentialEdgeNodes = new ArrayList<>();

        mapsFromFile();
        nodesFromFile();

        setNextNodeID();


        //loadNodesFromFile();

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
     * sets the next node id to be the largest value
     */
    private void setNextNodeID(){
        nodes.forEach((k,v) -> {
            nextNodeID = (k > nextNodeID) ? k : nextNodeID;
            nextNodeID++;
        });
    }
    /**
     * Given a mouse event -> gets
     * @param e
     * @return ID of new Node
     */
    public int newNodeAtLocation(MouseEvent e){
        double x = e.getX();
        double y = e.getY();
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
            changeNameToIncludeMap(selectedNode);
        }
    }

    /**
     * sets the selected node name
     * @param n
     */
    public void changeNameToIncludeMap(Node n){
        String mapPrefix = new String();
        mapPrefix = maps.getMap(n.getMap_id()).getName();
        n.setName(mapPrefix + " " + n.getName());
    }



    /**
     * Make sure that the file exists and its unique
     * @param path
     * @return true if it exists
     */
    private boolean validatePath(String path){
        this.maps.check(path);

        try {
            //TODO make this better
            //This throws an exception if user tries to load a map that DNE
            System.out.println("PATH ATTEMPT: " + path);
            Image mapI = new Image(getClass().getResourceAsStream("../images/" + path + ".png"));
            ImageView mapV = new ImageView(mapI);
        } catch (NullPointerException e){
            try {
                Image mapI = new Image(getClass().getResourceAsStream("/images/" + path + ".png"));
            }
            catch (NullPointerException f) {
                return false;
            }
        }

        return true;
    }

    /**
     * get the maps [to display]
     * @return
     */
    public Maps getMaps(){
        return this.maps;
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
        if (!potentialEdgeNodes.contains(node)) {
            boolean check = true;
            for(Edge e : selectedNode.getAdjacencies()) {
                if(e.getTarget() == node.getID()) {
                    check = false;
                }
            }
            if(check)
                potentialEdgeNodes.add(node);
        }
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
            nodes.get(selectedNode.getID()).addEdge(new Edge(n.getID(), 1));
            /* below should add the edge both ways */
            boolean check = true;
            for(Edge e : n.getAdjacencies()) {
                if(e.getTarget() == selectedNode.getID()) {
                    check = false;
                }
            }
            if(check)
                n.addEdge(new Edge(selectedNode.getID(), 1));
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
