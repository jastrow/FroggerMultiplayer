package model;

public class Balken {
	
	private int laenge = 0;
	private int oldPositionX = 0;
	private int oldPositionY = 0; 
	private int positionX = 0;
	private int positionY = 0; 
	
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
	

	public int getOldPositionX() {
		return this.oldPositionX;
	}
	
	public int getOldPositionY() {
		return this.oldPositionY;
	}
	
	
	public void setPositionX(int positionX) {
		this.oldPositionX = this.positionX;
		this.positionX = positionX;
	}
	
	public void setPositionY(int positionY) {
		this.oldPositionY = this.positionY;
		this.positionY = positionY;
	}
	
	
}
