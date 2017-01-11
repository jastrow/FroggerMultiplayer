package views;

import java.text.DecimalFormat;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import controller.SceneController;
import javafx.application.Platform;
import javafx.geometry.Insets;
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

public class OverScene {
	
	// Hauptpanel
	private Scene scene;
	private SceneController sceneController;
	private BorderPane rootOver = new BorderPane();
	private GridPane contentOver = new GridPane();
	private Label[] overList = new Label[3];
	private String[] labelText = new String[3];

	
	public OverScene(SceneController sceneController) {
		
		this.labelText[0] = "Salim Andreas Oussayfi - 797754";
		this.labelText[1] = "Mike Jastrow - xxxxxx";
		this.labelText[2] = "Manuel Bogus - 791563";
		
		this.sceneController = sceneController;
		this.contentOver.getStyleClass().add("content");
		scene = new Scene(rootOver,Configuration.xFields * 50,Configuration.yFields * 50);
		scene.getStylesheets().add(getClass().getResource("../overScene.css").toExternalForm());
		scene.setUserData("OverScene");

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
				overList[i] = new Label(this.labelText[i]);
				overList[i].getStyleClass().add("labelOver"+(i+1));
				verboAeussereBox.getChildren().add(overList[i]); 
			};
			
		this.contentOver.getChildren().add(verboAeussereBox);
		
		this.rootOver.setTop(this.buildMenu());
		this.rootOver.setBottom(this.contentOver);
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
 
   		menuBar.getMenus().addAll(froggerMenu);
		
		menuBox.getChildren().add(menuBar);
		return menuBox;
	}
		
	public Scene getScene() {
		return this.scene;
	}
}
