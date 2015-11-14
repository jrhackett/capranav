package MapBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private HashMap<Integer, Node> nodes;   //all nodes
    private HashMap<Integer, Map> maps;     //all maps

    private HashMap<Integer, Node> nodesForCurrentMap;

    private int currentMap;
    private int currentNode;
    private int nextNodeID;
    private int nextMapID;



    /* current node information */
    private String currentNodeName;
    private double currentNodeX;
    private double currentNodeY;
    private double currentNodeZ = 0;



    @Override
    public void start(Stage s) throws Exception {
        this.nextNodeID = 17;

        //TODO: On load, get information:
        loadMapsFromFile();
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

    public boolean createAndWriteNewMap(String name, String path, double ratio){
        boolean validate = validatePath(path);
        if (validate){
            Map newMap = new Map(nextMapID, 0, 0, name, path, ratio);
            maps.put(nextMapID, newMap);
            nextMapID++;
            return  true;
        } else {
            return false;
        }
    }

    private boolean validatePath(String path){
        System.out.println("VALIDATING PATH!");
        //checking if this map exists
        ArrayList<String> paths = new ArrayList<>();
        maps.forEach((k,v) -> {
             paths.add(v.getPath());
        });

        for (String s : paths) {
            if (s.equals(path)){
                System.out.println("Map Already Exists!");
                return false;
            }
        }

        //checking if we can find this patj
        try {
            //TODO make this better
            System.out.println("PATH ATTEMPT: " + path);
            Image mapI = new Image(getClass().getResourceAsStream("../images/" + path + ".png"));
            ImageView mapV = new ImageView(mapI);
        } catch (NullPointerException e){
            System.out.println(e);
            return false;
        }
        return  true;
    }


    //load and set the ArrayList of Maps [using parser]
    public void loadMapsFromFile(){
        //TODO complete this!
        //gets and creates maps currently written

        //garb tester:
        this.maps = new HashMap<>();
        maps.put(0, new Map (11,1,2,"Campus Center","wpi-campus-map", 1));
        maps.put(1, new Map (99,1,2,"Project Center Floor 1","project_center_floor_1_redo_1024", 1));
    }

    //return the ArrayList of Maps [to display]
    public HashMap<Integer, logic.Map> getMaps(){
        return maps;
    }

    //load and set the HashMap of Nodes
    public void loadNodesFromFile(){
        //TODO complete this!
        //gets and creates maps currently written


        //below is tester garb
        nodes = new HashMap<Integer, Node>();
        Node n1 = new Node("Institute",0, 0, 0, 0, 11);
        Node n2 = new Node("RecCenter",1, 10, 10, 0, 11);
        Node n3 = new Node("Field",2, 0, 20, 10, 99);
        Node n4 = new Node("Harrington",3, 3, 10, 20, 11);
        Node n5 = new Node("Quad",4, 55, 34, 76, 11);
        Node n6 = new Node("Morgan",5, 55, 44, 66, 99);
        Node n7 = new Node("Riley",6, 11, 88, 55, 11);
        Node n8 = new Node("Higgins Labs",7, 90, 53, 0, 99);
        Node n9 = new Node("Campus Center",8, 100, 250, 0, 99);
        Node n10 = new Node("Fountain",9, 106, 170, 0, 11);
        Node n11 = new Node("Alden",10, 166, 53, 0, 99);
        Node n12 = new Node("West Street",11, 168, 8, 0, 11);
        Node n13 = new Node("Library",12, 260, 260, 0, 11);
        Node[] tester = {n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13};
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

        this.nodesForCurrentMap = value;
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
     * Gets the node given a key
     * @param id
     * @return node
     */
    public Node getNode(int id){
        return nodes.get(id);
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
    public boolean writeOut(){
       //TODO: write Maps to map file

        //TODO: clear old file, and write nodes to new one
        //TODO: either manually or via code save backups

        return  true;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
