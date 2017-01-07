package controller;

import application.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.*;


public class SceneController implements SubscriberInterface {
	public GameLogic game;
	
	public StartScene startScene;
	public GameScene gameScene;
	public ScoreScene scoreScene;
	private Stage primaryStage;
	private Integer time;
	private boolean gameRunning;
	
	
	public SceneController() {
		this.startScene = new StartScene(this);
		this.scoreScene = new ScoreScene(this);
		this.primaryStage = new Stage();
		Observer.add("time", this);
		Observer.add("close", this);
		Observer.add("end", this);
		Observer.add("start", this);
		this.gameScene = new GameScene(this);
	}
	
	public void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
//		this.primaryStage.setScene(getStartScene());

//		this.gameScene = new GameScene(this);
		this.primaryStage.setScene(this.getGameScene());
//		Observer.trigger("start", new SubscriberDaten());

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
		System.out.println("na dann suche mal!");
	}
	
	public void startGame(){
//		this.gameScene = new GameScene(this);
//		this.primaryStage.setScene(this.getGameScene());
//		Observer.trigger("start", new SubscriberDaten());
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
		data.time = 0;
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
				System.out.println("End Game");
				break;
			}
			case "start": {
				this.gameRunning = true;
				System.out.println("Start Game");
				break;
			}
		}
	}
}
