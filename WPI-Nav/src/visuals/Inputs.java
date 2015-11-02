package visuals;

import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * This class will be the input text field
 * Once we allow the user to have multiple destinations this will need some work
 * @author Charlie Lovering
 *
 */
public class Inputs extends TextField {
	private final static int WIDTH = 160;
	private int rank = 0;
	private String initial;
	
	public Inputs(String s){
		super(s);
		initial = s;
		this.setMaxWidth(WIDTH);
		this.setMinWidth(WIDTH);
		rank++;
		
		DropShadow ds = new DropShadow();
		ds.setOffsetX(.5);
		ds.setOffsetY(.5);
		ds.setColor(Color.GRAY);
		this.setEffect(ds);

		/* add listeners here for when selected/input typed etc */
	}
}
