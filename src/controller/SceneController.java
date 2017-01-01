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
	
	public Scene getScene(int sceneID) {
	switch(sceneID) {
		case 1: return this.startScene;
		case 2: return scoreScene.getScene();
		default: return gameScene.getScene();
		}
	}
	
	public void updateGameScene(Frog[] frosch, Balken[] balken, Car[] auto) {
		
		for (int i = 0 ; i < frosch.length; i++ ) {
			gameScene.setFrog(frosch[i]);
		}
		
		for (int i = 0 ; i < balken.length; i++ ) {
			gameScene.setBalken(balken[i]);
		}
		
		for (int i = 0 ; i < auto.length; i++ ) {
			gameScene.setCar(auto[i]);
		}
	
	} 
	
	public void setGame(GameLogic game) {
		this.game = game;
	} 
}
