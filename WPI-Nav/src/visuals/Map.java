package visuals;

import controller.Controller;
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

public class Map extends StackPane{
	/* Constants */
	private static final double BORDER = 7;
	private double height;
	private double width;

	/* Data Structures */
	private HashMap<Integer, Node>  IDToNode; //no need to even have this here?
	private HashMap<Integer, Circle> itToVisual;

	private Node[] NodeList; // current structure -> switching to HashMap
	private Controller controller;

	/* Visuals */
	private Image mapImage;
	private ImageView mapView;
	private Rectangle default_background;
	private Group nodes;
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
	public Map(double width, double height, Controller controller){
		super();
		this.controller = controller;
		this.width = width;
		this.height = height;
		this.nodes = new Group();

		set_up_background(); //gray border
		default_map();		 //default campus map
		//this.setAlignment(Pos.TOP_LEFT);
		this.getChildren().addAll(default_background, mapView, nodes); //
	}

	/**
	 * Given a MAP_NAME -> ask Controller for map name and nodes for the map
	 * Add image to map
	 * Then add the Nodes
	 * @param MAP_NAME
	 */
	public void setMap(String MAP_NAME){
		/* not sure if this alone will change image, may have to update imageview as well */
		try {
			this.mapImage = new Image(getClass().getResourceAsStream("images/" + MAP_NAME + ".png"), height - BORDER, width - BORDER, true, true);
		}
		catch (NullPointerException e) {
			this.mapImage = new Image(getClass().getResourceAsStream("/images/" + MAP_NAME + ".png"), height - BORDER, width - BORDER, true, true);
		}
		//drawNodes(MAP_NAME);
	}

	public void drawPath(){}
		/*
		if (PATH_DRAWN)
		{
			PATH_DRAWN = false;
			this.nodes.getChildren().remove(pathCanvas);
			return;
		}
		PATH_DRAWN = true;
		*/


		/*
		this.pathCanvas = new Canvas(width, height);
		GraphicsContext gc = pathCanvas.getGraphicsContext2D();


		Node[] pathNodeList = controller.getPathNodes();

		double x = width - pathNodeList[0].getX() * 15;
		double y = height - pathNodeList[0].getY() * 15;

		gc.beginPath();
		gc.moveTo(x, y);
		for (int i = 1; i < pathNodeList.length; i++){
			x= width - pathNodeList[i].getX() * 15;
			y= height - pathNodeList[i].getY() * 15;
			gc.lineTo(x, y);
			System.out.printf("X: %f, Y: %f\n", x, y);

		}
		gc.setStroke(Color.GREEN);
		gc.stroke();
		pathCanvas.setMouseTransparent(true);

		/*


		this.path = new Path();
		path.setStrokeWidth(3);
		path.setStroke(Color.GREEN);
		path.getElements().add(new MoveTo(x, y));

		x=0;
		y=0;
		path.getElements().add(new LineTo(x, y));

		x = 0;
		y = height;
		path.getElements().add(new LineTo(x, y));

		x=width;
		y=height;
		path.getElements().add(new LineTo(x, y));

		x = width;
		y = 0;
		path.getElements().add(new LineTo(x, y));

		for (int i = 1; i < pathNodeList.length; i++){
			x= width - pathNodeList[i].getX() * 15;
			y= height - pathNodeList[i].getY() * 15;
			System.out.printf("X: %f, Y: %f\n", x, y);
			path.getElements().add(new LineTo(x, y));

		}

		path.getElements().add(new ClosePath());


		/*
		for (Node n : pathNodeList){
			System.out.println("hello");
			if (i != 0 ){
				path.getElements().add(new LineTo(x, y));

				x = width - n.getX() * 15;
				y = height - n.getY() * 15;
				System.out.println("line added");
				System.out.printf("X: %f, Y: %f\n", x, y);

			}
			i++;
		}
*/
		/*
		this.path = new Path();
		path.setStrokeWidth(3);
		path.setStroke(Color.GREEN);
		path.getElements().add(new MoveTo(x, y));
		System.out.println("move added");
		System.out.printf("X: %f, Y: %f\n", x, y);

		x =  width - pathNodeList[1].getX() * 15;
		y = height - pathNodeList[1].getY() * 15;
		path.getElements().add(new LineTo(x, y));
		System.out.println("line added");
		System.out.printf("X: %f, Y: %f\n", x, y);


		x =  width - pathNodeList[2].getX() * 15;
		y = height - pathNodeList[2].getY() * 15;
		path.getElements().add(new LineTo(x, y));
		System.out.println("line added");
		System.out.printf("X: %f, Y: %f\n", x, y);

		x =  width - pathNodeList[3].getX() * 15;
		y = height - pathNodeList[3].getY() * 15;
		path.getElements().add(new LineTo(x, y));
		System.out.println("line added");
		System.out.printf("X: %f, Y: %f\n", x, y);

		x =  width - pathNodeList[4].getX() * 15;
		y = height - pathNodeList[4].getY() * 15;
		path.getElements().add(new LineTo(x, y));
		System.out.println("line added");
		System.out.printf("X: %f, Y: %f\n", x, y);
*/
		/*

		*/

		//this.getChildren().add(pathCanvas);
	//}

/*	public void drawNodes(String MAP_NAME){
		this.nodes = new Group();
		NodeList = controller.getMapNodes(MAP_NAME);
		*//* assuming you put down 'normal' x - y cooridinates, now must translate into display x - y *//*
		*//* also assuming we know the correct height etc *//*
		for (Node n : NodeList){
			double x = (height - n.getX()) * 15;  *//* the nodes currently have way too small X / Y s - later we'll need to somehow scale *//*
			double y = (width - n.getY()) * 15;

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

			nodes.getChildren().add(circle); *//* adding it to group *//*
		}
	}*/

	private void newDestination(Node n) {
		Controller.setNewDestination(n);
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
		nodes.getChildren().forEach(e -> {
			if (e instanceof Circle) {
				highlight((Circle) e, Color.GOLD, Color.RED);
			}
		});
	}
	private void hideAll() {
		nodes.getChildren().forEach(e -> {
			if (e instanceof Circle) {
				normal((Circle) e);
			}
		});
	}

	private void default_map(){
		//String default_map_name = controller.getDefaultMapName();
		//this.mapImage = new Image(getClass().getResourceAsStream("../images/" + default_map_name + ".png"), width - BORDER, height - BORDER, true, true);
		this.mapView = new ImageView(mapImage);
		mapView.setOnMouseClicked(e -> {
			if(!HIGHLIGHTED){
				highlightAll();
				HIGHLIGHTED = true;
			} else {
				hideAll();
				HIGHLIGHTED = false;
			}
		});
		//drawNodes(default_map_name);
	}

	private void set_up_background(){
		default_background = new Rectangle(width, height);
		default_background.setFill(Color.DARKBLUE);
		default_background.setOpacity(.2);
		default_background.setArcHeight(7);
		default_background.setArcWidth(7);
	}
}
