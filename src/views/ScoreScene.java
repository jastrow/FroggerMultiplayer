package views;

import application.Configuration;
import controller.SceneController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ScoreScene {
	
	// Hauptpanel
	private Scene scene;
	private SceneController sceneController;
	private BorderPane root = new BorderPane();
	private GridPane content = new GridPane();
	private Label[] highScoreList = new Label[3];
	
	public ScoreScene(SceneController sceneController) {
		this.sceneController = sceneController;
		this.content.getStyleClass().add("content");
		scene = new Scene(root,Configuration.xFields * 50,Configuration.yFields * 50);
		//Szene Formatierungs CSS  zuweisen
		scene.getStylesheets().add(getClass().getResource("../scoreScene.css").toExternalForm());
		this.buildScene();
	}

	/**
	 * Funktion zum erstellen der Szene mit ihren Elementen
	 * @return komplette Szene mit allen Elementen
	 */
	private void buildScene() {

		//Äußere VertikalBox zur Aufnahme aller weiteren Elemente
		VBox verboAeussereBox = new VBox();
		
		verboAeussereBox.getStyleClass().add("verboAeussereBox");
			
			for(int i = 0 ; i < 3; i++) {
				highScoreList[i] = new Label("Test");
				highScoreList[i].getStyleClass().add("highlabel"+(i+1));
				verboAeussereBox.getChildren().add(highScoreList[i]); 
			};
		

		Button neuesSpiel = new Button();
		neuesSpiel.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_NeuesSpiel.png").toExternalForm())));
		neuesSpiel.setOnAction(actionEvent -> this.sceneController.newGame());
	
		verboAeussereBox.getChildren().add(neuesSpiel);
		this.content.getChildren().add(verboAeussereBox);
		
		this.root.setTop(this.sceneController.getMenuBar());
		this.root.setBottom(this.content);
	}

		
	public Scene getScene() {
		return this.scene;
	}
	
}
