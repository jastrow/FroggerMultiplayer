package model;

public class HighScore {
	
	private String[] playerName;
	private Integer[] playerPlace;
	private Integer[] playerTime;
	
	
	public HighScore() {
		this.playerName = new String[3];
		this.playerPlace = new Integer[3];
		this.playerTime = new Integer[3];
	}
	
	public String[] getPlayerName() {
		return this.playerName;
	}
	
	public Integer[] getPlayerPlace() {
		return this.playerPlace;
	}
	
	public Integer[] getPlayerTime() {
		return this.playerTime;
	}
	
	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}
	
	public void setPlayerPlace(Integer[] playerPlace) {
		this.playerPlace = playerPlace;
	}
	
	public void setPlayerTime(Integer[] playerTime) {
		this.playerTime = playerTime;
	}

}
