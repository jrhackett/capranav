package visuals;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Map extends StackPane{
	Rectangle default_background;
	
	/* this will be used to put together the map */
	/* overlaying the nodes and fixed map image should work */
	public Map(double width, double height){
		super();
		
		/* for now... */
		default_background = new Rectangle(height, width);
		default_background.setFill(Color.DARKBLUE);
		default_background.setArcHeight(7);
		default_background.setArcWidth(7);

		this.getChildren().addAll(default_background);
		
		
	};
}
