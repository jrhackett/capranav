package MapBuilder;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import logic.Node;

import java.util.HashMap;

public class MapVisual extends StackPane{
	/* Constants */
	private static final double BORDER = 7;
	private double height;
	private double width;

	/* Data Structures */
	private HashMap<Integer, Node>  IDToNode; //no need to even have this here?
	private HashMap<Integer, Circle> itToVisual;

	private Node[] NodeList; // current structure -> switching to HashMap
	private MapBuilderController controller;

	/* Visuals */
	private Image mapImage;
	private ImageView mapView;
	private Rectangle default_background;
	private Group nodeCircles;
	private Canvas pathCanvas;

	/* COLORS */
	private boolean HIGHLIGHTED = false;
	private boolean CLICKED = false;
	private Color last = Color.TRANSPARENT;
	private Color lastStroke = Color.TRANSPARENT;
	private Color lastC = Color.TRANSPARENT;
	private Color lastStrokeC = Color.TRANSPARENT;

	private boolean PATH_DRAWN = false;

	/* this will be used to put together the map */
	/* overlaying the nodes and fixed map image should work */
	public MapVisual(MapBuilderController controller){
		super();
		this.controller = controller;
		this.nodeCircles = new Group();

		set_up_background(); //gray border
		//this.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(default_background, nodeCircles); //
	}

	/**
	 * Given a MAP_NAME -> ask Controller for map name and nodes for the map
	 * Add image to map
	 * Then add the Nodes
	 * @param map
	 */
	public void setMap(logic.Map map){
		/* not sure if this alone will change image, may have to update imageview as well */
		System.out.println("MAP PATH:  " + map.getPath());
		this.mapImage = new Image(getClass().getResourceAsStream("../images/" + map.getPath() + ".png"));
		this.mapView = new ImageView(mapImage);
		drawNodes(controller.getNodesOfMap(map.getID()));
		this.getChildren().add(mapView);
	}


	public void drawNodes(HashMap<Integer, Node> nodes){
		this.nodeCircles = new Group();

		//NodeList = controller.getMapNodes(MAP_NAME);
		/* assuming you put down 'normal' x - y cooridinates, now must translate into display x - y */
		/* also assuming we know the correct height etc */

		nodes.forEach((k,v) -> {
			double x = (height - v.getX()) * 15;  /* the nodes currently have way too small X / Y s - later we'll need to somehow scale */
			double y = (width - v.getY()) * 15;

			Circle circle = new Circle(x - 2.5, y - 2.5, 5, Color.DARKGRAY);
			circle.setOnMouseEntered(e -> {
				last = (Color)circle.getFill();
				lastStroke = (Color)circle.getStroke();
				highlight(circle, Color.BLUE, Color.BLACK);
			});
			circle.setOnMouseExited(e -> highlight(circle, last, lastStroke));
			circle.setOnMousePressed(e -> {
				if (!CLICKED) {
					//setNodeDest(n);
					lastC = (Color)circle.getFill();
					lastStrokeC = (Color)circle.getStroke();
					highlight(circle, Color.GREEN, Color.GREEN);
					CLICKED = true;
				}
			});
			circle.setOnMouseReleased(e -> {
				if (CLICKED) {
					highlight(circle, lastC, lastStrokeC);
					CLICKED = false;
				}
			});

			nodeCircles.getChildren().add(circle); /* adding it to group */
		});

	}

	private void highlight(Circle c, Color color, Color colorStroke ) {
		//c.setFill(Color.web("#33CC00", .4));
		c.setFill(color);
		c.setOpacity(.6);
		c.setStroke(colorStroke);
		c.setStrokeWidth(1);
	}

	private void normal(Circle c) {
		c.setFill(Color.TRANSPARENT);
		c.setOpacity(1);
		c.setStrokeWidth(0);
	}

	private void highlightAll() {
		nodeCircles.getChildren().forEach(e -> {
			if (e instanceof Circle) {
				highlight((Circle) e, Color.GOLD, Color.RED);
			}
		});
	}
	private void hideAll() {
		nodeCircles.getChildren().forEach(e -> {
			if (e instanceof Circle) {
				normal((Circle) e);
			}
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
