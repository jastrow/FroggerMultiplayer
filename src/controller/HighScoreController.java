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
		
		if(this.was.equals("get")) {
			
			ResultSet sqlResult = null;
			String[] playerName = new String[3];
			String[] playerDate = new String[3];
			Integer[] playerTime = new Integer[3];
			Integer help = 0;
			
			try {
			
			sqlResult = this.dbConnection.readData("SELECT * FROM highscore ORDER BY zeit LIMIT 3");
			
			if (sqlResult != null) {
			
			while (sqlResult.next()) {
				playerName[help] = sqlResult.getString(1);
				playerDate[help] = sqlResult.getString(2);
				playerTime[help] = sqlResult.getInt(3);
				help++;
			}
			
			}
			
			this.highScore.setPlayerName(playerName);
			this.highScore.setPlayerDate(playerDate);
			this.highScore.setPlayerTime(playerTime);
			
			for(int i = 0; i < 3 ; i++ ) {
				System.out.println(playerName[i]);
				System.out.println(playerDate[i]);
				System.out.println(playerTime[i]);
			}
			
			SubscriberDaten highData = new SubscriberDaten();
			highData.playerName = playerName;
			highData.playerDate = playerDate;
			highData.playerTime = playerTime;
			Platform.runLater(new Runnable() {
				public void run() {
					Observer.trigger("getHigh", highData);
				}
			});
			
			} catch (SQLException e) {
				
				for(int i = 0; i < 3 ; i++ ) {
					playerName[i] = "";
					playerDate[i] = "";
					playerTime[i] = 0;
				}
				
				this.highScore.setPlayerName(playerName);
				this.highScore.setPlayerDate(playerDate);
				this.highScore.setPlayerTime(playerTime);
				
			}
		} else {
			this.dbConnection.writeData("INSERT INTO highscore VALUES('" + this.playerName + "',NOW()," + this.playerTime + ")");
			System.out.println("#########################################");
		}
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
