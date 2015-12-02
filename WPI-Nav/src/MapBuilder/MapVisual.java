// MapVisual

package MapBuilder;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import logic.*;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//The visual component of the maps and the Nodes

public class MapVisual extends Pane {
	/* Constants */
	private static final double BORDER = 7;
	private double height;
	private double width;
	private int currentMapID;

	private double IMAGE_WIDTH = 700;//660
	private double IMAGE_HEIGHT = 525;//495

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
	public void setMap(logic.IMap map) {
		
		// Set the map image
		System.out.println("MAP PATH:  " + map.getFilePath());
		if (this.getChildren().size() > 0 ) {
			this.getChildren().remove(0, this.getChildren().size() - 1);
		}
		//0, this.getChildren().size()-1);


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

		drawEdges(controller.getCurrentNodeList());
		drawNodes(controller.getCurrentNodeList());

		this.getChildren().remove(this.edgeLines);
		this.getChildren().remove(this.nodeCircles);
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
					INode v = controller.getCurrentNodeList().get(k);
					double dist = Math.sqrt(Math.pow((v.getX() - e.getX()), 2) + Math.pow((v.getY() - e.getY()), 2));
					if (dist < minDist) {
						minDist = dist;
					}
				}

				// If the mouse click is far enough away from existing nodes
				// Make a new node
				if(minDist >= tolerance){

					double newNodeX = e.getX();
					double newNodeY = e.getY();

					if (controller.SNAPPING) {

						double xyTolerance = 5;

						INode closestX = null;
						double minXDist = xyTolerance;
						INode closestY = null;
						double minYDist = xyTolerance;

						boolean selectedNode = false;

						for (Integer k : controller.getCurrentNodeList().keySet()) {
							INode v = controller.getCurrentNodeList().get(k);

							if (!selectedNode) {
								closestX = v;
								closestY = v;
								selectedNode = true;
							}

							double distX = Math.abs(v.getX() - e.getX());
							double distY = Math.abs(v.getY() - e.getY());

							if (distX < minXDist) {
								minXDist = distX;
								closestX = v;
							}

							if (distY < minYDist) {
								minYDist = distY;
								closestY = v;
							}
						}

						if (minXDist < xyTolerance) {
							newNodeX = closestX.getX();
						}

						if (minYDist < xyTolerance) {
							newNodeY = closestY.getY();
						}
					}
					
					
					controller.newPathNodeAtLocation(newNodeX, newNodeY);
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
	public void drawNodes(HashMap<Integer, INode> nodes) {
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
	public void drawEdges(HashMap<Integer, INode> nodes) {
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

			//System.out.println("Random node edge list size: " + v.getAdjacencies().size());
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
	private Circle createCircle(INode v) {

		// the nodes currently have way too small X / Ys - later we'll need to
		// somehow scale
		double x = v.getX();
		double y = v.getY();

		Circle circle = new Circle(x, y, 5);

		normal(circle, v);


		circle.setOnMouseEntered(e -> {
			last = (Color) circle.getFill();
			lastStroke = (Color) circle.getStroke();
			highlight(circle, Color.GOLD, Color.BLACK);
			controller.setNodeInformation(v);

		});

		circle.setOnMouseExited(e -> {
			normal(circle, v);

		});

		// when the mouse clicks a node, do something special
		circle.setOnMouseClicked(e -> {
			if (e.getButton() == MouseButton.PRIMARY) {
				if (e.getClickCount() >= 2) {
					if(controller.isNodeSelected()){
						INode selectedNode = controller.getSelectedNode();
						controller.deselectNode();
						normal(id_circle.get(selectedNode.getID()), selectedNode);
					}

					normal(circle, v);


					System.out.println("Node double clicked");
					// set selected node to null
					PopOver infoPopOver = new PopOver();
					infoPopOver.show(circle);

					//VBox of buttons
					VBox buttonBox = new VBox();
					Button bathroom = new Button("Bathroom");
					Button mensroom = new Button("Mens' Bathroom");
					Button girlsroom = new Button("Womens' Bathroom");
					Button elevator = new Button("Elevator");
					Button food = new Button("Food");
					Button landmark = new Button("Landmark");
					Button path = new Button("Path");
					Button room = new Button("Room");
					Button stairs = new Button("Stairs");
					Button tstairs = new Button("Transition Stairs");

					buttonBox.getChildren().addAll(bathroom, mensroom, girlsroom, elevator, food, landmark, path, room, stairs, tstairs);

					//effects of buttons
					mensroom.setOnAction(c -> handleNodeChoiceHelper(new Bathroom(v), BathroomType.MENS, infoPopOver));
					girlsroom.setOnAction(c -> handleNodeChoiceHelper(new Bathroom(v), BathroomType.WOMAN, infoPopOver));
					bathroom.setOnAction(c -> handleNodeChoice(new Bathroom(v), infoPopOver));
					elevator.setOnAction(c -> handleNodeChoice(new Elevator(v), infoPopOver));
					food.setOnAction(c -> handleNodeChoice(new Food(v), infoPopOver));
					landmark.setOnAction(c -> handleNodeChoice(new Landmark(v), infoPopOver));
					path.setOnAction(c -> handleNodeChoice(new Path(v), infoPopOver));
					room.setOnAction(c -> handleNodeChoice(new Room(v), infoPopOver));
					stairs.setOnAction(c -> handleNodeChoice(new Stairs(v), infoPopOver));
					tstairs.setOnAction(c -> handleNodeChoice(new TStairs(v), infoPopOver));


					infoPopOver.setContentNode(buttonBox);
					// Generate pop-over
					// Add buttons for each kind of node
					// When a specific button is picked, modify the other elements in the pop-over to display pertinent information
					
					// Once the information is entered, store it temporarally
					
					// Once the pop-over is exited (by clicking anywhere else
						// take the old node
						// take the INode specific info: Heuristics, x, y, z, etc
						// Make a new node of the specified type
						// Enter old node information
						// Enter new information (i.e. name)
						// save new node to nodeList in controller
					
					//Close the pop-over
					//Redraw all the nodes so that special onces are a certain colour
				} else {
					if (controller.isNodeSelected() && controller.getSelectedNode().getID() == v.getID()) {
						System.out.println("Node Deselected");
						controller.deselectNode();
						normal(circle, v);
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
	 * This method helps the handleNodeChoice method
	 *
	 * @param node
	 * @param type
     */
	private void handleNodeChoiceHelper(Bathroom node, BathroomType type, PopOver popOver){
		node.setBathroomType(type);
		handleNodeChoice(node, popOver);
	}

	/**
	 * This is called in the node popover
	 * It takes in a new node and modified the selected node
	 *
	 * @param iNode
     */
	private void handleNodeChoice(INode iNode, PopOver popOver){
		if (iNode.isInteresting()){
			VBox input = new VBox();
			TextField newNodeName = new TextField();
			newNodeName.setText("Node Not Named");

			//populate popover with new
			Button doneButton = new Button("Done");
			Button cancelButton = new Button("Cancel");

			HBox buttonBox = new HBox();
			buttonBox.getChildren().addAll(doneButton, cancelButton);


			input.getChildren().addAll(newNodeName, buttonBox);
			popOver.setContentNode(input);

			doneButton.setOnAction(e -> saveNodeInformation(newNodeName.getText(), iNode, popOver));
			cancelButton.setOnAction(e -> popOver.hide());


		} else {
			controller.getNodesOfMap().put(iNode.getID(), iNode);
			popOver.hide();

			this.getChildren().remove(id_circle.get(iNode.getID()));
			Circle	c =		createCircle(iNode);
			this.getChildren().add(c);
			this.id_circle.put(iNode.getID(), c);
		}
	}


	private void saveNodeInformation(String s, INode iNode, PopOver popOver ){
		controller.getNodesOfMap().put(iNode.getID(), iNode);
		popOver.hide();

		ArrayList<String> list = parseString(s);
		iNode.addNames(list);
		this.getChildren().remove(id_circle.get(iNode.getID()));
		Circle	c =		createCircle(iNode);
		this.getChildren().add(c);
		this.id_circle.put(iNode.getID(), c);
	}


	private ArrayList<String> parseString(String str){
		return new ArrayList<String>(Arrays.asList(str.split("\\s*,\\s*")));
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
	private Line createLine(Line line, INode nodeA, INode nodeB) {

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
				normal(this.id_circle.get(controller.getSelectedNode().getID()), controller.getSelectedNode());

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
		normal(id_circle.get(id), controller.getNode(id));
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

	private void normal(Circle c, INode v) {
		if(controller.isNodeSelected() && controller.getSelectedNodeID() == v.getID()){
			last = (Color) c.getFill();
			lastStroke = (Color) c.getStroke();
			highlight(c, Color.GOLD, Color.BLACK);
		} else {

			if (!v.isTransition()) {
				c.setFill(Color.BLUE);
				c.setStrokeWidth(0);
				c.setRadius(5);
			} else {
				c.setFill(Color.HOTPINK);
				c.setStrokeWidth(0);
				c.setRadius(5);
			}
		}
	}


	private void highlightAll() {
		id_circle.forEach((k, v) -> {
			highlight((Circle) v, last, lastStroke);

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
