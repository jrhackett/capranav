package controller;

import SVGConverter.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.*;
import visuals.Display;
import visuals.Instructions;

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


    /* nodes and graphs */
    private HashMap<Integer, INode> nodes;   /* all the nodes */
    private HashMap<Integer, IMap> maps;

    /* information of the maps */
    private int currentBuilding = 0;
    private int currentFloor;
    private Campus campus;
    private logic.IMap currentMap;                  /* current map being used */
    private HashMap<Integer, Building> buildings;   /* information on organization of floors */

    /* switches */
    private boolean FIRST = false; //if the last thing to be set was first
    public boolean FLAG = false;

    private logic.INode tempStart;
    private logic.INode tempEnd;

    public logic.INode startNode;
    public logic.INode endNode;

    /* path data */
    public ArrayList<INode> pathNodes;       /* this is set then used to get instructions from logic.Directions */
    ArrayList<ArrayList<Instructions>> fullPath;
    private int currentIndex;
    private int lastMapID;


    private INode selectedInformationNode;


    @Override
    public void start(Stage s) throws Exception {
        /* load up svg converter */
        SvgImageLoaderFactory.install(); //TODO FIND A BETTER WAY

		/* get information */
        nodesFromFile();
        mapsFromFile();
        buildingsFromFile();

        /* node images */
        //TODO FIX
        nodes.forEach((k,v) -> {
            v.setPicturePath("../images/Riley.png");
        });

        /* basic layout */
        s.initStyle(StageStyle.DECORATED);  // <-- removes the top part of the app close/open
        campus = (Campus) maps.get(0);

		/* basic layout */
        //s.initStyle(StageStyle.TRANSPARENT);  // <-- removes the top part of the app close/open [switch to UNDECORATED]
        s.setResizable(true);

		/* setup */
        this.myDisplay = new Display(this);    //creates scene
        Scene display = myDisplay.Init();      //initializes scene
        s.setScene(display);                   //sets scene to display
        display.getStylesheets().add(getClass().getResource("../visuals/style.css").toExternalForm());
        s.show();   //shows scene

       /* sets the campus map as the loaded map */
        defaultMap();
    }


    /****************************************************************************************************************
                                    FUNCTIONS THAT ARE CALLED FROM UI AND CONTACT UI
     ****************************************************************************************************************/

    /**
     * Brings up the picture of the node on to the screen and greys out the screen
     * TODO:
     *      Potentials ideas / improvements
     *      Arrows on left and right to switch to node information
     *          Would have to notify the selected node information
     */
    public void showNodeImage(){
        if (this.selectedInformationNode != null) { //if there is a selected node

            StackPane imageStack = new StackPane();
            StackPane shadowStack = new StackPane();
            shadowStack.setStyle("-fx-background-color: #333333; -fx-opacity: .75");

            imageStack.setOnMouseClicked(e -> {
                myDisplay.root.getChildren().removeAll(imageStack, shadowStack);
            });

            //add image to stack pane -> if no image return void
            Image image = new Image(getClass().getResourceAsStream("../images/" + this.selectedInformationNode.getPicturePath()));
            ImageView iv = new ImageView(image);

            imageStack.getChildren().add(iv);

            this.myDisplay.root.getChildren().addAll(shadowStack, imageStack);

            //for(Map.Entry<String, Integer> : nodes.entrySet())

        }
    }

    /**
     * Switch this to just the INode
     * @param n
     */
    public void updateNodeInformation(INode n){
        this.myDisplay.updateNodeIcon(n.getIcon(), n);
        this.myDisplay.updateNodeTitle(n.toString());
        this.myDisplay.ICON_VISIBLE.setValue(true);

        if (n.getPicturePath() != null){
            this.myDisplay.updatePictureIcon(true);
            this.selectedInformationNode = n;
        } else {
            this.myDisplay.updatePictureIcon(false);
            this.selectedInformationNode = null;
        }

    }

    /**
     * Sends email
     * @param email
     * @return
     */
    public boolean sendEmail(String email){
        INode end = null;
        String startString = null;
        String endString = null;
        ArrayList<String> simplifiedInstruction = new ArrayList<>();
        if (fullPath == null) return false;
        for (ArrayList<Instructions> il : fullPath){
            for (Instructions i : il){
                simplifiedInstruction.add(i.getInstruction_string());
                end = i.getNode();
            }
        }

        if (fullPath != null) {
            INode start = fullPath.get(0).get(0).getNode();
            if (start.isInteresting()) {
                startString = start.getNames().get(0);
            } else if (start.isTransition()) {
                startString = start.toString();
            }
            if (end != null && end.isInteresting()) {
                endString = end.getNames().get(0);
            } else if (start.isTransition()) {
                endString = end.toString();
            }
        }

        logic.Email e = new logic.Email(email);
        if(simplifiedInstruction.size() != 0 && startString != null && endString != null) {
            return e.sendDirections(simplifiedInstruction, startString, endString);
        }
        else return false;
    }

    /**
     * returns the nodes
     * @return
     */
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
    public void handleSearchInput(int id, boolean START) {
        FIRST = START; //Set START so when / if map clicked properly sets start/end node

        //clears old path lines from display
        //myDisplay.mapDisplay.revertPathNodes();

        if (nodes.containsKey(id)) {

            if (START) {//setting start
                //System.out.println("START TRUE!");

                //if start is A and end is B and user tries to make start B, then end will be A
                if (endNode != null && id == endNode.getID() ){ //&& startNode != null
                    endNode = startNode;
                }

                if (!FLAG) {//if not from a click on the map hide the last
                    //System.out.println("FLAG FALSE!");
                    if (startNode != null) {
                        //this visually hides the last start node -> sets it to normal
                        this.myDisplay.mapDisplay.hideLast(startNode);
                    }
                }

                //set the startNode id
                this.startNode = nodes.get(id);
            //Else we are changing the EndNode
            } else {

                //flip startNode and endNode if need be
                if (startNode != null && id == startNode.getID() ){ //&& startNode != null
                    startNode = endNode;
                   // this.myDisplay.mapDisplay.setStartNode(endNode);
                }


                if (!FLAG) {//if not from a click on the map hide the last
                   // System.out.println("FLAG FALSE!");
                    if (endNode != null) {
                      //  System.out.println("HIDING END!");
                        this.myDisplay.mapDisplay.hideLast(endNode);
                    }
                }

                this.endNode = nodes.get(id);
            }

            if (startNode != null && endNode != null) {
                findPaths();
            } else {
                switchMapSetting(startNode.getMap_id());
            }

            if (startNode != null) {
                //TODO if current map contains it, play, if it doesn't - switch and play
                //Hopefully in find paths this is taken care of - will confirm later
                if (startNode.getMap_id() == currentMap.getID()) {
                    //gotta switch maps
                    //switchMapSetting(startNode.getMap_id());
                    myDisplay.mapDisplay.setStartNode(startNode);
                }
            }

            if (endNode != null) {
               // System.out.println("END NODE HIGHLIGHTED!");
                //TODO if current map contains it, play, if it doesn't - dont play, just set and color
                if (endNode.getMap_id() == currentMap.getID()) {
                    myDisplay.mapDisplay.setEndNode(endNode, true);
                } else {
                    myDisplay.mapDisplay.setEndNode(endNode, false);
                }
            }

        }
    }

    /**
     * This function is the one you want when you
     * @param t
     */
    public void handleEnterBuilding(Transition t){
        switchToBuildingView(t.getBuildingID(), t.getToFloor());
        this.currentMap = maps.get(t.getMap_id());
        this.currentBuilding = t.getBuildingID();
        this.currentFloor = t.getToFloor();
        handleMapLines();
    }

    private void switchMapSetting(int mapId){
        if (maps.get(mapId).getID() == 0){
            hideBuildingPane();
            defaultMap();
            this.currentMap = campus;
        } else {
            this.currentMap = maps.get(mapId);
            this.currentBuilding = currentMap.getBuildingID();
            this.currentFloor = currentMap.getFloor();
            System.out.println("SWITCH MAP SETTING");
            System.out.println("currentMap: " + maps.get(mapId));
            System.out.println("currentBuilding: " + currentMap.getBuildingID());
            System.out.println("currentFloor: " + currentMap.getFloor());

            showBuildingPane();//changes the pane to visible
            switchToBuildingView(currentBuilding, currentFloor);
        }
    }


    private void switchToBuildingView(int buildingID, int startingFLOOR){
        System.out.println("SWITCH TO BUILDING VIEW");
        System.out.println("buildingId: " + buildingID);
        System.out.println("startingfloor: " + startingFLOOR);
        myDisplay.setBuildingName(buildings.get(buildingID).getName());
        setFloor(startingFLOOR);
    }

    public void handleIncreaseFloorButton(){
        if (currentBuilding != 0 && buildings.get(currentBuilding).getFloorMap().containsKey(currentFloor + 1)){
            //setCurrentMap(buildings.get(currentBuilding).getFloorMap().get(++currentFloor));
            setFloor(++currentFloor);
        }
    }

    public void handleDecreaseFloorButton(){
        if (currentBuilding != 0 && buildings.get(currentBuilding).getFloorMap().containsKey(currentFloor - 1)){
            //setCurrentMap(buildings.get(currentBuilding).getFloorMap().get(--currentFloor));
            setFloor(--currentFloor);
        }
    }


    public void setFloor(int i){
        if (buildings.get(currentBuilding).containsFloor(i)) {
            setCurrentMap(buildings.get(currentBuilding).getFloorID(i));
            this.currentFloor = i;
            this.myDisplay.setBuildingNumber(i); //TODO Change this to something better / more informatative
            handleMapLines(); //removes old lines

            if (currentBuilding != 0 && buildings.get(currentBuilding).getFloorMap().containsKey(currentFloor + 1)) {
                //set id for normal
                this.myDisplay.setRightButtonID("arrow-buttons");
            } else {
                //set id for grey
                this.myDisplay.setRightButtonID("arrow-buttons-grayed");
            }
            if (currentBuilding != 0 && buildings.get(currentBuilding).getFloorMap().containsKey(currentFloor - 1)) {
                //set id for normal
                this.myDisplay.setLeftButtonID("arrow-buttons");
            } else {
                //set id for grey
                this.myDisplay.setLeftButtonID("arrow-buttons-grayed");
            }
        }
    }

    /**
     * Hides the pane displaying building pane: name and arrows
     */
    public void hideBuildingPane(){
        this.myDisplay.BUILDING_VISIBLE.setValue(false);
    }

    /**
     * Show the pane displaying building pane: names and arrows
     */
    public void showBuildingPane(){
        this.myDisplay.BUILDING_VISIBLE.setValue(true);
    }

    /**
     * Sets the current map, both the variable and the display
     * @param id
     */
    public void setCurrentMap(int id){
        this.currentMap = maps.get(id);
        this.myDisplay.mapDisplay.setMap(maps.get(id));
    }

    /**
     * Handles the showing of lines and switching of lastmapid
     */
    public void handleMapLines(){
        myDisplay.mapDisplay.showLines(lastMapID, currentMap.getID());
        lastMapID = currentMap.getID();
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
            //if (startNode != null) this.myDisplay.mapDisplay.hideLast(startNode.getID()); //hide the last start
            //this.startNode = n;

            if (!nodes.containsKey(n.getID())) {
                //System.out.println("Adding to hashmap 1");
                tempStart = n;
                nodes.put(n.getID(), n);//puts the node in the map!
            } else {
                if (startNode != null) this.myDisplay.mapDisplay.hideLast(startNode);
            }

            this.FLAG = true;//this is set in attempts to avoid triggering stuff twice
            myDisplay.start.setValue(myDisplay.start.addNode(n, currentMap));


        } else {//Else if the last node we added was the last [note, a lot of this gets wonky when we add midway points, jesus
            //this.endNode = n;

            if (!nodes.containsKey(n.getID())) {
                //System.out.println("Adding to hashmap 2");
                tempEnd = n;
                nodes.put(n.getID(), n);
            } else {
                if (endNode != null) this.myDisplay.mapDisplay.hideLast(endNode);
            }

            this.FLAG = true;
            myDisplay.end.setValue(myDisplay.end.addNode(n, currentMap));

        }

        this.FLAG = false;//this should prevent some double triggering of events
    }

    public INode createTempLandmark(double x, double y){

        double z;

        if(currentMap.inside()) {
           z = ((Floor)currentMap).getFloor() * 15; //ROUGH Z value
        } else {
           z = 0;
        }

        double x2 = x;//TODO ADD TRANSLATE HERE
        double y2 = y;
        double z2 = z;

        Landmark temp;

        if(!FIRST) {
            if (startNode != null){//have to delete old before creating new one (with same ID)
                //System.out.println("Hiding old start node");
                this.myDisplay.mapDisplay.hideLast(startNode); //hide the last start
            }
            eradicate(tempStart, true); //completely get rid of the last start
            //System.out.println("-1");
            temp = new Landmark(-1, x, y, z, x2, y2, z2, currentMap.getID(), "Near " + nearestNamedNodeName(x2, y2, z2));
        } else {
            if (endNode != null){
                //System.out.println("Hiding old end node");
                this.myDisplay.mapDisplay.hideLast(endNode); //hide the last end
            }
            eradicate(tempEnd, false); //completely get rid of the last temp
            //System.out.println("-2");
            temp = new Landmark(-2, x, y, z, x2, y2, z2, currentMap.getID(), "Near " + nearestNamedNodeName(x2, y2, z2));
        }

        int target = nearestNodeID(x2, y2, z2);
        temp.addEdge(new Edge(target, 1.0));
        nodes.get(target).addEdge(new Edge(temp.getID(), 1.0));

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

            if(v.getMap_id() == currentMap.getID() && Math.sqrt((v.getX() - x)*(v.getX() - x) + (v.getY() - y)*(v.getY() - y)) < distance){
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
            if(v.isInteresting() && cursor.getKey() != -1 && cursor.getKey() != -2){ //we dont want the name Near Near Stratton Hall
                if(v.getMap_id() == currentMap.getID() && Math.sqrt((v.getX() - x)*(v.getX() - x) + (v.getY() - y)*(v.getY() - y)) < distance ){
                    //System.out.println("smaller distance found");
                    n = v;
                    distance = Math.sqrt((v.getX() - x)*(v.getX() - x) + (v.getY() - y)*(v.getY() - y));
                }
            }
        }

        if (n == null){
            //System.out.println("NO DISTANCE FOUND OR SOMETHING");
            return "Entrance";
        }
        return ((Interest)n).getName();
    }


    /**
     * Removes this node from everything
     * @param n
     */
    public void eradicate(INode n, boolean start){
        if( n != null) {
            nodes.remove(n); //remove node froms nodes [the hashmap]

            //remove references of this node in other nodes
            for (Edge e : n.getAdjacencies()) {//
                nodes.get(e.getTarget()).removeEdge(n.getID());
            }

            if (start) {
                //System.out.printf("Removing from options tempstart id:%d (should be -1)!\n", tempStart.getID());
                myDisplay.start.setValue(null);        //set the list value to empty [this won't last very long]
                myDisplay.start.removeNode(n.getID()); //remove it from the observable list
            } else {
                //System.out.printf("Removing from options tempend id:%d (should be -2)!\n", tempStart.getID());
                myDisplay.end.setValue(null);
                myDisplay.end.removeNode(n.getID());
            }

            //System.out.printf("Removing node  %d from mapdisplay: %d", n.getID(),n.getID());
            myDisplay.mapDisplay.removeNode(n.getID()); //remove it from the map, visually and from the list
        }
    }

    /**
     * Sets the current map to the campus map
     */
    public void defaultMap(){
        setCurrentMap(campus.getID());
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

    private boolean validateNotEquality(INode n, INode m){
        if (n.getID() == m.getID()){
            return false;
        } else {
            return true;
        }
    }


    public ArrayList<logic.INode> getPathNodes(logic.INode s, logic.INode f){
        this.pathNodes = AStarShortestPath.AStarSearch(s, f, nodes);
        return pathNodes;
    }

    /**
     * gets the instructions by via pathNodes set by getPathNodes
     * @return an ArrayList<String?
     */
    public void getInstructions(){
        this.fullPath =  Directions.stepByStep(this.pathNodes, this.maps);
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

    public INode getNode(int id){
        return nodes.get(id);
    }

    /**
     * This will kick everything off!
     * We can later change it so other things trigger this.
     * We also have to think about clearing things
     */
    public void findPaths(){

        //Creates instructions - setting fullPath
        getPathNodes(startNode, endNode);
        getInstructions();

        //set ids of buttons
        if (fullPath != null && fullPath.size() > 0 &&  this.currentIndex + 1 < fullPath.size()){
            this.myDisplay.setIDRightArrowButton("arrow-buttons");
        }

        if (fullPath != null && fullPath.size() > 0 && this.currentIndex - 1 > -1) {
            this.myDisplay.setIDLeftArrowButton("arrow-buttons");
        }

        //current index of which arraylist of instructions we are displaying
        currentIndex = 0;//start off with the first list

        //the last map we wee showing
        lastMapID = fullPath.get(currentIndex).get(0).getNode().getMap_id();

        //sets the instructions to the corresponding list of instructions to the first path
        myDisplay.setInstructions(fullPath.get(currentIndex)); //TODO UPDATE setInstructions

        //create the path
        myDisplay.mapDisplay.createPath(fullPath);

        //switch the map to the building
        switchMapSetting(startNode.getMap_id());

        //shows the lines
        myDisplay.mapDisplay.showLines(-1, lastMapID); //TODO UPDATE showPath
    }

    public void handleIncrementPathMap(){
        //if there is another list of instructions to go
        if (fullPath != null && fullPath.size() > 0 &&  this.currentIndex + 1 < fullPath.size()){
            myDisplay.setInstructions(fullPath.get(++currentIndex)); //TODO UPDATE setInstructions
            switchMapSetting(fullPath.get(currentIndex).get(0).getNode().getMap_id());
            this.myDisplay.mapDisplay.softSelectAnimation(fullPath.get(currentIndex).get(0).getNode().getID());
            this.myDisplay.mapDisplay.showLines(lastMapID, fullPath.get(currentIndex).get(0).getNode().getMap_id()); //TODO UPDATE showPath
        }

        if (fullPath != null && fullPath.size() > 0 &&  this.currentIndex + 1 < fullPath.size()){
            this.myDisplay.setIDRightArrowButton("arrow-buttons");
        } else {
            this.myDisplay.setIDRightArrowButton("arrow-buttons-grayed");

        }

    }
    public void handleDecrementPathMap(){
        //if there is another list of instructions to go
        if (fullPath != null && fullPath.size() > 0 && this.currentIndex - 1 > -1){
            myDisplay.setInstructions(fullPath.get(--currentIndex)); //TODO UPDATE setInstructions
            switchMapSetting(fullPath.get(currentIndex).get(0).getNode().getMap_id());
            this.myDisplay.mapDisplay.softSelectAnimation(fullPath.get(currentIndex).get(0).getNode().getID());
        }

        if (fullPath != null && fullPath.size() > 0 && this.currentIndex - 1 > -1) {
            this.myDisplay.setIDLeftArrowButton("arrow-buttons");
        } else {
            this.myDisplay.setIDLeftArrowButton("arrow-buttons-grayed");
        }
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
        nodes = new Parser<INode>().fromFileGraph();
    }

    /**
     * Loads buildings from file
     */
    private void buildingsFromFile(){
        buildings = new Parser<Building>().fromFileBuilding();
    }

    /**
     * Loads maps from file
     */
    private void mapsFromFile() {
        maps = new Parser<IMap>().fromFileMap();
        campus = (Campus)maps.get(0);
    }

    public Building getBuilding(int buildingID) {
        return buildings.get(buildingID);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        launch(args);
    }
}
