import java.util.ArrayList;

public class Endgame extends Problem {
	public static String ThanosCoordinates;
	public static String gridSize;
	private int stepCost;
	private EndGameState currentState;
	private String[] currentCoordinates;
	private String currentAction;
	//
	public Endgame(Object[] operators, Object initialState) {
		super(operators, initialState);
		stepCost = 0;
	}
	//position: [y, x]
	// coordinates: 0 for iron man, 1 for warriors, 2 for stones
	@Override
	public Node transitionFunction(Node node, Object action) {
		EndGameState endgameState = ((EndGameState) node.state);
		EndGameState returnedEndgameState = null;
		String[] coordinates = endgameState.getCoordinates().split(";");
		currentState = endgameState;
		currentCoordinates = coordinates;
		currentAction = (String)action;
		/*parsing string to array*/
		int[] ironmanCoordinatesArray = stringCoordinatesToArrayCoordinates(coordinates[0]);
		int ironmanCoordinatesY = ironmanCoordinatesArray[0];
		int ironmanCoordinatesX = ironmanCoordinatesArray[1];
		int[] thanosCoordinates = stringCoordinatesToArrayCoordinates(ThanosCoordinates);
		int thanosCoordinatesY = thanosCoordinates[0];
		int thanosCoordinatesX = thanosCoordinates[1];
		String warriorsCoordinatesString;
		try {
			warriorsCoordinatesString = coordinates[1];}
		catch(Exception E){
			warriorsCoordinatesString = "-1,-1";
		}
		String stonesCoordinatesString = "";
		if(coordinates.length == 3)
			stonesCoordinatesString = coordinates[2];
		ArrayList<int[]> warriorsCoordinates = stringCoordinatesToArrayListCoordinates(warriorsCoordinatesString);
		ArrayList<int[]> stonesCoordinates = stringCoordinatesToArrayListCoordinates(stonesCoordinatesString);
		int[] gridSizeArray = stringCoordinatesToArrayCoordinates(gridSize);
		int m = gridSizeArray[0];
		int n = gridSizeArray[1];
		/*parsing string to array*/
		stepCost = 0;
		damageFromAdjacentCells(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString);
		//movements
		if(isMove(currentAction)) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinates, stonesCoordinates);
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX);
			}
		}
		//other actions
		else if(((String)action).equals("collect")) {
			returnedEndgameState = collectActionState(ironmanCoordinatesY, ironmanCoordinatesX, stonesCoordinatesString);
		}
		else if(((String)action).equals("snap") && stonesCoordinatesString.length() == 0) {
			returnedEndgameState = endgameState;
		}
		else if(((String)action).equals("kill")) {	
			returnedEndgameState = killActionState(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString);
		}
		//Node returnedNode = new Node(returnedEndgameState, node, action, node.getDepth()+1, 0);
		Node returnedNode = new Node(returnedEndgameState, node, action, node.getDepth()+1, stepCost);
		return returnedNode;
	}

	@Override
	public boolean goalTest(Object node) {	
		EndGameState endGameState = (EndGameState) ((Node) node).getState();
		String[] coordinates = endGameState.getCoordinates().split(";");
		if(((Node) node).getOperator().equals("snap")) {
			if(coordinates.length == 3)
				return coordinates[2].length() == 0 && coordinates[0].equals(ThanosCoordinates);
			else
				return coordinates.length == 2 && coordinates[0].equals(ThanosCoordinates);}

		return false;
	}

	public Node[] expand(Node node, Object[] operators) {
		Node[] nodes = new Node[operators.length];
		for(int i=0; i<operators.length;i++) {
			nodes[i] = transitionFunction(node, operators[i]);
		}
		return nodes;
	}

	public ArrayList<int[]> stringCoordinatesToArrayListCoordinates(String pos) {
		ArrayList<int[]> coordinates = new ArrayList<int[]>();
		if(pos.length() == 0) {
			return coordinates;
		}
		String[] positions = pos.split(",");
		for(int i = 0; i<positions.length; i+=2) {
			coordinates.add(
					new int[]{
							Integer.parseInt(positions[i])
							,Integer.parseInt(positions[i+1])
					}
					);
		}
		return coordinates;
	}
	public void damageFromAdjacentCells(int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput) {
		damageFromAdjacentWarriors(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesInput);
		damageFromAdjacentThanos(ironmanCoordinatesY, ironmanCoordinatesX);
	}
	public void damageFromAdjacentWarriors (int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput) {
		ArrayList<int[]> warriorsCoordinates = stringCoordinatesToArrayListCoordinates(warriorsCoordinatesInput);
		for(int i=0; i< warriorsCoordinates.size(); i++) {
			int[] warriorCoordinate = warriorsCoordinates.get(i);
			int warriorCoordinateY = warriorCoordinate[0];
			int warriorCoordinateX = warriorCoordinate[1];
			if((ironmanCoordinatesY - 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX)) {
				stepCost=stepCost+1;
			}
			if((ironmanCoordinatesX - 1 == warriorCoordinateX) && (ironmanCoordinatesY == warriorCoordinateY)) {
				stepCost=stepCost+1;
			}
			if((ironmanCoordinatesY + 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX)) {
				stepCost=stepCost+1;
			}
			if((ironmanCoordinatesX + 1 == warriorCoordinateX) && (ironmanCoordinatesY == warriorCoordinateY)) {
				stepCost=stepCost+1;
			}
		}
		if(stepCost!=0) {
			//System.out.println("damage from warriors");
		}
	}

	public void damageFromAdjacentThanos(int ironmanCoordinatesY, int ironmanCoordinatesX) {
		//int decreasedHp = 0;
		int[] thanosCoordinates = stringCoordinatesToArrayCoordinates(ThanosCoordinates);
		int thanosCoordinatesY = thanosCoordinates[0];
		int thanosCoordinatesX = thanosCoordinates[1];
		if((ironmanCoordinatesY - 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX)) {
			stepCost=stepCost+5;
		}
		if((ironmanCoordinatesX - 1 == thanosCoordinatesX) && (ironmanCoordinatesY == thanosCoordinatesY)) {
			stepCost=stepCost+5;
		}
		if((ironmanCoordinatesY + 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX)) {
			stepCost=stepCost+5;
		}
		if(ironmanCoordinatesX + 1 == thanosCoordinatesX && (ironmanCoordinatesY == thanosCoordinatesY)) {
			stepCost=stepCost+5;
		}
		if(stepCost!=0) {
			//System.out.println("damage from thanos");
		}
	}

	public boolean isMove(String action) {
		if(action.equals("up") || action.equals("down") || action.equals("up") || action.equals("left") || action.equals("right"))
			return true;
		return false;
	}
	public boolean canMove(int ironmanCoordinatesY, int ironmanCoordinatesX, ArrayList<int[]> warriorsCoordinates,ArrayList<int[]> stonesCoordinates) {
		int[] gridSizeArray = stringCoordinatesToArrayCoordinates(gridSize);
		int m = gridSizeArray[0];
		int n = gridSizeArray[1];
		int[] thanosCoordinates = stringCoordinatesToArrayCoordinates(ThanosCoordinates);
		int thanosCoordinatesY = thanosCoordinates[0];
		int thanosCoordinatesX = thanosCoordinates[1];
		if((ironmanCoordinatesY - 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX) && currentAction.equals("up")) {
			if(stonesCoordinates.size()==0)
				return true;
			//System.out.println("can't move because of thanos 1");
			return false;
		}
		if((ironmanCoordinatesX - 1 == thanosCoordinatesX) && (ironmanCoordinatesY == thanosCoordinatesY) && currentAction.equals("left")) {
			if(stonesCoordinates.size()==0)
				return true;
			//System.out.println("can't move because of thanos 2");
			return false;
		}
		if((ironmanCoordinatesY + 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX) && currentAction.equals("down")) {
			if(stonesCoordinates.size()==0)
				return true;
			//System.out.println("can't move because of thanos 3");
			return false;
		}
		if(ironmanCoordinatesX + 1 == thanosCoordinatesX && (ironmanCoordinatesY == thanosCoordinatesY) && currentAction.equals("right")) {
			if(stonesCoordinates.size()==0)
				return true;
			//System.out.println("can't move because of thanos 4");
			return false;
		}
		//borders
		if((ironmanCoordinatesY == 0) && currentAction.equals("up")) {
			//System.out.println("can't move because of borders");
			return false;
		}

		else if((ironmanCoordinatesY == m - 1) && currentAction.equals("down")) {
			//System.out.println("can't move because of borders");
			return false;
		}
		else if((ironmanCoordinatesX == 0) && currentAction.equals("left")) {
			//System.out.println("can't move because of borders");
			return false;

		}
		else if((ironmanCoordinatesX == n - 1) && currentAction.equals("right")) {
			//System.out.println("can't move because of borders");
			return false;
		}
		//moving to a warrior cell
		for(int i=0; i< warriorsCoordinates.size(); i++) {
			int[] warriorCoordinate = warriorsCoordinates.get(i);
			int warriorCoordinateY = warriorCoordinate[0];
			int warriorCoordinateX = warriorCoordinate[1];
			if(ironmanCoordinatesX - 1 == warriorCoordinateX && (ironmanCoordinatesY == warriorCoordinateY)  && currentAction.equals("left")) {

				//System.out.println("can't move because of warriors");
				return false;
			}
			else if(ironmanCoordinatesX + 1 == warriorCoordinateX && (ironmanCoordinatesY == warriorCoordinateY) && currentAction.equals("right")) {
				//System.out.println("can't move because of warriors");
				return false;
			}
			else if((ironmanCoordinatesY - 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX) && currentAction.equals("up")) {
				//System.out.println("can't move because of warriors");
				return false;
			}
			else if(ironmanCoordinatesY + 1 == warriorCoordinateY && (ironmanCoordinatesX == warriorCoordinateX) && currentAction.equals("down")) {
				//System.out.println("can't move because of warriors");
				return false;
			}
		}
		return true;
	}
	public EndGameState moveActionState(int ironmanCoordinatesY, int ironmanCoordinatesX) {
		String warriorCoordinates;
		try {
			warriorCoordinates = currentCoordinates[1];}
		catch(Exception E) {
			warriorCoordinates = "-1,-1";
		}
		String stonesCoordinates = "";
		if(currentCoordinates.length == 3)
			stonesCoordinates = currentCoordinates[2];
		if(currentAction.equals("up")) {
			return new EndGameState(
					(ironmanCoordinatesY - 1) +","+ (ironmanCoordinatesX) + ";" + warriorCoordinates + ";" + stonesCoordinates
					);
		}
		else if(currentAction.equals("down")) {
			return new EndGameState(
					(ironmanCoordinatesY + 1) +","+ (ironmanCoordinatesX) + ";" + warriorCoordinates + ";" + stonesCoordinates
					);
		}
		else if(currentAction.equals("left")) {
			return new EndGameState(
					(ironmanCoordinatesY) +","+ (ironmanCoordinatesX - 1)+ ";" + warriorCoordinates + ";" + stonesCoordinates
					);
		}
		else if(currentAction.equals("right")) {
			return new EndGameState(
					(ironmanCoordinatesY) +","+ (ironmanCoordinatesX +1)+ ";" + warriorCoordinates + ";" + stonesCoordinates
					);
		}
		return null;
	}
	public EndGameState collectActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String stonesCoordinatesInput) {
		String stonesCoordinatesString = "";
		if(currentCoordinates.length == 3)
			stonesCoordinatesString = currentCoordinates[2];
		ArrayList<int[]> stonesCoordinates = stringCoordinatesToArrayListCoordinates(stonesCoordinatesString);
		for(int i=0; i< stonesCoordinates.size(); i++) {
			int[] stoneCoordinate = stonesCoordinates.get(i);
			int stonesCoordinateY = stoneCoordinate[0];
			int stoneCoordinateX = stoneCoordinate[1];
			if(stonesCoordinateY == ironmanCoordinatesY && stoneCoordinateX == ironmanCoordinatesX) {
				stepCost = stepCost + 3;
				stonesCoordinates.remove(i);
				return new EndGameState(
						currentCoordinates[0] + ";" + currentCoordinates[1] + ";" + arrayListCoordinatesToStringCoordinates(stonesCoordinates)
						);
			}
		}
		return null;
	}
	public EndGameState killActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput) {
		ArrayList<int[]> warriorsCoordinates;
		try {
			warriorsCoordinates = stringCoordinatesToArrayListCoordinates(currentCoordinates[1]);}
		catch (Exception E){
			warriorsCoordinates = new ArrayList<>();
		}
		int stackedDamage = 0;
		for(int i=0; i< warriorsCoordinates.size(); i++) {
			int[] warriorCoordinate = warriorsCoordinates.get(i);
			int warriorCoordinateY = warriorCoordinate[0];
			int warriorCoordinateX = warriorCoordinate[1];
			if((ironmanCoordinatesY - 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX )) {
				stackedDamage = stackedDamage +2;
				warriorsCoordinates.remove(i);
			}
			else if((ironmanCoordinatesX - 1 == warriorCoordinateX) && (ironmanCoordinatesY == warriorCoordinateY)) {
				stackedDamage = stackedDamage +2;
				warriorsCoordinates.remove(i);
			}
			else if((ironmanCoordinatesX + 1 == warriorCoordinateX) && (ironmanCoordinatesY == warriorCoordinateY)) {
				stackedDamage = stackedDamage +2;
				warriorsCoordinates.remove(i);
			}
			else if((ironmanCoordinatesY + 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX)) {
				stackedDamage = stackedDamage +2;
				warriorsCoordinates.remove(i);
			}
		}
		stepCost =stepCost + stackedDamage;
		if(stackedDamage != 0) {
			String stonesCoordinatesString = "";
			if(currentCoordinates.length==3)
				stonesCoordinatesString = currentCoordinates[2];
			return new EndGameState(
					currentCoordinates[0] + ";" + arrayListCoordinatesToStringCoordinates(warriorsCoordinates) + ";" + stonesCoordinatesString
					);
		}
		return null;
	}

	public int[] stringCoordinatesToArrayCoordinates(String pos) {
		String[] posToString = pos.split(",");
		return new int[] {Integer.parseInt(posToString[0]), Integer.parseInt(posToString[1])};
	}

	public String arrayListCoordinatesToStringCoordinates(ArrayList<int[]> arrayInput) {
		String stringOutput = "";
		for(int i = 0; i<arrayInput.size(); i++) {
			stringOutput = stringOutput + arrayInput.get(i)[0]+","+arrayInput.get(i)[1]+",";
		}
		if(stringOutput.length()==0)
			return "";
		return stringOutput.substring(0, stringOutput.length() - 1);
	}

	public void printGrid(Node node) {
		EndGameState state = (EndGameState) node.getState();
		String[] coordinates = state.getCoordinates().split(";");
		String ironmanCoods = coordinates[0];
		String stonesCords;
		try {
			stonesCords = coordinates[2];}
		catch(Exception E){
			stonesCords = "";
		}
		String warriorsCords;
		try {
			warriorsCords = coordinates[1];}
		catch (Exception E){
			warriorsCords = "";
		}
		int[] ironmanCoordinatesArray = stringCoordinatesToArrayCoordinates(ironmanCoods);
		ArrayList<int[]> warriorsCoordinatesArray = stringCoordinatesToArrayListCoordinates(warriorsCords);
		ArrayList<int[]> stonesCoordinatesArray = stringCoordinatesToArrayListCoordinates(stonesCords);
		int[] gridSizeArray = stringCoordinatesToArrayCoordinates(gridSize);
		int[] thanosCoordinates = stringCoordinatesToArrayCoordinates(ThanosCoordinates);
		System.out.print(""+"  ");

		for(int i = 0; i<gridSizeArray[0]; i++)
			System.out.print(i + " ");
		System.out.println("");

		for(int i = 0; i<gridSizeArray[0]; i++) {
			System.out.print(i+ " ");
			for(int j = 0 ; j<gridSizeArray[1]; j++) {
				boolean found = false;
				
				for(int ii =0; ii<warriorsCoordinatesArray.size();ii++ ) {
					int[] curr = warriorsCoordinatesArray.get(ii);
					if(curr[0] >= 0 && curr[1] >= 0) {
						if(curr[0] == i && curr[1] == j) {
							found = true;
							System.out.print("W" + " ");
						}
					}

				}
				
				for(int ii =0; ii<stonesCoordinatesArray.size();ii++ ) {
					int[] curr = stonesCoordinatesArray.get(ii);
					if(curr[0] == i && curr[1] == j) {
						System.out.print("S" + " ");
						found = true;
					}

				}
				
				if(i == ironmanCoordinatesArray[0] && j == ironmanCoordinatesArray[1]) {
					System.out.print("I" + " ");
					found = true;
				}
				else if((i == thanosCoordinates[0] && j == thanosCoordinates[1])){
					System.out.print("T" + " ");
					found = true;
				}
				
				if(!found)
					System.out.print("E"+" ");
				//				else {
				//					System.out.print("E"+" ");
				//				}

			}	
			System.out.println("");
		}
	}
	@Override
	public boolean isVisited(Object state) {
		EndGameState endgameState = (EndGameState) state;
		if(visitedStates.contains(endgameState.getCoordinates())) {
			return true;
		}
		return false;
	}

	@Override
	public void calculatePathCost(Node node, int stepCost) {
		node.setPathCost(node.parent.getPathCost() + stepCost);
	}

	@Override
	public void putinVisitedStates(Object state) {
		EndGameState endgameState = (EndGameState) state;
		visitedStates.add(endgameState.getCoordinates());
	}
}
