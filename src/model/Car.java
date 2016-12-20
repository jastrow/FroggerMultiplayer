package model;

import javafx.scene.image.Image;

public class Car {

	private int oldPositionX = 0;
	private int oldPositionY = 0;
	private int positionX = 0;
	private int positionY = 0; 
	private Image carPic;
	
	public Car (int positionY, int positionX, Image carPic) {
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
		this.oldPositionX = this.positionX;
		this.positionX = positionX;
	}
	
	public void setPositionY(int positionY) {
		this.oldPositionY = this.positionY;
		this.positionY = positionY;
	}
	
	/* public void setOldPositionX(int oldPositionX) {
		this.oldPositionX = oldPositionX;
	}
	
	public void setOldPositionY(int oldPositionY) {
		this.oldPositionY = oldPositionY;
	}*/
	
	public Image getImage() {
		return this.carPic;
	}  
  
  
}
