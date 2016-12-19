package views;

import application.Configuration;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ScoreScene extends Scene {
	
	// Hauptpanel
	private static GridPane wurzel = new GridPane();
	private Label[][] highScoreListe = new Label[3][3];
	private HBox horBox[] = new HBox[3];
	
	public ScoreScene() {
		
		super(wurzel,Configuration.xFields * 50,Configuration.yFields * 50);
		// new Image(url)
		Image hinterGrundBild = new Image(getClass().getResource("Hintergrund_Highscore.jpg").toExternalForm());
		// new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
		BackgroundSize backgroundSize = new BackgroundSize(Configuration.xFields * 50, Configuration.yFields * 50, false, false, false, false);
		// new BackgroundImage(image, repeatX, repeatY, position, size)
		BackgroundImage backgroundImage = new BackgroundImage(hinterGrundBild, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		// new Background(images...)
		Background background = new Background(backgroundImage);
		
		this.wurzel.setAlignment(Pos.CENTER);
		this.wurzel.setBackground(background);
		this.erstelleScene();
	}

	/**
	 * Funktion zum erstellen der Szene mit ihren Elementen
	 * @return komplette Szene mit allen Elementen
	 */
	private void erstelleScene() {

		//Äußere VertikalBox zur Aufnahme aller weiteren Elemente
		VBox verboAeussereBox = new VBox();
		
		for(int i = 0; i < 3 ;i++ ) {
			horBox[i] = new HBox(); 
			verboAeussereBox.getChildren().add(horBox[i]);
			
			for(int j = 0 ; j < 3; j++) {
				highScoreListe[i][j] = new Label("Test");
				horBox[i].getChildren().add(highScoreListe[i][j]); 
			};
		};

		Button neuesSpiel = new Button();
		neuesSpiel.setPrefWidth(100);
		
		BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource("balken.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,null);
		Background buttonBackground = new Background(backgroundImage);
		
		neuesSpiel.setBackground(buttonBackground);
		verboAeussereBox.getChildren().add(neuesSpiel);
		
		this.wurzel.getChildren().add(verboAeussereBox);	
	}
		
	public Scene getScene() {
		return this;
	}
	
}
