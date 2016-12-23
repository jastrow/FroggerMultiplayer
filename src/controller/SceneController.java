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
	public HeadMenu headMenu;
	
	private int i = 1;
	
	
	public SceneController() {
		this.headMenu = new HeadMenu(this);
		this.startScene = new StartScene(this);
		this.gameScene = new GameScene(this);
		this.scoreScene = new ScoreScene(this);

	}
	
	public VBox getMenuBar() {
		System.out.println("Aufruf: " + i);
		i++;
		return this.headMenu.getResultBox();
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
	
	public void setGame(GameLogic game) {
		this.game = game;
	} 
}
