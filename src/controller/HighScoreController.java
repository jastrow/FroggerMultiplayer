package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.DBConnection;
import model.HighScore;

public class HighScoreController {
	
	private HighScore highScore;
	private DBConnection dbConnection;
	private SceneController sceneController;
	
	public HighScoreController(SceneController sceneController) {
		this.sceneController = sceneController;
		this.sceneController.setHighScoreController(this, this.highScore);
		this.highScore = new HighScore();
		this.dbConnection = new DBConnection();
		
	}
	
	
	public void setHighScore() {
		
		String[] playerName = new String[3];
		Integer[] playerPlace = new Integer[3];
		Integer[] playerTime = new Integer[3];
		
		playerName = this.highScore.getPlayerName();
		playerPlace = this.highScore.getPlayerPlace();
		playerTime = this.highScore.getPlayerTime();
		
		for(int i = 0 ; i < 3; i++) {
			this.dbConnection.writeData("UPDATE highscore SET spielername='"+ playerName[i] + "' WHERE place = " + i+1 + " ");
			this.dbConnection.writeData("UPDATE highscore SET spielername='"+ playerTime[i] + "' WHERE place = " + i+1 + " ");
		}
		
		
	}
	
	public void getHighScore() {
		ResultSet sqlResult = null;
		String[] playerName = new String[3];
		Integer[] playerPlace = new Integer[3];
		Integer[] playerTime = new Integer[3];
		Integer help = 0;
		
		try {
		
		sqlResult = this.dbConnection.readData("SELECT * FROM highscore");
		
		while (sqlResult.next()) {
			playerName[help] = sqlResult.getString(1);
			playerPlace[help] = sqlResult.getInt(2);
			playerTime[help] = sqlResult.getInt(3);
		}
		System.out.println("############## erfolgreiche Abfrage" + playerName[0]);
		System.out.println("############## erfolgreiche Abfrage" + playerName[1]);
		System.out.println("############## erfolgreiche Abfrage" + playerName[2]);
		System.out.println("############## erfolgreiche Abfrage" + playerPlace[0]);
		System.out.println("############## erfolgreiche Abfrage" + playerPlace[1]);
		System.out.println("############## erfolgreiche Abfrage" + playerPlace[2]);
		System.out.println("############## erfolgreiche Abfrage" + playerTime[0]);
		System.out.println("############## erfolgreiche Abfrage" + playerTime[1]);
		System.out.println("############## erfolgreiche Abfrage" + playerTime[2]);
		
		this.highScore.setPlayerName(playerName);
		this.highScore.setPlayerPlace(playerPlace);
		this.highScore.setPlayerTime(playerTime);
		
		} catch (SQLException e) {
			e.printStackTrace();			
		}

	}

}
