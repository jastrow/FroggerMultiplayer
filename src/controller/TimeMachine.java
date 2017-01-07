package controller;

import application.*;

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
	
	public void reset() {
		this.running = false;
		this.timeLog = 0;
	}
	
	public void start() {
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
//			System.out.println(this.timeLog);
		}
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "stopGame") {
			this.stop();
		}
	}
	

}
