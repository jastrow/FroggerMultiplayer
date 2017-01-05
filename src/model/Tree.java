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
	
	
	/**
	 * 
	 * @param id
	 * @param positionX
	 * @param positionY
	 */
	public Tree(int id, Integer positionX, Integer positionY) {
		super();
		this.id = IdCounter.getId();
		this.positionX = positionX;
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		if(!leftToRight) {
			this.positionX = Configuration.xFields;
		}
		this.lastMovement = 0;
		Observer.add("time", this);
		this.sendObserver("new");
	}
	
	//
	public int getId() {
		return this.id;
	}
	
	//
	public Boolean getLeftToRight() {
		return this.leftToRight;
	}
	
	//
	public int getPositionX() {
		return this.positionX;
	}
	
	//
	public int getPositionY() {
		return this.positionY;
	}
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		// TODO Auto-generated method stub		
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
			this.checkLeftRiver();
		}
		
	}
	
	public void checkLeftRiver() {
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
		//this.sendObserver(typ);
	}
	
	
	public void sendObserver(String typ) {
		SubscriberDaten data = new SubscriberDaten();
		data.id 		= this.id;
		data.name 		= "Wood";
		data.xPosition 	= this.positionX;
		data.yPosition 	= this.positionY;
		data.typ 		= typ;
		Observer.trigger("wood", data);		
	}

}
