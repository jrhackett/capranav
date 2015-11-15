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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

}