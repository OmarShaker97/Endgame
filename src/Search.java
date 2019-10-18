import java.util.ArrayList;

public class Search {
	public Problem problem;

	public Search(Problem problem) {
		this.problem = problem;
	}
	
	public Node solve(String Strategy) {
		boolean cont = true;
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove(0);
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState()) && node.getPathCost() < 100) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					if(expandedNodes[i].getState() != null) {
						nodes.add(expandedNodes[i]);
					}
				}	
				problem.putinVisitedStates(node.getState());
			}
			if(nodes.size() == 0) {
				System.out.println("out of length");
				cont = false;
			}

			//cont = false;
		}
		return null;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
	
	

}


