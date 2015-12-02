// MapVisual

package MapBuilder;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import logic.Edge;
import logic.INode;

import java.util.HashMap;

//The visual component of the maps and the Nodes

public class MapVisualSecondary extends Pane {
    /* Constants */
    private static final double BORDER = 7;
    private double height;
    private double width;
    private int currentMapID;

    private double IMAGE_WIDTH = 700;//660
    private double IMAGE_HEIGHT = 525;//495

    private HashMap<Integer, Circle> id_circle;
    private MapBuilderController controller;

    /* Visuals */
    private Image mapImage;
    private ImageView mapView;
    private Rectangle default_background;
    private AnchorPane nodeCircles = new AnchorPane();;

    /* COLORS */
    private Color last = Color.TRANSPARENT;
    private Color lastStroke = Color.TRANSPARENT;

	/* this will be used to put together the map */
	/* overlaying the nodes and fixed map image should work */
    /**
     * Constructor, requires the controller object
     *
     * @param controller
     */
    public MapVisualSecondary(MapBuilderController controller) {
        super();
        this.controller = controller;
        this.mapView = new ImageView();
        // this.nodeCircles = new AnchorPane();

        set_up_background(); // gray border
        this.getChildren().addAll(); // nodeCircles
    }

    /**
     * Given a MAP_NAME -> ask Controller for map name and nodes for the map Add
     * image to map Then add the Nodes
     *
     * @param map
     */
    public void setMap(logic.IMap map) {

        // Set the map image
        System.out.println("MAP PATH:  " + map.getFilePath());
        this.getChildren().remove(mapView);

        try {
            // Image(getClass().getResourceAsStream("../images/" + map.getPath()
            // + ".png"), 660, 495, true, true);
            this.mapImage = new Image(getClass().getResourceAsStream("../images/floorPlans/" + map.getFilePath()));
        } catch (NullPointerException e) {
            // Image(getClass().getResourceAsStream("/images/" + map.getPath() +
            // ".png"), 660, 495, true, true);
            this.mapImage = new Image(getClass().getResourceAsStream("/images/floorPlans/" + map.getFilePath()));
        }

        this.mapView = new ImageView(mapImage);
        mapView.setFitHeight(IMAGE_HEIGHT);
        mapView.setFitWidth(IMAGE_WIDTH);
        // mapView.setFitWidth(width/2);
        //mapView.setPreserveRatio(true);
        // mapView.setCache(true);

        // add the mapView object
        this.getChildren().add(mapView);
        // update the current Map ID
        currentMapID = map.getID();

        //drawEdges(controller.getSecondaryNodeList());
        drawNodes(controller.getSecondaryNodeList());

        this.getChildren().remove(this.nodeCircles);
        this.getChildren().addAll(this.nodeCircles);

    }

    /**
     * Draws the nodes given on the map
     *
     * @param nodes
     *            List of nodes for the map
     */
    public void drawNodes(HashMap<Integer, INode> nodes) {
        // this.getChildren().remove(nodeCircles);
        this.nodeCircles.getChildren().clear();

        // Sets it so that objects lower in the stackPane can still be clicked
        nodeCircles.setPickOnBounds(false);

        this.id_circle = new HashMap<>();

        nodes.forEach((k, v) -> {
            Circle circle = createCircle(v);
            id_circle.put(k, circle);
            this.nodeCircles.getChildren().add(circle);
        });

        // If a given node is selected, colour it

    }

    /**
     * This creates a circle object for a given node
     *
     * @param v
     *            Value, given node
     * @return
     */
    private Circle createCircle(INode v) {

        // the nodes currently have way too small X / Ys - later we'll need to
        // somehow scale
        double x = v.getX();
        double y = v.getY();
        Circle circle = new Circle(x, y, 5);
        normal(circle);

        // Highlight the node when entered
        circle.setOnMouseEntered(e -> {
            last = (Color) circle.getFill();
            lastStroke = (Color) circle.getStroke();
            highlight(circle, Color.GOLD, Color.BLACK);
        });

        // Unhighlight it when exited
        circle.setOnMouseExited(e -> {
            normal(circle);
        });

        // when the mouse clicks a node, do something special
        circle.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if(controller.isNodeSelected() && controller.getSelectedNode().isTransition()){

                    // Make an edge to that node

                    // Add edge between nodes

                    // check to see if there is a new edge to be created
                    boolean isNewEdge = true;

                    for (Edge edge : controller.getSelectedNode().getAdjacencies()) {
                        if (edge.getTarget() == v.getID()) {
                            isNewEdge = false;
                        }
                    }

                    // If there is a new edge, create each direction and
                    // add them to the respective nodes
                    // Also draw the edge
                    if (isNewEdge) {
                        System.out.println("New Edge Created!");
                        controller.setEdgeInformation("New Edge Across Maps Created!");


                        Edge newCEdge = new Edge(v.getID(), 1);
                        Edge newVEdge = new Edge(controller.getSelectedNode().getID(), 1);

                        controller.getSelectedNode().addEdge(newCEdge);
                        v.addEdge(newVEdge);
                    } else {
                        System.out.println("Edge already exists");
                        controller.setEdgeInformation("Edge already exists");
                    }
                }
            }
        });

        return circle;
    }

	/*
	 * private void selected(Circle circle) {
	 *
	 * lastC = (Color) circle.getFill(); lastStrokeC = (Color)
	 * circle.getStroke(); highlight(circle, Color.GOLD, Color.RED);
	 * circle.setRadius(7.5); }
	 */

    private void highlight(Circle c, Color color, Color colorStroke) {
        c.setFill(color);
        c.setStroke(colorStroke);
        c.setStrokeWidth(1);
    }

    private void normal(Circle c) {
        c.setFill(Color.HOTPINK);
        c.setStrokeWidth(0);
        c.setRadius(5);
    }

    private void set_up_background() {
        default_background = new Rectangle(width, height);
        default_background.setFill(Color.DARKBLUE);
        default_background.setOpacity(.2);
        default_background.setArcHeight(7);
        default_background.setArcWidth(7);
    }
}
