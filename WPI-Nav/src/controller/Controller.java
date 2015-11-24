package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.*;
import visuals.Display;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * IMPORTANT: This Class will 'launch' the application.
 *            And control the application
 */
public class Controller extends Application {
    /* visual constants */
    private static final Double WINDOW_WIDTH = 1400.0;
    private static final Double WINDOW_HEIGHT = 700.0;

    /* visual component */
    private Display myDisplay;

    /* information variables */
    public ArrayList<Node> pathNodes;        /* this is set then used to get instructions from logic.Directions */

    /* nodes and graphs */
    private HashMap<Integer, Node> nodes;    /* all the nodes */
    private Maps maps;                       /* information of the maps */
    private logic.Map currentMap;            /* current map being used */

    /* switches */
    private boolean FIRST = false; //if the last thing to be set was first
    public boolean FLAG = true;


    public logic.Node startNode;
    public logic.Node endNode;


    @Override
    public void start(Stage s) throws Exception {

		/* get information */
        nodesFromFile();
        mapsFromFile();

        /* icon */
      /*  try {
            s.getIcons().add(new Image(getClass().getResourceAsStream("../images/globe.png")));
        }
        catch (NullPointerException e) {
            s.getIcons().add(new Image(getClass().getResourceAsStream("/images/globe.png")));
        }*/

		/* basic layout */
        //s.initStyle(StageStyle.UNDECORATED);  // <-- removes the top part of the app close/open
        s.setResizable(true);
       // s.setTitle("CapraNav");

		/* setup */
        this.myDisplay = new Display(WINDOW_WIDTH, WINDOW_HEIGHT, this);    //creates scene
        Scene display = myDisplay.Init(); //initializes scene
        s.setScene(display); //sets scene to display
        display.getStylesheets().add(getClass().getResource("../visuals/style.css").toExternalForm());
        s.show();   //shows scene
    }

    public void reset(){
        this.currentMap = new Map();
        this.pathNodes = new ArrayList<>();
    }

    public void resetStartEnd(){
        this.startNode = null;
        this.endNode = null;
        this.myDisplay.start.getSelectionModel().clearSelection();
        this.myDisplay.end.getSelectionModel().clearSelection();

    }

    public void nodeFromMapHandler(Node n){
        //check if we have a start or false
        if (myDisplay.start.getValue() == null){
            //if (validateNotEquality(n, (Node)myDisplay.start.getValue())) {
                //no start, thus -> set it to n
                this.FLAG = false;
                myDisplay.start.addNode(n);
                myDisplay.start.setValue(n);
                this.FLAG = true;
                myDisplay.mapDisplay.setStartNode(n.getID(), true);
                this.startNode = n;
           // }
        } else if (myDisplay.end.getValue() == null){
           // if(validateNotEquality(n,(Node)myDisplay.end.getValue())) {
                this.FLAG = false;
                myDisplay.end.addNode(n);//// TODO: 11/18/15
                myDisplay.end.setValue(n);
                this.FLAG = true;
                myDisplay.mapDisplay.setStartNode(n.getID(), false);
                this.endNode = n;
          //  }
        } else if (!FIRST){
            if(validateNotEquality(n,(Node)myDisplay.start.getValue())) {
                //no start, thus -> set it to n
                this.FLAG = false;
                myDisplay.start.addNode(n);//// TODO: 11/18/15
                myDisplay.start.setValue(n);
                this.FLAG = true;
                myDisplay.mapDisplay.setStartNode(n.getID(), true);
                FIRST = true;
                this.startNode = n;
            }
            //myDisplay.mapDisplay.mapDescriptor.setText("Refresh to Click and Choose");
        } else {
            if(validateNotEquality(n,(Node)myDisplay.end.getValue())) {
                this.FLAG = false;
                myDisplay.end.addNode(n);//// TODO: 11/18/15  
                myDisplay.end.setValue(n);
                this.FLAG = true;
                myDisplay.mapDisplay.setStartNode(n.getID(), false);
                FIRST = false;
                this.endNode = n;
            }
        }
    }

    private boolean validateNotEquality(Node n, Node m){
        if (n.getID() == m.getID()){
            return false;
        } else {
            return true;
        }
    }


    public ArrayList<logic.Node> getPathNodes(logic.Node s, logic.Node f){
        this.pathNodes = AStarShortestPath.AStarSearch(s,f, nodes);
        return pathNodes;
    }

    /**
     * gets the instructions by via pathNodes set by getPathNodes
     * @return an ArrayList<String?
     */
    public ArrayList<String> getInstructions(){
        return Directions.stepByStep(this.pathNodes, this.maps.getMaps());
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

    /**
     * This will kick everything off!
     * We can later change it so other things trigger this.
     * We also have to think about clearing things
     */
    public void findPaths(){
        //validate that there are inputs for beginging and end
        if (this.startNode != null && this.endNode != null){
            //logic.Node s = (logic.Node)this.start.getValue();
            //logic.Node e = (logic.Node)this.end.getValue();
            ArrayList<logic.Node> path = this.getPathNodes(startNode, endNode);
            ArrayList<String> instructions = this.getInstructions();//pass correct instructions
            myDisplay.setInstructions(path, instructions);
            myDisplay.mapDisplay.showPath(path);
        }
        //String name = controller.getMapName();
        //map.setMap(name);
        //mapDisplay.drawPath();
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
