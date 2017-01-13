package controller;

import java.sql.ResultSet;
import java.sql.SQLException;


import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import javafx.application.Platform;
import model.HighScore;

public class HighScoreController implements Runnable, SubscriberInterface {
	
	private Thread t;
	private HighScore highScore;
	private DBConnectionController dbConnection;
	private String playerName;
	private Integer playerTime;
	private String was;
	private StringBuffer resultQuery;
	private String[] playerArray = new String[3];
	private Integer[] timeArray = new Integer[3];
	private Integer[] dateArray = new Integer[3];
	
	public HighScoreController() {
		Observer.add("player", this);
		Observer.add("entry", this);
		Observer.add("readHigh", this);

		this.highScore = new HighScore();
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
		
//		if(this.was.equals("get")) {
//			
//			try {
//				this.resultQuery = this.dbConnection.readData();
//
//
//				JSONObject json = new JSONObject (this.resultQuery.toString());
//
//				// Nun aber zum Array. Wir lesen die items in ein array ein. 
//				JSONArray jsonArrItems = json.getJSONArray(json.getString("id"));
//				
//				for (int i = 0; i <= jsonArrItems.length(); i++) {
//					JSONObject jsonObj = jsonArrItems.getJSONObject(i);
//					 // jetzt haben wir solange elemente bis es komplett durchgeloopt ist.
//					this.playerArray[i] = jsonObj.getString("name"); 
//					this.timeArray[i] = Integer.getInteger(jsonObj.getString("zeit")); 
//					this.dateArray[i] = Integer.getInteger(jsonObj.getString("created")); 
//					System.out.println(this.playerArray[i] + "  " + this.timeArray[i] + "   " + this.dateArray[i]);
//				}	
//			} catch (Exception e) {
//				System.out.println("Parsen erfolglos");
//			}		
//		} else {	
//			try {
//			this.dbConnection.writeData("{\"name\":\"" + this.playerName + "\"time\":\""+ this.playerTime + "\"}");
//			} catch (Exception e) {
//				
//			}
//		}
	}



	@Override
	public void calling(String trigger, SubscriberDaten data) {
		
		if (trigger == "player") {
			this.playerName = data.name;
		}
		if (trigger == "entry") {
			this.setHighScore(data.time);
		}
		if (trigger == "readHigh") {
			this.getHighScore();
		}

		
	}

	
}
