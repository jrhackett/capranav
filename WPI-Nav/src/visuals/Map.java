package visuals;

import controller.Controller;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import logic.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class Map extends StackPane{
	private Rectangle default_background;
	private static final double BORDER = 7;
	private Image mapTest;
	private double height;
	private double width;

	private ArrayList<Node> NodeList;
	private HashMap<Integer, Node> idToNode;
	private HashMap<Integer, Circle> itToVisual;
	private Group nodes;
	private Controller controller;


	/* this will be used to put together the map */
	/* overlaying the nodes and fixed map image should work */
	public Map(double width, double height, Controller controller){
		super();
		this.controller = controller;
		this.width = width;
		this.height = height;
		this.nodes = new Group();

		/* for now... */
		default_background = new Rectangle(height, width);
		default_background.setFill(Color.DARKBLUE);
		default_background.setArcHeight(7);
		default_background.setArcWidth(7);

		this.mapTest = new Image(getClass().getResourceAsStream("images/wpi-campus-map.png"), height - BORDER, width - BORDER, true, true);
		ImageView mapTestView = new ImageView(mapTest);

		this.getChildren().addAll(default_background, mapTestView, nodes);
	}

	/**
	 * Given a MAP_ID -> ask Controller for map name and nodes for the map
	 * Add image to map
	 * Then add the Nodes
	 * @param MAP_ID
	 */
	public void setMap(String MAP_ID){
		/* not sure if this alone will change image, may have to update imageview as well */
		this.mapTest = new Image(getClass().getResourceAsStream("images/" + MAP_ID + ".png"), height - BORDER, width - BORDER, true, true);
		this.NodeList = controller.getNodes(MAP_ID);
		drawNodes();
	}

	public void drawPath(){
		ArrayList<Node> pathNodeList = Controller.getPathNodes();

		double x = width - pathNodeList.get(0).getX();
		double y = height - pathNodeList.get(0).getY();
		Path path = new Path();
		path.setFill(Color.GREEN);
		path.setStrokeWidth(2);
		path.setStroke(Color.GREEN);
		path.getElements().add(new MoveTo(x, y));

		int i = 0;
		for (Node n : pathNodeList){
			if (i != 0){
				x = width - n.getX();
				y = height - n.getY();
				path.getElements().add(new LineTo(x, y));
			}
			i++;
		}

		nodes.getChildren().add(path);
	}

	public void drawNodes(){
		this.nodes = new Group();
		/* assuming you put down 'normal' x - y cooridinates, now must translate into display x - y */
		/* also assuming we know the correct height etc */
		for (Node n : NodeList){
			double x = height - n.x_coord;
			double y = width - n.y_coord;

			Circle circle = new Circle(x - 2.5, y - 2.5, 5, Color.TRANSPARENT);
			circle.setOnMouseEntered(e -> highlight(circle));
			circle.setOnMouseExited(e -> normal(circle));
			circle.setOnMouseClicked(e -> newDestination(n));

			nodes.getChildren().add(circle); /* adding it to group */
		}
	}

	private void newDestination(Node n) {
		Controller.setNewDestination(n);
	}

	private void highlight(Circle c) {
		c.setFill(Color.GREEN);
		c.setOpacity(.3);
		c.setStroke(Color.GREEN);
		c.setStrokeWidth(2);
	}

	private void normal(Circle c) {
		c.setFill(Color.TRANSPARENT);
		c.setOpacity(1);
		c.setStrokeWidth(0);
	}


}
