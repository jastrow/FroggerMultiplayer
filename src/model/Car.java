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
	private int startTime = 0;
	
	public Car (Boolean leftToRight, Integer positionY) {
		this.id = IdCounter.getId();
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		if(!leftToRight) {
			this.positionX = Configuration.xFields;
		}
		Observer.add("time", this);
		this.sendObserver("new");
	}
	
	public void calling(String trigger, SubscriberDaten daten) {
		if(this.startTime == 0) {
			this.startTime = daten.time;
		}
		this.movement(daten.time);
	}
	
	private void movement(Integer timeNow) {
		Integer fieldsMoved = (int) (timeNow - this.startTime) / Configuration.carSpeed;

		if(this.leftToRight) {
			this.positionX = 1 + fieldsMoved;
		} else {
			this.positionX = Configuration.xFields - fieldsMoved;
		}
		this.checkLeftStreet();
		
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
	}
  

	public Integer getId() {
		return this.id;
	}
	public Integer getPositionX() {
		return this.positionX;
	}
}
