import java.util.ArrayList;

public class EndGameInfo {

	int[] ironManCoordinates;
	int[] thanosCoordinates;
	ArrayList<int[]> warriorsCoordinates;
	ArrayList<int[]> stonesCoordinates; 

	public EndGameInfo(int[] ironManCoordinates, int[] thanosCoordinates, ArrayList<int[]> warriorsCoordinates,
			ArrayList<int[]> stonesCoordinates) {
		this.ironManCoordinates = ironManCoordinates;
		this.thanosCoordinates = thanosCoordinates;
		this.warriorsCoordinates = warriorsCoordinates;
		this.stonesCoordinates = stonesCoordinates;
	}

	public int[] getIronManCoordinates() {
		return ironManCoordinates;
	}

	public void setIronManCoordinates(int[] ironManCoordinates) {
		this.ironManCoordinates = ironManCoordinates;
	}

	public int[] getThanosCoordinates() {
		return thanosCoordinates;
	}

	public void setThanosCoordinates(int[] thanosCoordinates) {
		this.thanosCoordinates = thanosCoordinates;
	}

	public ArrayList<int[]> getWarriorsCoordinates() {
		return warriorsCoordinates;
	}

	public void setWarriorsCoordinates(ArrayList<int[]> warriorsCoordinates) {
		this.warriorsCoordinates = warriorsCoordinates;
	}

	public ArrayList<int[]> getStonesCoordinates() {
		return stonesCoordinates;
	}

	public void setStonesCoordinates(ArrayList<int[]> stonesCoordinates) {
		this.stonesCoordinates = stonesCoordinates;
	}
	
}
