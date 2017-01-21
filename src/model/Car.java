package model;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

/**
 * definiert ein Auto auf der SpielSzene
 * 
 * @author Die UMeLs
 *
 */
public class Car implements SubscriberInterface {

	private Integer id;
	private Boolean leftToRight = false;
	private Integer positionX;
	private Integer positionY; 
	private Integer positionXend;
	private Integer positionYend; 
	private Integer startTime = 0;
		
	/**
	 * Konstruktor
	 *
	 * @param leftToRight / gibt an in welche Richtung das Auto faehrt
	 * @param positionY / gibt die yPosition des Autos im Spielfeldraster an 
	 *
	 */
	public Car (Boolean leftToRight, Integer positionY) {
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		this.positionX = Configuration.xCar * (-1);
		System.out.println(this.positionX);
		if(!this.leftToRight) {
			this.positionX = Configuration.xGameZone;
		}
		this.initialize();
	}
	/**
	 * Konstruktor
	 *
	 * @param leftToRight / gibt an in welche Richtung das Auto faehrt
	 * @param positionY / gibt die yPosition des Autos im Spielfeldraster an 
	 * @param positionX / gibt die xPosition des Autos im Spielfeldraster an 
	 * @param startTime / gibt den StartZeitpunkt der Erstellung des Autos an 
	 *
	 */
	public Car (Boolean leftToRight, Integer positionY, Integer positionX, Integer startTime) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.leftToRight = leftToRight;
		this.startTime = startTime;
		this.initialize();
	}
	/** 
	 * Initialisierung des Autos
	 * 
	 */
	private void initialize() {
		this.positionXend = this.positionX + Configuration.xCar;
		this.positionYend = this.positionY + Configuration.yElements;

		this.id = IdCounter.getId();
		Observer.add("time", this);
		this.sendObserver("new");
	}
	
	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger == "time") {
			if(this.startTime == 0) {
				this.startTime = daten.time;
			}
			this.movement(daten.time);
		}
	}
	
	/** 
	 * Bewegung des Autos
	 *
	 * @param timeNow / aktuelle Zeit
	 */
	private void movement(Integer timeNow) {
		Integer fieldsMoved = (int) (timeNow - this.startTime) / Configuration.carSpeed;

		if(this.leftToRight) {
			this.positionX = fieldsMoved - Configuration.xCar; 
		} else {
			this.positionX = Configuration.xGameZone - fieldsMoved - 1; 
		}
		this.positionXend = this.positionX + Configuration.xCar;
		this.checkLeftStreet();
		
	}
	
	/** 
	 * prueft die Position des Autos auf der Starsse
	 * 
	 */
	public void checkLeftStreet() {
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
	 * senden eines Triggers an den Observer
	 *
	 * @param typ / Art des Triggers
	 * 
	 */
	public void sendObserver(String typ) {
		
		SubscriberDaten data = new SubscriberDaten();
		data.id 			= this.id;
		data.name 			= "Car";
		data.xPosition 		= this.positionX;
		data.yPosition 		= this.positionY;
		data.typ 			= typ;
		data.leftToRight 	= this.leftToRight;
		Observer.trigger("car", data);
		
		if(typ == "delete") {
			Observer.removeMe(this);
		}
	}
  

	/** 
	 * ermitteln der ID des Autos
	 *
	 * @return Integer / ID des Autos
	 */
	public Integer getId() {
		return this.id;
	}
	
	
	/** 
	 * ermitteln der xPosition des Autos im Spielraster
	 *
	 * @return Integer / xPosition im Spielraster
	 * 
	 */
	public Integer getPositionX() {
		return this.positionX;
	}
	
	/** 
	 * ermitteln der xPosition des Autos im Spielraster
	 *
	 * @return Integer / xPosition im Spielraster
	 * 
	 */
	public Integer getPositionXend() {
		return this.positionXend;
	}
	
	/** 
	 * setzen der xPosition des Autos im Spielraster
	 *
	 * @param x / xPosition im Spielraster
	 * 
	 */
	public void setPositionX(Integer x) {
		this.startTime = x * Configuration.carSpeed * (-1);
		this.positionX = x;
	}

	/** 
	 * ermitteln der yPosition des Autos im Spielraster
	 *
	 * @return Integer / yPosition im Spielraster
	 * 
	 */
	public Integer getPositionY() {
		return this.positionY;
	}
	
	/** 
	 * Startzeit setzen
	 *
	 * @param zeit / Startzeit
	 * 
	 */
	public void setStartTime(Integer zeit) {
		this.startTime = zeit;
	}
}
