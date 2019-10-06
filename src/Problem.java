import java.util.ArrayList;

public abstract class Problem {

	Object[] operators;
	Object initialState;
	ArrayList<Object> visitedStates;
	
	public Problem(Object[] operators, Object initialState) {
		this.operators = operators;
		this.initialState = initialState;
		visitedStates = new ArrayList<Object>();
	}
	
	public abstract Object stateSpace(Object state, Object action);
	
	public abstract boolean goalTest(Object state);
	
	public abstract int pathCost(Node node);
	
	public abstract int stepCost(Node node, Object action);
	
	public abstract Node[] expand(Node node, Object[] operators);
	
	public abstract boolean isVisited(Object state);
	
	
}
