import java.util.ArrayList;

public class Endgame extends Problem {
	static String ThanosCoordinates;
	static String gridSize;
	//
	public Endgame(Object[] operators, Object initialState) {
		super(operators, initialState);
		// TODO Auto-generated constructor stub
	}
	//position: [y, x]
	@Override
	public Object stateSpace(Object state, Object action) {
		EndGameState endgameState = ((EndGameState) state);
		int[] ironmanCoordinatesArray = stringCoordinatesToArrayCoordinates(endgameState.ironManCoordinates);
		int ironmanCoordinatesY = ironmanCoordinatesArray[0];
		int ironmanCoordinatesX = ironmanCoordinatesArray[1];
		//TODO: handle thanos damage
		int decreasedHp = damageFromAdjacentWarriors(ironmanCoordinatesY, ironmanCoordinatesX, endgameState.getCoordinates()[0]);
		
		//movements
		if(((String)action).equals("up")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, endgameState.getCoordinates()[0], "up");
			if(canMove) {
				return moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "up", endgameState, decreasedHp);
			}
			return endgameState;

		}
		if(((String)action).equals("down")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, endgameState.getCoordinates()[0], "down");
			if(canMove) {
				return moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "down", endgameState, decreasedHp);
			}
			return endgameState;
		}
		if(((String)action).equals("left")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, endgameState.getCoordinates()[0], "left");
			if(canMove) {
				return moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "left", endgameState, decreasedHp);
			}
			return endgameState;
		}
		if(((String)action).equals("right")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, endgameState.getCoordinates()[0], "right");
			if(canMove) {
				return moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "right", endgameState, decreasedHp);
			}
			return endgameState;
		}
		//other actions
		if(((String)action).equals("collect")) {
			return collectState(ironmanCoordinatesY, ironmanCoordinatesX, endgameState.getCoordinates()[1], endgameState, decreasedHp);
		}
		if(((String)action).equals("snap")) {
			return endgameState;
		}
		if(((String)action).equals("kill")) {
			return null;
		}
			
		return null;
	}

	@Override
	public boolean goalTest(Object state) {	
		EndGameState endGameState = (EndGameState) state;
		return endGameState.getCoordinates()[1].equals("") && endGameState.getIronManCoordinates().equals(ThanosCoordinates) && endGameState.hp>0;
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
	
	public Node[] expand(Node node, Object[] operators) {
		Node[] nodes = new Node[operators.length];
		for(int i=0; i<operators.length;i++) {
			nodes[i] = new Node(stateSpace(node.state, operators[i]), node, operators[i], node.depth+1, 0);
		}
		return nodes;
	}
	
	public ArrayList<int[]> stringCoordinatesToArrayListCoordinates(String pos) {
		ArrayList<int[]> coordinates = new ArrayList<int[]>();
		String[] positions = pos.split(",");
		for(int i = 0; i<positions.length; i+=2) {
			coordinates.add(
					new int[]{
							Integer.parseInt(pos.charAt(i)+"")
							,Integer.parseInt(pos.charAt(i+2)+"")
							}
					);
		}
		return coordinates;
	}
	
	public int damageFromAdjacentWarriors (int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput) {
		int decreasedHp = 0;
		ArrayList<int[]> warriorsCoordinates = stringCoordinatesToArrayListCoordinates(warriorsCoordinatesInput);
		for(int i=0; i< warriorsCoordinates.size(); i++) {
			int[] warriorCoordinate = warriorsCoordinates.get(i);
			int warriorCoordinateY = warriorCoordinate[0];
			int warriorCoordinateX = warriorCoordinate[1];
			if(ironmanCoordinatesY - 1 == warriorCoordinateY) {
				decreasedHp+=1;
			}
			if(ironmanCoordinatesX - 1 == warriorCoordinateX) {
				decreasedHp+=1;
			}
			if(ironmanCoordinatesY + 1 == warriorCoordinateX) {
				decreasedHp+=1;
			}
			if(ironmanCoordinatesX + 1 == warriorCoordinateX) {
				decreasedHp+=1;
			}
		}
		return decreasedHp;
	}
	
	public boolean canMove(int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput, String direction) {
		ArrayList<int[]> warriorsCoordinates = stringCoordinatesToArrayListCoordinates(warriorsCoordinatesInput);
		int[] gridSizeArray = stringCoordinatesToArrayCoordinates(gridSize);
		int m = gridSizeArray[0];
		int n = gridSizeArray[1];
		//borders
		if((ironmanCoordinatesY == 0) && direction.equals("up"))
			return false;
		else if((ironmanCoordinatesY == m - 1) && direction.equals("down"))
			return false;
		else if((ironmanCoordinatesX == 0) && direction.equals("left"))
			return false;
		else if((ironmanCoordinatesX == n - 1) && direction.equals("right"))
			return false;
		//moving to a warrior cell
		for(int i=0; i< warriorsCoordinates.size(); i++) {
			int[] warriorCoordinate = warriorsCoordinates.get(i);
			int warriorCoordinateY = warriorCoordinate[0];
			int warriorCoordinateX = warriorCoordinate[1];
			if(ironmanCoordinatesY - 1 == warriorCoordinateY  && direction.equals("left")) {
				return false;
			}
			else if((ironmanCoordinatesX - 1 == warriorCoordinateX) && direction.equals("up")) {
				return false;
			}
			else if(ironmanCoordinatesY + 1 == warriorCoordinateX && direction.equals("right")) {
				return false;
			}
			else if(ironmanCoordinatesX + 1 == warriorCoordinateX && direction.equals("down")) {
				return false;
			}
		}
		return true;
	}
	public EndGameState collectState(int ironmanCoordinatesY, int ironmanCoordinatesX, String stonesCoordinatesInput, EndGameState giveState, int decreasedHp) {
		ArrayList<int[]> stonesCoordinates = stringCoordinatesToArrayListCoordinates(stonesCoordinatesInput);
		String warriorsCoordinates = giveState.getCoordinates()[0];
		for(int i=0; i< stonesCoordinates.size(); i++) {
			int[] stoneCoordinate = stonesCoordinates.get(i);
			int stonesCoordinateY = stoneCoordinate[0];
			int stoneCoordinateX = stoneCoordinate[1];
			if(stonesCoordinateY == ironmanCoordinatesY && stoneCoordinateX == ironmanCoordinatesX) {
				decreasedHp+=3;
				stonesCoordinates.remove(i);
				return new EndGameState(
						giveState.getIronManCoordinates()
						, new String [] {warriorsCoordinates, arrayListCoordinatesToStringCoordinates(stonesCoordinates)}
						,giveState.getHp() - decreasedHp
						);
			}
		}
		return giveState;
	}
	public EndGameState moveActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String direction, EndGameState giveState, int decreasedHp) {
		if(direction.equals("up")) {
			return new EndGameState(
					(ironmanCoordinatesY - 1) +","+ (ironmanCoordinatesX)
					, giveState.getCoordinates()
					,giveState.getHp() - decreasedHp
					);
		}
		else if(direction.equals("down")) {
			return new EndGameState(
					(ironmanCoordinatesY + 1) +","+ (ironmanCoordinatesX)
					, giveState.getCoordinates()
					,giveState.getHp() - decreasedHp
					);
		}
		else if(direction.equals("left")) {
			return new EndGameState(
					(ironmanCoordinatesY) +","+ (ironmanCoordinatesX - 1)
					, giveState.getCoordinates()
					,giveState.getHp() - decreasedHp
					);
		}
		else if(direction.equals("right")) {
			return new EndGameState(
					(ironmanCoordinatesY) +","+ (ironmanCoordinatesX +1)
					, giveState.getCoordinates()
					,giveState.getHp() - decreasedHp
					);
		}
		return giveState;
	}
	
	public int[] stringCoordinatesToArrayCoordinates(String pos) {
		String[] posToString = pos.split(",");
		return new int[] {Integer.parseInt(posToString[0]), Integer.parseInt(posToString[1])};
	}
	
	public String arrayCoordinatesToStringCoordinates(int[] posArray) {
		String joinedString = "";
		for(int i=0;i< posArray.length; i++)
			joinedString+=posArray[i]+",";
		return joinedString.substring(0, joinedString.length()-2);
	}
	
	public String arrayListCoordinatesToStringCoordinates(ArrayList<int[]> arrayInput) {
		String stringOutput = "";
		for(int i = 0; i<arrayInput.size(); i++) {
			stringOutput+=arrayCoordinatesToStringCoordinates(arrayInput.get(i));
		}
		return stringOutput;
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
