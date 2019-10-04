
public class Node {
	
	Object state;
	Node parent;
	Object operator;
	int depth;
	int pathCost;
	
	public Node(Object state, Node parent, Object operator, int depth, int pathCost) {
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
	}

}
