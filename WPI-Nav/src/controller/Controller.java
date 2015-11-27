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

    /* visual component */
    private Display myDisplay;

    /* information variables */
    public ArrayList<INode> pathNodes;       /* this is set then used to get instructions from logic.Directions */

    /* nodes and graphs */
    private HashMap<Integer, INode> nodes;   /* all the nodes */
    private HashMap<Integer, IMap> maps;

    //Maps maps;                       /* information of the maps */

    private Campus campus;
    private logic.IMap currentMap;            /* current map being used */
    private HashMap<Integer, Building> buildings;   /* information on organization of floors */

    /* switches */
    private boolean FIRST = false; //if the last thing to be set was first
    public boolean FLAG = true;

    private logic.INode tempStart;
    private logic.INode tempEnd;

    public logic.INode startNode;
    public logic.INode endNode;


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
        this.myDisplay = new Display(this);    //creates scene
        Scene display = myDisplay.Init(); //initializes scene
        s.setScene(display); //sets scene to display
        display.getStylesheets().add(getClass().getResource("../visuals/style.css").toExternalForm());
        s.show();   //shows scene
        defaultMap();
    }


    /****************************************************************************************************************
                                    FUNCTIONS THAT ARE CALLED FROM UI AND CONTACT UI
     ****************************************************************************************************************/
    public void sendEmail(String email){
        logic.Email e = new logic.Email(email);
        System.out.println(email);
        //e.sendEmail()
        //TODO FILL IN WITH NEW EMAIL CODE

    }

    public HashMap<Integer, INode> getNodes(){
        return nodes;
    }


    /**
     * This begins replaces getNamedNodes, it returns all 'named' nodes
     * @return
     */
    public HashMap<Integer, INode> getInterestingNodes(){
        HashMap<Integer, INode> value = new HashMap<>();

        nodes.forEach((k,v) -> {
             if(v.isInteresting()){
                 value.put(k, v);}});

        return value;
    }


    /**
     * This handles the values from the SEARCH BARS
     * @param id
     * @param START
     */
    public void handleSearchInput(int id, boolean START){
        if (nodes.containsKey(id) && FLAG){ //The FLAG prevents it from setting it from clicks doubly
            if (START) startNode = nodes.get(id);
            else       endNode   = nodes.get(id);

            myDisplay.mapDisplay.setStartNode(id, true);

            if (startNode != null && endNode != null) {
                findPaths();
            }
        }
    }

    /**
     * THIS IS POST TEMP NODE CREATION
     * We are going to either
     *      A: Set it as the new Start Value
     *      B: Set it as the new End Value
     * Then change the value in the boxes
     *
     * @param n
     */
    public void handleMapClick(INode n){
        /**
         * This function SHOULD make it so there are up to two temporary nodes at a time
         * both start/end. But, it won't remove them til more are requested even if maps are
         * switched.
         */

        if (!FIRST){ //If the last node we added was the first
            eradicate(tempStart);
            this.startNode = n;

            if (!nodes.containsKey(n.getID())) {//TODO double check this works, ie that isn't already added
                tempStart = startNode;
            }

            myDisplay.start.addNode(n, currentMap);
            myDisplay.start.setValue(n);
        } else {//Else if the last node we added was the last [note, a lot of this gets wonky when we add midway points, jesus
            eradicate(tempEnd);

        }
        FIRST = !FIRST;
    }

    public INode createTempRoom(double x, double y){
        double z;
        if(currentMap.inside()) {
           z = ((Floor)currentMap).getFloor() * 15; //ROUGH Z value
        } else {
           z = 0;
        }
        double x2 = x;//logic.Translate();
        double y2 = y;
        double z2 = z;


        Room temp = new Room(-1, x, y, z, x2, y2, z2, currentMap.getID(), "Near "+ nearestNamedNodeName(x2, y2, z2));
        int target = nearestNodeID(x2, y2, z2);

        temp.addEdge(new Edge(target, 1.0));

        return temp;
    }

    /**
     * Returns the id of the nearest node
     * @param x
     * @param y
     * @param z
     * @return
     */
    public int nearestNodeID(double x, double y, double z){
        double distance = Double.MAX_VALUE;
        INode n = null;

        for(HashMap.Entry<Integer, INode> cursor : nodes.entrySet()){
            INode v = cursor.getValue();
                if(Math.sqrt((v.getX() - x)*(v.getX() - x) + (v.getY() - y)*(v.getY() - y)) < distance){
                    n = v;
                    distance =Math.sqrt((v.getX() - x)*(v.getX() - x) + (v.getY() - y)*(v.getY() - y));
                }
            }


        //TODO IF THIS IS TOO BAD WE CAN CHANGE IT
        return n.getID();
    }


    /**
     * Returns the name of the nearest node of the location, that is named.
     * @param x
     * @param y
     * @param z
     * @return
     */
    public String nearestNamedNodeName(double x, double y, double z){
        double distance = Double.MAX_VALUE;
        INode n = null;


        for(HashMap.Entry<Integer, INode> cursor : nodes.entrySet()){
            INode v = cursor.getValue();
            if(v.isInteresting()){
                if(Math.sqrt((v.getX() - x)*(v.getX() - x) + (v.getY() - y)*(v.getY() - y)) < distance){
                    n = v;
                    distance =Math.sqrt((v.getX() - x)*(v.getX() - x) + (v.getY() - y)*(v.getY() - y));
                }
            }
        }

        return ((Interest)n).getName();
    }


    /**
     * Removes this node from everything
     * @param n
     */
    public void eradicate(INode n){
        if( n != null) {
            nodes.remove(n);
            for (Edge e : n.getAdjacencies()) {
                nodes.get(e.getTarget()).removeEdge(n.getID());
            }
            myDisplay.start.removeNode(n.getID());
            myDisplay.start.setValue(null);
            myDisplay.mapDisplay.removeNode(n.getID());
        }
    }


    public void defaultMap(){
        currentMap = campus;
        setCurrentMap(campus.getID());
        this.myDisplay.mapDisplay.setMap(currentMap);

    }

    /**
     * For the creation of search bar, get all the names of the building
     * @param building_id
     * @return
     */
    public ArrayList<String> getBuildingNames(int building_id){
        return this.buildings.get(building_id).getNames();
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void reset(){
        defaultMap(); //TODO CONSIDER THIS
        this.pathNodes = new ArrayList<>();
    }

    public void resetStartEnd(){
        this.startNode = null;
        this.endNode = null;
        this.myDisplay.start.getSelectionModel().clearSelection();
        this.myDisplay.end.getSelectionModel().clearSelection();

    }

    public void nodeFromMapHandler(INode n){
        //check if we have a start or false
        if (myDisplay.start.getValue() == null){
            //if (validateNotEquality(n, (Node)myDisplay.start.getValue())) {
                //no start, thus -> set it to n
                this.FLAG = false;
                //myDisplay.start.addNode(n);
                myDisplay.start.setValue(n);
                this.FLAG = true;
                myDisplay.mapDisplay.setStartNode(n.getID(), true);
                this.startNode = n;
           // }
        } else if (myDisplay.end.getValue() == null){
           // if(validateNotEquality(n,(Node)myDisplay.end.getValue())) {
                this.FLAG = false;
               // myDisplay.end.addNode(n);//// TODO: 11/18/15
                myDisplay.end.setValue(n);
                this.FLAG = true;
                myDisplay.mapDisplay.setStartNode(n.getID(), false);
                this.endNode = n;
          //  }
        } else if (!FIRST){
            if(validateNotEquality(n,(INode)myDisplay.start.getValue())) {
                //no start, thus -> set it to n
                this.FLAG = false;
                //myDisplay.start.addNode(n);//// TODO: 11/18/15
                myDisplay.start.setValue(n);
                this.FLAG = true;
                myDisplay.mapDisplay.setStartNode(n.getID(), true);
                FIRST = true;
                this.startNode = n;
            }
            //myDisplay.mapDisplay.mapDescriptor.setText("Refresh to Click and Choose");
        } else {
            if(validateNotEquality(n,(INode)myDisplay.end.getValue())) {
                this.FLAG = false;
                //myDisplay.end.addNode(n);//// TODO: 11/18/15
                myDisplay.end.setValue(n);
                this.FLAG = true;
                myDisplay.mapDisplay.setStartNode(n.getID(), false);
                FIRST = false;
                this.endNode = n;
            }
        }
    }

    private boolean validateNotEquality(INode n, INode m){
        if (n.getID() == m.getID()){
            return false;
        } else {
            return true;
        }
    }


    public ArrayList<logic.INode> getPathNodes(logic.INode s, logic.INode f){
        this.pathNodes = AStarShortestPath.AStarSearch(s,f, nodes);
        return pathNodes;
    }

    /**
     * gets the instructions by via pathNodes set by getPathNodes
     * @return an ArrayList<String?
     */
    public ArrayList<String> getInstructions(){
        return Directions.stepByStep(this.pathNodes, this.maps);
    }


    /**
     * return the HashMap of Nodes that have NAMES!!
     */
    public HashMap<Integer, INode> getNamedNodesOfMap(){
        HashMap<Integer, INode> value = getNodesOfMap(this.currentMap.getID());
        nodes.forEach((k,v) -> {
            if (k !=0) {
                //if (v.getName().equals("ENTER TEXT") || v.getName().equals("")) {
                if(!v.isInteresting()){
                    value.remove(k, v);}}});
        return  value;
    }



    /**
     * return the HashMap of Nodes [to display][of the current map]
     * @param id
     * @return
     */
    public HashMap<Integer, INode> getNodesOfMap(int id){

        HashMap<Integer, INode> value = new HashMap<>();

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
            ArrayList<logic.INode> path = this.getPathNodes(startNode, endNode);
            ArrayList<String> instructions = this.getInstructions();//pass correct instructions
            myDisplay.setInstructions(path, instructions);
            myDisplay.mapDisplay.showPath(path);
        }
        //String name = controller.getMapName();
        //map.setMap(name);
        //mapDisplay.drawPath();
    }




    public void setCurrentMap(int id){
        this.currentMap = maps.get(id);
    }


    /****************************************************************************************************************
                                                  PARSING FUNCTIONS
     ****************************************************************************************************************/


    public HashMap<Integer, IMap> getMaps(){
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
    /*private void mapsFromFile() {
        Parser parser = new Parser("maps.json");
        this.maps = (Maps)parser.fromFile();
    }
*/

    private void mapsFromFile() {
        maps = new HashMap<>();
        campus = new Campus(0, "wpi-campus-map", 24);
        maps.put(0, campus);

        //TODO get this functional
       /*
        Parser parser = new Parser("campus.json");
        this.campus = (Campus)parser.fromFile();

        parser = new Parser("floors.json");
        HashMap<Integer, Floor> floors = ((Floors)parser.fromFile()).getMaps();

        maps.putAll(floors);
        maps.put(0, campus);
        */
    }



    private void campusFromFile(){
        Parser parser = new Parser("campus.json");
        this.campus = (Campus)parser.fromFile();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
