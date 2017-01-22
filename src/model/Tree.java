package model;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

/**
 * Klasse definiert ein Baumstamm auf der SpielSzene.
 *
 * @author Die UMeLs
 *
 */
public class Tree implements SubscriberInterface {

	private Integer id;
	private Boolean leftToRight = false;	// Baumstamm fliesst von links nach rechts (ansonsten andersrum)
	private Integer positionX;
	private Integer positionY;
	private Integer positionXend;
	private Integer positionYend;
	private Integer length;
	private Integer startTime = 0;
	private Integer lastMoveDistanceX = 0;


	/**
	 * Konstruktor.
	 *
	 * @param leftToRight / gibt an in welche Richtung der Stamm schwimmt
	 * @param positionY / gibt die yPosition des Stammes im Spielfeldraster an
	 * @param positionX / gibt die xPosition des Stammes im Spielfeldraster an
	 * @param length / gibt die laenge des erstellten Stammes an
	 *
	 */
	public Tree(Integer positionX, Integer positionY, Integer length, Boolean leftToRight) {
		this.id = IdCounter.getId();
		this.positionX = positionX;
		this.positionY = positionY;
		this.length = length;
		this.positionXend = positionX + Configuration.xTree[(length-2)];
		this.positionYend = positionY + Configuration.yTree;

		this.leftToRight = leftToRight;
		if(!leftToRight) {
			this.positionX = Configuration.xGameZone;
		}
		Observer.add("time", this);
		this.sendObserver("new");
	}

	/**
	 * Methode zum ermitteln der ID des Stammes.
	 *
	 * @return Integer / ID des Stammes
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * Methode zum ermitteln der xPosition des Stammes im Spielraster.
	 *
	 * @return Integer / xPosition im Spielraster
	 *
	 */
	public Integer getPositionX() {
		return this.positionX;
	}

	/**
	 * Methode getPositionXend.
	 *
	 * @return Integer / positionXend
	 */
	public Integer getPositionXend() {
		return this.positionXend;
	}

	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger == "time") {
			this.movement(daten.time);
		}
	}

	/**
	 * Bewegung des Stammes.
	 *
	 * @param timeNow / aktuelle Zeit
	 */
	private void movement(Integer timeNow) {
		if(this.startTime == 0){
			this.startTime = timeNow;
		} else if(this.startTime.compareTo(timeNow) > 0) {
				this.sendObserver("delete");
		} else {

			Integer fieldsMoved = (int) (timeNow - this.startTime) / Configuration.treeSpeed;

			Integer lastPositionX = this.positionX;
			if(this.leftToRight) {
				this.positionX = 1 - Configuration.xTree[(this.length - 2)] + fieldsMoved;
			} else {
				this.positionX = Configuration.xGameZone - fieldsMoved;
			}
			this.lastMoveDistanceX = Math.abs(lastPositionX - this.positionX);
			this.positionXend = this.positionX + Configuration.xTree[(this.length - 2)];;
			this.checkLeftTree();
		}
	}

	/**
	 * Methode prueft die Position des Stammes auf dem Fluss.
	 *
	 */
	public void checkLeftTree() {
		String typ = "move";
		if(this.leftToRight) {
			if(this.positionX.compareTo(Configuration.xGameZone) > 0) {
				typ = "delete";
			}
		} else {
			if(this.positionXend < 1) {
				typ = "delete";
			}
		}
		this.sendObserver(typ);
	}

	/**
	 * Methode sendent einen Triggers an den Observer.
	 *
	 * @param typ / Art des Triggers
	 *
	 */
	public void sendObserver(String typ) {
		SubscriberDaten data = new SubscriberDaten();
		data.id 			= this.id;
		data.name 			= "Tree";
		data.xPosition 		= this.positionX;
		data.yPosition 		= this.positionY;
		data.xPositionEnd 	= this.positionXend;
		data.yPositionEnd 	= this.positionYend;
		data.typ 			= typ;
		data.length			= this.length;
		data.leftToRight	= this.leftToRight;
		data.lastMovementDistanceX = this.lastMoveDistanceX;

		Observer.trigger("tree", data);

		if(typ == "delete") {
			Observer.removeMe(this);
		}

	}

	/**
	 * Methode checkInGame.
	 *
	 * @return Boolean
	 */
	public Boolean checkInGame() {
		return true;
	}

	/**
	 * Methode ermittelt Laenge des Stammes.
	 *
	 * @return int / Stammlaenge
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * Methode setzt Stammlaenge.
	 *
	 * @param length / lange des Stammes
	 *
	 */
	public void setLength(int length) {
		this.length = length;
	}

}
