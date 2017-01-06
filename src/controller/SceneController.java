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
	
	
	public SceneController() {
		this.startScene = new StartScene(this);
		this.gameScene = new GameScene(this);
		this.scoreScene = new ScoreScene(this);
		this.primaryStage = new Stage();
		Observer.add("time", this);
		Observer.add("close", this);
		Observer.add("end", this);
	}
	
	public void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setScene(getStartScene());
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
		this.primaryStage.setScene(this.getGameScene());
		this.submitStart();
	}
	
	public void showGameScene() {
		
		if (this.time > 0) {
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
	
	private void submitStart() {
		SubscriberDaten data = new SubscriberDaten();
		data.time = Configuration.timeEnd;
		Observer.trigger("start", data);
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
		System.out.println("is wech");
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
				System.out.println("End Game");
				break;
			}
		
		}
	}
}
