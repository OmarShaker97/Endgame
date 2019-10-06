import java.util.ArrayList;

public class Endgame extends Problem {
	static String ThanosCoordinates;
	static String gridSize;
	//
	public Endgame(Object[] operators, Object initialState) {
		super(operators, initialState);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object stateSpace(Object state, Object action) {
		EndGameState endgameState = ((EndGameState) state);
		int[] ironmanCoordinatesArray = stringCoordinatesToArrayCoordinates(endgameState.ironManCoordinates);
		int[] gridSizeArray = stringCoordinatesToArrayCoordinates(gridSize);
		//last cell before the upper border
		//borders control
		if((ironmanCoordinatesArray[0] == 1) && ((String)action).equals("up"))
			return endgameState;
		if((ironmanCoordinatesArray[0] == gridSizeArray[0] - 2) && ((String)action).equals("down"))
			return endgameState;
		if((ironmanCoordinatesArray[1] == 1) && ((String)action).equals("left"))
			return endgameState;
		if((ironmanCoordinatesArray[1] == gridSizeArray[1] - 2) && ((String)action).equals("right"))
			return endgameState;
		//warriors interactions?
		if((ironmanCoordinatesArray[0] == 1) && ((String)action).equals("up")) {
			int decreasedHp = 0;
			return endgameState;
		}
			
		//movement
		if((ironmanCoordinatesArray[0] == 1) && ((String)action).equals("up")) {
			return new EndGameState(
					(ironmanCoordinatesArray[0]-1) +","+ ironmanCoordinatesArray[1]
					, endgameState.getCoordinates()
					,endgameState.getHp());
		}
		if((ironmanCoordinatesArray[0] == gridSizeArray[0] - 2) && ((String)action).equals("down"))
			return endgameState;
		if((ironmanCoordinatesArray[1] == 1) && ((String)action).equals("left"))
			return endgameState;
		if((ironmanCoordinatesArray[1] == gridSizeArray[1] - 2) && ((String)action).equals("right"))
			return endgameState;
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
