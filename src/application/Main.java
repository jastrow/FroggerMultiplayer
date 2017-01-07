package application;
	
import controller.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



public class Main extends Application implements SubscriberInterface {
	
	public SceneController sceneController;
	public GameLogic gameData;	// Ganze Spiellogik
	public ActionController actionController; // klicks, keybords Prüfung ob geht, GameLogic, usw.
	public ServerController server; // API zum Server
	
	public void start(Stage primaryStage) {
		
		this.sceneController = new SceneController();
		this.gameData = new GameLogic();
		this.actionController = new ActionController();
		
		
		this.sceneController.setGame(this.gameData);
		this.gameData.setScene(this.sceneController);
		
		
		try {
//			this.sceneController.setStage(primaryStage);
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
	
	public void changeScene() {
		// Ändert borderPane zu einer der bestehenden scenen aus this.sceneController
	}
	
	public void action(Action action) { // Action beinhaltet nicht nur die bezeichnung, sondern auch alle evt, notwendigen Daten
		
	}

	public void calling(String trigger, SubscriberDaten data) {
		
	}
	

}
