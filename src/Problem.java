import java.util.HashSet;
public abstract class Problem {

	Object[] operators;
	Object initialState;
	HashSet<String> visitedStates;

	public Problem(Object[] operators, Object initialState) {
		this.operators = operators;
		this.initialState = initialState;
		visitedStates = new HashSet<>();
	}

	public abstract Object transitionFunction(Node node, Object action);

	public abstract boolean goalTest(Object state);

	public abstract void calculatePathCost(Node node, int stepCost);

	public abstract Node[] expand(Node node, Object[] operators);
	
	public abstract void putinVisitedStates(Object state);

	public abstract boolean isVisited(Object state);
	
	public abstract Node setHeuristicFunction(Node node, String informedSearchAlgorithm);

	public Object[] getOperators() {
		return operators;
	}

	public void setOperators(Object[] operators) {
		this.operators = operators;
	}

	public Object getInitialState() {
		return initialState;
	}

	public void setInitialState(Object initialState) {
		this.initialState = initialState;
	}

	public HashSet<String> getVisitedStates() {
		return visitedStates;
	}

	public void setVisitedStates(HashSet<String> visitedStates) {
		this.visitedStates = visitedStates;
	}


}
