package MapBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Edge;
import logic.Map;
import logic.Node;

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
 * TODO: consider order of these!
 *
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
    private ArrayList<Map> maps;

    private int currentMap;
    private int currentNode;

    /* current node information */
    private String currentNodeName;
    private double currentNodeX;
    private double currentNodeY;
    private double currentNodeZ = 0;



    @Override
    public void start(Stage s) throws Exception {


        //TODO: On load, get information:
        //TODO: ArrayList<Maps>
        //TODO: HashMap<Key,Nodes>


		/* basic layout */
        //s.initStyle(StageStyle.UNDECORATED);  // <-- removes the top part of the app close/open
        s.setResizable(false);

		/* setup */
        this.myDisplay = new MapBuilderDisplay(WINDOW_WIDTH, WINDOW_HEIGHT, this);    //creates scene

        Scene scene = new Scene(myDisplay, WINDOW_WIDTH, WINDOW_HEIGHT);
        s.setScene(scene); //sets scene to display

        s.show();   //shows scene
    }

    //load and set the ArrayList of Maps
    public void loadMapsFromFile(){
        //TODO complete this!
        //gets and creates maps currently written
    }

    //return the ArrayList of Maps [to display]
    public ArrayList<logic.Map> getMaps(){
        //TODO do this!
        ArrayList<logic.Map> maps1 = new ArrayList<>();
        maps1.add(new Map (0,1,2,"Campus Center","wpi-campus-map"));
        maps1.add(new Map (0,1,2,"Campus Map","wpi-campus-map"));
        maps1.add(new Map (0,2,3, "Trash Map", "wpi-campus-map"));

        return maps1;
    }

    //load and set the ArrayList of Maps
    public void loadNodesFromFile(){
        //TODO complete this!
        //gets and creates maps currently written
    }

    /**
     * return the HashMap of Nodes [to display][of the current map]
     * @param id
     * @return
     */
    public HashMap<Integer, Node> getNodesOfMap(int id){
        HashMap<Integer, Node> value = null;

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
    }

    /**
     * Gets the current map id
     * @param
     */
    public int getCurrentMap(){
        return this.currentMap;
    }

    /**
     * Sets the current node to id
     * @param id: id of new node
     */
    public boolean setCurrentNode(int id){
        if (nodes.containsKey(id)){
            //probably don't want to edit it old nodes
            //TODO: consider when adding a node to a 'completed' map, how to add edges both ways

            return false;
        } else {
            //nodes.put(id, new Node());
            //we'll add when we complete/save
        }

        this.currentNode = id;
        return true;
    }

    /**
     * Gets the current node id
     */
    public int getCurrenNode(){
        return this.currentNode;
    }


    /**
     * Adds edges. NOTE: currently weight is defaulted to 1
     * @param toNodes: the nodes to be added as edges
     */
    public boolean addEdges(ArrayList<Node> toNodes){
        for (Node n : toNodes){
            nodes.get(currentNode).addEdge(new Edge(n, 1));
        }
        return true;
    }

    /**
     * Save/Write node
     * @param
     */
    public boolean writeNode(){
        //create the actual node object
        Node newNode = new Node(currentNodeName, currentNode, currentNodeX, currentNodeY, currentNodeZ, currentMap);

        //TODO: write it to file

        nodes.put(currentNode, newNode);

        return true;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
