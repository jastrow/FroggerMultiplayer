package views;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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



/** 
 * HighScoreSzene / Szene zur Darstellung der HighScores
 * 
 * @author JackRyan
 *
 */
public class ScoreScene implements SubscriberInterface {
	
	// Hauptpanel
	private Scene scene;
	private SceneController sceneController;
	private BorderPane rootScore = new BorderPane();
	private GridPane contentScore = new GridPane();
	private Label[] highScoreList = new Label[3];
	private String[] playerName = new String[3];
	private Integer[] playerDate = new Integer[3];
	private Integer[] playerTime = new Integer[3];
	
	/**
	 * Konstruktor
	 *
	 * @param sceneController / für die Szenen zustaendiger Controller
	 */
	public ScoreScene(SceneController sceneController) {
		this.sceneController = sceneController;
		this.contentScore.getStyleClass().add("content");
		scene = new Scene(rootScore,Configuration.xFields * 50,Configuration.yFields * 50);
		//Szene Formatierungs CSS  zuweisen
		scene.getStylesheets().add(getClass().getResource("scoreScene.css").toExternalForm());
		scene.setUserData("ScoreScene");
		Observer.add("getHigh",this);
		Observer.trigger("readHigh", new SubscriberDaten());
		this.buildScene();
	}

	/**
	 * Funktion zum erstellen der Szene mit ihren Elementen
	 * 
	 * @return komplette Szene mit allen Elementen
	 */
	private void buildScene() {

		//Äußere VertikalBox zur Aufnahme aller weiteren Elemente
		VBox verboAeussereBox = new VBox();
		
		verboAeussereBox.getStyleClass().add("verboAeussereBox");	
		for(int i = 0 ; i < 3; i++) {
			highScoreList[i] = new Label();
			highScoreList[i].getStyleClass().add("highlabel"+(i+1));
			verboAeussereBox.getChildren().add(highScoreList[i]); 
		}
		Button neuesSpiel = new Button();
		neuesSpiel.getStyleClass().add("neuesSpiel");
		neuesSpiel.setPrefWidth(300);
		neuesSpiel.setPrefHeight(65);
		neuesSpiel.setOnAction(actionEvent -> this.sceneController.newGame());	
		verboAeussereBox.getChildren().add(neuesSpiel);
		this.contentScore.getChildren().add(verboAeussereBox);	
		this.rootScore.setTop(this.buildMenu());
		this.rootScore.setBottom(this.contentScore);
	}
	
	/** 
	 * Menueleiste bauen
	 *
	 * @return HBox / HorizontalBox mit Menue 
	 */
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
   		menuBar.getMenus().addAll(froggerMenu);		
		menuBox.getChildren().add(menuBar);
		return menuBox;
	}
	
	/**
	 * Hilfsfunktion zum formatieren der Datumsausgabe
	 *
	 * @param dateToFormat / uebergebenes Datum in UnixTimeStamp
	 * @return formatierter DatumsString In Tag.Monat.Jahr
	 * 
	 */
	private String formatDate(Integer dateToFormat) {

		String formatedDate = "";
	
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		if (dateToFormat != null) {
			date.setTime( (long) dateToFormat * 1000);	
			formatedDate = simpleDateFormat.format(date);		
		}	
		return formatedDate;
	}

	/**
	 * Hilfsfunktion zum formatieren der Zeitausgabe
	 *
	 * @param timeToFormat / uebergebene Zeit in Millisekunden
	 * @return formatierter ZeitString in Sekunden
	 */
	private String formatTime(Integer timeToFormat) {
		
		if (timeToFormat != null) {
			String formatedTime = "";
			DecimalFormat format = new DecimalFormat("00");
			timeToFormat = timeToFormat / 1000 ;
			formatedTime = format.format((timeToFormat - ((timeToFormat/60) * 60))) + " Sek."; 
			return formatedTime; 
		} else {
			return "00 Sek.";
		}
	}
	
	/**
	 * Funktion zur Rueckgabe der Szene
	 * @return komplette Szene mit allen Elementen
	 */	
	public Scene getScene() {
		return this.scene;
	}

	@Override
	public void calling(String trigger, SubscriberDaten data) {
		if (trigger == "getHigh") {
			this.playerName = data.playerName;
			this.playerDate = data.playerDate;
			this.playerTime = data.playerTime;
			for (int i = 0 ; i < 3; i ++) {
				this.highScoreList[i].setText(this.playerName[i] + " - " + this.formatDate(this.playerDate[i]) + " - " + this.formatTime(this.playerTime[i]));
			}
		}		
	}
	
}
