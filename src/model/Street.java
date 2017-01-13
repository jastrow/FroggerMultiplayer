package model;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Street implements SubscriberInterface{

	private Integer positionY; // Position der Straße auf y-Feldern
	public Queue<Car> cars = new ConcurrentLinkedQueue<Car>();
	private Boolean leftToRight = false;	// Autos fahren von links nach rechts (ansonsten andersrum)
	private Car lastCar;

	public Street(Integer position) {
		this.positionY = position;
		
		// Fahrrichtung abwechselnd
		if((this.positionY % 2) == 0) {
			this.leftToRight = true;
		}
		
		// Anmeldung Observer
		Observer.add("time", this);
		Observer.add("car", this);
		Observer.add("start", this);
		Observer.add("resetGame", this);
	}

	/**
	 * Subscriber Schnittstelle.
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
			this.randomCar();
		}
		
		if(trigger.equals("resetGame")) {
			this.reset();
		}
		
		if(trigger.equals("start")) {
			this.startRandomCars();
		}
		

	}
	
	public void reset() {
		for(Car car: this.cars) {
			Observer.removeMe(car);
		}
		this.cars.clear();
		//this.showInConsole();
	}	
	
	public void startRandomCars() {
		Integer maxTime = Configuration.xFields * Configuration.carSpeed;
		Integer startTime;
		Car newLastCar = null;
		Integer lastCarTime = 0;
		Integer posX;
		Boolean collision = false;
		
		while(this.cars.size() < 3) {
			startTime = (new Random()).nextInt(maxTime) * -1;
			posX = (int) startTime / Configuration.carSpeed * -1;
			if(!this.leftToRight) {
				posX = Configuration.xFields - posX;
			}
			
			collision = false;
			for(Integer i = (posX-1); i <= (posX+2); i++) {
				if(this.collisionCheck(i, this.positionY)) {
					collision = true;
					break;
				}
			}
			if(collision) { continue; }
			
			Car neu = new Car(this.leftToRight, this.positionY, posX, startTime);
			this.cars.add(neu);

			if(startTime < lastCarTime) {
				lastCarTime = startTime;
				newLastCar = neu;
			}
		}

		this.lastCar = newLastCar;
	}
	
	/**
	 * Fortschritt der Gamezeit. 
	 * Zufallsgenerator, ob ein neues Auto auftaucht.
	 * @param newTime
	 */
	public void randomCar() {
		if(this.cars.isEmpty()) {
			Car neu = new Car(this.leftToRight, this.positionY);
			this.cars.add(neu);
			this.lastCar = neu;
		} else if(this.cars.size() < Configuration.carMaxPerStreet) {
			Integer lastDistance = this.lastCarDistance();
			if(lastDistance >= 3) {
				if(this.random(lastDistance)) {
					Car neu = new Car(this.leftToRight, this.positionY);
					this.cars.add(neu);
					this.lastCar = neu;
				}
			}
		}
	}

	
	/**
	 * Ermittelt ob ein Auto erstellt werden soll.
	 * Umso weiter sich das letzte Auto vom Start entfernt hat,
	 * desto höher wird die Wahrscheinlichkeit, dass ein neues auftaucht. 
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
	 * @return
	 */
	public Integer lastCarDistance() {
		Integer distance = null;
		if(this.lastCar == null) {
			return null;
		}
		if(this.leftToRight) {
			distance = this.lastCar.getPositionX() - 1;
		} else {
			distance = Configuration.xFields - (this.lastCar.getPositionX() + 1); 
		}
		return distance;
	}
	
	/**
	 * Ein Auto hat die Straße verlassen.
	 * @param id
	 */
	public void carLeftStreet(Integer carid) {
		for(Car car: this.cars) {
			if(car.getId().equals(carid)) {
				this.cars.remove(car);
				break;
			}
		}
	}
	
	public boolean collisionCheck(Integer positionX, Integer positionY2) {
		for(Car car: this.cars) {
			if(
				car.getPositionX()     == positionX || 
				(car.getPositionX()+1 ) == positionX 
			) {
				return true;
			}
		}	
		return false;
	}
	
	public Integer getPositionY() {
		return this.positionY;
	}
	
	
	
	/**
	 * Nur für Ausgabetests
	 */
	public void  showInConsole() {
		System.out.println(
			this.showStreet()
		);
	}
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
	public Boolean checkPosition(int p) {
		for(Car car: this.cars) {
			if(car.getPositionX() == p) {
				return true;
			}
		}	
		return false;
	}

}
