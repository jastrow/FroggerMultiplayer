package application;

import java.util.List;

import controller.*;
import model.*;

public class GameLogic implements SubscriberInterface {

	////////////////
	// Attributes //
	////////////////

	// HINWEIS: Spielfeld - ganz oben links beginnt zählung: Feld1 = 1,1
	// ALLE bewegten Bildelemente machen ganze-Feld-Sprünge (nicht fließend, außer Frosch?)



	private TimeMachine timer; // millisecounds
	private Frog frogPlayer1;

	private Streets streets;
	private Rivers rivers;

	private SceneController scene;


	/////////////
	// Methods //
	/////////////

	public GameLogic() {
		this.streets = new Streets();
		this.rivers = new Rivers();
		this.timer = new TimeMachine();
		this.frogPlayer1 = new Frog(this.rivers, this.streets);

		// Observer anmeldung
		Observer.add("start", this);

	}

	public void calling(String trigger, SubscriberDaten daten) {
		switch(trigger) {
			case "start": {
				this.timer.start(); 
				break;
			}
			default: break;
		}
	}

	public void setScene(SceneController scene) {
		this.scene = scene;
	}
	/**
	 * Resets all Game Parameters.
	 */
	public void resetGame() {

	}



}
