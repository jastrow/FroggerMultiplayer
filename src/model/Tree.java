package model;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Tree implements SubscriberInterface {
	
	private int id;
	private Boolean leftToRight = false;	// Baumstamm fliesst von links nach rechts (ansonsten andersrum)
	private Integer positionX;
	private Integer positionY;
	private int lastMovement;
	private int length;
	
	
	/**
	 * 
	 * @param id
	 * @param positionX
	 * @param positionY
	 */
	public Tree(int id, Integer positionX, Integer positionY, Integer length, Boolean leftToRight) {
		super();
		this.id = IdCounter.getId();
		this.positionX = positionX;
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		if(!leftToRight) {
			this.positionX = Configuration.xFields;
		}
		this.lastMovement = 0;
		this.length = length;
		
		Observer.add("time", this);
		this.sendObserver("new");
	}
	
	public Integer getId() {
		return this.id;
	}

	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger == "time") {
			this.movement(daten.time);
		}
	}
	
	//Check position
	private void movement(Integer timeNow) {
		Integer timeDif = timeNow - this.lastMovement;
		if(timeDif >= Configuration.treeSpeed) {
			if(this.leftToRight) {
				this.positionX++;
			} else {
				this.positionX--;
			}
			this.lastMovement += Configuration.treeSpeed;
			if(this.checkInGame()) {
				this.sendObserver("move");
			} else {
				this.sendObserver("delete");
			}
		}
		
	}
	
	public void sendObserver(String typ) {
		SubscriberDaten data = new SubscriberDaten();
		data.id 		= this.id;
		data.name 		= "Tree";
		data.xPosition 	= this.positionX;
		data.yPosition 	= this.positionY;
		data.typ 		= typ;
		data.length		= this.length;
		Observer.trigger("tree", data);		
	}
	
	public Boolean checkInGame() {
		return true;
	}

}
