package controller;

import application.*;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import views.*;
import model.*;


public class SceneController implements SubscriberInterface {
	public GameLogic game;
	
	public StartScene startScene;
	public GameScene gameScene;
	public ScoreScene scoreScene;
	private Stage primaryStage;
	private Stage secondaryStage;
	
	
	public SceneController() {
		this.startScene = new StartScene(this);
		this.gameScene = new GameScene(this);
		this.scoreScene = new ScoreScene(this);
		this.secondaryStage = new Stage();
		
		Observer.add("time", this);
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
	
	public void setGame(GameLogic game) {
		this.game = game;
	} 
	
	private void submitStart() {
		SubscriberDaten daten = new SubscriberDaten();
		daten.time = 0;
		Observer.trigger("start", daten);
	}
	
	private void submitEnd() {
		
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "time") {
			// zeige zeit in gamescene
			// data.time
		}
	}
}
