package model;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

/**
 * Klasse zur Definition einer Fliege
 * 
 * @author Die UMeLs
 *
 */
public class Fly implements SubscriberInterface {

	private Integer id;
	private Integer positionX; 
	private Integer positionY;
	private Integer flyOnTreeId;
	private Boolean leftToRight;
	
	/**
	 * Konstruktor
	 *
	 * @param x / xPosition der Fliege auf dem Spielraster
	 * @param y / yPosition der Fliege auf dem Spielraster
	 * @param id / ID der Fliege
	 * @param direction / Richtung in welche sich die Fliege bewegt
	 *
	 */
	public Fly(Integer x, Integer y, Integer id, Boolean direction) {
		this.id = IdCounter.getId();
		this.positionX = x;
		this.positionY = y;
		this.flyOnTreeId = id;
		this.leftToRight = direction; 
		
		Observer.add("tree", this);
		this.triggerObserver("new");
	}
	
	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "tree") {
			this.moveMe(data);
		}
	}
	
	/** 
	 * bewege die Fliege
	 *
	 * @param data / DatenObjekt mit Positionsdaten und ID der Fliege
	 * 
	 */
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
	
	/** 
	 * Positionspruefung und wenn Rand erreicht loeschen der Fliege
	 * 
	 */
	private void checkForLeaving() {
		if(this.leftToRight && this.positionX >= Configuration.xFields) {
			this.triggerObserver("delete");
		}
		if(!this.leftToRight && this.positionX <= 1) {
			this.triggerObserver("delete");
		}
	}
	
	/** 
	 * senden eines Triggers an den Observer
	 *
	 * @param typ / Art des Triggers
	 * 
	 */
	public void triggerObserver(String typ) {
		SubscriberDaten data = new SubscriberDaten();
		data.name = "Fly";
		data.id = this.id;
		data.xPosition = this.positionX;
		data.yPosition = this.positionY;
		data.typ = typ;
		Observer.trigger("fly", data);
	}
	
	/** 
	 * ermitteln der ID der Fliege
	 *
	 * @return Integer / ID der Fliege
	 */
	public Integer getId() {
		return this.id;
	}
	
	/** 
	 * ermitteln der xPosition der Fliege im Spielraster
	 *
	 * @return Integer / xPosition im Spielraster
	 * 
	 */
	public Integer getX() {
		return this.positionX;
	}
	
	/** 
	 * ermitteln der yPosition der Fliege im Spielraster
	 *
	 * @return Integer / yPosition im Spielraster
	 * 
	 */
	public Integer getY() {
		return this.positionY;
	}
	
	/** 
	 * getFlyOnTreeId
	 *
	 * @return Integer / ID 
	 */
	public Integer getFlyOnTreeId() {
		return this.flyOnTreeId;
	}
}
