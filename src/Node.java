import java.util.ArrayList;

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
		//setPathCost(setpCost);
	}
	
	public String toString()
	{
		if(getState()!=null)
	      return "state: "+((EndGameState)getState()).getCoordinates()+" operator:"+operator+" depth:"+depth+" pathCost:"+pathCost;
		else {
			return "state: "+null+" operator:"+operator+" depth:"+depth+" pathCost:"+pathCost;
		}
	}
	
//	public Node(Object state, Node parent, Object operator, int depth, int setpCost) {
//		this.state = state;
//		this.parent = parent;
//		this.operator = operator;
//		this.depth = depth;
//		setPathCost(setpCost);
//	}

	public Object getState() {
		return state;
	}

	public void setState(Object state) {
		this.state = state;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Object getOperator() {
		return operator;
	}

	public void setOperator(Object operator) {
		this.operator = operator;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getPathCost() {
		return pathCost;
	}

	public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
	}
	
	public ArrayList<Node> getPathFromRoot() {
		ArrayList<Node> path = new ArrayList<Node>();
        Node current = this;
        while (!(current.parent==null)) {
            path.add(0, current);
            current = current.getParent();
        }
        path.add(0, current);
        return path;
    }
	

}
