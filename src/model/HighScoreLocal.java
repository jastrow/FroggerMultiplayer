package model;

public class HighScoreLocal {

	private String name;
	private String date;
	private Integer time;
	
	public HighScoreLocal(String name, String date, Integer time) {
		this.name = name;
		this.date = date;
		this.time = time;
	}
	
	public String getName() { return this.name; }
	public String getDate() { return this.date; }
	public Integer getTime() { return this.time; }
	
	@Override
	public String toString() {
		return this.name+" "+this.time+" "+this.date;
	}
		
}
