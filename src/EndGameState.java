public class EndGameState  {

	String coordinates; // our string looks like that by convention "ironman coordinates; warriors coordinates; stones coordinates"

	public EndGameState(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

}
