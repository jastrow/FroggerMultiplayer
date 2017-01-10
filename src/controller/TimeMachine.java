package controller;

import application.*;
//import java.util.concurrent.TimeUnit;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javafx.application.Platform;
//import javafx.scene.control.ListView;

public class TimeMachine implements Runnable, SubscriberInterface {
//public class TimeMachine implements SubscriberInterface {

	private Thread t;
	private Boolean running;
	private Integer timeLog;
	private Integer timeEnd;
	
	public TimeMachine() {
		this.timeEnd = Configuration.timeEnd;
		this.reset();
		Observer.add("stopGame", this);
	}
	
	public Integer getTime() {
		return this.timeLog;
	}
	
	public void reset() {
		this.running = false;
		this.timeLog = 0;
	}
	
	public void start() {
		this.timeLog = 0;
		this.running = true;
		this.t = new Thread(this);
		this.t.start();
	}

	
	public void stop() {
		this.running = false;
	}
	
	public void restart() {
		this.reset();
		this.start();
	}
	
	@Override
	public void run() {
		while(this.running) {
			try {
				Thread.sleep( Configuration.timeSteps );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.timeLog += Configuration.timeSteps;
			if(this.timeLog >= this.timeEnd) {
				this.running = false;
			} 
			SubscriberDaten daten = new SubscriberDaten();
			daten.name = "time";
			daten.time = this.timeLog;
			
			Platform.runLater(new Runnable() {
				public void run() {
					Observer.trigger("time", daten);
				}
			});
		}
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "stopGame") {
			this.stop();
		}
	}
	

}
