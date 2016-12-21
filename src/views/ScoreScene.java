package views;

import application.Configuration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScoreScene {
	
	// Hauptpanel
	private Scene scene;
	private GridPane root = new GridPane();
	private Label[][] highScoreListe = new Label[3][3];
	private HBox horBox[] = new HBox[3];
	
	public ScoreScene() {
		
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
		
		for(int i = 0; i < 3 ;i++ ) {
			horBox[i] = new HBox(); 
			verboAeussereBox.getChildren().add(horBox[i]);
			
			for(int j = 0 ; j < 3; j++) {
				highScoreListe[i][j] = new Label("Test");
				horBox[i].getChildren().add(highScoreListe[i][j]); 
			};
		};

		Button neuesSpiel = new Button();
		neuesSpiel.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_NeuesSpiel.png").toExternalForm())));
	
		verboAeussereBox.getChildren().add(neuesSpiel);
		
		this.root.getChildren().add(verboAeussereBox);	
	}

		
	public Scene getScene() {
		return this.scene;
	}
	
}
