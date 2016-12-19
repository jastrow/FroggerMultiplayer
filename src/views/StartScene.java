package views;

import application.Configuration;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartScene extends Scene{

	// Hauptpanel
	private static GridPane wurzel = new GridPane();
	private Label spielerName = new Label("Spielername");
	
	public StartScene() {
		super(wurzel,Configuration.xFields * 50,Configuration.yFields * 50);
		// new Image(url)
		Image hinterGrundBild = new Image(getClass().getResource("img/Hintergrund_Start.jpg").toExternalForm());
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

	private void erstelleScene() {

		//Äußere VertikalBox zur Aufnahme aller weiteren Elemente
		VBox verboAeussereBox = new VBox();
		
		verboAeussereBox.setStyle("-fx-padding: 150 0 0 0;");
		
		spielerName.setPrefWidth(300);
		spielerName.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: green;");
		
		Button sucheSpieler = new Button();
		sucheSpieler.setPrefWidth(100);
		BackgroundImage backgroundImageSuchen = new BackgroundImage(new Image(getClass().getResource("balken.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,null);
		Background buttonSucheBackground = new Background(backgroundImageSuchen);
		sucheSpieler.setBackground(buttonSucheBackground);
		
		
		Button starteSpiel = new Button();
		starteSpiel.setPrefWidth(100);
		BackgroundImage backgroundImageStart = new BackgroundImage(new Image(getClass().getResource("gras.jpg").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,null);
		Background buttonStartBackground = new Background(backgroundImageStart);
		starteSpiel.setBackground(buttonStartBackground);	

		verboAeussereBox.getChildren().add(spielerName);
		verboAeussereBox.getChildren().add(sucheSpieler);
		verboAeussereBox.getChildren().add(starteSpiel);

		
		this.wurzel.getChildren().add(verboAeussereBox);	
	}
	
	public Scene getScene() {
		return this;
	}
	
	
}
