import java.util.Comparator;

public class NodeComparatorGR1 implements Comparator<Node> {
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
		
		if(n2Stuff[2].length() == n1Stuff[2].length())  
			return 0;  
		else if(n1Stuff[2].length() > n2Stuff[2].length()) 
			return 1;  
		else  
			return -1;  
	}

}
