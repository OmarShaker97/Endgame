
public class EndGameState {
	
	int[] ironManCoordinates;
	boolean thanosIsAlive;
	// int[][] warriorCoordinates;
	boolean[] warriorIsAlive;
	int[][] stoneCoordinates; 
	int hp;
	//String[][] grid;
	
	public EndGameState(String[][] grid, int hp) {
	/*
		this.ironManCoordinates = ironManCoordinates;
		this.thanosCoordinates = thanosCoordinates;
		this.warriorCoordinates = warriorCoordinates;
		this.stoneCoordinates = stoneCoordinates; */
		this.hp = hp;
		this.grid = grid;
		setIronManCoordinates(grid);
	}
	
	public void setIronManCoordinates(String[][] grid) {
		for(int i = 0; i<grid.length; i++)
			for(int j = 0; j<grid[0].length; j++)
				if(grid[i][j].equals("I")) {
					this.ironManCoordinates = new int[]{i, j};
				}
	}

	

}
