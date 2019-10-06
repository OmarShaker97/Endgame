import java.util.ArrayList;

public class Main{
	
	 static Endgame problem;
	 
	public static void main(String[] args) {
		String gridString = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		solve(gridString, "BF", false);
	}
	
	public static Node Search (Problem problem ,String strategy) {
		if(strategy.equals("BF")) {
			boolean cont = true;
			ArrayList<Node> nodes = new ArrayList<Node>();
			while(cont) {
				nodes.add(new Node(problem.initialState, null, "", 0, 0));
				Node node = nodes.remove(0);
				if(problem.goalTest(node.state)) {
					return node;
				}
				if(!problem.isVisited(node.state)) {
					Node[] expandedNodes = problem.expand(node, problem.operators);
					for(int i=0;i<expandedNodes.length;i++)
						nodes.add(expandedNodes[i]);
					problem.visitedStates.add(node.state);
				}
				
				if(nodes.size() == 0)
					cont = false;
			}
		}
		return null;
		
	}
	

	public static String solve(String grid, String strategy, boolean visualize) {
		String[] operators = {"up", "down", "left", "right", "collect", "kill", "snap"};
		EndGameInfo endgameInfo = StringtoGrid(grid);
		problem = new Endgame(operators, new EndGameState(
						endgameInfo.getIronManCoordinates(),
						endgameInfo.getCoordinates(),
						100
						)
				);
		Endgame.ThanosCoordinates = endgameInfo.getThanosCoordinates();
		Endgame.gridSize = endgameInfo.getGridSize();
		Node nodef = Search(problem, strategy);
		ArrayList<Node> nodesFRomRoot = nodef.getPathFromRoot();
		System.out.println(((EndGameState) nodef.getState()).getHp());
		for(int i=0;i<nodesFRomRoot.size();i++) {
			System.out.println(nodesFRomRoot.get(i).getOperator());
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