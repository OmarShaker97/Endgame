public class EndGameInfo {

	String ironManCoordinates;
	String thanosCoordinates;
	String[] coordinates;
	String gridSize;
	
	public EndGameInfo(String gridSize, String ironManCoordinates,String thanosCoordinates, String[] coordinates) {
		this.ironManCoordinates = ironManCoordinates;
		this.thanosCoordinates = thanosCoordinates;
		this.coordinates = coordinates;
		this.gridSize = gridSize;
	}
	public String getGridSize() {
		return gridSize;
	}
	public void setGridSize(String gridSize) {
		this.gridSize = gridSize;
	}
	public String getThanosCoordinates() {
		return thanosCoordinates;
	}
	public void setThanosCoordinates(String thanosCoordinates) {
		this.thanosCoordinates = thanosCoordinates;
	}
	public String getIronManCoordinates() {
		return ironManCoordinates;
	}
	public void setIronManCoordinates(String ironManCoordinates) {
		this.ironManCoordinates = ironManCoordinates;
	}
	public String[] getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String[] coordinates) {
		this.coordinates = coordinates;
	}
}
