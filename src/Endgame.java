import java.util.ArrayList;

public class Endgame extends Problem {
	static String ThanosCoordinates;
	static String gridSize;
	EndGameState endgame;
	//
	public Endgame(Object[] operators, Object initialState) {
		super(operators, initialState);
		// TODO Auto-generated constructor stub
	}
	//position: [y, x]
	@Override
	public Object stateSpace(Object state, Object action) {
		EndGameState endgameState = ((EndGameState) state);
		endgame = endgameState;
		EndGameState returnedEndgameState = null;
		int[] ironmanCoordinatesArray = stringCoordinatesToArrayCoordinates(endgameState.ironManCoordinates);
		int ironmanCoordinatesY = ironmanCoordinatesArray[0];
		int ironmanCoordinatesX = ironmanCoordinatesArray[1];
		String warriorsCoordinatesString = endgameState.getCoordinates()[0];
		String stonesCoordinatesString = endgameState.getCoordinates()[1];
		///should damage after the move ?
		int decreasedHp = damageFromAdjacentCells(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString);
		//		System.out.println(endgameState.getCoordinates()[0]+ " war -length");
		//		System.out.println(endgameState.getCoordinates()[1]+ " stones -length");
		//		System.out.println(endgameState.getIronManCoordinates()+ " ironman coord");
		//		System.out.println(action);
		//		System.out.println(endgameState.getHp());

		//if(stonesCoordinatesString.split(",").length < 5) {
		//	System.out.print(stonesCoordinatesString.split(",")[0]);

		//}
		//printGrid(endgameState);
		//		System.out.println("VVVVVVVVVV");
		//movements
		if(((String)action).equals("up")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "up");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "up", endgameState);
				returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
			}
		}
		else if(((String)action).equals("down")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "down");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "down", endgameState);
				returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
			}
		}
		else if(((String)action).equals("left")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "left");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "left", endgameState);
				returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
			}
		}
		else if(((String)action).equals("right")) {
			boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, "right");
			if(canMove) {
				returnedEndgameState = moveActionState(ironmanCoordinatesY, ironmanCoordinatesX, "right", endgameState);
				returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
			}
		}
		//other actions
		else if(((String)action).equals("collect")) {
			returnedEndgameState = collectActionState(ironmanCoordinatesY, ironmanCoordinatesX, stonesCoordinatesString, endgameState);
			returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
		}
		else if(((String)action).equals("snap") && stonesCoordinatesString.length() == 0 && !endgameState.isSnapped()) {
			returnedEndgameState = endgameState;
			returnedEndgameState.setSnapped(true);
			returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
		}
		else if(((String)action).equals("kill")) {	
			returnedEndgameState = killActionState(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString,  endgameState);
			returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
		}
		/*	if(returnedEndgameState == null) {
			// returnedEndgameState = endgameState;
			//returnedEndgameState.setHp(returnedEndgameState.getHp() - decreasedHp);
			//System.out.println("null state");
		}*/

		//boolean canMove = canMove(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString, stonesCoordinatesString, ((String)action));
		//System.out.println(canMove);
		//		System.out.println(returnedEndgameState.getCoordinates()[0] + " war -length");
		//		System.out.println(returnedEndgameState.getCoordinates()[1] + " stones -length");
		//		System.out.println(returnedEndgameState.getIronManCoordinates() + " ironman coord");
		//		System.out.println(returnedEndgameState.getHp());
		//if(returnedEndgameState!=null) {
		//		printGrid(returnedEndgameState);
		//		System.out.println("------------");}

		//returnedEndgameState.setHp(returnedEndgameState.getHp()-damageFromAdjacentCells(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesString));
		return returnedEndgameState;
	}

	@Override
	public boolean goalTest(Object state) {	
		EndGameState endGameState = (EndGameState) state;
		return endGameState.getCoordinates()[1].length() == 0 && endGameState.getIronManCoordinates().equals(ThanosCoordinates) && endGameState.getHp()>0 && endGameState.isSnapped();
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
		//System.out.println(node.depth+1+ "lolololol");
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
	public int damageFromAdjacentCells(int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput) {
		return damageFromAdjacentWarriors(ironmanCoordinatesY, ironmanCoordinatesX, warriorsCoordinatesInput)
				+ damageFromAdjacentThanos(ironmanCoordinatesY, ironmanCoordinatesX);
	}
	public int damageFromAdjacentWarriors (int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput) {
		int decreasedHp = 0;
		ArrayList<int[]> warriorsCoordinates = stringCoordinatesToArrayListCoordinates(warriorsCoordinatesInput);
		for(int i=0; i< warriorsCoordinates.size(); i++) {
			int[] warriorCoordinate = warriorsCoordinates.get(i);
			int warriorCoordinateY = warriorCoordinate[0];
			int warriorCoordinateX = warriorCoordinate[1];
			if((ironmanCoordinatesY - 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX)) {
				decreasedHp+=1;
			}
			if((ironmanCoordinatesX - 1 == warriorCoordinateX) && (ironmanCoordinatesY == warriorCoordinateY)) {
				decreasedHp+=1;
			}
			if((ironmanCoordinatesY + 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX)) {
				decreasedHp+=1;
			}
			if((ironmanCoordinatesX + 1 == warriorCoordinateX) && (ironmanCoordinatesY == warriorCoordinateY)) {
				decreasedHp+=1;
			}
		}
		if(decreasedHp!=0) {
			//System.out.println("damage from warriors");
		}
		return decreasedHp;
	}

	public int damageFromAdjacentThanos(int ironmanCoordinatesY, int ironmanCoordinatesX) {
		int decreasedHp = 0;
		int[] thanosCoordinates = stringCoordinatesToArrayCoordinates(ThanosCoordinates);
		int thanosCoordinatesY = thanosCoordinates[0];
		int thanosCoordinatesX = thanosCoordinates[1];
		if((ironmanCoordinatesY - 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX)) {
			decreasedHp+=5;
		}
		if((ironmanCoordinatesX - 1 == thanosCoordinatesX) && (ironmanCoordinatesY == thanosCoordinatesY)) {
			decreasedHp+=5;
		}
		if((ironmanCoordinatesY + 1 == thanosCoordinatesY) && (ironmanCoordinatesX == thanosCoordinatesX)) {
			decreasedHp+=5;
		}
		if(ironmanCoordinatesX + 1 == thanosCoordinatesX && (ironmanCoordinatesY == thanosCoordinatesY)) {
			decreasedHp+=5;
		}
		if(decreasedHp!=0) {
			//System.out.println("damage from thanos");
		}
		return decreasedHp;
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
		ArrayList<int[]> stonesCoordinates = stringCoordinatesToArrayListCoordinates(stonesCoordinatesInput);
		//		for(int i = 0; i<stonesCoordinates.size(); i++) {
		//			System.out.println(stonesCoordinates.get(i)[0]+","+stonesCoordinates.get(i)[1]+",");
		//		}
		int stackedHp = 0;
		String warriorsCoordinates = giveState.getCoordinates()[0];
		for(int i=0; i< stonesCoordinates.size(); i++) {
			int[] stoneCoordinate = stonesCoordinates.get(i);
			int stonesCoordinateY = stoneCoordinate[0];
			int stoneCoordinateX = stoneCoordinate[1];
			if(stonesCoordinateY == ironmanCoordinatesY && stoneCoordinateX == ironmanCoordinatesX) {
				stackedHp = stackedHp + 3;
				stonesCoordinates.remove(i);
				return new EndGameState(
						giveState.getIronManCoordinates()
						, new String [] {warriorsCoordinates, arrayListCoordinatesToStringCoordinates(stonesCoordinates)}
						,giveState.getHp() - stackedHp
						);
			}
		}
		return giveState;
	}
	public EndGameState killActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String warriorsCoordinatesInput, EndGameState giveState) {
		ArrayList<int[]> warriorsCoordinates = stringCoordinatesToArrayListCoordinates(warriorsCoordinatesInput);
		String stonesCoordinates = giveState.getCoordinates()[1];
		int stackedHp = 0;
		for(int i=0; i< warriorsCoordinates.size(); i++) {
			int[] warriorCoordinate = warriorsCoordinates.get(i);
			int warriorCoordinateY = warriorCoordinate[0];
			int warriorCoordinateX = warriorCoordinate[1];
			if((ironmanCoordinatesY - 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX )) {
				stackedHp=stackedHp +2;
				warriorsCoordinates.remove(i);
			}
			else if((ironmanCoordinatesY == warriorCoordinateY) && (ironmanCoordinatesX - 1 == warriorCoordinateX)) {
				stackedHp=stackedHp +2;
				warriorsCoordinates.remove(i);
			}
			else if((ironmanCoordinatesX + 1 == warriorCoordinateX) && (ironmanCoordinatesY == warriorCoordinateY)) {
				stackedHp=stackedHp +2;
				warriorsCoordinates.remove(i);
			}
			else if((ironmanCoordinatesY + 1 == warriorCoordinateY) && (ironmanCoordinatesX == warriorCoordinateX)) {
				stackedHp=stackedHp +2;
				warriorsCoordinates.remove(i);
			}
		}
		return new EndGameState(
				giveState.getIronManCoordinates()
				, new String [] {arrayListCoordinatesToStringCoordinates(warriorsCoordinates), stonesCoordinates}
				,giveState.getHp() - stackedHp
				);
	}
	public EndGameState moveActionState(int ironmanCoordinatesY, int ironmanCoordinatesX, String direction, EndGameState giveState) {
		if(direction.equals("up")) {
			return new EndGameState(
					(ironmanCoordinatesY - 1) +","+ (ironmanCoordinatesX)
					, giveState.getCoordinates()
					,giveState.getHp() 
					);
		}
		else if(direction.equals("down")) {
			return new EndGameState(
					(ironmanCoordinatesY + 1) +","+ (ironmanCoordinatesX)
					, giveState.getCoordinates()
					,giveState.getHp() 
					);
		}
		else if(direction.equals("left")) {
			return new EndGameState(
					(ironmanCoordinatesY) +","+ (ironmanCoordinatesX - 1)
					, giveState.getCoordinates()
					,giveState.getHp() 
					);
		}
		else if(direction.equals("right")) {
			return new EndGameState(
					(ironmanCoordinatesY) +","+ (ironmanCoordinatesX +1)
					, giveState.getCoordinates()
					,giveState.getHp() 
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
		String ironmanCoods = state.getIronManCoordinates();
		String[] coordinates = state.getCoordinates();
		String stonesCords = coordinates[1];
		String warriorsCords = coordinates[0];
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
		String endgameStateIronmanCoordinates = endgameState.getIronManCoordinates();
		String[] endgameStateCoordinates = endgameState.getCoordinates();
		int endgameStateHp = endgameState.getHp();
		//System.out.println(visitedStates.size()+"");
		for(int i=0; i<visitedStates.size();i++) {
			EndGameState currState = (EndGameState) visitedStates.get(i);
			String currStateIronmanCoordinates = currState.getIronManCoordinates();
			String[] currStatecoordinates = currState.getCoordinates();
			int currStateHp = currState.getHp();
			if((currStateIronmanCoordinates.equals(endgameStateIronmanCoordinates))
					&& (endgameStateCoordinates[0].equals(currStatecoordinates[0]))
					&& (endgameStateCoordinates[1].equals(currStatecoordinates[1]))
					&& (currStateHp == endgameStateHp)
					&& (currState.isSnapped() == endgameState.isSnapped())) {
				return true;

			}
		}
		return false;
	}
}
