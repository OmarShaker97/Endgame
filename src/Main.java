import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Main{
	
	static Problem problem; // our endgame problem
	static int countOfExtendedNodes; // number of expanded nodes

	public static void main(String[] args) {
		countOfExtendedNodes = 0;
		String strategy = "GR1";
		long startTime = System.currentTimeMillis();
		String gridString = "5,5;2,2;4,2;4,0,1,2,3,0,2,1,4,1,2,4;3,2,0,0,3,4,4,3,4,4";
		solve(gridString, strategy, false);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\n" + totalTime + " milliseconds");
	}

	// general search method
	public static Node search (Problem problem ,String strategy) {
		Node node = null;
		if(strategy.equals("BF"))
			node = BFS(problem);
		else if(strategy.equals("DF"))
			node = DFS(problem);
		else if(strategy.equals("ID")) 
			node = ID(problem);
		else if(strategy.equals("UC"))
			node = UCS(problem);
		else if(strategy.equals("GR1")) 
			node = GR1(problem);
		else if(strategy.equals("GR2")) 
			node = GR2(problem);
		else if(strategy.equals("AS1")) 
			node = AS1(problem);
		else if(strategy.equals("AS2")) 
			node = AS2(problem);
		return node;
	}
	
	// intialize the problem and run the search on the goal node.
		public static String solve(String grid, String strategy, boolean visualize) {
			problem = new Endgame(grid);
			((Endgame)problem).setOperatorsOrder(strategy);
			Node goalNode = search(problem, strategy);
			return printResult(visualize, goalNode);
		}
		
		public static String printResult(boolean visualize, Node goalNode) {
			String outputString = "";
			if(goalNode!=null) {
				ArrayList<Node> nodesFRomRoot = goalNode.getPathFromRoot();
				for(int i=0;i<nodesFRomRoot.size();i++)
					outputString += nodesFRomRoot.get(i).getOperator() + ",";
				outputString = outputString.substring(1, outputString.length() - 1);
				outputString+= ";" + goalNode.getPathCost();
				outputString+= ";" + countOfExtendedNodes;
				System.out.println(outputString);
				if(visualize) {
					visualize(visualize, nodesFRomRoot);
				}
			}
			else {
				outputString = "There is no solution.";
			}
			
			return outputString;
		}

	// GR1 is commented for explanation to ease for reading, the rest of the algorithms follow the same structure
	public static Node GR1(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorHeuristic());
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0)); // add initial node
		while(cont) {
			Node node = nodes.remove(); // remove first node
			if(problem.goalTest(node)) {
				return node;// goal test
			}
			if(!problem.isVisited(node.getState())) { // check if node is visited
				problem.putinVisitedStates(node.getState()); // if not put it in the visited states
				Node[] expandedNodes = problem.expand(node, problem.getOperators()); // expand its children
				for(int i=0;i<expandedNodes.length;i++) {
					Node newNode = expandedNodes[i];
					if(newNode!=null) {
						countOfExtendedNodes += 1; // increment the expanded nodes
						nodes.add(newNode); // add our node
						newNode = problem.setHeuristicFunction(newNode, "GR1"); // set heuristic function
					}
				}
			}
			if(nodes.size() == 0) {
				System.out.println("There is no solution.");
				cont = false;
				break;
			}
		}

		return null;
	}

	public static Node GR2(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorHeuristic());
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node newNode = expandedNodes[i];
					if(newNode!=null) {
						countOfExtendedNodes+=1;
						nodes.add(newNode);
						newNode = problem.setHeuristicFunction(newNode, "GR2");
					}
				}
			}
			if(nodes.size() == 0) {
				System.out.println("There is no solution.");
				cont = false;
			}
		}

		return null;
	}

	public static Node AS1(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorHeuristic());
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node newNode = expandedNodes[i];
					if(newNode!=null) {
						countOfExtendedNodes+=1;
						nodes.add(expandedNodes[i]);
						newNode = problem.setHeuristicFunction(newNode, "AS1");
					}
				}
			}
			if(nodes.size() == 0) {
				System.out.println("There is no solution.");
				cont = false;
			}
		}

		return null;
	}

	public static Node AS2(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorHeuristic());
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node newNode = expandedNodes[i];
					if(newNode!=null) {
						countOfExtendedNodes+=1;
						nodes.add(newNode);
						newNode = problem.setHeuristicFunction(newNode, "AS2");
					}
				}
			}
			if(nodes.size() == 0) {
				System.out.println("There is no solution.");
				cont = false;
			}
		}
		return null;
	}

	public static Node BFS(Problem problem) {
		boolean cont = true;
		Queue<Node> nodes = new LinkedList<Node>();
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.poll();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node childNode = expandedNodes[i];
					if(childNode!=null) {
						nodes.add(expandedNodes[i]);
						countOfExtendedNodes+=1;
					}
				}	
				problem.putinVisitedStates(node.getState());
			}



			if(nodes.size() == 0) {
				System.out.println("There is no solution.");
				cont = false;
			}
		}
		return null;
	}


	public static Node UCS(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparator());
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node childNode = expandedNodes[i];
					if(childNode!=null) {
						nodes.add(expandedNodes[i]);
						countOfExtendedNodes+=1;
					}
				}	
				problem.putinVisitedStates(node.getState());
			}
			if(nodes.size() == 0) {
				System.out.println("There is no solution.");
				cont = false;
			}
		}

		return null;

	}

	public static Node DFS(Problem problem) {
		boolean cont = true;
		Stack<Node> nodes = new Stack<Node>();
		nodes.push(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.pop();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node childNode = expandedNodes[i];
					if(childNode!=null) {
						nodes.push(expandedNodes[i]);
						countOfExtendedNodes+=1;
					}
				}	
				problem.putinVisitedStates(node.getState());
			}
			if(nodes.size() == 0) {
				System.out.println("There is no solution.");
				cont = false;
			}

		}

		return null;
	}

	public static Node ID(Problem problem) {
		boolean cont = true;
		Stack<Node> nodes = new Stack<Node>();
		Node initialNode = new Node(problem.getInitialState(), null, "", 0, 0);
		nodes.push(initialNode);
		int depthLimit = 0;
		while(cont) {
			Node node = nodes.pop();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node childNode = expandedNodes[i];
					if(childNode!=null && childNode.getDepth() <= depthLimit) {
						nodes.push(expandedNodes[i]);
						countOfExtendedNodes+=1;
					}
				}	
				problem.putinVisitedStates(node.getState());
			}
			if(nodes.size() == 0) {
				problem.visitedStates.clear();
				nodes.add(initialNode);
				depthLimit += 1;	
			}
		}

		return null;
	}


	public static void visualize(boolean visualize, ArrayList<Node> nodesFromRoot) {
		if(visualize) {
			for(int i=0;i<nodesFromRoot.size();i++) {
				((Endgame)problem).printGrid(nodesFromRoot.get(i));
				System.out.println("----------------");
			}
		}
	}

}