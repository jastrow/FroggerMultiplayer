package controller;

import application.*;
import javafx.scene.Scene;
import views.*;
import model.*;


public class SceneController {
	public GameLogic game;
	
	public StartScene startScene;
	public GameScene gameScene;
	public ScoreScene scoreScene;
	
	
	public SceneController() {
		this.startScene = new StartScene();
		this.gameScene = new GameScene();
		this.scoreScene = new ScoreScene();
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
	
	public void setGame(GameLogic game) {
		this.game = game;
	} 
}
