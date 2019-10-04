import java.util.ArrayList;

public class Main{
	
	 static Endgame problem;
	public static void main(String[] args) {
		String gridString = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		solve(gridString, "BF", false);
	}
	
	public static Node Search (Problem problem ,String strategy) {
		if(strategy.equals("BF")) {
			ArrayList<Node> nodes = new ArrayList<Node>();
			nodes.add(new Node(problem.initialState, null, "", 0, 0));
			Node node = nodes.remove(0);
			problem.goalTest(node.state);
		}
		return null;
	}
	

	public static String solve(String grid, String strategy, boolean visualize) {
		String[] operators = {"up", "down", "left", "right", "collect", "kill", "snap"};
		EndGameInfo endgameInfo = StringtoGrid(grid);
		problem = new Endgame(
				operators, new EndGameState(
						endgameInfo.getIronManCoordinates(),
						endgameInfo.getWarriorsCoordinates(),
						endgameInfo.getStonesCoordinates(),
						100
						)
				);
		Search(problem, strategy);
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
		
		return new EndGameInfo(
				new int[] {Integer.parseInt(ironManCoordinates[0]), Integer.parseInt(ironManCoordinates[1])},
				new int[] {Integer.parseInt(thanosCoordinates[0]), Integer.parseInt(thanosCoordinates[1])},
				warriorsCoordinatesArray,
				stonesCoordinatesArray
				);
	}
}