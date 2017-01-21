package model;

import java.util.ArrayList;
import java.util.List;

import application.Configuration;

/**
 * Klasse zur Erstellung der Strassen in der Spielszene
 * 
 * @author Die UMeLs
 *
 */
public class Streets {

	public List<Street> streetlines = new ArrayList<Street>(); 
	
	/**
	 * Konstruktor
	 *
	 *
	 */
	public Streets() {
		for(Integer position: Configuration.streetLines) {
			this.streetlines.add(
				new Street(position)
			);
		}
	}
	
	/** 
	 * showStreetsInConsole
	 * 
	 */
	public void showStreetsInConsole() {
		for(Street street: this.streetlines) {
			street.showInConsole();
		}
		System.out.println("\r\n");
	}

	/** 
	 * Kollisionspruefung Strasse
	 *
	 * @param positionX / xPosition der Strasse in Spielraster
	 * @param positionY / yPosition der Strasse in Spielraster
	 * @param positionXend / positionXend
	 * 
	 * @return Integer / Kollisionsergebnis
	 */
	public boolean collisionCheck(Integer positionX, Integer positionXend, Integer positionY) {
		for(Street street: this.streetlines) {
			if(street.getPositionY().compareTo(positionY) == 0) {
				if(street.collisionCheck(positionX, positionXend, positionY)) {
					return true;
				}
			}
		}
		return false;
	}

	
}
