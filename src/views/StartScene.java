package views;

import application.Configuration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StartScene {

	Scene scene; 
	
	// Hauptpanel
	private GridPane root = new GridPane();
	private Label spielerName = new Label("Spielername");
	
	public StartScene() {
		scene = new Scene(root,Configuration.xFields * 50,Configuration.yFields * 50);
		//Szene Formatierungs CSS  zuweisen
		scene.getStylesheets().add(getClass().getResource("../startScene.css").toExternalForm());
		this.buildScene();
	}

	private void buildScene() {

		//Äußere VertikalBox zur Aufnahme aller weiteren Elemente
		VBox verboAeussereBox = new VBox();
		
		verboAeussereBox.getStyleClass().add("verboAeussereBox");
		
		spielerName.getStyleClass().add("spielerName");
		
		Button sucheSpieler = new Button();
		sucheSpieler.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_SpielerSuchen.png").toExternalForm())));

		Button starteSpiel = new Button();
		starteSpiel.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_Start.png").toExternalForm())));

		verboAeussereBox.getChildren().add(spielerName);
		verboAeussereBox.getChildren().add(sucheSpieler);
		verboAeussereBox.getChildren().add(starteSpiel);

		
		this.root.getChildren().add(verboAeussereBox);	
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	
}
