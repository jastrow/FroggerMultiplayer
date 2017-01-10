package model;

import java.util.ArrayList;
import java.util.List;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;

public class Street implements SubscriberInterface{

	///////////////
	// Attribute //
	///////////////

	private Integer positionY; // Position der Straße auf y-Feldern
	private List<Car> cars = new ArrayList<Car>(); // Alle fahrenden Autos
	private Integer lastTime = 0;	// Gamezeit in Millisekunden seit das letzte Auto auftauchte
	private Boolean leftToRight = false;	// Autos fahren von links nach rechts (ansonsten andersrum)
	
	/////////////////
	// Konstruktor //
	/////////////////

	public Street(Integer position) {
		this.positionY = position;
		
		// Fahrrichtung zufall
//		Integer direction = (int)(Math.random() * 2);
//		if(direction >= 1) {
//			this.leftToRight = true;
//		}
		// Fahrrichtung abwechselnd
		if((this.positionY % 2) == 0) {
			this.leftToRight = true;
		}
		
		// Anmeldung Observer
		Observer.add("time", this);
		Observer.add("car", this);
		Observer.add("start", this);
	}

	//////////////
	// Methoden //
	//////////////

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
		} else if(trigger == "time") {
			this.randomCar();
//			this.showInConsole();
		}
		if(trigger == "start") {
			for(Car car: this.cars) {
				Observer.removeMe(car);
			}
			this.cars.clear();
		}
	}
	
	/**
	 * Fortschritt der Gamezeit. 
	 * Zufallsgenerator, ob ein neues Auto auftaucht.
	 * @param newTime
	 */
	public void randomCar() {
		// Nur wenn nicht die max Anzahl Autos auf der Straße sind
		if(this.cars.size() < Configuration.carMaxPerStreet) {
			// Wenn das letzte Auto mindestens 3 Felder weiter ist
			Integer lastDistance = this.lastCarDistance();
			// Null noch kein Auto auf der Strecke war (50/50 Chance)
			if(lastDistance == null) {
				lastDistance = (int) Configuration.xFields / 2;
			}
			if(lastDistance >= 3) {
				// Zufallsgenerator ob neues Auto
				if(this.random(lastDistance)) {
					Car neu = new Car(this.leftToRight, this.positionY);
					this.cars.add(neu);
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
		Car car = getLastCar();
		if(car == null) {
			return null;
		}
		if(this.leftToRight) {
			distance = car.getPositionX() - 1;
		} else {
			distance = Configuration.xFields - (car.getPositionX() + 1); 
		}
		return distance;
	}
	
	/**
	 * Das zuletzt gestartete Auto geben lassen.
	 * @return Car
	 */
	public Car getLastCar() {
		Integer lastId = 0;
		Car last = null;
		// Letztes Auto ermitteln
		for(Car car: this.cars) {
			if(car.getId() > lastId) {
				lastId = car.getId();
				last = car;
			}
		}
		return last;
	}
	
	public Car getCarById(Integer id) {
		for(Car car: this.cars) {
			if(car.getId() == id) {
				return car;
			}
		}
		return null;	
	}
	
	/**
	 * Ein Auto hat die Straße verlassen.
	 * @param id
	 */
	public void carLeftStreet(Integer id) {
		Car car = this.getCarById(id);
		if(car != null) {
			this.cars.remove(car);
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
		for(int i = 1; i <= Configuration.yFields; i++) {
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
