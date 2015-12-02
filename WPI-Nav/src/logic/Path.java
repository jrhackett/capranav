package logic;

import javafx.scene.image.Image;
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

    public Path(INode iNode){
        super(iNode);
    }

    public ImageView getIcon(){
        Image image = new Image(getClass().getResourceAsStream("../images/path.svg"), 22, 22, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(22);
        imageView.setFitWidth(22);

        return imageView;
    }
}
