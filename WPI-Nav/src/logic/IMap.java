package logic;

/**
 * Created by Xenocidist on 11/26/15.
 */
 public interface IMap {

     int getID();

     String getPath();

     void setPath(String path);

     double getPixelToFeetRatio();

     boolean inside();

     int getBuildingID();

     int getFloor();
}
