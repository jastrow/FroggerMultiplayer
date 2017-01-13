package controller;

import java.util.regex.Pattern;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import javafx.application.Platform;

public class HighScoreController implements Runnable, SubscriberInterface {
	
	private Thread t;
	private DBConnectionController dbConnection;
	private String playerName;
	private int playerTime;
	private String was;
	private String[] resultQuery = new String[3];
	private String[] playerArray = new String[3];
	private Integer[] timeArray = new Integer[3];
	private Integer[] dateArray = new Integer[3];
	
	public HighScoreController() {
		Observer.add("player", this);
		Observer.add("entry", this);
		Observer.add("readHigh", this);
		
		this.dbConnection = new DBConnectionController();
		
	}
	
		
	public void getHighScore() {
		this.was = "get";
		if(this.t == null) {
			this.t = new Thread(this);
			t.start();
		} else {
			if(!this.t.isAlive()) {
				this.t = new Thread(this);
				t.start();
			}
		}
	}
	public void setHighScore(Integer playerTime) {
		this.playerTime = playerTime;
		this.was = "set";
		this.t = new Thread(this);
		t.start();
	}

	public void run() {
		
		if(this.was.equals("get")) {
			
				try {
					this.resultQuery = this.dbConnection.readData();
					
					for (int i = 0 ; i < this.resultQuery.length; i++) {
						String[] actString = this.resultQuery[i].split(Pattern.quote("|"));
						this.playerArray[i] = actString[1];
						this.timeArray[i] = Integer.valueOf(actString[2]);
						this.dateArray[i] = Integer.valueOf(actString[3]);
						System.out.println(this.playerArray[i] + this.timeArray[i] + this.dateArray[i]);
					}
					
					SubscriberDaten highData = new SubscriberDaten();
					highData.playerName = this.playerArray;
					highData.playerDate = this.dateArray;
					highData.playerTime = this.timeArray;
					Platform.runLater(new Runnable() {
						public void run() {
							Observer.trigger("getHigh", highData);
						}
					});

					
				} catch (Exception e) {
						System.out.println("Resultverarbeitung fehlerhaft!");
				}
	
		} else {	
			try {
			this.dbConnection.writeData(this.playerName, this.playerTime);
			} catch (Exception e) {
				
			}
		}
	}



	@Override
	public void calling(String trigger, SubscriberDaten data) {
		
		if (trigger == "player") {
			this.playerName = data.name;
		}
		if (trigger == "entry") {
			this.setHighScore((int) data.time);
		}
		if (trigger == "readHigh") {
			this.getHighScore();
		}

		
	}

	
}
