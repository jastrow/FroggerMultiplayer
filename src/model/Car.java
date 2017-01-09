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
	
	private String log = "";
	
	public Car (Boolean leftToRight, Integer positionY) {
		this.id = IdCounter.getId();
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		if(!leftToRight) {
			this.positionX = Configuration.xFields + 1;
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
		Integer lastPositionX = this.positionX;

		if(this.leftToRight) {
			this.positionX = 1 + fieldsMoved; // TODO sollte 0 sein
		} else {
			this.positionX = Configuration.xFields - fieldsMoved - 1; // TODO -1 sollte raus
			// System.out.println(this.id+" "+this.positionX);
		}
		
		// Prüfung und Meldung nur, wenn sich die Position verändert hat.
		if(lastPositionX != this.positionX) {
			this.log = this.log + " " + this.positionX;
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
		if(typ == "delete") {
			//System.out.println("Car"+this.id+" "+this.log);
		}
		this.sendObserver(typ);
	}
	
	public void sendObserver(String typ) {
		
		SubscriberDaten data = new SubscriberDaten();
		data.id 			= this.id;
		data.name 			= "Car";
		data.xPosition 		= this.positionX;
		data.yPosition 		= this.positionY;
		data.typ 			= typ;
		data.leftToRight 	= this.leftToRight;
		Observer.trigger("car", data);
		
		//System.out.println(data.toString());
		if(typ == "delete") {
			Observer.removeMe(this);
		}
	}
  

	public Integer getId() {
		return this.id;
	}
	public Integer getPositionX() {
		return this.positionX;
	}

	public Integer getPositionY() {
		return this.positionY;
	}
}
