import java.util.Comparator;

/*
 * A comparator to be used in making a min PriorityQueue of Edges in Nodes
 */
public class EdgeComparator implements Comparator<Edge>
{
	/*
	 * Returns 1 if the first weight is greater
	 * Returns -1 if second weight is greater
	 * Returns 0 if weights are equal
	 */
	@Override
	public int compare(Edge o1, Edge o2) 
	{
		if(o1.getWeight() > o2.getWeight()) return 1;
		if(o1.getWeight() < o2.getWeight()) return -1;
		return 0;
	}
}
