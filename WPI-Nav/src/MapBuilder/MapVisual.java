package MapBuilder;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import logic.Node;

import java.util.HashMap;

//TODO Fix this file
//The visual component of the maps and the Nodes

/*
public class MapVisual extends Pane {
	*/
/* Constants *//*

	private static final double BORDER = 7;
	private double height;
	private double width;

	public boolean MAP  = true;	 //Describes what phase we are in
	public boolean EDGE = false;
	public boolean NODE = false;



	*/
/* Data Structures *//*

	private HashMap<Integer, Circle> id_circle;
	private MapBuilderController controller;

	*/
/* Visuals *//*

	private Image mapImage;
	private ImageView mapView;
	private Rectangle default_background;
	private AnchorPane nodeCircles;

	*/
/* COLORS *//*

	private boolean HIGHLIGHTED = false;
	private boolean CLICKED = false;
	private Color last = Color.TRANSPARENT;
	private Color lastStroke = Color.TRANSPARENT;
	private Color lastC = Color.TRANSPARENT;
	private Color lastStrokeC = Color.TRANSPARENT;


	*/
/* this will be used to put together the map *//*

	*/
/* overlaying the nodes and fixed map image should work *//*

	public MapVisual(MapBuilderController controller){
		super();
		this.controller = controller;
		this.mapView = new ImageView();
		//this.nodeCircles = new AnchorPane();

		set_up_background(); //gray border
		this.getChildren().addAll(); //nodeCircles
	}

	*/
/**
	 * Given a MAP_NAME -> ask Controller for map name and nodes for the map
	 * Add image to map
	 * Then add the Nodes
	 * @param map
	 *//*

	public void setMap(logic.Map map){
		System.out.println("MAP PATH:  " + map.getPath());
		this.getChildren().remove(mapView);
		//id_circle.forEach((k,v) -> {if(v!= null){this.getChildren().remove(v); }});
		try {
			this.mapImage = new Image(getClass().getResourceAsStream("../images/" + map.getPath() + ".png"), 660, 495, true, true);
		}
		catch (NullPointerException e) {
			this.mapImage = new Image(getClass().getResourceAsStream("/images/" + map.getPath() + ".png"), 660, 495, true, true);
		}

		this.mapView = new ImageView(mapImage);
		this.getChildren().add(mapView);
		drawNodes(controller.getNodesOfMap(map.getID()));

		mapView.setOnMouseClicked(e -> {
			if (NODE) {
				int id = controller.newNodeAtLocation(e); //x & y are already relative to map
				//create a circle for this node!!
				Node n = controller.getNode(id);
				Circle c = createCircle(n);
				id_circle.put(id, c);
				//nodeCircles.getChildren().removeAll();
				this.getChildren().add(c);//nodeCircles
			}
		});
	}

	*/
/**
	 * Draws the nodes given on the map
	 * @param nodes
     *//*

	public void drawNodes(HashMap<Integer, Node> nodes){
		this.getChildren().remove(nodeCircles);
		//this.nodeCircles = new AnchorPane();
		this.id_circle = new HashMap<>();



		//nodeCircles.setMouseTransparent(true);
		//nodeCircles.setPickOnBounds(false);
		//this.getChildren().add(nodeCircles);
		nodes.forEach((k,v) -> {
			Circle circle = createCircle(v);
			id_circle.put(k, circle);
			this.getChildren().add(circle); */
/* adding directly to stackpane *//*

		});
	}






	private Circle createCircle(Node v){

		double x = v.getX();  */
/* the nodes currently have way too small X / Y s - later we'll need to somehow scale *//*

		double y = v.getY();
		Circle circle = new Circle(x, y, 5);
		normal(circle);

		circle.setOnMouseEntered(e -> {
			if (!(EDGE && controller.SELECTED)) {
				last = (Color) circle.getFill();
				lastStroke = (Color) circle.getStroke();
				highlight(circle, Color.GOLD, Color.BLACK);
				//TODO: POPOVER FOR NAME HERE
				*/
/*
				PopOver popOver = new PopOver();
				popOver.show(circle);
				popOver.setContentNode(new Text(v.toString()));
				*//*

			}
		});

		circle.setOnMouseExited(e -> {
			if (controller.SELECTED && controller.selectedNode.getID() == v.getID()) {
			} else if (EDGE && controller.SELECTED) {
			} else {
				highlight(circle, last, lastStroke);
			}

		});


		//when the mouse clicks a node change color!
		//depending on the PHASE OF THE MBT, DO SOMETHING!
		circle.setOnMouseClicked(e -> {
			if (MAP){
				//do nothing
			} else if (NODE){
				if (!controller.SELECTED){//no node is currently selected
					controller.SELECTED = true;
					controller.selectedNode = v;
					selected(circle);
				} else if (controller.SELECTED && v.getID() == controller.selectedNode.getID()){
					deselect(v.getID());
				}
			}else if (EDGE){
				if (!controller.SELECTED){//no node is currently selected
					controller.SELECTED = true;
					controller.selectedNode = v;
					selected(circle);
				} else if (controller.SELECTED && v.getID() == controller.selectedNode.getID()){
					deselect(v.getID());
					controller.resetPotentialEdges();
				} else {//controller selected
					highlight(circle, Color.FIREBRICK, Color.WHITE);
					controller.addPotentialEdge(v);
				}
			}
		});


		circle.setOnMousePressed(e -> {
			if (MAP) {
				if (!CLICKED) {
					lastC = (Color) circle.getFill();
					lastStrokeC = (Color) circle.getStroke();
					highlight(circle, Color.GREEN, Color.GREEN);
					CLICKED = true;
				}
			}
		});

		circle.setOnMouseReleased(e -> {
			if (MAP) {
				if (CLICKED) {
					highlight(circle, lastC, lastStrokeC);
					CLICKED = false;
				}
			}
		});


		return circle;
	}

	*/
/**
	 *
	 * @param id:
     *//*

	public void deselect(int id){
		controller.SELECTED = false;
		controller.selectedNode = null;
		this.last = Color.BLUE;
		this.lastStroke = Color.BLACK;
		highlightAll();
		normal(id_circle.get(id));
	}

	private void selected(Circle circle){

		lastC = (Color)circle.getFill();
		lastStrokeC = (Color)circle.getStroke();
		highlight(circle, Color.GOLD, Color.RED);
		circle.setRadius(7.5);
	}

	private void highlight(Circle c, Color color, Color colorStroke ) {
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
		id_circle.forEach((k,v) -> {
			highlight((Circle) v, last, lastStroke);

		});

	}
	private void hideAll() {
		id_circle.forEach((k,v) -> {
			normal(v);
		});
	}
	private void set_up_background(){
		default_background = new Rectangle(width, height);
		default_background.setFill(Color.DARKBLUE);
		default_background.setOpacity(.2);
		default_background.setArcHeight(7);
		default_background.setArcWidth(7);
	}
}
*/
