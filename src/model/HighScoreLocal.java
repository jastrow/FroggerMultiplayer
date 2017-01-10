package model;

public class HighScoreLocal {

	private String name;
	private String date;
	private Integer time;
	
	
	@Override
	public String toString() {
		return this.name+" "+this.time+" "+this.date;
	}
		
}
