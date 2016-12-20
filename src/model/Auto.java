package model;

public class Car {

	private int oldPositionX;
	private int oldPositionY;
	private int positionX;
	private int positionY; 
	private Image carPic;
	
	public Auto (int positionY, int positionX, Image carPic) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.carPic = carPic;
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
		this.positionX = positionX;
	}
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	public void setOldPositionX(int oldPositionX) {
		this.oldPositionX = oldPositionX;
	}
	
	public void setOldPositionY(int oldPositionY) {
		this.oldPositionY = oldPositionY;
	}
	
	public void getImage() {
		return this.carPic;
	}  
  
  
}
