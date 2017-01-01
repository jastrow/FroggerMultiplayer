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
	private Frog frogPlayer2;
	
	private Integer frog1zug = 0;
	private Integer frog2zug = 0;
	
	private List<Log> frogLog;
	
	private Streets streets;
	private River rivers;
	
	private SceneController scene;
	
	private Boolean running; 
	
	/////////////
	// Methods //
	/////////////
	
	public GameLogic() {
		this.frogPlayer1 = new Frog(1,1,"Spieler1",null);
		this.frogPlayer2 = new Frog(2,2,"Spieler2",null);
		this.streets = new Streets();
		this.rivers = new River(Configuration.riverLines);
		this.timer = new TimeMachine(Configuration.timeEnd);
		
		// Observer anmeldung
		Observer.add("start", this);

		// Spielstarten
		this.timer.start();
		
	}
	
	public void calling(String trigger, SubscriberDaten daten) {
		switch(trigger) {
			case "start": System.out.println("Game started"); break;
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
	
	/**
	 * Frog Collision Check with Gamezone.
	 */
	public Boolean collisionCheck() {
		// Frage FrogPlayer1
		return false;
	}
	
	/**
	 * Frog Dead Question.
	 */
	public Boolean deathCheck() {
		// Frosch eh schon tot?
		// Frosch position abgleichen mit gefahren (auto, nichtBaum)
		return false;
	}
	
	public void Action(String actionType) {
		if(actionType == "moveUp") {
			
		} else if(actionType == "moveDown") {
		} else if(actionType == "moveLeft") {
		} else if(actionType == "moveRight") {
			
		}
	}
	

	
}
