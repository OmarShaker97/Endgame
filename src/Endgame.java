
public class Endgame extends Problem {
	static int[] ThanosCoordinates;

	public Endgame(Object[] operators, Object initialState) {
		super(operators, initialState);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object stateSpace(Object state, Object action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean goalTest(Object state) {
		String[][] grid = ((EndGameState) state).grid;
		int hp = ((EndGameState) state).hp;
		int stones = 6;
		boolean thanosIsDead = true;
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				if(grid[i][j].equals("S"))
					stones--;
				
				if(grid[i][j].equals("T"))
					thanosIsDead = false;
			}
		}
					
			
		return stones == 0 && thanosIsDead && hp>0;
	}

	@Override
	public int pathCost(Node node) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int stepCost(Node node, Object action) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	public void printGrid(String[][] grid) {
		System.out.print("g"+" ");
		
		for(int i = 0; i<grid.length; i++)
			System.out.print(i + " ");
		
		System.out.println("");
		
		for(int i = 0; i<grid.length; i++) {
			System.out.print(i+ " ");
			for(int j = 0 ; j<grid[0].length; j++) {
				System.out.print(grid[i][j] + " ");
			}	
			System.out.println("");
		}
	}

}
