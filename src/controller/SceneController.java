package controller;

import application.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.DBConnection;
import model.HighScore;
import views.*;


public class SceneController implements SubscriberInterface {
	public GameLogic game;
	
	public StartScene startScene;
	public GameScene gameScene;
	public ScoreScene scoreScene;
	private Stage primaryStage;
	private Integer time;
	private boolean gameRunning = false;
	//#################################################
	private HighScoreController highScoreController;
	private HighScore highScore;
	//#################################################
	
	
	
	public SceneController() {
		//####################################################
		this.highScoreController = new HighScoreController(this);
		this.highScoreController.getHighScore();
		
		/*
		String[] playerName = this.highScore.getPlayerName();
		Integer[] playerPlace = this.highScore.getPlayerPlace();
		Integer[] playerTime = this.highScore.getPlayerTime();
		for(int i = 0 ; i < 3; i++) {
			System.out.println(playerName[i]);
			System.out.println(playerPlace[i]);
			System.out.println(playerTime[i]);
		}*/
		
		
		//###############################################
		this.startScene = new StartScene(this);
		this.scoreScene = new ScoreScene(this);
		this.primaryStage = new Stage();
		Observer.add("time", this);
		Observer.add("close", this);
		Observer.add("end", this);
		Observer.add("start", this);
	}
	
	public void setHighScoreController(HighScoreController highScoreController, HighScore highScore) {
		this.highScoreController = highScoreController;
		this.highScore = highScore;
	}
	
	public void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setScene(this.getStartScene());
		this.primaryStage.show();

	}
	
	public Scene getGameScene() {
		return this.gameScene.getScene();
	}
	
	public Scene getScoreScene() {
		return this.scoreScene.getScene();
	}
	
	public Scene getStartScene() {
		return this.startScene.getScene();
	}
	
	
	public void newGame(){
		this.primaryStage.setScene(this.getStartScene());
		this.submitEnd();
	}
	
	public void showHighscore(){
		this.primaryStage.setScene(this.getScoreScene());
	}
	
	public void searchPlayer(){

	}
	
	public void startGame(){
		this.gameScene = new GameScene(this);
		this.gameScene.setOnKeyPressed(new EventHandler <KeyEvent>()
        {
            @Override
            public void handle(KeyEvent e)
            {
            	SubscriberDaten data = new SubscriberDaten();
            		
                if((e.getCode() == KeyCode.LEFT)  || (e.getCode() == KeyCode.A))
                {
                	data.typ = "left";
                    Observer.trigger("key", data);
                }
                else if((e.getCode() == KeyCode.RIGHT)  || (e.getCode() == KeyCode.D))
                {
                	data.typ = "right";
                    Observer.trigger("key", data);
                }
                else if((e.getCode() == KeyCode.UP) || (e.getCode() == KeyCode.W))
                {
                	data.typ = "up";
                    Observer.trigger("key", data);
                }
                else if((e.getCode() == KeyCode.DOWN)  || (e.getCode() == KeyCode.S))
                {
                	data.typ = "down";
                    Observer.trigger("key", data);
                }
            }
        });
		this.primaryStage.setScene(this.getGameScene());
		Observer.trigger("start", new SubscriberDaten());
	}
	
	public void showGameScene() {
		
		if (gameRunning) {
			this.primaryStage.setScene(this.getGameScene());
			this.primaryStage.show();
		} else {
			this.primaryStage.setScene(this.getGameScene());
			this.primaryStage.show();

		}
	}
	
	public void setGame(GameLogic game) {
		this.game = game;
	} 
	
	private void submitEnd() {
		SubscriberDaten data = new SubscriberDaten();
		Observer.trigger("end", data);
	}
	
	public void submitClose(String actScene){
		SubscriberDaten data = new SubscriberDaten();
		data.name = actScene;
		Observer.trigger("close", data);
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		switch (trigger) {
			case "time": { 
				this.time = data.time;
				break;
			}
			case "close": {
				if (data.name == "ScoreScene") {
					this.showGameScene();
				} else {
					Platform.exit();
				}
				break;
			}
			case "end": {
				this.gameRunning = false;
				break;
			}
			case "start": {
				this.gameRunning = true;
				break;
			}
		}
	}
}
