import java.util.ArrayList;
import java.util.HashSet;

public class Main{
	static Endgame problem;
	static int count;

	public static void main(String[] args) {
		count = 0;
		long startTime = System.currentTimeMillis();
		String gridString = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		//String gridString = "15,15;7,7;5,9;0,2,3,7,5,4,8,12,11,6,13,10;0,3,4,5,8,3,9,7,14,3";
		solve(gridString, "GR1", true);
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

		else {
			node = GR1(problem);
		}


		return node;

	}
	public static Node GR1(Problem problem) {
		boolean cont = true;
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove(0);
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
				nodes.sort(new NodeComparatorGR1());
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
			count+=1;
			if(problem.goalTest(node)) {
				return node;
			}
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					//System.out.println("Node"+i+":"+expandedNodes[i].toString());
					if(expandedNodes[i].getState() != null) {
						nodes.add(expandedNodes[i]);
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

	public static Node UCS(Problem problem) {
		boolean cont = true;
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			Node node = nodes.remove(0);
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
					}
				}
				nodes.sort(new NodeComparator());
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
						count+=1;
						nodes.add(0, expandedNodes[i]);
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
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
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
						count+=1;
						nodes.add(0, expandedNodes[i]);
					}
				}	
				problem.putinVisitedStates(node.getState());
			}
			if(nodes.size() == 0) {
				//System.out.println("out of length");
				for(int i = 0; i<nodes.size();i++)
					nodes.remove(i);
				problem.setVisitedStates(new HashSet<String>());
				nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
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
		ArrayList<Node> nodesFRomRoot = nodef.getPathFromRoot();

		for(int i=0;i<nodesFRomRoot.size();i++) {
			outputString += nodesFRomRoot.get(i).getOperator() + ",";
		}

		outputString = outputString.substring(1, outputString.length() - 1);
		outputString+= ";" + nodef.getPathCost();
		outputString+= ";" + nodef.getDepth();
		System.out.println(count);
		//System.out.print(nodef.getDepth());


		System.out.println(outputString);

		if(visualize) {
			visualize(visualize, nodesFRomRoot);
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