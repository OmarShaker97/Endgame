
public abstract class Problem {

	String[] operators;
	String[] initialState;
	// String[] stateSpace; This is a transition function
	//String[] goalTest; This is a function
	// int pathCost; This is a function
	
	public abstract String[] stateSpace(String[] state, char action);
	
	public abstract boolean goalTest(String[] state);
	
	public abstract int pathCost(Node node);
	
	
}
