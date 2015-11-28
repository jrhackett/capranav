package logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by jacobhackett on 11/24/15.
 */
public class Bathroom extends Interest {

    private BathroomType bathroomType = BathroomType.GENERAL;

    public Bathroom(int id, double x, double y, double z, double x1, double x2, double x3, String name) {
        super(id, x, y, z, x1, x2, x3, name);
    }

    public Bathroom(int id, double x, double y, double z, double x1, double x2, double x3, int mapID, String name) {
        super(id, x, y, z, x1, x2, x3, mapID, name);
    }

    public void setBathroomType(BathroomType t){
        this.bathroomType = t;
    }

    public String toString(){
        switch (bathroomType){
            case GENERAL:
                return "Bathroom";
            default:
                return this.bathroomType.toString().toUpperCase().substring(0,1) + this.bathroomType.toString().toLowerCase().substring(1) + " Bathroom";
        }
    }

    public ImageView getIcon(){
        //TODO FILL THIS IN
        switch (bathroomType){
            case GENERAL:
            case MENS:
            case WOMAN:
            case HANDICAP:
                default:
                    return new ImageView(new Image(getClass().getResourceAsStream("../images/forward.svg"), 20, 20, true, true));
        }
    }
}
