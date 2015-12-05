package logic;

public class Edge{
    private double weight;
    private int target;

    /**
     * Edges are used to represent connecting two Nodes, stored in an ArrayList in each Node
     * @param targetNode: the id of the node to attach to
     * @param weightVal: the weight of the edge, representing distance between the nodes
     * @return void
     */
    public Edge(int targetNode, double weightVal){
            this.target = targetNode;
            this.weight = weightVal;
    }

    public double getWeight() { return this.weight; }

    public int getTarget() { return this.target; }

	/**
	 * @warning do not generate hashcode/equals, it creates a Stack Overflow in
	 *          JUnit
	 */

}