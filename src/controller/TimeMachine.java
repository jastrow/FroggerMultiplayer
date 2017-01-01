package controller;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;

public class TimeMachine implements Runnable {

	private Boolean running;
	private Integer timeLog;
	private Integer timeEnd;
	
	public TimeMachine(Integer timeEnd) {
		this.timeEnd = timeEnd;
		this.reset();
	}
	
	public void reset() {
		this.running = false;
		this.timeLog = 0;
	}
	
	public void start() {
		this.running = true;
		this.run();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.timeLog += Configuration.timeSteps;
			if(this.timeLog >= this.timeEnd) {
				this.running = false;
			} 
			SubscriberDaten daten = new SubscriberDaten();
			daten.name = "time";
			daten.time = this.timeLog;
			
			Observer.trigger("time", daten);
			System.out.println(this.timeLog);
		}
	}

}
