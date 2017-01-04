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
	
	public Scene getScene(int sceneID) {
		switch(sceneID) {
			case 1: return startScene.getScene();
			case 2: return scoreScene.getScene();
			default: return gameScene.getScene();
		}


	}
	
	public void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setScene(getStartScene());
		
	}
	
	
	public void setBar(Integer length) {
		
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
	
	public void updateScene(Data data) {
		this.gameScene.updateScene(data);
	}
	
	public void newGame(){
		this.primaryStage.setScene(this.getGameScene());
	}
	
	public void showHighscore(){
		this.secondaryStage.setScene(this.getScoreScene());
		this.secondaryStage.show();
	}
	
	public void searchPlayer(){
		System.out.println("na dann suche mal!");
	}
	
	public void startGame(){
		System.out.println("na dann starte mal!");

		SubscriberDaten daten = new SubscriberDaten();
		daten.time = 0;
		Observer.trigger("start", daten);

	}
	
	public void setGame(GameLogic game) {
		this.game = game;
	} 
	
	public void calling(String trigger, SubscriberDaten data) {
		if(trigger == "time") {
			// zeige zeit in gamescene
			// data.time
		}
	}
}
