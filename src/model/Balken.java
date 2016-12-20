package model;

public class Balken {
	
	private int laenge;
	private int positionX;
	private int positionY; 
	
	public Balken (int positionY, int positionX, int laenge) {
		this.laenge = laenge;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	
	public int getLaenge() {
		return this.laenge;
	}
	
	public int getPositionX() {
		return this.positionX;
	}
	
	public int getPositionY() {
		return this.positionY;
	}
	
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	
}
