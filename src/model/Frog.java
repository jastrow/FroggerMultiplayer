package model;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Frog implements SubscriberInterface {

	private Integer id;
	private Integer positionX; 	// GameRaster X
	private Integer positionY; 	// GameRaster Y
	private String 	facing; 	// Facing n,s,w,o
	
	private Rivers rivers = null;	// Hat nur Frosch 1
	private Streets streets = null;	// Hat nur Frosch 1
	
	public Frog() {
		this.initializeFrog();
	}
	public Frog(Rivers rivers, Streets streets) {
		this.initializeFrog();
		this.rivers = rivers;
		this.streets = streets;
	}
	
	private void initializeFrog() {
		this.id = IdCounter.getId();
		// Startposition
		this.positionX = (int)(Configuration.xFields / 2);
		this.positionY = Configuration.yFields;
		this.facing = "n";
		
		Observer.add("key", this);
		Observer.add("tree", this);
		Observer.add("car", this);
		Observer.add("start", this);
		
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "key") {
			this.move(data.typ); 
		}
		if(trigger == "start") {
			this.triggerObserver("new");
		}
		
		// nur in frosch 1
		if(this.streets != null) {
			this.collisionCheck();
		}
	}
	
	private void move(String direction) {
		Integer newX = this.positionX;
		Integer newY = this.positionY;
		String newFacing = "";
		// Auswertung Tastatureingabe
		if(direction == "left" && this.positionX != 1) {
			newX = this.positionX - 1;
			newFacing = "w";
		} else if(direction == "right" && this.positionX != Configuration.xFields) {
			newX = this.positionX + 1;
			newFacing = "o";
		} else if(direction == "up" && this.positionY != 1) {
			newY = this.positionY - 1;
			newFacing = "n";
		} else if(direction == "down" && this.positionY != Configuration.yFields) {
			newY = this.positionY + 1;
			newFacing = "s";
		}
		// Wenn er sich bewegt hat
		if(newX != this.positionX || newY != this.positionY) {
			this.positionX = newX;
			this.positionY = newY;
			this.facing = newFacing;
			this.triggerObserver("move");
		}
	}
	
	public void collisionCheck() {
		// 1. Kollision mit Cars
		if(this.streets == null) { 
			System.out.println("keine streets in frog"); 
		} else {
			if(this.streets.collisionCheck(
					this.positionX, 
					this.positionY)
					) {
				this.triggerObserver("killed");
				Observer.trigger("stopGame", new SubscriberDaten());
			}
		}
		// 2. Kollision mit Trees
	}
	
	public void triggerObserver(String typ) {
		SubscriberDaten data = new SubscriberDaten();
		data.name = "Frog";
		data.id = this.id;
		data.xPosition = this.positionX;
		data.yPosition = this.positionY;
		data.facing = this.facing;
		data.typ = typ;
		Observer.trigger("frog", data);
		System.out.println(data.toString());
	}
	
}
