
public abstract class Problem {

	Object[] operators;
	Object initialState;
	// String[] stateSpace; This is a transition function
	//String[] goalTest; This is a function
	// int pathCost; This is a function
	
	public Problem(Object[] operators, Object initialState) {
		this.operators = operators;
		this.initialState = initialState;
	}
	
	public abstract Object stateSpace(Object state, Object action);
	
	public abstract boolean goalTest(Object state);
	
	public abstract int pathCost(Node node);
	
	public abstract int stepCost(Node node, Object action);
	
	public abstract Node[] expand(Node node, Object[] operators);
	
	
}
