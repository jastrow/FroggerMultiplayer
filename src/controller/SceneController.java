package controller;

import application.*;
import javafx.scene.Scene;
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
	
	public Scene getScene(int sceneID) {
	switch(sceneID) {
		case 1: return startScene.getScene();
			break;
		case 2: return scoreScene.getScene();
			break;
		default: return gameScene.getScene();
			break;
		}
	}
	
	public void updateGameScene(Frog frosch, Balken balken, car auto) {
		
		for (i = 0 ; i < frosch.length; i++ ) {
			gameScene.setFrog(frosch[i]);
		}
		
		for (i = 0 ; i < balken.length; i++ ) {
			gameScene.setBalken(balken[i]);
		}
		
		for (i = 0 ; i < auto.length; i++ ) {
			gameScene.setAuto(auto[i]);
		}
	
	} 
	
	/*
	
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
	} */
}
