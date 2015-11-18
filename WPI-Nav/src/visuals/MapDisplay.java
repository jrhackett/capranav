package visuals;

import controller.Controller;
import javafx.animation.ScaleTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import logic.Node;

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
    private Image mapImage;
    private ImageView mapView;

    private Color last = Color.TRANSPARENT;
    private Color lastStroke = Color.TRANSPARENT;

    private boolean HIGLIGHTED = false;

    private ArrayList<Node> path; //last set path

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


    /**
     * Given a MAP_NAME -> ask Controller for map name and nodes for the map
     * Add image to map
     * Then add the Nodes
     * @param map
     */
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

        //HIGLIGHTED = false;

        mapView.setOnMouseClicked(e -> {
            if (!HIGLIGHTED) {
                highlightAll();
                HIGLIGHTED = true;
            } else {
                hideAll();
                //if(path.size() > 0)
                    showPath(path);
                HIGLIGHTED = false;
            }
        });

    }

    /**
     * Show path of nodes
     *
     */
    public void showPath(ArrayList<Node> path){
        this.path = path;
        hideAll();
        drawPaths(path);

        for (Node n : path){
            highlightPath(id_circle.get(n.getID()));
        }

    }


    public void drawPaths(ArrayList<Node> pathNodes){
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
    public void drawNodes(HashMap<Integer, Node> nodes){
        this.id_circle = new HashMap<>();

        nodes.forEach((k,v) -> {
            Circle circle = createCircle(v);
            id_circle.put(k, circle);
            this.getChildren().add(circle);
        });
    }


    private Circle createCircle(Node v){

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
            controller.nodeFromMapHandler(v);
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
    public void setStartNode(int id){
        Circle c = id_circle.get(id);
        highlight(c, Color.BLUE, Color.LIGHTBLUE);
        ScaleTransition st = new ScaleTransition(Duration.millis(150), c);
        st.setByX(1.1f);
        st.setByY(1.1f);
        st.setCycleCount(4);
        st.setAutoReverse(true);
        st.play();
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
        c.setFill(Color.TRANSPARENT);
        c.setStrokeWidth(0);
        c.setRadius(5);
        c.setOpacity(1);
        c.setEffect(null);
    }


    /**
     * clear selection
     * @param
     */
    /*
    public void clearSelection(int id){
        normal(id_circle.get(id));
    }
*/

    private void highlightAll() {
        this.getChildren().forEach(e -> {
            if (e instanceof Circle) {
                highlight((Circle) e, Color.GOLD, Color.RED);
            }
        });
    }

}


