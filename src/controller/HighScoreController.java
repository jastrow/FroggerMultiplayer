package controller;

import java.util.regex.Pattern;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import javafx.application.Platform;

/**
 * Steuert das Auslesen und das Eintragen des HighScore in die Datenbank
 * 
 * @author Die UMeLs
 *
 */
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
	
	/**
	 * Konstruktor
	 *
	 */
	public HighScoreController() {
		Observer.add("player", this);
		Observer.add("entry", this);
		Observer.add("readHigh", this);
		
		this.dbConnection = new DBConnectionController();
		
	}
	
		
	/** 
	 * liest HighScoreDaten aus Datenbank aus
	 * 
	 */
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
	
	/** 
	 * speichert HighScore in Datenbank
	 *
	 * @param playerScore / Spielerpunkte
	 */
	public void setHighScore(Integer playerScore) {
		this.playerTime = playerScore;
		this.was = "set";
		this.t = new Thread(this);
		t.start();
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		if(this.was.equals("get")) {
			
				try {
					this.resultQuery = this.dbConnection.readData();
					
					for (int i = 0 ; i < this.resultQuery.length; i++) {
						String[] actString = this.resultQuery[i].split(Pattern.quote("|"));
						this.playerArray[i] = actString[1];
						this.timeArray[i] = Integer.valueOf(actString[2]);
						this.dateArray[i] = Integer.valueOf(actString[3]);
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



	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
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
