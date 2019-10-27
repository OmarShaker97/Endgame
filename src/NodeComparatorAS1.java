import java.util.Comparator;

public class NodeComparatorAS1 implements Comparator<Node> {
	//	public int compare(Node n1, Node n2) {
	//		if(n1.getPathCost()==n2.getPathCost())  
	//			return 0;  
	//		else if(n1.getHeuristicCost() < n2.getHeuristicCost()) 
	//			return 1;  
	//		else  
	//			return -1;  
	//	}

	public int compare(Node n1, Node n2) {
		String[] n1Stuff =  ((EndGameState)n1.getState()).getCoordinates().split(";");
		String[] n2Stuff =  ((EndGameState)n2.getState()).getCoordinates().split(";");
		if(n1Stuff.length != 3 )
			return 1;
		else if (n2Stuff.length != 3)
			return -1;
		
		
		byte totalDamageN1 = (byte) (((EndGameState)n1.getState()).getCoordinates().split(";")[2].length() * 3 + n1.getPathCost());
		byte totalDamageN2 = (byte) (((EndGameState)n2.getState()).getCoordinates().split(";")[2].length() * 3 + n2.getPathCost());
		
		if(totalDamageN2 == totalDamageN1)  
			return 0;  
		else if(totalDamageN1 > totalDamageN2) 
			return 1;  
		else  
			return -1;   
	}

}
