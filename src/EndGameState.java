public class EndGameState {

	String coordinates;
	// String[] coordinates;
	//boolean isSnapped;

	public EndGameState(String coordinates) {
		this.coordinates = coordinates;
		//isSnapped = false;
	}

	//	public boolean isSnapped() {
	//		return isSnapped;
	//	}
	//
	//	public void setSnapped(boolean isSnapped) {
	//		this.isSnapped = isSnapped;
	//	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public boolean isEqaul(EndGameState stateTobeCompared) {
		if(this.coordinates == stateTobeCompared.coordinates) {
			return true;
		}
		return false;

	}

}
