public class EndGameState {
	
	String coordinates;
	// String[] coordinates;
	int hp;
	boolean isSnapped;
	
	public EndGameState(String coordinates, int hp) {
		this.coordinates = coordinates;
		this.hp = hp;
		isSnapped = false;
	}

	public boolean isSnapped() {
		return isSnapped;
	}

	public void setSnapped(boolean isSnapped) {
		this.isSnapped = isSnapped;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

}
