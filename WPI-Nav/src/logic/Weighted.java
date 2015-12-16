package logic;

import java.util.HashMap;

/**
 * Created by Kurt on 12/7/15.
 *
 * TODO: should this be static?
 *
 * This will add weights to the nodes and edges to be able to add delighter features such as
 * handicapped and weather dependent
 */
public class Weighted {

    private static double weatherWeight = 15;
    private static double handicapWeight = 50;
    /**
     * changes edge weights for all Tstairs Nodes to handicap weight
     * @param allNodes
     */
    public static void makeEdgesHandicapped(HashMap<Integer, INode> allNodes){
        allNodes.forEach((k, v) -> {
            if (v instanceof TStairs) {  //Checking for stair nodes
                //set all edges for this Node to new weight
                if (v.getAdjacencies() != null) {//bad fix
                    for (Edge e : v.getAdjacencies()) {
                        e.setWeight(handicapWeight);
                        INode targetNode = allNodes.get(e.getTarget());
                        for (Edge a : targetNode.getAdjacencies()) {
                            if (allNodes.get(a.getTarget()) == v) {
                                a.setWeight(handicapWeight);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * changes all Nodes with map id of "0" to
     * @param allNodes
     */
    public static void makeEdgesWeather(HashMap<Integer, INode> allNodes){
        allNodes.forEach((k, v) -> {
            if (v.getMap_id() == 0){ //Checking for Map ID of 0 (Nodes on the campus map)
                    //set all edges for this Node to new weight
                    if (v.getAdjacencies() != null){//bad fix
                        for (Edge e : v.getAdjacencies()) {
                        e.setWeight(weatherWeight);
                        INode targetNode = allNodes.get(e.getTarget());
                        for (Edge a : targetNode.getAdjacencies()){
                            if (allNodes.get(a.getTarget()) == v){
                                a.setWeight(weatherWeight);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * resets edges back to value of 1
     * @param allNodes
     */
    public static void resetEdges(HashMap<Integer, INode> allNodes){
        allNodes.forEach((k, v) -> {
            if (v.getAdjacencies() != null){//bad fix
                 for (Edge e : v.getAdjacencies())
                      e.setWeight(1);
             }
        });
    }
}
