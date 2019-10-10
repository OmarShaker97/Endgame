import java.util.ArrayList;
import java.util.Arrays;

public class Endgame extends Problem {
	static String ThanosCoordinates;
	static String gridSize;
	int stepCost;
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
		int[] ironmanCoordinatesArray = stringCoordinatesToArrayCoordinates(coordinates[0]);
		int ironmanCoordinatesY = ironmanCoordinatesArray[0];
		int ironmanCoordinatesX = ironmanCoordinatesArray[1];
		String warriorsCoordinatesString = coordinates[1];
		String stonesCoordinatesString = coordinates[2];
		stepCost = 0;
		damageFromAdjacentCells(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString);
		//System.out.println(endgameState.getCoordinates()+ " coords");
		//System.out.println(node.getPathCost());
		//		System.out.println(endgameState.getCoordinates()[0]+ " war -length");
		//		System.out.println(endgameState.getCoordinates()[1]+ " stones -length");
		//		System.out.println(endgameState.getIronManCoordinates()+ " ironman coord");
		String[] coordinatesToPrint = endgameState.getCoordinates().split(";");
		System.out.println(node.getPathCost());
		System.out.println("ironman coord:"+coordinatesToPrint[0]);
		System.out.println("warriors:"+coordinatesToPrint[1]);
		System.out.println("stones:"+coordinatesToPrint[2]);
		System.out.println(action);
		//		System.out.println(endgameState.getHp());

//		if(stonesCoordinatesString.split(",").length <= 2) {
//			System.out.print(stonesCoordinatesString.split(",")[0]);
//		}
		printGrid(endgameState);
		System.out.println("VVVVVVVVVV");
		//movements
		if(((String)action).equals("up")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "up");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "up", endgameState);
				//stepCost = 
				//returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
			}
		}
		else if(((String)action).equals("down")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "down");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "down", endgameState);
				//returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
			}
		}
		else if(((String)action).equals("left")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "left");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "left", endgameState);
				//returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
			}
		}
		else if(((String)action).equals("right")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "right");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "right", endgameState);
				//returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
			}
		}
		//other actions
		else if(((String)action).equals("collect")) {
			returnedEndgameState = collectActionState(ironmanCoordinatesY, ironmanCoordinatesX, stonesCoordinatesString, endgameState);
			//if(returnedEndgameState!=null)
				//returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
		}
		else if(((String)action).equals("snap") && stonesCoordinatesString.length() == 0) {
			returnedEndgameState = endgameState;
			//returnedEndgameState.setSnapped(true);
			//returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
		}
		else if(((String)action).equals("kill")) {	
			returnedEndgameState = killActionState(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString,  endgameState);
			//if(returnedEndgameState!=null)
				//returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
		}
//			if(returnedEndgameState == null) {
//			 returnedEndgameState = endgameState;
//			System.out.println("null state");
//		}

		//boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, ((String)action));
		//System.out.println(canMove);
//				System.out.println(returnedEndgameState.getCoordinates()[0] + " war -length");
//				System.out.println(returnedEndgameState.getCoordinates()[1] + " stones -length");
//				System.out.println(returnedEndgameState.getIronManCoordinates() + " ironman coord");
//				System.out.println(returnedEndgameState.getHp());
		 if(returnedEndgameState!=null) {
			 String[] coordinatesToPrint1 = returnedEndgameState.getCoordinates().split(";");
				System.out.println(node.getPathCost());
				System.out.println("ironman coord:"+coordinatesToPrint1[0]);
				System.out.println("warriors:"+coordinatesToPrint1[1]);
				System.out.println("stones:"+coordinatesToPrint1[2]);
			 printGrid(returnedEndgameState);
			 
		 }
		 else {
			 System.out.println("null state");
		 }
		//returnedEndgameState.setHp(returnedEndgameState.getHp()-damageFromAdjacentCells(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString));
		Node returnedNode = new Node(returnedEndgameState, node, action, node.getDepth()+1,0 );
		calculatePathCost(returnedNode, stepCost);
		System.out.println(returnedNode.getPathCost());
		System.out.println("------------");
		return returnedNode;
	}

	@Override
	public boolean goalTest(Object state) {	
		EndGameState endGameState = (EndGameState) state;
		String[] coordinates = endGameState.getCoordinates().split(";");
		return coordinates[2].length() == 0 && coordinates[0].equals(ThanosCoordinates);
	}
	
	public Node[] expand(Node node, Object[] operators) {
		Node[] nodes = new Node[operators.length];
		for(int i=0; i<operators.length;i++) {
			nodes[i] = transitionFunction(node, operators[i]);
			//nodes[i] = new Node(stateSpace(node.state, operators[i]), node, operators[i], node.depth+1, 0);
		}
		return nodes;
	}

	public ArrayList<int[]> stringCoordinatesToArrayListCoordinates(String pos) {
		//System.out.println(endgame.getCoordinates()[1] +"-length: "+endgame.getCoordinates()[1].length());
		//System.out.println(endgame.getCoordinates()[0]+"-length: "+endgame.getCoordinates()[0].length());
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


	public boolean canMove(int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput,String stonesCoordinatesInput, String direction) {
		ArrayList<int[]> warriorsCoordinates = stringCoordinatesToArrayListCoordinates(warriorsCoordinatesInput);
		int[] gridSizeArray = stringCoordinatesToArrayCoordinates(gridSize);
		int m = gridSizeArray[0];
		int n = gridSizeArray[1];
		int[] thanosCoordinates = stringCoordinatesToArrayCoordinates(ThanosCoordinates);
		int thanosCoordinatesY = thanosCoordinates[0];
		int thanosCoordinatesX = thanosCoordinates[1];
		if((ironmanCoordinatesY - 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX)) {
			if(stonesCoordinatesInput.length()==0)
				return true;
			//System.out.println("can't move because of thanos");
			return false;
		}
		if((ironmanCoordinatesX - 1 == thanosCoordinatesX) && (ironmanCoordinatesY == thanosCoordinatesY)) {
			if(stonesCoordinatesInput.length()==0)
				return true;
			//System.out.println("can't move because of thanos");
			return false;
		}
		if((ironmanCoordinatesY + 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX)) {
			if(stonesCoordinatesInput.length()==0)
				return true;
			//System.out.println("can't move because of thanos");
			return false;
		}
		if(ironmanCoordinatesX + 1 == thanosCoordinatesX && (ironmanCoordinatesY == thanosCoordinatesY)) {
			if(stonesCoordinatesInput.length()==0)
				return true;
			//System.out.println("can't move because of thanos");
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
				//System.out.println("can't move because of a warrior");
				return false;
			}
			else if(ironmanCoordinatesX + 1 == warriorCoordinateX && (ironmanCoordinatesY == warriorCoordinateY) && direction.equals("right")) {
				//System.out.println("can't move because of a warrior");
				return false;
			}
			else if((ironmanCoordinatesY - 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX) && direction.equals("up")) {
				//System.out.println("can't move because of a warrior");
				return false;
			}
			else if(ironmanCoordinatesY + 1 == warriorCoordinateY && (ironmanCoordinatesX == warriorCoordinateX) && direction.equals("down")) {
				//System.out.println("can't move because of a warrior");
				return false;
			}
		}
		return true;
	}
	public EndGameState collectActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String stonesCoordinatesInput, EndGameState giveState) {
		//System.out.println(stonesCoordinatesInput+"lola");
		//		for(int i = 0; i<stonesCoordinates.size(); i++) {
		//			System.out.println(stonesCoordinates.get(i)[0]+","+stonesCoordinates.get(i)[1]+",");
		//		}
		
		//int stackedHp = 0;
		String[] coordinates = giveState.getCoordinates().split(";");
		ArrayList<int[]> stonesCoordinates = stringCoordinatesToArrayListCoordinates(coordinates[2]);
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
		ArrayList<int[]> warriorsCoordinates = stringCoordinatesToArrayListCoordinates(coordinates[1]);
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
			return new EndGameState(
					coordinates[0] + ";" + arrayListCoordinatesToStringCoordinates(warriorsCoordinates) + ";" + coordinates[2]
					);
		}
		return null;
	}
	public EndGameState moveActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String direction, EndGameState giveState) {
		String[] coordinates = giveState.getCoordinates().split(";");
		String warriorCoordinates = coordinates[1];
		String stonesCoordinates = coordinates[2];
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

	public String arrayCoordinatesToStringCoordinates(int[] posArray) {
		String joinedString = "";
		for(int i=0;i< posArray.length; i++)
			joinedString+=posArray[i]+",";
		return joinedString.substring(0, joinedString.length() - 1);
	}

	public String arrayListCoordinatesToStringCoordinates(ArrayList<int[]> arrayInput) {
		String stringOutput = "";
		for(int i = 0; i<arrayInput.size(); i++) {
			//System.out.println(arrayInput.get(i)[0]+","+arrayInput.get(i)[1]+",");
			stringOutput+=arrayInput.get(i)[0]+","+arrayInput.get(i)[1]+",";
		}
		if(stringOutput.length()==0)
			return "";
		return stringOutput.substring(0, stringOutput.length() - 1);
	}

	public void printGrid(EndGameState state) {
		String[] coordinates = state.getCoordinates().split(";");
		String ironmanCoods = coordinates[0];
		String stonesCords = coordinates[2];
		String warriorsCords = coordinates[1];
		int[] ironmanCoordinatesArray = stringCoordinatesToArrayCoordinates(ironmanCoods);
		ArrayList<int[]> warriorsCoordinatesArray = stringCoordinatesToArrayListCoordinates(warriorsCords);
		ArrayList<int[]> stonesCoordinatesArray = stringCoordinatesToArrayListCoordinates(stonesCords);
		int[] gridSizeArray = stringCoordinatesToArrayCoordinates(gridSize);
		int[] thanosCoordinates = stringCoordinatesToArrayCoordinates(ThanosCoordinates);
		System.out.print("g"+" ");

		for(int i = 0; i<gridSizeArray[0]; i++)
			System.out.print(i + " ");
		System.out.println("");

		for(int i = 0; i<gridSizeArray[0]; i++) {
			System.out.print(i+ " ");
			for(int j = 0 ; j<gridSizeArray[1]; j++) {
				boolean found = false;
				if(i == ironmanCoordinatesArray[0] && j == ironmanCoordinatesArray[1]) {
					System.out.print("I" + " ");
					found = true;
				}
				else if((i == thanosCoordinates[0] && j == thanosCoordinates[1])){
					System.out.print("T" + " ");
					found = true;
				}
				for(int ii =0; ii<warriorsCoordinatesArray.size();ii++ ) {
					int[] curr = warriorsCoordinatesArray.get(ii);
					if(curr[0] == i && curr[1] == j) {
						found = true;
						System.out.print("W" + " ");
					}

				}
				for(int ii =0; ii<stonesCoordinatesArray.size();ii++ ) {
					int[] curr = stonesCoordinatesArray.get(ii);
					if(curr[0] == i && curr[1] == j) {
						System.out.print("S" + " ");
						found = true;
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
	public void calculatePathCost(Node node, int stepCost) {
		node.setPathCost(node.parent.getPathCost()+stepCost);
	}

	@Override
	public void putinVisitedStates(Object state) {
		EndGameState endgameState = (EndGameState) state;
		visitedStates.add(endgameState.getCoordinates());
	}
}
