import java.util.Comparator;

public class NodeComparatorGR2 implements Comparator<Node> {
	//	public int compare(Node n1, Node n2) {
	//		if(n1.getPathCost()==n2.getPathCost())  
	//			return 0;  
	//		else if(n1.getHeuristicCost() < n2.getHeuristicCost()) 
	//			return 1;  
	//		else  
	//			return -1;  
	//	}

	  public int compare(Node n1, Node n2) {
	        byte totalDamageN1 = (byte) (n1.getHeuristicCost());
	        byte totalDamageN2 = (byte) (n2.getHeuristicCost());

	        if(totalDamageN2 == totalDamageN1)
	            return 0;
	        else if(totalDamageN1 > totalDamageN2) 
	            return 1;
	        else
	            return -1;
	    }
}
