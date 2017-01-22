package model;

import java.util.ArrayList;
import java.util.List;

import application.Configuration;

/**
 * Klasse zur Erstellung der Fluesse in der Spielszene.
 *
 * @author Die UMeLs
 *
 */
public class Rivers  {

	public List<River> riverlines = new ArrayList<River>();

	/**
	 * Konstruktor.
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


	/**
	 * Methode showRiversInConsole.
	 *
	 */
	public void showRiversInConsole() {
		for(River river: this.riverlines) {
			river.showInConsole();
		}
		System.out.println("\r\n");
	}

	/**
	 * Kollisionspruefung Fluesse.
	 *
	 * @param positionX / xPosition des Flusses in Spielraster
	 * @param positionXend / positionXend
	 * @param positionY / yPosition des Flusses in Spielraster
	 *
	 * @return Integer / Kollisionsergebnis
	 */
	public Integer collisionCheck(Integer positionX, Integer positionXend, Integer positionY) {
		for(River river: this.riverlines) {
			if(river.getPositionY().compareTo(positionY) == 0) {
				return river.collisionCheck(positionX, positionXend);
			}
		}
		return -1;
	}

}