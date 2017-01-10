package views;

import java.text.DecimalFormat;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import controller.SceneController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ScoreScene implements SubscriberInterface {
	
	// Hauptpanel
	private Scene scene;
	private SceneController sceneController;
	private BorderPane rootScore = new BorderPane();
	private GridPane contentScore = new GridPane();
	private Label[] highScoreList = new Label[3];
	private String[] playerName = new String[3];
	private String[] playerDate = new String[3];
	private Integer[] playerTime = new Integer[3];
	
	public ScoreScene(SceneController sceneController) {
		this.sceneController = sceneController;
		this.contentScore.getStyleClass().add("content");
		scene = new Scene(rootScore,Configuration.xFields * 50,Configuration.yFields * 50);
		//Szene Formatierungs CSS  zuweisen

		//this.getStylesheets().add(getClass().getResource("../scoreScene.css").toExternalForm());

		scene.getStylesheets().add(getClass().getResource("../scoreScene.css").toExternalForm());
		scene.setUserData("ScoreScene");
		Observer.add("getHigh",this);
		Observer.trigger("readHigh", new SubscriberDaten());


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
				highScoreList[i] = new Label(this.playerName[i] + " - " + this.formatDate(this.playerDate[i]) + " - " + this.formatTime(this.playerTime[i]));
				highScoreList[i].getStyleClass().add("highlabel"+(i+1));
				verboAeussereBox.getChildren().add(highScoreList[i]); 
			};
		

		Button neuesSpiel = new Button();

		//neuesSpiel.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_NeuesSpiel.png").toExternalForm())));

		//neuesSpiel.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_NeuesSpiel.png").toExternalForm())));
		neuesSpiel.getStyleClass().add("neuesSpiel");
		neuesSpiel.setPrefWidth(300);
		neuesSpiel.setPrefHeight(65);
		neuesSpiel.setOnAction(actionEvent -> this.sceneController.newGame());

	
		verboAeussereBox.getChildren().add(neuesSpiel);
		this.contentScore.getChildren().add(verboAeussereBox);
		
		this.rootScore.setTop(this.buildMenu());
		this.rootScore.setBottom(this.contentScore);
	}
	
	private VBox buildMenu() {
		
		MenuBar menuBar = new MenuBar();
		VBox menuBox = new VBox();
		
		menuBox.setPrefHeight(20);
		
		Menu froggerMenu = new Menu("Frogger");
		MenuItem neuMenuItem = new MenuItem("Neues Spiel");
		MenuItem exitMenuItem = new MenuItem("Exit");
    
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());
		neuMenuItem.setOnAction(actionEvent -> this.sceneController.newGame());
    
		froggerMenu.getItems().addAll(neuMenuItem,new SeparatorMenuItem(), exitMenuItem);
    
		Menu infoMenu = new Menu("Info");
		MenuItem highMenuItem = new MenuItem("Highscore");
    
		highMenuItem.setOnAction(actionEvent -> this.sceneController.showHighscore());
    
		infoMenu.getItems().addAll(highMenuItem);
    
		menuBar.getMenus().addAll(froggerMenu, infoMenu);
		
		menuBox.getChildren().add(menuBar);
		return menuBox;
	}
	
	private String formatDate(String dateToFormat) {
		String formatedDate = "";
		
		if (dateToFormat != null) {
		
		String year = dateToFormat.substring(0,4);
		String month = dateToFormat.substring(5,7);
		String day = dateToFormat.substring(8,10);
		
		formatedDate = day + "." + month + "." + year;
		
		}
		

		
		return formatedDate;
	}

	/**
	 * Hilfsfunktion zum formatieren der Zeitausgabe
	 *
	 */
	
	private String formatTime(Integer timeToFormat) {
		
		if (timeToFormat != null) {
			String formatedTime = "";
			DecimalFormat format = new DecimalFormat("00");
			timeToFormat = timeToFormat / 1000 ;
			formatedTime = format.format(( timeToFormat / 60 )) + ":" + format.format((timeToFormat - ((timeToFormat/60) * 60))); 
			return formatedTime; 
		} else {
			return "00:00";
		}
	}
		
	public Scene getScene() {
		return this.scene;
	}

	@Override
	public void calling(String trigger, SubscriberDaten data) {
		if (trigger == "getHigh") {
			System.out.println("####################");
			this.playerName = data.playerName;
			this.playerDate = data.playerDate;
			this.playerTime = data.playerTime;
		}
		
	}
	
}
