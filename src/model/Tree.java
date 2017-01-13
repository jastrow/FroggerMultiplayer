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
	private Integer length;
	private Integer startTime = 0;


	/**
	 *
	 * @param id
	 * @param positionX
	 * @param positionY
	 */
	public Tree(Integer positionX, Integer positionY, Integer length, Boolean leftToRight) {
		System.out.println("initialized car");
		this.id = IdCounter.getId();
		this.positionX = positionX;
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		if(!leftToRight) {
			this.positionX = Configuration.xFields;
		}
		this.length = length;
		Observer.add("time", this);
		this.sendObserver("new");
	}

	public Integer getId() {
		return this.id;
	}
	public Integer getPositionX() {
		return this.positionX;
	}

	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger == "time") {
			this.movement(daten.time);
		}
	}

	//Check position
	private void movement(Integer timeNow) {
		if(this.startTime == 0){
			this.startTime = timeNow;
		} else if(this.startTime.compareTo(timeNow) > 0) {
				this.sendObserver("delete");
		} else {
		
			Integer fieldsMoved = (int) (timeNow - this.startTime) / Configuration.treeSpeed;
	
			Integer lastPositionX = this.positionX;
			if(this.leftToRight) {
				this.positionX = 1 - length + fieldsMoved; // TODO sollte 0 sein
			} else {
				this.positionX = Configuration.xFields - fieldsMoved; // TODO -1 sollte raus
			}
			if(lastPositionX != this.positionX) {
				this.checkLeftTree();
			}
		}
	}


	public void checkLeftTree() {
		String typ = "move";
		if(this.leftToRight) {
			if(this.positionX > Configuration.xFields) {
				typ = "delete";
			}
		} else {
			if((this.positionX + this.length - 1) < 1) {
				typ = "delete";
			}
		}
		this.sendObserver(typ);
	}

	public void sendObserver(String typ) {
		SubscriberDaten data = new SubscriberDaten();
		data.id 		= this.id;
		data.name 		= "Tree";
		data.xPosition 	= this.positionX;
		data.yPosition 	= this.positionY;
		data.typ 		= typ;
		data.length		= this.length;
		data.leftToRight= this.leftToRight;

		Observer.trigger("tree", data);

		if(typ == "delete") {
			Observer.removeMe(this);
		}

	}

	public Boolean checkInGame() {
		return true;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
