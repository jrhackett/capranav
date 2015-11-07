package visuals;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This will be the 'visual element' that will hold pictures of WPI
 * 
 * For now I aim on putting a default picture in as a place holder,
 * but ultimately perhaps we'll have a few different 'nice' pictures
 * to show before anything is chosen.
 * 
 * Then - if a location on the map is clicked on and it has a picture, display it.
 * Same with the locations involved in any given instruction clicked on.
 * 
 * @author Charles J. Lovering
 *
 */
public class ImageDisplay extends ImageView{
	Image currentImage;
	double dimension;
	public ImageDisplay(double dimension){
		super();
		this.dimension = dimension;
		currentImage = new Image(getClass().getResourceAsStream("images/wpibasictest.png"), dimension, dimension, false, false);
		//currentImage = new Image("file:wpibasictest.png", dimension, dimension, false, false);
		this.setImage(currentImage);
		this.maxHeight(dimension);
		this.minHeight(dimension);
		this.maxWidth(dimension);
		this.minWidth(dimension);
	}

}
