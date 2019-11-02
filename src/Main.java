import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Main{

	static Endgame problem; // our endgame problem
	static int countOfExtendedNodes; // number of expanded nodes

	public static void main(String[] args) {
		countOfExtendedNodes = 0;
		long startTime = System.currentTimeMillis();
		String gridString = "5,5;2,2;4,2;4,0,1,2,3,0,2,1,4,1,2,4;3,2,0,0,3,4,4,3,4,4";
		solve(gridString, "AS2", false);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\n" + totalTime + " milliseconds");
	}
	
	// general search method
	public static Node Search (Problem problem ,String strategy) {

		Node node = null;

		if(strategy.equals("BF"))
			node = BFS2(problem);


		else if(strategy.equals("DF")) {
			node = DFS2(problem);
		}
		
		else if(strategy.equals("ID2")) {
			node = ID2(problem);
		}
		
		else if(strategy.equals("ID")) {
			node = ID3(problem);
		}

		else if(strategy.equals("UC")) {
			node = UCS(problem);
		}

		else if(strategy.equals("GR1")) {
			node = GR1(problem);
		}

		else if(strategy.equals("GR2")) {
			node = GR2(problem);
		}

		else if(strategy.equals("AS1")) {
			node = AS1(problem);
		}

		else if(strategy.equals("AS2")) {
			node = AS2(problem);
		}


		return node;

	}
	
	// GR1 is commented for explanation to ease for reading, the rest of the algorithms follow the same structure
	public static Node GR1(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorGR1());
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0)); // add initial node
		while(cont) {
			Node node = nodes.remove(); // remove first node
			if(problem.goalTest(node)) {
				return node;		// goal test
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
				System.out.println("out of length");
				cont = false;
				break;
			}

			//cont = false;
		}

		return null;

	}

	public static Node GR2(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorGR2());
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
				System.out.println("out of length");
				cont = false;
			}
		}

		return null;

	}

	public static Node AS1(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorAS1());
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
				System.out.println("out of length");
				cont = false;
			}
		}

		return null;

	}

	public static Node AS2(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorAS2());
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
				System.out.println("out of length");
				cont = false;
				break;
			}

			//cont = false;
		}

		return null;

	}
	
	public static Node BFS2(Problem problem) {
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
				System.out.println("out of length");
				cont = false;
			}
		}
		return null;

			//cont = false;
		}


	public static Node BFS(Problem problem) {
		boolean cont = true;
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove(0);
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
				System.out.println("out of length");
				cont = false;
			}

			//cont = false;
		}

		return null;

	}

	public static Node UCS(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparator());
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			//System.out.println(nodes.size());
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
				System.out.println("out of length");
				cont = false;
			}

			//cont = false;
		}

		return null;

	}
	
	public static Node DFS2(Problem problem) {
		boolean cont = true;
		Stack<Node> nodes = new Stack<Node>();
		nodes.push(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			//System.out.println(nodes.size());
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
				System.out.println("out of length");
				cont = false;
			}

			//cont = false;
		}

		return null;

	}

	public static Node DFS(Problem problem) {
		boolean cont = true;
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			//System.out.println(nodes.size());
			Node node = nodes.remove(0);
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node childNode = expandedNodes[i];
					if(childNode!=null) {
						nodes.add(0, expandedNodes[i]);
						countOfExtendedNodes+=1;
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
	
	public static Node ID2(Problem problem) {
		LinkedList<Node> nodes = new LinkedList<Node>();
		Node initialNode = new Node(problem.getInitialState(), null, "", 0, 0);
		nodes.add(initialNode);
		int depthLimit = 0;
		while(true) {
			Node node = nodes.removeFirst();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node childNode = expandedNodes[i];
					if(childNode!=null && childNode.getDepth() <= depthLimit) {
						nodes.addFirst(childNode);
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

	}

	public static Node ID(Problem problem) {
		boolean cont = true;
		ArrayList<Node> nodes = new ArrayList<Node>();
		Node initialNode = new Node(problem.getInitialState(), null, "", 0, 0);
		nodes.add(initialNode);
		int depthLimit = 0;
		while(cont) {
			Node node = nodes.remove(0);
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState())) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					Node childNode = expandedNodes[i];
					if(childNode!=null && childNode.getDepth() <= depthLimit) {
						nodes.add(0, expandedNodes[i]);
						countOfExtendedNodes+=1;
					}
				}	
				problem.putinVisitedStates(node.getState());
			}
			
		}

		return null;

	}
	
	public static Node ID3(Problem problem) {
		boolean cont = true;
		Stack<Node> nodes = new Stack<Node>();
		Node initialNode = new Node(problem.getInitialState(), null, "", 0, 0);
		nodes.push(initialNode);
		int depthLimit = 0;
		while(cont) {
			//System.out.println(nodes.size());
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


	// break down the grid, and pass all the inputs to our problem
	public static String solve(String grid, String strategy, boolean visualize) {
		String[] operators = {"up", "down", "left", "right", "collect", "kill", "snap"};
		//String[] operators = {"collect", "snap","up", "down", "left", "right", "kill"};
		String[] coordinates = grid.split(";");
		String gridSizeString = coordinates[0];
		String ironmanCoordinates = coordinates[1];
		String thanosCoordinates = coordinates[2];
		String stonesCoordinates = coordinates[3];
		String warriorsCoordinates = coordinates[4];
		String outputString = "";
		problem = new Endgame(operators, new EndGameState(
				ironmanCoordinates + ";" + warriorsCoordinates + ";" + stonesCoordinates
				)
				);
		Endgame.ThanosCoordinates = thanosCoordinates;
		Endgame.gridSize = gridSizeString;
		Node nodef = Search(problem, strategy);
		if(nodef!=null) {
			ArrayList<Node> nodesFRomRoot = nodef.getPathFromRoot();

			for(int i=0;i<nodesFRomRoot.size();i++) {
				outputString += nodesFRomRoot.get(i).getOperator() + ",";
			}

			outputString = outputString.substring(1, outputString.length() - 1);
			outputString+= ";" + nodef.getPathCost();
			outputString+= ";" + countOfExtendedNodes;
			//System.out.println(nodef.getPathCost());
			//System.out.print(nodef.getDepth());


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

	public static void visualize(boolean visualize, ArrayList<Node> nodesFRomRoot) {
		if(visualize) {
			for(int i=0;i<nodesFRomRoot.size();i++) {
				problem.printGrid(nodesFRomRoot.get(i));
				System.out.println("----------------");
			}
		}
	}

}