import java.util.ArrayList;

public class EndGameState {
	
	int[] ironManCoordinates;
	ArrayList<int[]> warriorsCoordinates;
	ArrayList<int[]> stonesCoordinates; 
	int hp;
	
	public EndGameState(int[] ironManCoordinates, ArrayList<int[]> warriorsCoordinates
			, ArrayList<int[]> stonesCoordinates, int hp) {
	
		this.ironManCoordinates = ironManCoordinates;
		this.warriorsCoordinates = warriorsCoordinates;
		this.stonesCoordinates = stonesCoordinates; 
		this.hp = hp;
	}
	
//	public void setIronManCoordinates(String[][] grid) {
//		for(int i = 0; i<grid.length; i++)
//			for(int j = 0; j<grid[0].length; j++)
//				if(grid[i][j].equals("I")) {
//					this.ironManCoordinates = new int[]{i, j};
//				}
//	}

	

}
