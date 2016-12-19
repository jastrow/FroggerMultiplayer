package controller;

import application.*;
import views.*;


public class SceneController {
	public GameLogic game;
	
	public StartScene startScene;
	public GameScene gameScene;
	public ScoreScene scoreScene;
	
	
	public void SceneControler() {
		this.startScene = new StartScene();
		this.gameScene = new GameScene();
		this.scoreScene = new ScoreScene();
	}
	
	public Scene zeigeGameScene() {
		return gameScene.getScene();
	}
	
	public Scene zeigeStartScene() {
		return startScene.getScene();
	}
	
	public Scene zeigeScoreScene() {
		return scoreScene.getScene();
	}
	
	public void setGame(GameLogic game) {
		this.game = game;
	}
}
