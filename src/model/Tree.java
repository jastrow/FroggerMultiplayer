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
	private int startTime = 0;


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
		this.setLength(length);
		//Observer.trigger("stopGame", new SubscriberDaten());
		System.out.println("ID " + this.id);
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
		if(this.startTime!=0){
			this.startTime = timeNow;
			}
		Integer fieldsMoved = (int) (timeNow - this.startTime) / Configuration.treeSpeed;

		Integer lastPositionX = this.positionX;
			if(this.leftToRight) {
				this.positionX = 1-length + fieldsMoved; // TODO sollte 0 sein
			} else {
				this.positionX = Configuration.xFields - fieldsMoved; // TODO -1 sollte raus
			}
			// Prüfung und Meldung nur, wenn sich die Position verändert hat.
			if(lastPositionX != this.positionX) {
				this.checkLeftTree();
			}
	}


	public void checkLeftTree() {
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
		data.name 		= "Tree";
		data.xPosition 	= this.positionX;
		data.yPosition 	= this.positionY;
		data.typ 		= typ;
		data.length		= this.getLength();
		Observer.trigger("tree", data);

		if(typ == "delete") {
			Observer.removeMe(this);
			//System.out.println("Deleted");
		}
	}

	public Boolean checkInGame() {
		return true;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
