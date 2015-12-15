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

    public Bathroom(INode node){
        super(node);
    }

    public void setBathroomType(BathroomType t){
        this.bathroomType = t;
    }

    public String toString(){
        return bathRoomTypeToString(bathroomType);
    }

    public static String bathRoomTypeToString(BathroomType bathroomType){
        switch (bathroomType){
            case MENS:
                return "Men's Bathroom";
            case WOMAN:
                return "Women's Bathroom";
            case GENERAL:
                return "Bathroom";
       }
        return "Bathroom";
    }

    public ImageView getIcon(){
        //TODO FILL THIS IN
        Image image;
        ImageView imageView;

        switch (bathroomType){
            case MENS:
                image = new Image(getClass().getResourceAsStream("../images/female105.svg"), 22, 22, true, true);
                imageView = new ImageView(image);
                imageView.setFitHeight(22);
                imageView.setFitWidth(22);
                return imageView;
            case WOMAN:
                image = new Image(getClass().getResourceAsStream("../images/female105.svg"), 22, 22, true, true);
                imageView = new ImageView(image);
                imageView.setFitHeight(22);
                imageView.setFitWidth(22);
                return imageView;
            case HANDICAP:
            case GENERAL:
            default:
                image = new Image(getClass().getResourceAsStream("../images/female105.svg"), 22, 22, true, true);
                imageView = new ImageView(image);
                imageView.setFitHeight(22);
                imageView.setFitWidth(22);
                    return imageView;
                }
        }
    }

