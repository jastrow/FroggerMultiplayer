package views;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StartScene implements SubscriberInterface {

	private Scene scene;
	private SceneController sceneController;
	
	// Hauptpanel
	private BorderPane rootStart = new  BorderPane();
	private GridPane contentStart = new GridPane();
	private TextField spielerName = new TextField("Spielername");
	
	public StartScene(SceneController sceneController) {
		this.sceneController = sceneController;
		this.contentStart.getStyleClass().add("content");
		scene = new Scene(rootStart,Configuration.xFields * 50,Configuration.yFields * 50);
		//Szene Formatierungs CSS  zuweisen
		scene.getStylesheets().add(getClass().getResource("../startScene.css").toExternalForm());
		scene.setUserData("StartScene");
		this.buildScene();
	}

	private void buildScene() {

		//Äußere VertikalBox zur Aufnahme aller weiteren Elemente
		VBox verboAeussereBox = new VBox();
		
		verboAeussereBox.getStyleClass().add("verboAeussereBox");
		
		spielerName.getStyleClass().add("spielerName");
		
		Button sucheSpieler = new Button();

//		sucheSpieler.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_SpielerSuchen.png").toExternalForm())));
		//starteSpiel.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_Start.png").toExternalForm())));

		Button starteSpiel = new Button();

		//sucheSpieler.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_SpielerSuchen.png").toExternalForm())));
		sucheSpieler.getStyleClass().add("sucheSpieler");
		sucheSpieler.setPrefHeight(65);
		sucheSpieler.setPrefWidth(330);
		sucheSpieler.setOnAction(actionEvent -> this.sceneController.searchPlayer());

		//starteSpiel.setGraphic(new ImageView(new Image(getClass().getResource("../img/btn_Start.png").toExternalForm())));
		starteSpiel.getStyleClass().add("starteSpiel");
		starteSpiel.setPrefHeight(78);
		starteSpiel.setPrefWidth(260);
		starteSpiel.setOnAction(actionEvent -> {
			this.submitStart();
			this.sceneController.startGame();
			});


		verboAeussereBox.getChildren().add(spielerName);
		verboAeussereBox.getChildren().add(sucheSpieler);
		verboAeussereBox.getChildren().add(starteSpiel);
	
		this.contentStart.getChildren().add(verboAeussereBox);
		this.rootStart.setTop(this.buildMenu());
		this.rootStart.setBottom(this.contentStart);
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
	
	private void submitStart() {
		SubscriberDaten data = new SubscriberDaten();
		data.name = this.spielerName.getText();
		data.time = Configuration.timeEnd;
		Observer.trigger("start", data);
	}

	
	public Scene getScene() {
		return this.scene;
	}

	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		// TODO Automatisch generierter Methodenstub
		
	}
	
	
}
