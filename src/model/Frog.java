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
	
	public Frog() {
		this.id = IdCounter.getId();
		// Startposition
		this.positionX = (int)(Configuration.xFields / 2);
		this.positionY = Configuration.yFields;
		this.facing = "n";
		
		Observer.add("key", this);
		
		this.triggerObserver("new");
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		switch(trigger) {
			case "key": this.move(data.typ);
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
