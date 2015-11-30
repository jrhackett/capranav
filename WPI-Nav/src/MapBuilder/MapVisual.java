package MapBuilder;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import logic.Edge;
import logic.Node;

import java.util.ArrayList;
import java.util.HashMap;

//The visual component of the maps and the Nodes

public class MapVisual extends Pane {
	/* Constants */
	private static final double BORDER = 7;
	private double height;
	private double width;
	private int currentMapID;

	private HashMap<Integer, Circle> id_circle;
	private MapBuilderController controller;
	private ArrayList<Line> lines;

	/* Visuals */
	private Image mapImage;
	private ImageView mapView;
	private Rectangle default_background;
	private AnchorPane nodeCircles = new AnchorPane();;
	private AnchorPane edgeLines = new AnchorPane();;

	/* COLORS */
	private Color last = Color.TRANSPARENT;
	private Color lastStroke = Color.TRANSPARENT;
	private Color lastC = Color.TRANSPARENT;
	private Color lastStrokeC = Color.TRANSPARENT;

	/* this will be used to put together the map */
	/* overlaying the nodes and fixed map image should work */
	/**
	 * Constructor, requires the controller object
	 * 
	 * @param controller
	 */
	public MapVisual(MapBuilderController controller) {
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
	public void setMap(logic.Map map) {

		// Set the map image
		System.out.println("MAP PATH:  " + map.getPath());
		this.getChildren().remove(mapView);

		try {
			// Image(getClass().getResourceAsStream("../images/" + map.getPath()
			// + ".png"), 660, 495, true, true);
			this.mapImage = new Image(getClass().getResourceAsStream("../images/" + map.getPath() + ".png"));
		} catch (NullPointerException e) {
			// Image(getClass().getResourceAsStream("/images/" + map.getPath() +
			// ".png"), 660, 495, true, true);
			this.mapImage = new Image(getClass().getResourceAsStream("/images/" + map.getPath() + ".png"));
		}

		this.mapView = new ImageView(mapImage);

		// mapView.setFitWidth(width/2);
		mapView.setPreserveRatio(true);
		// mapView.setCache(true);

		// add the mapView object
		this.getChildren().add(mapView);
		// update the current Map ID
		currentMapID = map.getID();

		drawEdges(controller.getCurrentNodeList());
		drawNodes(controller.getCurrentNodeList());

		this.getChildren().addAll(this.edgeLines, this.nodeCircles);

		// This is called when the map is clicked
		mapView.setOnMouseClicked(e -> {
			// If a node is currently selected, de-select it
			if (controller.isNodeSelected()) {
				controller.deselectNode();
			} else {
				// create new node if possible
				double tolerance = 15.0;

				double minDist = tolerance;
				
				// Iterate through all the existing nodes to find the minimum distance to one
				for(Integer k : controller.getCurrentNodeList().keySet()){
					Node v = controller.getCurrentNodeList().get(k);
					double dist = Math.sqrt(Math.pow((v.getX() - e.getX()), 2) + Math.pow((v.getY() - e.getY()), 2));
					if (dist < minDist) {
						minDist = dist;
					}
				}
				
				// If the mouse click is far enough away from existing nodes
				// Make a new node
				if(minDist >= tolerance){
					double xyTolerance = 5;
					
					Node closestX = null;
					double minXDist = xyTolerance;
					Node closestY = null;
					double minYDist = xyTolerance;
					
					
					boolean selectedNode = false;
					
					for(Integer k : controller.getCurrentNodeList().keySet()){
						Node v = controller.getCurrentNodeList().get(k);
						
						if(!selectedNode){
							closestX = v;
							closestY = v;
							selectedNode = true;
						}
						
						double distX = Math.abs(v.getX() - e.getX());
						double distY = Math.abs(v.getY() - e.getY());
						
						if(distX < minXDist){
							minXDist = distX;
							closestX = v;
						}
						
						if(distY < minYDist){
							minYDist = distY;
							closestY = v;
						}
					}
					
					double newNodeX = e.getX();
					double newNodeY = e.getY();
					
					if(minXDist < xyTolerance){
						newNodeX = closestX.getX();
					}
					
					if(minYDist < xyTolerance){
						newNodeY = closestY.getY();
					}
					
					
					controller.newNodeAtLocation(newNodeX, newNodeY);
				} else {
					//System.out.println("To Close to an existing node");
					//System.out.println("Distance: " + minDist);
				}

				
			}

			// Redraw the nodes and edges
			drawEdges(controller.getCurrentNodeList());
			drawNodes(controller.getCurrentNodeList());
		});
	}

	/**
	 * Draws the nodes given on the map
	 * 
	 * @param nodes
	 *            List of nodes for the map
	 */
	public void drawNodes(HashMap<Integer, Node> nodes) {
		// this.getChildren().remove(nodeCircles);
		// this.nodeCircles = new AnchorPane();
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
		if (controller.isNodeSelected()) {
			Circle tempCircle = id_circle.get(controller.getSelectedNode().getID());

			last = (Color) tempCircle.getFill();
			lastStroke = (Color) tempCircle.getStroke();
			highlight(tempCircle, Color.GOLD, Color.BLACK);
		}
	}

	/**
	 * Draws the edges for a given map
	 * 
	 * @param nodes
	 *            List of nodes for the map
	 */
	public void drawEdges(HashMap<Integer, Node> nodes) {
		// this.getChildren().remove(edgeLines);
		// this.edgeLines = new AnchorPane();
		this.edgeLines.getChildren().clear();

		// edgeLines.setMouseTransparent(true);
		edgeLines.setPickOnBounds(false);

		this.lines = new ArrayList<Line>();

		ArrayList<Integer> nodeKeyList = new ArrayList<Integer>();

		nodes.forEach((k, v) -> {
			// Add the node to the arrayList to avoid drawing double edges
			nodeKeyList.add(k);

			// Get the x & y for the main node
			double mainX = v.getX();
			double mainY = v.getY();

			v.getAdjacencies().forEach(e -> {
				Integer nextKey = e.getTarget();
				if (!nodeKeyList.contains(nextKey) && nodes.containsKey(e.getTarget())) {
					// Get the x & y for each of it's children
					double subX = nodes.get(e.getTarget()).getX();
					double subY = nodes.get(e.getTarget()).getY();

					// Draw the line
					Line line = new Line();
					line.setStartX(mainX);
					line.setStartY(mainY);
					line.setEndX(subX);
					line.setEndY(subY);

					createLine(line, v, nodes.get(e.getTarget()));

					line.setStroke(Color.web("#00CCFF", 0.7));
					line.setStrokeWidth(3);
					// line.setStrokeDashOffset(5);
					// line.getStrokeDashArray().addAll(2d, 7d);

					// Add it to array list
					lines.add(line);
					// this.getChildren().add(line);
					this.edgeLines.getChildren().add(line);
				}
			});

		});

		// this.getChildren().add(edgeLines);

	}

	/**
	 * This creates a circle object for a given node
	 * 
	 * @param v
	 *            Value, given node
	 * @return
	 */
	private Circle createCircle(Node v) {

		// the nodes currently have way too small X / Ys - later we'll need to
		// somehow scale
		double x = v.getX();
		double y = v.getY();
		Circle circle = new Circle(x, y, 5);
		normal(circle);

		circle.setOnMouseEntered(e -> {
			last = (Color) circle.getFill();
			lastStroke = (Color) circle.getStroke();
			highlight(circle, Color.GOLD, Color.BLACK);
			// TODO: POPOVER FOR NAME HERE
			/*
			 * PopOver popOver = new PopOver(); popOver.show(circle);
			 * popOver.setContentNode(new Text(v.toString()));
			 */

		});

		circle.setOnMouseExited(e -> {
			if (!controller.isNodeSelected()) {
				normal(circle);
			} else if (controller.getSelectedNode().getID() != v.getID()) {
				normal(circle);
			}

		});

		// when the mouse clicks a node, do something special
		circle.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				if (e.getClickCount() >= 2) {
					System.out.println("Node double clicked");
					// set selected node to null
					// Do special case to enter node info
					// Change colour
				} else {
					if (controller.isNodeSelected() && controller.getSelectedNode().getID() == v.getID()) {
						System.out.println("Node Deselected");
						controller.deselectNode();
						normal(circle);
					} else {
						if (!controller.isNodeSelected()) {
							System.out.println("New node selected");
							controller.selectNode(v.getID());
						} else {
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

								Edge newCEdge = new Edge(v.getID(), 1);
								Edge newVEdge = new Edge(controller.getSelectedNode().getID(), 1);

								controller.getSelectedNode().addEdge(newCEdge);
								v.addEdge(newVEdge);

								drawEdges(controller.getCurrentNodeList());
								drawNodes(controller.getCurrentNodeList());
							} else {
								System.out.println("Edge already exists");
							}
						}
					}
				}
			} else if (e.getButton() == MouseButton.SECONDARY) {
				// Deselect the current node
				controller.deselectNode();

				// Delete the node
				controller.deleteNode(v.getID());

				// The update the visuals
				drawEdges(controller.getCurrentNodeList());
				drawNodes(controller.getCurrentNodeList());
			}
		});

		return circle;
	}

	/**
	 * This creates a line object for each edge. The here is where the mouse
	 * events are added
	 * 
	 * @param line
	 *            Pre-created line with x and y coordinates already
	 * @param nodeA
	 *            One of the ends of the edge
	 * @param nodeB
	 *            The other end of the edge
	 * @return
	 */
	private Line createLine(Line line, Node nodeA, Node nodeB) {

		// Set the initial colour
		line.setStroke(Color.web("#00CCFF", 0.7));
		line.setStrokeWidth(3);

		// Set the colour when scrolling over the line
		line.setOnMouseEntered(e -> {
			line.setStroke(Color.web("#EE0000", 0.7));
		});

		// Set the colour when leaving the line
		line.setOnMouseExited(e -> {
			line.setStroke(Color.web("#00CCFF", 0.7));
		});

		// Delete the line if right clicked
		line.setOnMouseClicked(e -> {
			// If there was a selected node, deselect it and change it's colour
			if (this.controller.isNodeSelected()) {
				normal(this.id_circle.get(controller.getSelectedNode().getID()));

				controller.deselectNode();
			}

			// Only delete the edge if it is right clicked
			if (e.getButton() == MouseButton.SECONDARY) {
				System.out.println("Edge removed");

				// Remove the line from the animation locations
				this.edgeLines.getChildren().remove(line);

				// Remove the edge from each node
				Edge edgeToDelete = null;

				for (Edge edge : nodeA.getAdjacencies()) {
					if (edge.getTarget() == nodeB.getID()) {
						edgeToDelete = edge;
					}
				}

				if (edgeToDelete != null) {
					nodeA.getAdjacencies().remove(edgeToDelete);
				}

				edgeToDelete = null;

				for (Edge edge : nodeB.getAdjacencies()) {
					if (edge.getTarget() == nodeA.getID()) {
						edgeToDelete = edge;
					}
				}

				if (edgeToDelete != null) {
					nodeB.getAdjacencies().remove(edgeToDelete);
				}
			}
		});

		return null;
	}

	/**
	 * This deselects the selected node
	 *
	 * @param id:
	 */
	public void deselect(int id) {
		controller.deselectNode();
		this.last = Color.BLUE;
		this.lastStroke = Color.BLACK;
		highlightAll();
		normal(id_circle.get(id));
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
		c.setFill(Color.BLUE);
		c.setStrokeWidth(0);
		c.setRadius(5);
	}

	private void highlightAll() {
		id_circle.forEach((k, v) -> {
			highlight((Circle) v, last, lastStroke);

		});

	}

	private void hideAll() {
		id_circle.forEach((k, v) -> {
			normal(v);
		});
	}

	private void set_up_background() {
		default_background = new Rectangle(width, height);
		default_background.setFill(Color.DARKBLUE);
		default_background.setOpacity(.2);
		default_background.setArcHeight(7);
		default_background.setArcWidth(7);
	}
}
