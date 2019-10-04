
public class Endgame extends Problem {

	public Endgame(Object[] operators, Object initialState) {
		super(operators, initialState);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object stateSpace(Object state, char action) {
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
	public int stepCost(Node node, char action) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String[][] StringtoGrid(String grid) {
		String[] inputString = grid.split(";");
		String[] gridCoordinates = inputString[0].split(",");
		String[] ironManCoordinates = inputString[1].split(",");
		String[] thanosCoordinates = inputString[2].split(",");
		String[] stonesCoordinatesString = inputString[3].split(",");
		//String[][] stonesCoordinatesArray = new String[6][2];
		String[] warriorsCoordinatesString = inputString[4].split(",");
		//String[][] warriorsCoordinatesArray = new String[warriorsCoordinatesString.length/2][2];
		String[][] finalGrid = new String[Integer.parseInt(gridCoordinates[0])][Integer.parseInt(gridCoordinates[1])];
		
		for(int i = 0; i<stonesCoordinatesString.length; i+=2) {
			finalGrid[Integer.parseInt(stonesCoordinatesString[i])][Integer.parseInt(stonesCoordinatesString[i+1])] = "S";
			//stonesCoordinatesArray[i] = new String[]{stonesCoordinatesString[i],stonesCoordinatesString[i+1]};	
		}
		for(int i = 0; i<warriorsCoordinatesString.length; i+=2) {
			finalGrid[Integer.parseInt(warriorsCoordinatesString[i])][Integer.parseInt(warriorsCoordinatesString[i+1])] = "W";
			//warriorsCoordinatesArray[i] = new String[]{warriorsCoordinatesString[i],warriorsCoordinatesString[i+1]};	
		}
		finalGrid[Integer.parseInt(ironManCoordinates[0])][Integer.parseInt(ironManCoordinates[1])] = "I";
		finalGrid[Integer.parseInt(thanosCoordinates[0])][Integer.parseInt(thanosCoordinates[1])] = "T";
		
		for(int i = 0; i<finalGrid.length; i++) {
			for(int j = 0 ; j<finalGrid[0].length; j++) {
				if(finalGrid[i][j] == "")
					finalGrid[i][j] = "E";
			}	
		}
		
		return finalGrid;
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
