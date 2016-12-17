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
	
	public void setGame(GameLogic game) {
		this.game = game;
	}
}
