import java.util.ArrayList;
import java.util.HashSet;

public class Main{

	static Endgame problem;

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String gridString = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		solve(gridString, "BF", true);
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
			if(problem.goalTest(node)) {
				return node;
			}
			ArrayList<Node> expandedNodes = problem.expand(node, problem.getOperators());
			//System.out.print(expandedNodes.size());
			for(int i=0;i<expandedNodes.size();i++) {
				//System.out.print(expandedNodes.get(i).toString());
					nodes.add(expandedNodes.get(i));
			}
			problem.putinVisitedStates(node.getState());
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
			if(problem.goalTest(node)) {
				return node;
			}
				problem.putinVisitedStates(node.getState());
				ArrayList<Node> expandedNodes = problem.expand(node, problem.getOperators());
				for(int i=0;i<expandedNodes.size();i++) {
						nodes.add(expandedNodes.get(i));
				}
				nodes.sort(new NodeComparator());
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
			problem.putinVisitedStates(node.getState());
			ArrayList<Node> expandedNodes = problem.expand(node, problem.getOperators());
			for(int i=0;i<expandedNodes.size();i++) {
					nodes.add(0, expandedNodes.get(i));
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
			
			problem.putinVisitedStates(node.getState());
			ArrayList<Node> expandedNodes = problem.expand(node, problem.getOperators());
			for(int i=0;i<expandedNodes.size();i++) {
				Node currNode = expandedNodes.get(i);
				if(currNode.getDepth() <= depthLimit)
					nodes.add(currNode);
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
		EndGameInfo endgameInfo = StringtoGrid(grid);
		String[] coordinates = grid.split(";");
		String outputString = "";
		problem = new Endgame(operators, new EndGameState(
				coordinates[1] + ";" + coordinates[4] + ";" + coordinates[3]
				)
				);
		Endgame.ThanosCoordinates = endgameInfo.getThanosCoordinates();
		Endgame.gridSize = endgameInfo.getGridSize();
		Node nodef = Search(problem, strategy);
		ArrayList<Node> nodesFRomRoot = nodef.getPathFromRoot();

		for(int i=0;i<nodesFRomRoot.size();i++) {
			outputString += nodesFRomRoot.get(i).getOperator() + ",";
		}

		outputString = outputString.substring(1, outputString.length() - 1);
		outputString+= ";" + nodef.getPathCost();
		outputString+= ";" + nodef.getDepth();
		//System.out.println(nodef.getPathCost());
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