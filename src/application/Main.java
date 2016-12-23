package application;
	
import java.util.Observable;
import java.util.Observer;

import controller.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Data;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application implements Observer {
	
	public SceneController sceneController;
	public GameLogic gameData;
	public ActionController actionController; // klicks, keybords Prüfung ob geht, GameLogic, usw.
	public ServerController server; // API zum Server
	
	public void start(Stage primaryStage) {
		
		this.sceneController = new SceneController();
		this.gameData = new GameLogic();
		this.actionController = new ActionController(this.sceneController);
		
		
		this.sceneController.setGame(this.gameData);
		this.gameData.setScene(this.sceneController);
		
		
		try {
			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(this.sceneController.getScoreScene());
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void changeScene() {
		// Ändert borderPane zu einer der bestehenden scenen aus this.sceneController
	}
	
	public void action(Action action) { // Action beinhaltet nicht nur die bezeichnung, sondern auch alle evt, notwendigen Daten
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("test");
	}
}
