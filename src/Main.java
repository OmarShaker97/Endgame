import java.util.ArrayList;

public class Main{
	
	 static Endgame problem;
	
	public static void main(String[] args) {
		// problem = new Endgame();
		
		String[] operators = {"up", "down", "left", "right", "collect", "kill", "snap"};
		String[][] grid = problem.StringtoGrid("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3");
		problem = new Endgame(operators, new EndGameState(grid, 100));
		solve(grid, "BF", false);
	}
	

	public static String solve(String grid, String strategy, boolean visualize) {
		
		if(strategy.equals("BF")) {
			ArrayList<Node> nodes = new ArrayList<Node>();
			nodes.add(new Node(problem.initialState, null, "", 0, 0));
			Node node = nodes.remove(0);
			problem.goalTest(node.state);
		}
		
	}
	
	
	
}