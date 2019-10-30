import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Main{

	static Endgame problem;
	static int countOfExtendedNodes;

	public static void main(String[] args) {
		countOfExtendedNodes = 0;
		long startTime = System.currentTimeMillis();
		String gridString = "15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11";
		solve(gridString, "ID2", true);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\n" + totalTime + " milliseconds");
	}

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
			node = ID(problem);
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

	public static Node GR1(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorGR1());
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
						countOfExtendedNodes += 1;
						nodes.add(newNode);
						try {
							newNode.setHeuristicCost(((EndGameState)(expandedNodes[i].getState())).getCoordinates().split(";")[2].length());
						}
						catch(Exception E){
							newNode.setHeuristicCost(0);
						}
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
						try {
							newNode.setHeuristicCost(((EndGameState)(expandedNodes[i].getState())).getCoordinates().split(";")[2].length() + (Endgame.thanosDamage*2));
						}
						catch(Exception E){
							newNode.setHeuristicCost(Endgame.thanosDamage*2);
						}
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
						try {
							newNode.setHeuristicCost(((EndGameState)(expandedNodes[i].getState())).getCoordinates().split(";")[2].length()*3 + expandedNodes[i].getPathCost());
						}
						catch(Exception E){
							newNode.setHeuristicCost(0);
						}
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
						try {
							newNode.setHeuristicCost(((EndGameState)(expandedNodes[i].getState())).getCoordinates().split(";")[2].length()*3 + (Endgame.thanosDamage*2) + expandedNodes[i].getPathCost());
						}
						catch(Exception E){
							newNode.setHeuristicCost(Endgame.thanosDamage*2);
						}
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
			if(nodes.size() == 0) {
				problem.visitedStates.clear();
				nodes.add(initialNode);
				depthLimit += 1;	
			}
		}

		return null;

	}




	public static String solve(String grid, String strategy, boolean visualize) {
		String[] operators = {"up", "down", "left", "right", "collect", "kill", "snap"};
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