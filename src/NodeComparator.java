import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	
	public int compare(Node n1, Node n2) {
		if(n1.getPathCost()==n2.getPathCost())  
			return 0;  
		else if(n1.getPathCost()>n2.getPathCost()) 
			return 1;  
		else  
			return -1;  
	}

}
