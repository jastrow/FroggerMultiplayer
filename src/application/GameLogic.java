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


	public GameLogic() {
		this.streets = new Streets();
		this.rivers = new Rivers();
		this.flyfabric = new FlyFabric();
		this.frogPlayer1 = new Frog(this.rivers, this.streets);
		this.timer = new TimeMachine();

		// Observer anmeldung
		Observer.add("start", this);
		Observer.add("win", this);

	}

	public void calling(String trigger, SubscriberDaten daten) {
		switch(trigger) {
			case "start": {
				this.timer.start(); 
				break;
			}
			case "win": {
				Observer.trigger("stopGame", new SubscriberDaten());
				SubscriberDaten setTime = new SubscriberDaten();
				setTime.time = this.timer.getTime();
				Observer.trigger("winTime", setTime);
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
