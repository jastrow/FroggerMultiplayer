package model;

public class HighScore {
	
	private String[] playerName;
	private String[] playerDate;
	private Integer[] playerTime;
	
	
	public HighScore() {
		this.playerName = new String[3];
		this.playerDate = new String[3];
		this.playerTime = new Integer[3];
	}
	
	public String[] getPlayerName() {
		return this.playerName;
	}
	
	public String[] getPlayerDate() {
		return this.playerDate;
	}
	
	public Integer[] getPlayerTime() {
		return this.playerTime;
	}
	
	public void setPlayerName(String[] playerName) {
		this.playerName = playerName;
	}
	
	public void setPlayerDate(String[] playerDate) {
		this.playerDate = playerDate;
	}
	
	public void setPlayerTime(Integer[] playerTime) {
		this.playerTime = playerTime;
	}

}
