package model;

public class Frog {

	
	private int feldindex;			// 1 = Gras / 2 = Balken / 3 = Wasser
	private int positionX;
	private int positionY; 
	private int froschIndex;
	private String spielername;
	
	public Frog (int positionY, int positionX, int froschIndex, String spielername) {
		this.feldindex = 1;
		this.positionX = positionX;
		this.positionY = positionY;
		this.froschIndex = froschIndex;
		this.spielername = spielername;
	}
	
	public int getFroschIndex() {
		return this.froschIndex;
	}		
	
	public int getFeldindex() {
		return this.feldindex;
	}
	
	public int getPositionX() {
		return this.positionX;
	}
	
	public int getPositionY() {
		return this.positionY;
	}

	public void setFeldindex(int feldindex) {
		this.feldindex = feldindex;
	}
	
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	public void setSpielername(String Spielername) {
		this.spielername = Spielername;
	}
	
	public String getSpielername() {
		return this.spielername;
	}
	
	
}
