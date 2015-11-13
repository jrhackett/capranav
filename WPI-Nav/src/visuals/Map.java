package visuals;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Map extends StackPane{
	private Rectangle default_background;
	private static final double BORDER = 7;
	
	/* this will be used to put together the map */
	/* overlaying the nodes and fixed map image should work */
	public Map(double width, double height){
		super();
		
		/* for now... */
		default_background = new Rectangle(height, width);
		default_background.setFill(Color.DARKBLUE);
		default_background.setArcHeight(7);
		default_background.setArcWidth(7);

		Image mapTest = new Image(getClass().getResourceAsStream("wpi-campus-map.png"), height - BORDER, width - BORDER, true, true);
		ImageView mapTestView = new ImageView(mapTest);
		
		this.getChildren().addAll(default_background, mapTestView);
		
		
	};
}
