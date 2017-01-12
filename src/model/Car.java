package model;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Car implements SubscriberInterface {

	private Integer id;
	private Boolean leftToRight = false;
	private Integer positionX = 1;
	private Integer positionY; 
	private Integer startTime = 0;
		
	public Car (Boolean leftToRight, Integer positionY) {
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		this.positionX = -1;
		if(!this.leftToRight) {
			this.positionX = Configuration.xFields + 1;
		}
		this.initialize();
	}
	public Car (Boolean leftToRight, Integer positionY, Integer positionX, Integer startTime) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		this.startTime = startTime;
		this.initialize();
	}
	private void initialize() {
		this.id = IdCounter.getId();
		Observer.add("time", this);
		this.sendObserver("new");
	}
	
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger == "time") {
			if(this.startTime == 0) {
				this.startTime = daten.time;
			}
			this.movement(daten.time);
		}
	}
	
	private void movement(Integer timeNow) {
		Integer fieldsMoved = (int) (timeNow - this.startTime) / Configuration.carSpeed;
		Integer lastPositionX = this.positionX;

		if(this.leftToRight) {
			this.positionX = 1 + fieldsMoved; // TODO sollte 0 sein
		} else {
			this.positionX = Configuration.xFields - fieldsMoved - 1; // TODO -1 sollte raus
		}
		
		// Prüfung und Meldung nur, wenn sich die Position verändert hat.
		if(lastPositionX != this.positionX) {
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
		data.id 			= this.id;
		data.name 			= "Car";
		data.xPosition 		= this.positionX;
		data.yPosition 		= this.positionY;
		data.typ 			= typ;
		data.leftToRight 	= this.leftToRight;
		Observer.trigger("car", data);
		
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
	public void setPositionX(Integer x) {
		this.startTime = x * Configuration.carSpeed * (-1);
		this.positionX = x;
	}

	public Integer getPositionY() {
		return this.positionY;
	}
	
	public void setStartTime(Integer zeit) {
		this.startTime = zeit;
	}
}
