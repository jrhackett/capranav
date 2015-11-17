package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import logic.Graph;
import logic.Maps;
import logic.Node;
import logic.Parser;
import visuals.Display;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * IMPORTANT: This Class will 'launch' the application.
 *            And control the application
 */
public class Controller extends Application {
    /* visual constants */
    private static final Double WINDOW_WIDTH = 1000.0;
    private static final Double WINDOW_HEIGHT = 700.0;

    /* visual component */
    private Display myDisplay;

    /* information variables */
    //TODO delete below
    public String start;
    public String destination;
    public String[] mid_way_points;
    public int current_mid_way_points = 0;
    public int max_mid_way_points = 3;

    public ArrayList<Node> pathNodes;
    public ArrayList<String> instructions;


    /* nodes and graphs */
    private HashMap<Integer, Node> nodes;
    private Maps maps;

    private logic.Map currentMap;

    private boolean START = false;
    private boolean END = false;


    @Override
    public void start(Stage s) throws Exception {

		/* get information */
        nodesFromFile();
        mapsFromFile();


        /* icon */
        try {
            s.getIcons().add(new Image(getClass().getResourceAsStream("../images/globe.png")));
        }
        catch (NullPointerException e) {
            s.getIcons().add(new Image(getClass().getResourceAsStream("/images/globe.png")));
        }

		/* basic layout */
        //s.initStyle(StageStyle.UNDECORATED);  // <-- removes the top part of the app close/open
        s.setResizable(false);

		/* setup */
        this.myDisplay = new Display(WINDOW_WIDTH, WINDOW_HEIGHT, this);    //creates scene

        Scene display = myDisplay.Init(); //initializes scene
        s.setScene(display); //sets scene to display

        s.show();   //shows scene
    }


    /* logic methods */

    /* function that sets a new destination */
    public static void setNewDestination(Node n) {

    }




    /* function that gets a path, given two+ nodes */

    /* function for handicap path, given two+ nodes */

    /* function looking for nearest three bathrooms */

    /* function that shows three paths to food */


    public void nodeFromMapHandler(Node n){
        //check if we have a start or false
        if (myDisplay.start.getValue() == null){
            System.out.println("controller start");
            //no start, thus -> set it to n
            myDisplay.start.setValue(n);
            myDisplay.mapDisplay.setStartNode(n.getID());
        } else if (myDisplay.end.getValue() == null){
            System.out.println("controller end");
            myDisplay.end.setValue(n);
            myDisplay.mapDisplay.setStartNode(n.getID());
        } else {
            //myDisplay.mapDisplay.mapDescriptor.setText("Refresh to Click and Choose");
        }
    }


    public ArrayList<logic.Node> getPathNodes(logic.Node s, logic.Node f){
        //this.pathNodes = logic.AStarShortestPath.AStarSearch(s,f);
        //TODO make a star return an arraylist
        return null;
    }

    public ArrayList<String> getInstructions(){
        //return logic.instructionsConverter(this.pathNodes);
        return null;
    }


    /**
     * return the HashMap of Nodes that have NAMES!!
     */
    public HashMap<Integer, Node> getNamedNodesOfMap(){
        HashMap<Integer, Node> value = getNodesOfMap(this.currentMap.getID());

        nodes.forEach((k,v) -> {
            if (k !=0) {
                if (v.getName().equals("ENTER TEXT") || v.getName().equals("")) {
                    value.remove(k, v);}}});
        return  value;
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



    public void setCurrentMap(int id){
        this.currentMap = maps.getMap(id);
    }

    public Maps getMaps(){
        return this.maps;
    }

    /**
     * Load nodes to file
     * @param
     */
    private void nodesFromFile(){
        Parser test = new Parser("nodes.json");
        Graph graph = (Graph)test.fromFile();
        this.nodes = graph.getNodes();
    }

    /**
     * load maps from file
     */
    private void mapsFromFile() {
        Parser parser = new Parser("maps.json");
        this.maps = (Maps)parser.fromFile();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
