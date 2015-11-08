package logic;

public class Edge{
    private double weight;
    private Node target;

    /**
     * Edges are used to represent connecting two Nodes, stored in an ArrayList in each Node
     * @param targetNode: the node to attach to
     * @param weightVal: the weight of the edge, representing distance between the nodes
     * @return void
     */
    public Edge(Node targetNode, double weightVal){
            this.target = targetNode;
            this.weight = weightVal;
    }

    public double getWeight() { return this.weight; }

    public Node getTarget() { return this.target; }

}