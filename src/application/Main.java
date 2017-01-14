package application;

import controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class Main extends Application {

	public SceneController sceneController;
	public HighScoreController highScoreController;
	public GameLogic gameData;	
	public ActionController actionController; 
	public ServerController server; 

	public void start(Stage primaryStage) {

		this.highScoreController = new HighScoreController();
		this.sceneController = new SceneController();
		this.gameData = new GameLogic();
		this.actionController = new ActionController();

		this.gameData.setScene(this.sceneController);

		try {
			primaryStage.setUserData(this.sceneController);
	        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent e) {
	            	e.consume();
	            	Stage obj = (Stage) e.getSource();
	            	SceneController controller = (SceneController) obj.getUserData();
	            	controller.submitClose(obj.getScene().getUserData().toString());
	            }
	        });
	        this.sceneController.setStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}


}
