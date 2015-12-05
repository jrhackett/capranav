package logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jacobhackett on 11/24/15.
 */
public class Landmark extends Interest {

    public Landmark(int id, double x, double y, double z, double x1, double x2, double x3, String name) {
        super(id, x, y, z, x1, x2, x3, name);
    }

    public Landmark(int id, double x, double y, double z, double x1, double x2, double x3, int mapID, String name) {
        super(id, x, y, z, x1, x2, x3, mapID, name);
    }

    public ImageView getIcon(){
        Image image = new Image(getClass().getResourceAsStream("../images/pin56_small.svg"), 22, 22, true, true);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
}
