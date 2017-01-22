package model;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

/**
 * Klasse zum erstellen einer Strasse auf der Spielflaeche und erzeugen 
 * der Autos auf der Strasse.
 * 
 * @author Die UMeLs
 *
 */
public class Street implements SubscriberInterface{

	private Integer positionY; // Position der Straße auf y-Feldern
	public Queue<Car> cars = new ConcurrentLinkedQueue<Car>();
	private Boolean leftToRight = false;	// Autos fahren von links nach rechts (ansonsten andersrum)
	private Car lastCar;
	private Integer nextRandomCarTime;

	/**
	 * Konstruktor.
	 *
	 * @param position / yPosition der Strasse im Spielfeldraster
	 *
	 */
	public Street(Integer position) {
		this.positionY = position;
		
		// Fahrrichtung abwechselnd
		if(((this.positionY / 50) % 2) == 0) {
			this.leftToRight = true;
		}
		
		// Anmeldung Observer
		Observer.add("time", this);
		Observer.add("car", this);
		Observer.add("start", this);
		Observer.add("resetGame", this);
	}

	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger == "car") {
			switch(daten.typ) {
				case "delete": 
					this.carLeftStreet(daten.id); 
					break;
				default: break;
			}
		}
		
		if(trigger == "time") {
			this.randomCar(daten);
		}
		
		if(trigger.equals("resetGame")) {
			this.reset();
		}
		
		if(trigger.equals("start")) {
			this.startRandomCars();
		}
		

	}
	
	/** 
	 * Methode loescht Auto.
	 * 
	 */
	public void reset() {
		for(Car car: this.cars) {
			Observer.removeMe(car);
		}
		this.cars.clear();
	}	
	
	/** 
	 * Methode startet Zufallsauto.
	 * 
	 */
	public void startRandomCars() {
		Integer maxTime = Configuration.xGameZone * Configuration.carSpeed;
		Integer startTime;
		Car newLastCar = null;
		Integer lastCarTime = 0;
		Integer posX;
		Integer posXend;
		
		while(this.cars.size() < 3) {
			startTime = (new Random()).nextInt(maxTime) * -1;
			posX = (int) startTime / Configuration.carSpeed * -1;
			if(!this.leftToRight) {
				posX = Configuration.xGameZone - posX;
			}	
			posXend = posX + Configuration.xCar;
			
			if( this.collisionCheck(posX, posXend, this.positionY)) {
				continue;
			}
			
			Car neu = new Car(this.leftToRight, this.positionY, posX, startTime);
			this.cars.add(neu);

			if(startTime < lastCarTime) {
				lastCarTime = startTime;
				newLastCar = neu;
			}
		}

		this.lastCar = newLastCar;
		this.nextRandomCar();
	}
	
	/**
	 * Fortschritt der Gamezeit. 
	 * Zufallsgenerator, ob ein neues Auto auftaucht.
	 * 
	 * @param data / Datenobjekt mit Identifikationsangeben zum Auto
	 * 
	 */
	public void randomCar(SubscriberDaten data) {
		if(this.cars.isEmpty()) {
			Car neu = new Car(this.leftToRight, this.positionY, data.time);
			this.cars.add(neu);
			this.lastCar = neu;
			this.nextRandomCar();
		} else if(this.cars.size() < Configuration.carMaxPerStreet &&
			this.nextRandomCarTime.compareTo(data.time) <= 0) {
			Car neu = new Car(this.leftToRight, this.positionY, data.time);
			this.cars.add(neu);
			this.lastCar = neu;
			this.nextRandomCar();
		}
	}

	/**
	 * Fortschritt der Gamezeit. 
	 * Zufallsgenerator, ob ein neues Auto auftaucht.
	 * 
	 */
	public void nextRandomCar() {
		this.nextRandomCarTime = (new Random()).nextInt(Configuration.carRandom);
		this.nextRandomCarTime += this.lastCar.getStartTime();
		this.nextRandomCarTime += Configuration.carSpeed * Configuration.xCar;
	}
	
	/**
	 * Ermittelt ob ein Auto erstellt werden soll.
	 * Umso weiter sich das letzte Auto vom Start entfernt hat,
	 * desto hoeher wird die Wahrscheinlichkeit, dass ein neues auftaucht. 
	 * 
	 * @param lastDistance Distanz des letzten Autos
	 * @return Boolean true für neues Auto
	 */
	public Boolean random(Integer lastDistance) {
		Boolean doit = false;
		Integer zufallszahl = (int) (Math.random() * Configuration.xFields);
		if(zufallszahl < lastDistance) {
			doit = true;
		}
		return doit;
	}
	
	/**
	 * Ermittelt die Distanz der Autos zum Anfang der Strecke,
	 * je nach dem ob links-rechts Verkehr oder umgekehrt ist.
	 * 
	 * @return distance / Abstand zum forderen Auto
	 */
	public Integer lastCarDistance() {
		Integer distance = null;
		if(this.lastCar == null) {
			return null;
		}
		if(this.leftToRight) {
			distance = this.lastCar.getPositionX();
		} else {
			distance = Configuration.xGameZone - this.lastCar.getPositionXend(); 
		}
		return distance;
	}
	
	/**
	 * Ein Auto hat die Straße verlassen.
	 * 
	 * @param carid / ID des betreffenden Autos
	 * 
	 */
	public void carLeftStreet(Integer carid) {
		for(Car car: this.cars) {
			if(car.getId().equals(carid)) {
				this.cars.remove(car);
				break;
			}
		}
	}
	
	/** 
	 * Kollisionspruefung.
	 *
	 * @param positionX / xPosition des Autos auf der Spielflaeche
	 * @param positionY2 / positionY2
	 * @param positionXend / positionXend
	 * 
	 * @return boolean / es gab eine/keine Kollission 
	 */
	public boolean collisionCheck(Integer positionX, Integer positionXend, Integer positionY2) {
		for(Car car: this.cars) {
			if(
				(positionX.compareTo(car.getPositionX()) >= 0 &&
				positionX.compareTo(car.getPositionXend()) <= 0)
				||
				(positionXend.compareTo(car.getPositionX()) >= 0 &&
				positionXend.compareTo(car.getPositionXend()) <= 0)
			) {
				return true;
			}
		}	
		return false;
	}
	
	/** 
	 * Methode zum ermitteln der yPosition der Strasse im Spiel.
	 *
	 * @return Integer / yPosition im Spiel
	 * 
	 */
	public Integer getPositionY() {
		return this.positionY;
	}
	
	
	
	/**
	 * Nur für Ausgabetests.
	 */
	public void  showInConsole() {
		System.out.println(
			this.showStreet()
		);
	}
	
	
	/** 
	 * Methode showStreet.
	 *
	 * @return String / showStreet
	 */
	public String showStreet() {
		String ASCIIstreet = String.format("%02d", this.positionY) + " ";
		for(int i = 1; i <= Configuration.xFields; i++) {
			if(this.checkPosition(i)) {
				ASCIIstreet += "*";
			} else {
				ASCIIstreet += "_";
			}
		}
		return ASCIIstreet;
	}
	
	/** 
	 * Positiosnspruefung Auto.
	 *
	 * @param p / xPosition eines Autos auf dem Spiel
	 * @return Boolean / Kollission
	 */
	public Boolean checkPosition(int p) {
		for(Car car: this.cars) {
			if(car.getPositionX() == p) {
				return true;
			}
		}	
		return false;
	}

}
