package controller;

import application.*;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import views.*;
import model.*;


public class SceneController {
	public GameLogic game;
	
	public StartScene startScene;
	public GameScene gameScene;
	public ScoreScene scoreScene;
	
	
	public SceneController() {
		this.startScene = new StartScene(this);
		this.gameScene = new GameScene(this);
		this.scoreScene = new ScoreScene(this);

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
		System.out.println("Da isser klick !");
	}
	
	public void showHighscore(){
		System.out.println("Da isser Highscore !");
	}
	
	public void searchPlayer(){
		System.out.println("na dann suche mal!");
	}
	
	public void startGame(){
		System.out.println("na dann starte mal!");
	}
	
	public void setGame(GameLogic game) {
		this.game = game;
	} 
}
