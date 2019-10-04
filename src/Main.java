import java.util.ArrayList;

public class Main{
	
	Problem problem;
	
	public static void main(String[] args) {
		// problem = new Endgame();
	}
	
	public static String[][] StringtoArray(String grid) {
		String[] inputString = grid.split(";");
		String[] gridCoordinates = inputString[0].split(",");
		String[] ironManCoordinates = inputString[1].split(",");
		String[] thanosCoordinates = inputString[2].split(",");
		String[] stonesCoordinatesString = inputString[3].split(",");
		String[][] stonesCoordinatesArray = new String[6][2];
		String[] warriorsCoordinatesString = inputString[4].split(",");
		String[][] warriorsCoordinatesArray = new String[warriorsCoordinatesString.length/2][2];
		
		for(int i = 0; i<stonesCoordinatesString.length; i+=2) {
			stonesCoordinatesArray[i] = new String[]{stonesCoordinatesString[i],stonesCoordinatesString[i+1]};	
		}
		for(int i = 0; i<warriorsCoordinatesString.length; i+=2) {
			warriorsCoordinatesArray[i] = new String[]{warriorsCoordinatesString[i],warriorsCoordinatesString[i+1]};	
		}
		
		String[][] finalGrid = new String[Integer.parseInt(gridCoordinates[0])][Integer.parseInt(gridCoordinates[1])];
		
		for(int i = 0; finalGrid.length>i; i++) {
			for(int j = 0; finalGrid[i].length>j; j++) {
				
			}
		}
	}
	
	public static String solve(String[][] grid, String strategy, boolean visualize) {
		
		if(strategy.equals("BF")) {
			ArrayList<Node> nodes = new ArrayList<Node>();
			//nodes.add(0, new Node(ironManCoordinates, null, null, 0, 0));
		}
		
	}
	
}
