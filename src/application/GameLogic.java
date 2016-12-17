package application;

import java.util.List;

public class GameLogic {

	////////////////
	// Attributes //
	////////////////
	
	// HINWEIS: Spielfeld - ganz oben links beginnt zählung: Feld1 = 0,0
	// ALLE bewegten Bildelemente machen ganze-Feld-Sprünge (nicht fließend, außer Frosch?)
	
	public Integer xFields = 19;
	public Integer yFields = 12;
	public Integer[] streetLines = {1,2,9,10}; // Y-Positions (num of = length)
	public Integer[] riverLines = {4,5,6,7}; // Y-Positions (num = length)
	public Integer[] frogStartPosition = {11,9};
	
	public TimeMachine timer; // millisecounds
	public Integer timeEnd = 300; //sec
	
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
		this.frogPlayer1 = new Frog();
		this.frogPlayer2 = new Frog();
		this.streets = new Streets(this.streetLines);
		this.rivers = new River(this.riverLines);
		this.timer = new TimeMachine(this.timeEnd);
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
