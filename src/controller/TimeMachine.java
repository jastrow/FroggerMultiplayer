package controller;

import application.*;
import javafx.application.Platform;

/**
 * Stellt einen Timer zur Synchronisierung der Ablaeufe zur Verfuegung
 * 
 * 
 * @author Die UMeLs
 *
 */
public class TimeMachine implements Runnable, SubscriberInterface {

	private Thread t;
	private Boolean running;
	private Integer timeLog;
	private Integer timeEnd;
	
	/**
	 * Konstruktor
	 *
	 */
	public TimeMachine() {
		this.timeEnd = Configuration.timeEnd;
		this.reset();
		Observer.add("stopGame", this);
		Observer.add("resetGame", this);
	}
	
	/** 
	 * gibt aktuelle Zeit zurueck
	 *
	 * @return Integer / aktuelle Zeit
	 */
	public Integer getTime() {
		return this.timeLog;
	}
	
	/** 
	 * zuruecksetzen des Timers
	 * 
	 */
	public void reset() {
		this.running = false;
		this.timeLog = 0;
	}
	
	/** 
	 * TimerThread starten
	 * 
	 */
	public void start() {
		this.running = true;
		this.timeLog = 0;
		if(this.t == null) {
			this.t = new Thread(this);
			this.t.start();
		} else if(!this.t.isAlive()) {
			this.t = new Thread(this);
			this.t.start();
		}
	}
	
	/** 
	 * Neustart Timer
	 * 
	 */
	public void restart() {
		this.reset();
		this.start();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(this.running) {
			try {
				Thread.sleep( Configuration.timeSteps );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			SubscriberDaten daten = new SubscriberDaten();
			daten.name = "time";
			daten.time = this.timeLog;

			this.timeLog += Configuration.timeSteps;

			if(this.timeLog.compareTo((this.timeEnd - 100)) >= 0) {
				this.running = false;
				Platform.runLater(new Runnable() {
					public void run() {
						Observer.trigger("timeKilledFrog", daten);
					}
				});
			} else {				
				Platform.runLater(new Runnable() {
					public void run() {
						Observer.trigger("time", daten);
					}
				});
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "stopGame") {
			this.running = false;
		}
		if(trigger == "resetGame") {
			this.reset();
		}
	}
	

}
