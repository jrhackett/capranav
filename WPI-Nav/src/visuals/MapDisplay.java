package visuals;

import controller.Controller;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import logic.Building;
import logic.FileFetch;
import logic.IMap;
import logic.INode;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.HashMap;




public class MapDisplay extends Pane {
    /* constants */
    private double IMAGE_WIDTH = 700;//660
    private double IMAGE_HEIGHT = 525;//495


    /* Data Structures */
    private HashMap<Integer, Circle> id_circle;
    private Controller controller;
    private HashMap<Integer, ArrayList<Line>> lines; /* mapId to line */
    ArrayList<INode> idPath;

    /* Visuals */
    private ScaleTransition st;
    private Transition ste;


    private Image mapImage;
    public ImageView mapView;

    private PopOver previousPopOver;

    private Color last = Color.TRANSPARENT;
    private Color lastStroke = Color.TRANSPARENT;
    private Paint lastSoft = Color.TRANSPARENT;

    private boolean HIGLIGHTED = false;

    private ArrayList<INode> path; //last set path

    //////////////////////// ICON CODE ///////////////////////////
    private HashMap<Integer, ImageView> id_ICON;


    /**
     * Constructor
     *
     * @param controller
     */
    public MapDisplay(Controller controller) {
        super();
        this.controller = controller;
        this.setStyle("-fx-background-color: #F5F5DC");
        this.mapView = new ImageView();
        this.setMaxWidth(IMAGE_WIDTH);
        this.setMaxHeight(IMAGE_HEIGHT);
        this.id_circle = new HashMap<>();
        this.path = new ArrayList<>();
        this.lines = new HashMap<>();
        this.idPath = new ArrayList<>();
        this.ste = new ScaleTransition();
        this.st = new ScaleTransition();


        this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());


        controller.getNodes().forEach((k,v) -> {
            Circle c = createCircle(v);
            normal(c, v);
            id_circle.put(k,c);
        });

        previousPopOver = new PopOver();
    }


    /****************************************************************************************************************
     WORKING CIRCLE CODE
     ****************************************************************************************************************/


    /**
     * Draws the nodes given on the map
     *
     * @param nodes
     */
    public void drawNodes(HashMap<Integer, INode> nodes) {
        //to make it easy we will create all the nodes

        nodes.forEach((k, v) -> {
            this.getChildren().add(id_circle.get(k));
        });
    }

    /**
     * Given a MAP_NAME -> ask Controller for map name and nodes for the map [NEW VERSION]
     * Add image to map
     * Then add the Nodes
     *
     * @param map
     */
    public void setMap(IMap map) {

        //Removes all old nodes, old lines, and old maps
        this.getChildren().remove(0, this.getChildren().size());

        //this.mapImage = FileFetch.getImageFromFile("floorPlans/" + map.getFilePath(), )

        try {
            this.mapImage = new Image(getClass().getResourceAsStream("../images/floorPlans/" + map.getFilePath()));//
        } catch (NullPointerException e) {
            this.mapImage = new Image(getClass().getResourceAsStream("/images/floorPlans/" + map.getFilePath()));//, IMAGE_WIDTH, IMAGE_HEIGHT, true, true
        }


        this.mapView = new ImageView(mapImage);
        this.mapView.setFitHeight(IMAGE_HEIGHT);
        this.mapView.setFitWidth(IMAGE_WIDTH);

        this.getChildren().add(mapView);
        drawNodes(controller.getNodesOfMap(map.getID()));


        mapView.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() >= 2) createTempLandmark(e);
        });
    }


    private void createTempLandmark(MouseEvent e) {
        //CREATE TEMPORARY POINT ->
        INode temp = controller.createTempLandmark(e.getX(), e.getY());
        Circle c = createCircle(temp);
        id_circle.put(temp.getID(), c);
        normal(c, temp);
        this.getChildren().add(c);
        controller.handleMapClick(temp);
    }

    public void softSelectAnimation(int idOld, int idNew) {
        System.out.println("idOld: " + idOld);
        System.out.println("idNew: " + idNew);


        if (idOld > -1) {
            Circle c = id_circle.get(idOld); //TODO confirm we dont need to ensure its on the same map
            c.setFill(this.lastSoft);
        }

        Circle c = id_circle.get(idNew); //TODO confirm we dont need to ensure its on the same map
        this.lastSoft = c.getFill();
        c.setFill(Color.HOTPINK);

        ScaleTransition st = new ScaleTransition(Duration.millis(160), c);
        st.setByX(1.03f);
        st.setByY(1.03f);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }

    /**
     * default circle
     *
     * @param c
     */
    public void normal(Circle c, INode v) {
        if (v != null && v.isTransition()) { //v != null &&
            c.setFill(Color.YELLOW);
        } else {
            c.setFill(Color.TRANSPARENT);
        }

        c.setStrokeWidth(0);
        c.setRadius(5);
        c.setOpacity(1);
        c.setEffect(null);
    }


    private Circle createCircle(INode v) {



        double x = v.getX();  /* the nodes currently have way too small X / Y s - later we'll need to somehow scale */
        double y = v.getY();
        Circle circle = new Circle(x, y, 5);

        if (v.isTransition()) {
            circle.setOnMouseClicked(e -> {
               if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 1) {
                    controller.handleMapClick(v);
                }
            });
        } else {
            circle.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.SECONDARY) && e.getClickCount() == 1) {
                    controller.showNodeImage();
                } else if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() == 1) {
                    controller.handleMapClick(v);
                }
            });
        }

        if((v instanceof logic.Transition) && v.getMap_id() == 0 && ((logic.Transition)v).getBuildingID() != 0){

            PopOver popOver = createPopOverForNode(v);

            circle.setOnMouseEntered(e -> {
                //content of popover
                System.out.println("showing popover for ID: " + v.getID());
                if(!(previousPopOver.equals(popOver))) {
                    popOver.show(circle, -9);
                    previousPopOver.hide();
                    previousPopOver = popOver;
                    //controller.updateNodeInformation(v);
                }
            });

            /*circle.setOnMouseExited(e -> {
                popOver.hide();
            });*/
        }

        return circle;
    }

    public PopOver createPopOverForNode(INode v) {
        PopOver popOver = new PopOver();
        VBox vbox = new VBox();

        Building building = controller.getBuilding(((logic.Transition)v).getBuildingID());
        HashMap<Integer, Integer> floorPlan = building.getFloorMap();

        Label buildingName = new Label(building.getName());
        buildingName.setStyle("-fx-font-size:9;");
        buildingName.setTextFill(Color.web("#333333"));
        Image picture = FileFetch.getImageFromFile("picture.png", 12, 12, true, true);
        ImageView pictureView = new ImageView(picture);

        //TODO make pictureView show picture on clicked

        HBox hbox = new HBox();
        hbox.getChildren().addAll(buildingName, pictureView);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(2);

        Label selectFloor = new Label("Select Floor:");
        selectFloor.setTextFill(Color.web("#333333"));
        selectFloor.setStyle("-fx-font-size:8;");

        FlowPane flowPane = new FlowPane();

        /* displays only the buttons that the building has in rows of up to 3 */
        for(int i = floorPlan.size() - 1; i >= 0; i--) {
            Button button = new Button();
            button.setId("popover-buttons");
            String value;
            Object array[] = floorPlan.keySet().toArray();
            if(floorPlan.keySet().toArray()[i].equals(-1)) {
                value = "SB";
                button.setStyle("-fx-padding:4 3 4 3;");
            }
            else if(floorPlan.keySet().toArray()[i].equals(0)) {
                value = "B";
            }
            else
            {
                value = Integer.toString(i);
            }
            button.setText(value);
            final int x = i;
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("building.getID(): " + building.getID());
                    System.out.println("x: " + x);
                    controller.switchToBuildingView(building.getID(), x);
                    //controller.handleEnterBuilding((logic.Transition)v);
                    //controller.setCurrentMap(floorPlan.get(x));
                    popOver.hide();
                }
            });
//            button.setOnMouseClicked(e -> {
//                controller.switchToBuildingView(building.getID(), i);
//                //controller.handleEnterBuilding((logic.Transition)v);
//                //controller.setCurrentMap(floorPlan.get(x));
//                popOver.hide();
//            });
            flowPane.getChildren().add(button);
        }

        /*Button fourth = new Button();       //4
        fourth.setText("4");
        fourth.setId("popover-buttons");

        Button third = new Button();        //3
        third.setText("3");
        third.setId("popover-buttons");

        Button second = new Button();       //2
        second.setText("2");
        second.setId("popover-buttons");

        Button first = new Button();        //1
        first.setText("1");
        first.setId("popover-buttons");

        Button basement = new Button();     //0
        basement.setText("B");
        basement.setId("popover-buttons");

        Button subBasement = new Button();  //-1
        subBasement.setText("SB");
        subBasement.setId("popover-buttons");
        subBasement.setStyle("-fx-padding:4 3 4 3;");

        flowPane.getChildren().addAll(fourth, third, second, first, basement, subBasement);*/
        flowPane.setHgap(2);
        flowPane.setVgap(2);
        flowPane.setTranslateY(4);

        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setId("popover-id");
        vbox.setSpacing(2);
        vbox.getChildren().addAll(hbox, flowPane); //select floor? topbuttons, bottombuttons
        //vbox.getStylesheets().add("../visuals/style.css");

        popOver.setContentNode(vbox);
        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);
        popOver.setDetachable(false);
        popOver.setArrowSize(5.0);

        return popOver;
    }

    /**
     * highlights a circle
     *
     * @param c
     * @param color
     * @param colorStroke
     */
    private void highlight(Circle c, Color color, Color colorStroke) {
        c.setFill(color);
        c.setStroke(colorStroke);
        c.setStrokeWidth(1);
    }

    public void highlightPath(int id){
        if (controller.startNode.getID() != id && controller.endNode.getID() != id)
        highlightPath(id_circle.get(id));
    }

    /**
     * Highlights the path of nodes.
     *
     * @param c
     */
    public void highlightPath(Circle c) {
        //c.setFill(Color.web("#00CCFF"));
        // c.setStroke(Color.web("#0018A8"));
        c.setRadius(5);
        highlight(c, Color.BLUE, Color.LIGHTBLUE);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.WHITE);
        ds.setOffsetX(2.0);
        ds.setOffsetY(2.0);
        c.setEffect(ds);
    }


    /**
     * removes all lines from the display
     */
    public void revertPathNodes(){
        //This removes all lines from the display [overkill
        lines.forEach((k,v) -> {
           this.getChildren().removeAll(v);
        });

        id_circle.forEach((k,v) -> {
            normal(id_circle.get(k), controller.getNode(k));
        });
    }

    /**
     * Show path of nodes
     */

    public void createPath(ArrayList<ArrayList<Instructions>> path) {

        //upon creation of a new path fully reset old nodes
        revertPathNodes();

        //clears the arraylist of nodes (idPath) and hashmap of arrays lists of lines (lines)
        idPath = new ArrayList<>(); //TODO AM I REMVOING THE OLD LINES
        lines = new HashMap<>();


        for (ArrayList<Instructions> list : path) {
            ArrayList<Line> lineArrayList = new ArrayList<>();
            double coordX = list.get(0).getNode().getX();
            double coordY = list.get(0).getNode().getY();

            for (Instructions i : list) {
                /** path nodes highlight blue **/
                highlightPath(id_circle.get(i.getNode().getID()));
                idPath.add(i.getNode());

                System.out.println("x: start" + coordX);
                System.out.println("y: start" + coordY);

                /** create all the lines and add them to a list **/
                Line line = new Line();
                line.setStartX(coordX);
                line.setStartY(coordY);
                coordX = i.getNode().getX();
                coordY = i.getNode().getY();
                line.setEndX(coordX);
                line.setEndY(coordY);
                line.setStroke(Color.web("#00CCFF", 0.7));
                line.setStrokeWidth(2);
                line.setStrokeDashOffset(5);
                line.getStrokeDashArray().addAll(2d, 7d);
                lineArrayList.add(line);
                System.out.println("x: end" + coordX);
                System.out.println("y: end" + coordY);
                System.out.println("lines created");
                System.out.println("");
                System.out.println("");
            }
            //System.out.println("lines put into hashmap");
            lines.put(list.get(0).getNode().getMap_id(), lineArrayList);
        }

        setStartNode(idPath.get(0));
        setEndNode(idPath.get(idPath.size()-1), false);
    }

    /**
     * Removes the old lines from the last map
     * Draw the new lines for this map
     *
     * @param mapIdOld
     * @param mapIdNew
     */
    public void showLines(int mapIdOld, int mapIdNew) {
        if (mapIdOld != -1) {
            try {
                System.out.println("Removing old map lines: " + mapIdOld);
                this.getChildren().removeAll(lines.get(mapIdOld));
            } catch (NullPointerException e){
                System.out.println("MAP HAS NO LINES YET");
            }
        }

        //if map has lines to show, show them
        System.out.println("Switching from map: " + mapIdOld + " to map: " + mapIdNew);
        if(this.lines.containsKey(mapIdNew)){
            System.out.println("POST CONTAINS");
            this.getChildren().addAll(lines.get(mapIdNew));
        }
    }


    /**
     * The color and effect for when a node is set as a destination
     *
     * @param iNode
     */
    public void setStartNode(INode iNode) {

       // if (st.getNode() == null || !st.getNode().equals(id_circle.get(iNode.getID()))) {
            Circle c;

            if (!id_circle.containsKey(iNode.getID())) {
                c = createCircle(controller.getNode(iNode.getID()));
            } else {
                c = id_circle.get(iNode.getID());
            }

            //this.getChildren().remove(c);
            //this.getChildren().add(c);


            c.setRadius(5);
            highlight(c, Color.GREEN, Color.LIGHTGREEN);
            id_circle.put(iNode.getID(), c);


            st = new ScaleTransition(Duration.millis(100), c);
            st.setByX(1.1f);
            st.setByY(1.1f);
            st.setCycleCount(4);
            st.setAutoReverse(true);
            st.play();
      //  }
    }


    public void setEndNode(INode v, boolean animation) {
        Circle c;

        if (id_circle.containsKey(v.getID())) {
            c = id_circle.get(v.getID());

        } else {
            c = createCircle(v);
        }

        c.setRadius(5);
        highlight(c, Color.FIREBRICK, Color.RED);

        if (animation) {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), c);
            st.setByX(1.1f);
            st.setByY(1.1f);
            st.setCycleCount(4);
            st.setAutoReverse(true);
            st.play();

            st.setOnFinished(event -> {
                c.setRadius(5);
            });
        }

        id_circle.put(v.getID(), c);
    }


    /**
     * remove node completely from map pane
     * (this is for temporary nodes)
     *
     * @param id
     */
    public void removeNode(int id) {
        if (id_circle.containsKey(id)) {
            //normal(id_circle.get(id), );//hideLast(id)
            this.getChildren().remove(id_circle.remove(id));
            id_circle.remove(id);
        }
    }

    public void hideLast(INode node) {
        normal(id_circle.get(node.getID()), node);
    }


    /****************************************************************************************************************
                                                 ICON CODE
     ****************************************************************************************************************/


    /**
     * Creates an SVGPath icon (dependent on the node type)
     *
     * @param v
     * @return
     */
    private ImageView createNodeIcon(INode v) {

        double x = v.getX();  /* the nodes currently have way too small X / Y s - later we'll need to somehow scale */
        double y = v.getY();

        ImageView icon = v.getIcon();
        //Circle circle = new Circle(x, y, 5);
        icon.setTranslateX(x);
        icon.setTranslateY(y);
        icon.setScaleX(.67);
        icon.setScaleY(.67);
        //normal(v);
        icon.setOnMouseClicked(e -> {
            controller.handleMapClick(v);
        });
        return icon;
    }


    /**
     * hides the icon
     *
     * @param c
     */
    public void hideICON(ImageView c) {
        c.setVisible(false);
        //c.setScaleX(.67);
        //c.setScaleY(.67);
        //c.setStrokeWidth(0);
        //c.setRadius(5);
        c.setOpacity(1);
        c.setEffect(null);
    }



/*    private void createTempLandmark(MouseEvent e){
        //CREATE TEMPORARY POINT ->
        INode temp = controller.createTempLandmark(e.getX(), e.getY());
        ImageView c = createNodeIcon(temp);
        id_ICON.put(temp.getID(), c);
        this.getChildren().add(c);
        controller.handleMapClick(temp);
    }*/


    /****************************************************************************************************************
     NOT UPDATED CODE BELOW
     ****************************************************************************************************************/

}