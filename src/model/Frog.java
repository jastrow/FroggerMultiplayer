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
	private Boolean killed;		// Frosch schon tot
	private Integer frogOnTreeId; // Tree-ID des Baumes auf dem der Frosch sitzt
	
	private Rivers rivers = null;	// Hat nur Frosch 1
	private Streets streets = null;	// Hat nur Frosch 1
	
	public Frog() {
		this.initializeFrog();
	}
	public Frog(Rivers rivers, Streets streets/*, River trees*/) {
		this.initializeFrog();
		this.rivers = rivers;
		this.streets = streets;

		Observer.add("key", this);
		Observer.add("tree", this);
		Observer.add("car", this);
		Observer.add("start", this);
	}
	
	private void initializeFrog() {
		this.id = IdCounter.getId();
		// Startposition
		this.positionX = (int)(Configuration.xFields / 2);
		this.positionY = Configuration.yFields;
		this.facing = "n";
		this.killed = false;
		this.frogOnTreeId = -1;
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "key" && !this.killed) {
			this.move(data.typ); 
		}
		if(trigger == "start") {
			this.initializeFrog();
			this.triggerObserver("new");
		}
		if(trigger == "tree") {
			if(data.id.equals(this.frogOnTreeId)) {
				if(data.leftToRight) {
					this.move("right");
				} else {
					this.move("left");
				}
			}
		}
		if(trigger == "car" || trigger == "tree") {
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
			
			if(this.positionY == 1) {
				this.triggerObserver("win");
			}
		}
	}
	
	public void collisionCheck() {
		// 1. Kollision mit Cars
		if(this.streets != null) { 
			if(this.streets.collisionCheck(
					this.positionX, 
					this.positionY)
					) {
				this.killed = true;
				this.triggerObserver("killed");
				Observer.trigger("stopGame", new SubscriberDaten());
			}
		}
		
		// 2. Kollision mit Baum
		if(this.rivers != null) { 
			this.frogOnTreeId = this.rivers.collisionCheck(this.positionX, this.positionY);
			if(this.frogOnTreeId == 0) {
				this.killed = true;
				this.triggerObserver("killed");
				Observer.trigger("stopGame", new SubscriberDaten());
			}
		}

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
	}
	
}
