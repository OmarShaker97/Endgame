import java.util.ArrayList;

public class Endgame extends Problem {
	static String ThanosCoordinates;
	static String gridSize;
	int stepCost;
	public static int thanosDamage;
	//
	public Endgame(Object[] operators, Object initialState) {
		super(operators, initialState);
		stepCost = 0;
		thanosDamage = 5;
	}
	//position: [y, x]

	// coordinates: 0 for iron man, 1 for warriors, 2 for stones
	@Override
	public Node transitionFunction(Node node, Object action) {
		EndGameState endgameState = ((EndGameState) node.state);
		EndGameState returnedEndgameState = null;
		String[] coordinates = endgameState.getCoordinates().split(";");
		int[] ironmanCoordinatesArray = stringCoordinatesToArrayCoordinates(coordinates[0]);
		int ironmanCoordinatesY = ironmanCoordinatesArray[0];
		int ironmanCoordinatesX = ironmanCoordinatesArray[1];
		String warriorsCoordinatesString;
		try {
			warriorsCoordinatesString = coordinates[1];}
		catch(Exception E){
			warriorsCoordinatesString = "-1,-1";
		}
		String stonesCoordinatesString = "";
		if(coordinates.length == 3)
			stonesCoordinatesString = coordinates[2];

		stepCost = 0;
		damageFromAdjacentCells(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString);
		//movements
		if(((String)action).equals("up")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "up");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "up", endgameState);
			}
		}
		else if(((String)action).equals("down")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "down");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "down", endgameState);
			}
		}
		else if(((String)action).equals("left")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "left");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "left", endgameState);
			}
		}
		else if(((String)action).equals("right")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "right");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "right", endgameState);
			}
		}
		//other actions
		else if(((String)action).equals("collect")) {
			returnedEndgameState = collectActionState(ironmanCoordinatesY, ironmanCoordinatesX, stonesCoordinatesString, endgameState);
		}
		else if(((String)action).equals("snap") && stonesCoordinatesString.length() == 0) {
			returnedEndgameState = endgameState;
		}
		else if(((String)action).equals("kill")) {	
			returnedEndgameState = killActionState(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString,  endgameState);
		}
		Node returnedNode = new Node(returnedEndgameState, node, action, node.getDepth()+1,0 );
		calculatePathCost(returnedNode, stepCost);
		if(returnedEndgameState==null || node.getPathCost()>100)
			return null;
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
		if(ironmanCoordinatesX == thanosCoordinatesX && (ironmanCoordinatesY == thanosCoordinatesY)) {
			stepCost=stepCost+5;
		}
		if(stepCost!=0) {
			//System.out.println("damage from thanos");
		}
	}


	public boolean canMove(int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput,String stonesCoordinatesInput, String direction) {
		ArrayList<int[]> warriorsCoordinates = stringCoordinatesToArrayListCoordinates(warriorsCoordinatesInput);
		int[] gridSizeArray = stringCoordinatesToArrayCoordinates(gridSize);
		int m = gridSizeArray[0];
		int n = gridSizeArray[1];
		int[] thanosCoordinates = stringCoordinatesToArrayCoordinates(ThanosCoordinates);
		int thanosCoordinatesY = thanosCoordinates[0];
		int thanosCoordinatesX = thanosCoordinates[1];
		if((ironmanCoordinatesY - 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX) && direction.equals("up")) {
			if(stonesCoordinatesInput.length()==0)
				return true;
			//System.out.println("can't move because of thanos 1");
			return false;
		}
		if((ironmanCoordinatesX - 1 == thanosCoordinatesX) && (ironmanCoordinatesY == thanosCoordinatesY) && direction.equals("left")) {
			if(stonesCoordinatesInput.length()==0)
				return true;
			//System.out.println("can't move because of thanos 2");
			return false;
		}
		if((ironmanCoordinatesY + 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX) && direction.equals("down")) {
			if(stonesCoordinatesInput.length()==0)
				return true;
			//System.out.println("can't move because of thanos 3");
			return false;
		}
		if(ironmanCoordinatesX + 1 == thanosCoordinatesX && (ironmanCoordinatesY == thanosCoordinatesY) && direction.equals("right")) {
			if(stonesCoordinatesInput.length()==0)
				return true;
			//System.out.println("can't move because of thanos 4");
			return false;
		}
		//borders
		if((ironmanCoordinatesY == 0) && direction.equals("up")) {
			//System.out.println("can't move because of borders");
			return false;
		}

		else if((ironmanCoordinatesY == m - 1) && direction.equals("down")) {
			//System.out.println("can't move because of borders");
			return false;
		}
		else if((ironmanCoordinatesX == 0) && direction.equals("left")) {
			//System.out.println("can't move because of borders");
			return false;

		}
		else if((ironmanCoordinatesX == n - 1) && direction.equals("right")) {
			//System.out.println("can't move because of borders");
			return false;

		}

		//moving to a warrior cell
		for(int i=0; i< warriorsCoordinates.size(); i++) {
			int[] warriorCoordinate = warriorsCoordinates.get(i);
			int warriorCoordinateY = warriorCoordinate[0];
			int warriorCoordinateX = warriorCoordinate[1];
			if(ironmanCoordinatesX - 1 == warriorCoordinateX && (ironmanCoordinatesY == warriorCoordinateY)  && direction.equals("left")) {

				//System.out.println("can't move because of warriors");
				return false;
			}
			else if(ironmanCoordinatesX + 1 == warriorCoordinateX && (ironmanCoordinatesY == warriorCoordinateY) && direction.equals("right")) {
				//System.out.println("can't move because of warriors");
				return false;
			}
			else if((ironmanCoordinatesY - 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX) && direction.equals("up")) {
				//System.out.println("can't move because of warriors");
				return false;
			}
			else if(ironmanCoordinatesY + 1 == warriorCoordinateY && (ironmanCoordinatesX == warriorCoordinateX) && direction.equals("down")) {
				//System.out.println("can't move because of warriors");
				return false;
			}
		}
		return true;
	}
	public EndGameState collectActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String stonesCoordinatesInput, EndGameState giveState) {
		String[] coordinates = giveState.getCoordinates().split(";");
		String stonesCoordinatesString = "";
		if(coordinates.length == 3)
			stonesCoordinatesString = coordinates[2];
		ArrayList<int[]> stonesCoordinates = stringCoordinatesToArrayListCoordinates(stonesCoordinatesString);
		for(int i=0; i< stonesCoordinates.size(); i++) {
			int[] stoneCoordinate = stonesCoordinates.get(i);
			int stonesCoordinateY = stoneCoordinate[0];
			int stoneCoordinateX = stoneCoordinate[1];
			if(stonesCoordinateY == ironmanCoordinatesY && stoneCoordinateX == ironmanCoordinatesX) {
				stepCost = stepCost + 3;
				stonesCoordinates.remove(i);
				return new EndGameState(
						coordinates[0] + ";" + coordinates[1] + ";" + arrayListCoordinatesToStringCoordinates(stonesCoordinates)
						);
			}
		}
		return null;
	}
	public EndGameState killActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput, EndGameState giveState) {
		String[] coordinates = giveState.getCoordinates().split(";");
		ArrayList<int[]> warriorsCoordinates;
		try {
			warriorsCoordinates = stringCoordinatesToArrayListCoordinates(coordinates[1]);}
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
			if(coordinates.length==3)
				stonesCoordinatesString = coordinates[2];
			return new EndGameState(
					coordinates[0] + ";" + arrayListCoordinatesToStringCoordinates(warriorsCoordinates) + ";" + stonesCoordinatesString
					);
		}
		return null;
	}
	public EndGameState moveActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String direction, EndGameState giveState) {
		String[] coordinates = giveState.getCoordinates().split(";");
		String warriorCoordinates;
		try {
			warriorCoordinates = coordinates[1];}
		catch(Exception E) {
			warriorCoordinates = "-1,-1";
		}
		String stonesCoordinates = "";
		if(coordinates.length == 3)
			stonesCoordinates = coordinates[2];
		if(direction.equals("up")) {
			return new EndGameState(
					(ironmanCoordinatesY - 1) +","+ (ironmanCoordinatesX) + ";" + warriorCoordinates + ";" + stonesCoordinates
					);
		}
		else if(direction.equals("down")) {
			return new EndGameState(
					(ironmanCoordinatesY + 1) +","+ (ironmanCoordinatesX) + ";" + warriorCoordinates + ";" + stonesCoordinates
					);
		}
		else if(direction.equals("left")) {
			return new EndGameState(
					(ironmanCoordinatesY) +","+ (ironmanCoordinatesX - 1)+ ";" + warriorCoordinates + ";" + stonesCoordinates
					);
		}
		else if(direction.equals("right")) {
			return new EndGameState(
					(ironmanCoordinatesY) +","+ (ironmanCoordinatesX +1)+ ";" + warriorCoordinates + ";" + stonesCoordinates
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

				if(i == ironmanCoordinatesArray[0] && j == ironmanCoordinatesArray[1] && !found) {
					System.out.print("I" + " ");
					found = true;
				}
				
				if((i == thanosCoordinates[0] && j == thanosCoordinates[1]) && !found){
					System.out.print("T" + " ");
					found = true;
				}

				if(!found) {
					for(int ii =0; ii<stonesCoordinatesArray.size();ii++ ) {
						int[] curr = stonesCoordinatesArray.get(ii);
						if(curr[0] == i && curr[1] == j) {
							System.out.print("S" + " ");
							found = true;
						}

					}
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
	public void putinVisitedStates(Object state) {
		EndGameState endgameState = (EndGameState) state;
		visitedStates.add(endgameState.getCoordinates());
	}

	@Override
	public void calculatePathCost(Node node, int stepCost) {
		node.setPathCost(node.parent.getPathCost()+stepCost);
	}
	
	public void setAS1Heurstic(Node node) {
		try {
		node.setHeuristicCost(((EndGameState)(node.getState())).getCoordinates().split(";")[2].length()*3 + node.getPathCost());
		}
		catch(Exception E){
			node.setHeuristicCost(0);
		}
	}
	
	public void setAS2Heurstic(Node node) {
		try {
			node.setHeuristicCost(((EndGameState)(node.getState())).getCoordinates().split(";")[2].length()*3 + (Endgame.thanosDamage*2) + node.getPathCost());
		}
		catch(Exception E){
			node.setHeuristicCost(Endgame.thanosDamage*2);
		}
	}
	
	public void setGR1Heurstic(Node node) {
		try {
			node.setHeuristicCost(((EndGameState)(node.getState())).getCoordinates().split(";")[2].length());
		}
		catch(Exception E){
			node.setHeuristicCost(0);
		}
	}
	
	public void setGR2Heurstic(Node node) {
		try {
			node.setHeuristicCost(((EndGameState)(node.getState())).getCoordinates().split(";")[2].length() + (Endgame.thanosDamage*2));
		}
		catch(Exception E){
			node.setHeuristicCost(Endgame.thanosDamage*2);
		}
	}
}