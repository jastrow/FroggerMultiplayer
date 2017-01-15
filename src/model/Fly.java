package model;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Fly implements SubscriberInterface {

	private Integer id;
	private Integer positionX; 
	private Integer positionY;
	private Integer flyOnTreeId;
	private Boolean leftToRight;
	
	public Fly(Integer x, Integer y, Integer id, Boolean direction) {
		this.id = IdCounter.getId();
		this.positionX = x;
		this.positionY = y;
		this.flyOnTreeId = id;
		this.leftToRight = direction; 
		
		Observer.add("tree", this);
		this.triggerObserver("new");
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "tree") {
			this.moveMe(data);
		}
	}
	
	private void moveMe(SubscriberDaten data) {
		Boolean moved = false;
		if(data.id.equals(this.flyOnTreeId) && data.typ == "move") {
			if(data.leftToRight) {
				this.positionX++;
				moved = true;
			} else {
				this.positionX--;
				moved = true;
 			}
		}
		if(moved) {
			this.triggerObserver("move");
			this.checkForLeaving();
		}
	}
	
	private void checkForLeaving() {
		if(this.leftToRight && this.positionX >= Configuration.xFields) {
			this.triggerObserver("delete");
		}
		if(!this.leftToRight && this.positionX <= 1) {
			this.triggerObserver("delete");
		}
	}
	
	public void triggerObserver(String typ) {
		SubscriberDaten data = new SubscriberDaten();
		data.name = "Fly";
		data.id = this.id;
		data.xPosition = this.positionX;
		data.yPosition = this.positionY;
		data.typ = typ;
		Observer.trigger("fly", data);
	}
	
	public Integer getId() {
		return this.id;
	}
	public Integer getX() {
		return this.positionX;
	}
	public Integer getY() {
		return this.positionY;
	}
	public Integer getFlyOnTreeId() {
		return this.flyOnTreeId;
	}
}
