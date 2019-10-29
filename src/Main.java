import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main{

	static Endgame problem;
	static int count;

	public static void main(String[] args) {
		count = 0;
		long startTime = System.currentTimeMillis();
		String gridString = "15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11";
		solve(gridString, "ID", true);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\n" + totalTime + " milliseconds");
	}

	public static Node Search (Problem problem ,String strategy) {

		Node node = null;

		if(strategy.equals("BF"))
			node = BFS(problem);


		else if(strategy.equals("DF")) {
			node = DFS(problem);
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
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					if(expandedNodes[i].getState() != null) {
						count+=1;
						nodes.add(expandedNodes[i]);
						try {
							expandedNodes[i].setHeuristicCost(((EndGameState)(expandedNodes[i].getState())).getCoordinates().split(";")[2].length());
						}
						catch(Exception E){
							expandedNodes[i].setHeuristicCost(0);
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
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					if(expandedNodes[i].getState() != null) {
						count+=1;
						nodes.add(expandedNodes[i]);
						try {
							expandedNodes[i].setHeuristicCost(((EndGameState)(expandedNodes[i].getState())).getCoordinates().split(";")[2].length() + (Endgame.thanosDamage*2));
						}
						catch(Exception E){
							expandedNodes[i].setHeuristicCost(Endgame.thanosDamage*2);
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

	public static Node AS1(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorAS1());
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					if(expandedNodes[i].getState() != null) {
						count+=1;
						nodes.add(expandedNodes[i]);
						try {
							expandedNodes[i].setHeuristicCost(((EndGameState)(expandedNodes[i].getState())).getCoordinates().split(";")[2].length()*3 + expandedNodes[i].getPathCost());
						}
						catch(Exception E){
							expandedNodes[i].setHeuristicCost(0);
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

	public static Node AS2(Problem problem) {
		boolean cont = true;
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new NodeComparatorAS2());
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove();
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					if(expandedNodes[i].getState() != null) {
						count+=1;
						nodes.add(expandedNodes[i]);
						try {
							expandedNodes[i].setHeuristicCost(((EndGameState)(expandedNodes[i].getState())).getCoordinates().split(";")[2].length()*3 + (Endgame.thanosDamage*2) + expandedNodes[i].getPathCost());
						}
						catch(Exception E){
							expandedNodes[i].setHeuristicCost(Endgame.thanosDamage*2);
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


	public static Node BFS(Problem problem) {
		boolean cont = true;
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			//System.out.println(nodes.size());
			Node node = nodes.remove(0);

			if(problem.goalTest(node)) {
				return node;
			}

			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					//System.out.println("Node"+i+":"+expandedNodes[i].toString());
					if(expandedNodes[i].getState() != null) {
						nodes.add(expandedNodes[i]);
						count+=1;
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
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					if(expandedNodes[i].getState() != null) {
						nodes.add(expandedNodes[i]);
						count+=1;
					}
				}
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
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					//System.out.println("Node"+i+":"+expandedNodes[i].toString());
					if(expandedNodes[i].getState() != null) {
						nodes.add(0, expandedNodes[i]);
						count+=1;
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

			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					if(expandedNodes[i].getState() != null && expandedNodes[i].getDepth() <= depthLimit) {
						nodes.add(0, expandedNodes[i]);
						count+=1;
					}
				}	
				problem.putinVisitedStates(node.getState());
			}

			//			if(depthLimit > node.getDepth()) {
			//				for(int i = 0; i<nodes.size();i++)
			//					nodes.remove(i);
			//				problem.setVisitedStates(new HashSet<String>());
			//				nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
			//				depthLimit+=1;
			//			}

			if(nodes.size() == 0) {
				//System.out.println("out of length");
				problem.visitedStates.clear();
			//	problem.setVisitedStates(new HashSet<String>());
				nodes.add(initialNode);
				depthLimit+=1;
				//cont = false;
			}

			//cont = false;
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
			outputString+= ";" + count;
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