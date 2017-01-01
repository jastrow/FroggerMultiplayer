package model;

import application.Configuration;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Car implements SubscriberInterface {

	private int id;
	private Boolean leftToRight = false;	// Auto fährt von links nach rechts (ansonsten andersrum)
	private int positionX = 1;
	private int positionY; 
	
	public Car (Boolean leftToRight, Integer positionY) {
		this.id = IdCounter.getId();
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		if(!leftToRight) {
			this.positionX = Configuration.xFields;
		}
	}

	public int getId() {
		return this.id;
	}
	public Boolean getLeftToRight() {
		return this.leftToRight;
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

	public void calling(String trigger, SubscriberDaten daten) {
		
	}
  
}
