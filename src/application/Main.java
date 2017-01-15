package application;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import views.GameScene;



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
		
		
		// Musik abspielen
		this.playMusic();
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	
	public void playMusic() {
		Media sound = new Media( 
				ClassLoader.getSystemResource("views/music.mp3").toString()
			);
			MediaPlayer mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					mediaPlayer.seek(Duration.ZERO);
				}
			});
			mediaPlayer.play();		
	}

}
