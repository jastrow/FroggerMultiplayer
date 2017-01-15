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
	private Integer fliesEaten;
	private Boolean win;

	public GameLogic() {
		this.streets = new Streets();
		this.rivers = new Rivers();
		this.flyfabric = new FlyFabric();
		this.frogPlayer1 = new Frog(this.rivers, this.streets);
		this.timer = new TimeMachine();
		
		// Observer anmeldung
		Observer.add("start", this);
		Observer.add("stopGame", this);
		Observer.add("flyeaten", this);
		Observer.add("frog", this);

	}

	public void calling(String trigger, SubscriberDaten daten) {
		switch(trigger) {
			case "start": {
				this.fliesEaten = 0;
				this.score = 0;
				this.win = false;
				this.timer.start(); 
				break;
			}
			case "flyeaten": {
				this.fliesEaten++;
				break;
			}
			case "stopGame": {
				this.getScore();
				break;
			}
			case "frog": {
				if(daten.typ.equals("win")) {
					this.win = true;
				}
			}
			default: break;
		}
	}

	public void setScene(SceneController scene) {
		this.scene = scene;
	}

	public void getScore() {
		Integer timeBonus = 0;
		Integer fliesBonus = 0;
		if(this.win) {
			timeBonus = (Configuration.timeEnd - this.timer.getTime()) / 10;
			fliesBonus = this.fliesEaten * Configuration.flyEatenPoints;
			this.score = timeBonus + fliesBonus;

			SubscriberDaten timeData = new SubscriberDaten();
			timeData.time = this.score;
			Observer.trigger("entry", timeData);
		}
		System.out.println("Zeitbonus: "+timeBonus);
		System.out.println("Fliegenbonus: "+fliesBonus+" ("+this.fliesEaten+")");
		System.out.println("HighScore: "+this.score);
	}

}
