package visuals;

import controller.Controller;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import logic.IMap;
import logic.INode;

import java.util.ArrayList;
import java.util.HashMap;


public class MapDisplay extends Pane {
    /* constants */
    private double IMAGE_WIDTH = 660;
    private double IMAGE_HEIGHT = 495;


    /* Data Structures */
    private HashMap<Integer, Circle> id_circle;
    private Controller controller;
    private ArrayList<Line> lines; /* to be able to remove/hide these */

    /* Visuals */
    private Transition sts;
    private Transition ste;


    private Image mapImage;
    private ImageView mapView;

    private Color last = Color.TRANSPARENT;
    private Color lastStroke = Color.TRANSPARENT;

    private boolean HIGLIGHTED = false;

    private ArrayList<INode> path; //last set path

    /**
     * Constructor
     * @param controller
     */
    public MapDisplay(Controller controller){
        super();
        this.controller = controller;
        this.setStyle("-fx-background-color: #F5F5DC");
        this.mapView = new ImageView();
        this.setMaxWidth(IMAGE_WIDTH);
        this.setMaxHeight(IMAGE_HEIGHT);
        this.id_circle = new HashMap<>();
        this.lines = new ArrayList<>();
        this.path = new ArrayList<>();
        this.ste = new ScaleTransition();
        this.sts = new ScaleTransition();
    }


    public void clearNodesEdges(int ids, int ide){
        int i = 0;
        id_circle.forEach((k,v) ->{
            //we have to also check
            if (k != ids && k != ide) { normal(v); }
        //    System.out.println(k);
        });
        //id_circle = new HashMap<>();
        this.getChildren().removeAll(lines);

        lines = new ArrayList<>();
    }


  /*
    public void setMap(logic.Map map){

        this.getChildren().remove(mapView);
        //this.getChildren().removeAll(lines);
        // path.clear();


        try {
            this.mapImage = new Image(getClass().getResourceAsStream("../images/" + map.getPath() + ".png"), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
        }
        catch (NullPointerException e) {
            this.mapImage = new Image(getClass().getResourceAsStream("/images/" + map.getPath() + ".png"), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
        }

        this.mapView = new ImageView(mapImage);
        this.getChildren().add(mapView);
        drawNodes(controller.getNodesOfMap(map.getID()));

        HIGLIGHTED = false;

        mapView.setOnMouseClicked(e -> {
            if (!HIGLIGHTED) {
                highlightAll();
                HIGLIGHTED = true;
            } else {
                //hack / decision
                this.getChildren().removeAll(lines);
                hideAll();
                controller.resetStartEnd();
                HIGLIGHTED = false;

                /*
                if (controller.startNode != null && controller.endNode != null) {

                    id_circle.forEach((k, v) -> {
                        if (k != controller.startNode.getID() && k != controller.endNode.getID()) normal(v);
                        else if (k != controller.startNode.getID()) setStartNode(k, false);
                        else setStartNode(k, true);
                    });
                    showPath(path);
                } else {
                    hideAll();
                }
                HIGLIGHTED = false;
                *//*
            }
        });

    }
*/


    /**
     * Given a MAP_NAME -> ask Controller for map name and nodes for the map [NEW VERSION]
     * Add image to map
     * Then add the Nodes
     * @param map
     */
    public void setMap(IMap map) {

        this.getChildren().remove(mapView);

        try {
            this.mapImage = new Image(getClass().getResourceAsStream("../images/" + map.getPath() + ".png"), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
        } catch (NullPointerException e) {
            this.mapImage = new Image(getClass().getResourceAsStream("/images/" + map.getPath() + ".png"), IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
        }

        this.mapView = new ImageView(mapImage);
        this.getChildren().add(mapView);
        drawNodes(controller.getNodesOfMap(map.getID()));

        // HIGLIGHTED = false;

        mapView.setOnMouseClicked(e -> createTempLandmark(e));
    }

    private void createTempLandmark(MouseEvent e){
        //CREATE TEMPORARY POINT ->
        INode temp = controller.createTempLandmark(e.getX(), e.getY());
        Circle c = createCircle(temp);
        id_circle.put(temp.getID(), c);
        this.getChildren().add(c);
        controller.handleMapClick(temp);
    }



    /**
     * remove node completely from map pane
     * (this is for temporary nodes)
     * @param id
     */
    public void removeNode(int id){
        if(id_circle.containsKey(id)){
            normal(id_circle.get(id));//hideLast(id)
            this.getChildren().remove(id_circle.remove(id));
            id_circle.remove(id);
        }
    }

    public void hideLast(int id){
        normal(id_circle.get(id));
    }

    /**
     * Show path of nodes
     *
     */
    public void showPath(ArrayList<INode> path){
        this.getChildren().removeAll(lines);//// TODO: 11/18/15 test this
        this.path = path;
        //hideAll();
        id_circle.forEach((k,v) -> {
            if(k != controller.startNode.getID() && k != controller.endNode.getID()) normal(v);
            else if (k != controller.startNode.getID()) setStartNode(k, false);
            else setStartNode(k, true);
        });
        drawPaths(path);

        for (int i = 1; i < path.size() - 1; i++){
            highlightPath(id_circle.get(path.get(i).getID()));
        }

    }


    public void drawPaths(ArrayList<INode> pathNodes){
        if (pathNodes.size() > 0){
            double coordX = pathNodes.get(0).getX();
            double coordY = pathNodes.get(0).getY();

            for (int i = 1; i < pathNodes.size(); i++){
                Line line = new Line();
                line.setStartX(coordX);
                line.setStartY(coordY);
                coordX = pathNodes.get(i).getX();
                coordY = pathNodes.get(i).getY();
                line.setEndX(coordX);
                line.setEndY(coordY);
                line.setStroke(Color.web("#00CCFF", 0.7));
                line.setStrokeWidth(2);
                line.setStrokeDashOffset(5);
                line.getStrokeDashArray().addAll(2d, 7d);
                //line.setStrokeLineCap(StrokeLineCap.BUTT);
                lines.add(line); //adding it to array lst
                this.getChildren().add(line);
            }
        }
    }

    /**
     * Hide all and show path
     */
    public void hideAll(){
        id_circle.forEach((k,v) -> {
            normal(v);
        });
    }


    /**
     * Draws the nodes given on the map
     * @param nodes
     */
    public void drawNodes(HashMap<Integer, INode> nodes){
        this.id_circle = new HashMap<>();

        nodes.forEach((k,v) -> {
            Circle circle = createCircle(v);
            id_circle.put(k, circle);
            this.getChildren().add(circle);
        });
    }


    private Circle createCircle(INode v){

        double x = v.getX();  /* the nodes currently have way too small X / Y s - later we'll need to somehow scale */
        double y = v.getY();
        Circle circle = new Circle(x, y, 5);
        normal(circle);

        /*
        circle.setOnMouseEntered(e -> {
            last = (Color)circle.getFill();
            lastStroke = (Color)circle.getStroke();
            highlight(circle, Color.BLUE, Color.BLACK);
        });

        circle.setOnMouseExited(e -> {
            highlight(circle, last, lastStroke);
        });
        */
        circle.setOnMouseClicked(e -> {
            controller.handleMapClick(v);
        });
/*
        circle.setOnMousePressed(e -> {

        });

        circle.setOnMouseReleased(e -> {

        });
        */

        return circle;
    }


    /**
     * The color and effect for when a node is set as a destination
     * @param id
     */
    public void setStartNode(int id, boolean START){
        Circle c = id_circle.get(id);
        c.setRadius(5);

        if (START) {
            highlight(c, Color.GREEN, Color.LIGHTGREEN);
            c.setRadius(5);


            if (sts != null) {
                sts.stop();
             /*   if((Circle)sts.getNode() == c){ return;}//do nothing _
                sts.*/
            }
            ScaleTransition st = new ScaleTransition(Duration.millis(150), c);
            st.setByX(1.1f);
            st.setByY(1.1f);
            st.setCycleCount(4);
            st.setAutoReverse(true);
            st.play();
            sts = st;
        }
        else {
            highlight(c, Color.FIREBRICK, Color.RED);
            if (ste != null) ste.stop();
            c.setRadius(5);


            ScaleTransition st = new ScaleTransition(Duration.millis(150), c);
            st.setByX(1.1f);
            st.setByY(1.1f);
            st.setCycleCount(4);
            st.setAutoReverse(true);
            st.play();
            ste = st;
        }
    }


    /**
     * Highlights the path of nodes.
     * @param c
     */
    private void highlightPath(Circle c){
        //c.setFill(Color.web("#00CCFF"));
       // c.setStroke(Color.web("#0018A8"));
        c.setRadius(5);
        highlight(c, Color.BLUE, Color.LIGHTBLUE);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.WHITE);
        ds.setOffsetX(2.0);
        ds.setOffsetY(2.0);
        c.setEffect(ds);
        c.setOnMouseEntered(null);
        c.setOnMouseExited(null);
    }


    /**
     * highlights a circle
     * @param c
     * @param color
     * @param colorStroke
     */
    private void highlight(Circle c, Color color, Color colorStroke ) {
        c.setFill(color);
        c.setStroke(colorStroke);
        c.setStrokeWidth(1);
    }


    /**
     * default circle
     * @param c
     */
    public void normal(Circle c) {
        c.setFill(Color.BLUE);
        c.setStrokeWidth(0);
        c.setRadius(5);
        c.setOpacity(1);
        c.setEffect(null);
    }


    /**
     * clear selection
     * @param
     */

    private void highlightAll() {
        this.getChildren().forEach(e -> {
            if (e instanceof Circle) {
                highlight((Circle) e, Color.GOLD, Color.RED);
            }
        });
    }

}


