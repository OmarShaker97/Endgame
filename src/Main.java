import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;

public class Main{

	static Endgame problem;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String gridString = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		solve(gridString, "UCS", false);
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\n----------------");
		System.out.println(totalTime + " milliseconds");
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
		
		else if(strategy.equals("UCS")) {
			node = UCS(problem);
		}


		return node;

	}

	public static Node BFS(Problem problem) {
		boolean cont = true;
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			//System.out.println(nodes.size());
			Node node = nodes.remove(0);
			if(problem.goalTest(node.getState())) {
				return node;
			}
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					//System.out.println("Node"+i+":"+expandedNodes[i].toString());
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
	
	public static Node UCS(Problem problem) {
		boolean cont = true;
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(new Node(problem.getInitialState(), null, "", 0, 0));
		while(cont) {
			//System.out.println(nodes.size());
			Node node = nodes.remove(0);
			if(problem.goalTest(node.getState())) {
				return node;
			}
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				problem.putinVisitedStates(node.getState());
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					if(expandedNodes[i].getState() != null) {
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
			if(problem.goalTest(node.getState())) {
				return node;
			}
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					//System.out.println("Node"+i+":"+expandedNodes[i].toString());
					if(expandedNodes[i].getState() != null) {
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
			
			if(problem.goalTest(node.getState())) {
				return node;
			}
			
			if(!problem.isVisited(node.getState()) && node.getPathCost()<100) {
				Node[] expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.length;i++) {
					if(expandedNodes[i].getState() != null && expandedNodes[i].getDepth() <= depthLimit) {
						nodes.add(0, expandedNodes[i]);
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
		EndGameInfo endgameInfo = StringtoGrid(grid);
		String[] coordinates = grid.split(";");
		problem = new Endgame(operators, new EndGameState(
				coordinates[1] + ";" + coordinates[4] + ";" + coordinates[3]
				)
				);
		Endgame.ThanosCoordinates = endgameInfo.getThanosCoordinates();
		Endgame.gridSize = endgameInfo.getGridSize();
		Node nodef = Search(problem, strategy);
		ArrayList<Node> nodesFRomRoot = nodef.getPathFromRoot();
		System.out.println(nodef.getPathCost());
		System.out.print(nodef.getDepth());
		for(int i=0;i<nodesFRomRoot.size();i++) {
			System.out.print(nodesFRomRoot.get(i).getOperator() + ",");
		}
		return "";
	}

	public static EndGameInfo StringtoGrid(String grid) {

		String[] inputString = grid.split(";");
		String[] gridCoordinates = inputString[0].split(",");
		String[] ironManCoordinates = inputString[1].split(",");
		String[] thanosCoordinates = inputString[2].split(",");
		String[] stonesCoordinatesString = inputString[3].split(",");
		ArrayList<int[]> stonesCoordinatesArray = new ArrayList<int[]>();
		String[] warriorsCoordinatesString = inputString[4].split(",");
		ArrayList<int[]> warriorsCoordinatesArray = new ArrayList<int[]>();
		String[][] finalGrid = new String[Integer.parseInt(gridCoordinates[0])][Integer.parseInt(gridCoordinates[1])];

		for(int i = 0; i<stonesCoordinatesString.length; i+=2) {
			finalGrid[Integer.parseInt(stonesCoordinatesString[i])][Integer.parseInt(stonesCoordinatesString[i+1])] = "S";
			stonesCoordinatesArray.add(
					new int[]{
							Integer.parseInt(stonesCoordinatesString[i])
							,Integer.parseInt(stonesCoordinatesString[i+1])
					}
					);
		}
		for(int i = 0; i<warriorsCoordinatesString.length; i+=2) {
			finalGrid[Integer.parseInt(warriorsCoordinatesString[i])][Integer.parseInt(warriorsCoordinatesString[i+1])] = "W";
			warriorsCoordinatesArray.add(
					new int[]{
							Integer.parseInt(warriorsCoordinatesString[i])
							,Integer.parseInt(warriorsCoordinatesString[i+1])
					}
					);
		}
		//ThanosCoordinates = new int[] {Integer.parseInt(thanosCoordinates[0]), Integer.parseInt(thanosCoordinates[1])};
		finalGrid[Integer.parseInt(ironManCoordinates[0])][Integer.parseInt(ironManCoordinates[1])] = "I";
		finalGrid[Integer.parseInt(thanosCoordinates[0])][Integer.parseInt(thanosCoordinates[1])] = "T";

		for(int i = 0; i<finalGrid.length; i++) {
			for(int j = 0 ; j<finalGrid[0].length; j++) {
				if(finalGrid[i][j] == "")
					finalGrid[i][j] = "E";
			}	
		}

		return new EndGameInfo(inputString[0], inputString[1], inputString[2], new String[] {inputString[4], inputString[3]});
	}
}