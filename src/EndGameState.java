public class EndGameState {
	
	String ironManCoordinates;
	String[] coordinates;
	int hp;
	
	public EndGameState(String ironManCoordinates, String[] coordinates, int hp) {
	
		this.ironManCoordinates = ironManCoordinates;
		this.coordinates = coordinates;
		this.hp = hp;
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

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

}