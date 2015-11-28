package logic;

import javafx.scene.image.ImageView;

/**
 * Created by jacobhackett on 11/24/15.
 */
public class Path extends Node {

    public Path(int id, double x, double y, double z, double x1, double x2, double x3) {
        super(id, x, y, z, x1, x2, x3);
    }

    public Path(int id, double x, double y, double z, double x1, double x2, double x3, int mapID) {
        super(id, x, y, z, x1, x2, x3, mapID);
    }

    public ImageView getIcon(){ //Path nodes have no image
        return new ImageView();
    }

}
