package logic;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Created by jacobhackett on 11/24/15.
 */
public class Transition extends Node {


    private int buildingID;
    private int toFloor;
    private ArrayList<String> name;


    public Transition(int id, double x, double y, double z, double x1, double y1, double z1){
        super(id, x, y, z, x1, y1, z1);
    }

    public Transition(int id, double x, double y, double z, double x1, double y1, double z1, int map_id){
        super(id, x, y, z, x1, y1, z1, map_id);
    }

    public Transition(INode iNode){
        super(iNode);
    }

    public boolean isTransition() {
        return true;
    }

    public ImageView getIcon(){
        Image image = FileFetch.getImageFromFile("exit.svg", 20, 20, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(22);
        imageView.setFitWidth(22);
        return imageView;
    }
    public String toString(){
        if(name != null) {
            return this.name.get(0);
        } else {
            return "Entrance";
        }
    }

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
    }

    public int getToFloor() {
        return toFloor;
    }

    public void setToFloor(int toFloor) {
        this.toFloor = toFloor;
    }

    public void addNames(ArrayList<String> name) {this.name = name;}

    public String getName() {return this.name.get(0);}
}
