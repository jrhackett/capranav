package controller;

import logic.Node;

import java.util.ArrayList;

/**
 * Created by Xenocidist on 11/7/15.
 */
public class  Controller {
    /* variables */
    public Node[][] start_stops = new Node[5][2];
    public int current_bonus_points = 0;
    public int max_bonus_points = 3;




    public Controller(){
        /* on creation, call/organize data ->
                eventually have a small load screen while this happens
                do any pre-emptive calculations during
         */
    }

    /* function that gets the map name */
    public static String getMapName() {
        return "wpi-campus-map";
    }

    /* function to get all nodes for a map */
    public static ArrayList<Node> getNodes(Object p0) {
        return null;
    }

    /* function that sets a new destination */
    public static void setNewDestination(Node n) {

    }

    public static ArrayList<Node> getPathNodes() {
        return null;
    }


    /* function that gets a path, given two+ nodes */

    /* function for handicap path, given two+ nodes */

    /* function looking for nearest three bathrooms */

    /* function that shows three paths to food */
}
