package model;

import java.util.ArrayList;
import java.util.List;

import application.Configuration;
import application.SubscriberDaten;
import application.SubscriberInterface;

/**
 * Klasse zur Erstellung der Fluesse in der Spielszene
 * 
 * @author Die UMeLs
 *
 */
public class Rivers implements SubscriberInterface {

	public List<River> riverlines = new ArrayList<River>();

	/**
	 * Konstruktor
	 *
	 *
	 */
	public Rivers() {
		for(Integer position: Configuration.riverLines) {
			this.riverlines.add(
				new River(position)
			);
		}
	}
	
	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	public void calling(String trigger, SubscriberDaten data) {
		this.showRiversInConsole();
	}

	/** 
	 * showRiversInConsole
	 * 
	 */
	public void showRiversInConsole() {
		for(River river: this.riverlines) {
			river.showInConsole();
		}
		System.out.println("\r\n");
	}
	
	/** 
	 * Kollisionspruefung Fluesse
	 *
	 * @param positionX / xPosition des Flusses in Spielraster
	 * @param positionY / yPosition des Flusses in Spielraster
	 * 
	 * @return Integer / Kollisionsergebnis
	 */
	public Integer collisionCheck(Integer positionX, Integer positionY) {
		for(River river: this.riverlines) {
			if(river.getPositionY() == positionY) {
				return river.collisionCheck(positionX);
			}
		}
		return -1;
	}

}