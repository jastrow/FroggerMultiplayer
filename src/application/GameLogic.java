package application;

import java.util.List;
import java.util.Observable;

import controller.*;
import model.*;

public class GameLogic extends Observable {

	////////////////
	// Attributes //
	////////////////
	
	// HINWEIS: Spielfeld - ganz oben links beginnt zählung: Feld1 = 0,0
	// ALLE bewegten Bildelemente machen ganze-Feld-Sprünge (nicht fließend, außer Frosch?)
	
	
	
	public TimeMachine timer; // millisecounds

	
	public Frog frogPlayer1;
	public Frog frogPlayer2;
	
	public Integer frog1zug = 0;
	public Integer frog2zug = 0;
	
	public List<Log> frogLog;
	
	public Streets streets;
	public River rivers;
	
	public SceneController scene;
	
	/////////////
	// Methods //
	/////////////
	
	public void GameLogic() {
		this.frogPlayer1 = new Frog(1,1,"Spieler1",null);
		this.frogPlayer2 = new Frog(2,2,"Spieler2",null);
		this.streets = new Streets(Configuration.streetLines);
		this.rivers = new River(Configuration.riverLines);
		this.timer = new TimeMachine(Configuration.timeEnd);
		
		// Observertest
		setChanged();
		Action info = new Action();
		notifyObservers(info);
	}
	
	public void setScene(SceneController scene) {
		this.scene = scene;
	}
	/**
	 * Resets alle Game Parameters.
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
