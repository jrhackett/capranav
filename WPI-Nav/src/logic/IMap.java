package logic;

/**
 * Created by Xenocidist on 11/26/15.
 */
 public interface IMap {

     int getID();

     String getFilePath();

     void setFilePath(String path);

     double getPixelToFeetRatio();

     boolean inside();

     int getBuildingID();

     int getFloor();
}
