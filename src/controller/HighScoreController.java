package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import model.HighScore;

public class HighScoreController implements SubscriberInterface {
	
	private HighScore highScore;
	private DBConnectionController dbConnection;
	private String playerName;
	
	public HighScoreController() {
		this.highScore = new HighScore();
		this.dbConnection = new DBConnectionController();
		Observer.add("player", this);
		Observer.add("entry", this);
		Observer.add("readHigh", this);
		
	}
	
	
	public void setHighScore(String playerName, Integer playerTime) {
					
		this.dbConnection.writeData("INSERT INTO highscore VALUES('" + playerName + "',NOW()," + playerTime + ")");
		System.out.println("#########################################");
		
	}
	
	public void getHighScore() {
		ResultSet sqlResult = null;
		String[] playerName = new String[3];
		String[] playerDate = new String[3];
		Integer[] playerTime = new Integer[3];
		Integer help = 0;
		
		try {
		
		sqlResult = this.dbConnection.readData("SELECT * FROM highscore ORDER BY zeit LIMIT 3");
		
		while (sqlResult.next()) {
			playerName[help] = sqlResult.getString(1);
			playerDate[help] = sqlResult.getString(2);
			playerTime[help] = sqlResult.getInt(3);
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
		Observer.trigger("getHigh", highData);
		
		} catch (SQLException e) {
			e.printStackTrace();			
		}

	}



	@Override
	public void calling(String trigger, SubscriberDaten data) {
		if (trigger == "player") this.playerName = data.name;
		if (trigger == "entry") this.setHighScore(this.playerName, data.time);
		if (trigger == "readHigh") this.getHighScore();
		
	}

}
