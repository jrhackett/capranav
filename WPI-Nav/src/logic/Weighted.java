package logic;

import java.util.HashMap;

/**
 * Created by Kurt on 12/7/15.
 *
 * This will add weights to the nodes and edges to be able to add delighter features such as
 * handicapped and weather dependent
 */
public class Weighted {

    private double weatherWeight = 15;
    private double handicapWeight = 10;
    /**
     * changes edge weights for all Tstairs Nodes to handicap weight
     * @param allNodes
     */
    public void makeEdgesHandicapped(HashMap<Integer, INode> allNodes){
        allNodes.forEach((k, v) -> {
            if (v instanceof TStairs){
                //set all edges for this Node to new weight
                for (Edge e : v.getAdjacencies()) {
                    e.setWeight(handicapWeight);
                }
                //set Nodes' edges that connect to this Node to new weight
                //TODO this stufff
            }
        });
    }

    /**
     * changes all Nodes with map id of "0" to
     * @param allNodes
     */
    public void makeEdgesWeather(HashMap<Integer, INode> allNodes){

    }

    /**
     * resets edges back to value of 1
     * @param allNodes
     */
    public void resetEdges(HashMap<Integer, INode> allNodes){

    }

}
