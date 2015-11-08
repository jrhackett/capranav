package logic;

public class Edge{
    public double weight;
    public Node target;

    public Edge(Node targetNode, double weightVal){
            this.target = targetNode;
            this.weight = weightVal;
    }

}
