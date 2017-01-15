package application;

import java.util.List;

import controller.*;
import model.*;

public class GameLogic implements SubscriberInterface {

	private TimeMachine timer; // millisecounds
	private Frog frogPlayer1;

	private Streets streets;
	private Rivers rivers;
	private FlyFabric flyfabric;

	private SceneController scene;
	
	private Integer score;
	private Integer timeMali;


	public GameLogic() {
		this.streets = new Streets();
		this.rivers = new Rivers();
		this.flyfabric = new FlyFabric();
		this.frogPlayer1 = new Frog(this.rivers, this.streets);
		this.timer = new TimeMachine();

		// Startpuntke
		this.score = Configuration.timeEnd / 10;
		
		// Observer anmeldung
		Observer.add("start", this);
		Observer.add("win", this);
		Observer.add("flyeaten", this);
		Observer.add("stopGame", this);

	}

	public void calling(String trigger, SubscriberDaten daten) {
		switch(trigger) {
			case "start": {
				this.score = Configuration.timeEnd / 10;;
				this.timer.start(); 
				break;
			}
			case "win": {
				Observer.trigger("stopGame", new SubscriberDaten());
				SubscriberDaten setTime = new SubscriberDaten();
				setTime.time = this.timer.getTime();
				Observer.trigger("winTime", setTime);
				break;
			}
			case "flyeaten": {
				this.score += Configuration.flyEatenPoints;
				break;
			}
			case "stopGame": {
				this.score += (Configuration.timeEnd - this.timer.getTime()) / 10; 
				System.out.println(this.score);
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
