package logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jacobhackett on 11/24/15.
 */
public class TStairs  extends Transition {

    public TStairs(int id, double x, double y, double z, double x1, double y1, double z1){
        super(id, x, y, z, x1, y1, z1);
    }

    public TStairs(int id, double x, double y, double z, double x1, double y1, double z1, int map_id){
        super(id, x, y, z, x1, y1, z1, map_id);
    }

    public TStairs(INode iNode){
        super(iNode);
    }

    public ImageView getIcon(){
        Image image = new Image(getClass().getResourceAsStream("../images/stairs7.svg"), 22, 22, true, true);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    public String toString(){
        return "Stairs";
    }
}
