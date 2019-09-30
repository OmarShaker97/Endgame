
public class Main {
	
	public static void main(String[] args) {
	
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
	}
	
	public static String solve(String grid, String strategy, boolean visualize) {
		
		
	}
}
