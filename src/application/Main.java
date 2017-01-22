package application;

import java.net.MalformedURLException;

import controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * MainKlasse.
 * 
 * @author Die UMeLs
 *
 */
public class Main extends Application {

	public SceneController sceneController;
	public HighScoreController highScoreController;
	public GameLogic gameData;	
	public SoundController sound;

	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	public void start(Stage primaryStage) throws MalformedURLException {

		this.highScoreController = new HighScoreController();
		this.sceneController = new SceneController();
		this.gameData = new GameLogic();
		this.sound = new SoundController();

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

	/** 
	 * Main.
	 *
	 * @param args / Startparameter
	 */
	public static void main(String[] args) {
		launch(args);
	}



}
