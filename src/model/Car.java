package model;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Car implements SubscriberInterface {

	private int id;
	private Boolean leftToRight = false;	// Auto fährt von links nach rechts (ansonsten andersrum)
	private int positionX = 1;
	private int positionY; 
	private int lastMovement;
	
	public Car (Boolean leftToRight, Integer positionY) {
		this.id = IdCounter.getId();
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		if(!leftToRight) {
			this.positionX = Configuration.xFields;
		}
		this.lastMovement = 0;
		Observer.add("time", this);
		this.sendObserver("new");
		System.out.println("new car");
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
		this.movement(daten.time);
	}
	
	private void movement(Integer timeNow) {
		Integer timeDif = timeNow - this.lastMovement;
		if(timeDif >= Configuration.carSpeed) {
			if(this.leftToRight) {
				this.positionX++;
			} else {
				this.positionX--;
			}
			this.lastMovement += Configuration.carSpeed;
			this.checkLeftStreet();
		}
		
	}
	
	public void checkLeftStreet() {
		String typ = "move";
		if(this.leftToRight) {
			if(this.positionX > Configuration.xFields) {
				typ = "delete";
			}
		} else {
			if(this.positionX < 1) {
				typ = "delete";
			}
		}
		this.sendObserver(typ);
	}
	
	public void sendObserver(String typ) {
		
		SubscriberDaten data = new SubscriberDaten();
		data.id 		= this.id;
		data.name 		= "Car";
		data.xPosition 	= this.positionX;
		data.yPosition 	= this.positionY;
		data.typ 		= typ;
		Observer.trigger("car", data);
		System.out.println(data.toString());
	}
  
}
