package views;

import application.Configuration;
import controller.SceneController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Szene zum anzeigen der Programmiererdaten
 * 
 * @author Die UMeLs
 *
 */
public class OverScene {
	
	// Hauptpanel
	private Scene scene;
	private SceneController sceneController;
	private BorderPane rootOver = new BorderPane();
	private GridPane contentOver = new GridPane();
	private Label[] overList = new Label[4];
	private String[] labelText = new String[4];
	private TextArea licenseText  = new TextArea();

	

	/**
	 * Konstruktor
	 *
	 * @param sceneController / für die Szenen zustaendiger Controller
	 */
	public OverScene(SceneController sceneController) {
		
		this.labelText[0] = "Die UMeLs";
		this.labelText[1] = "Salim Andreas Oussayfi - 797754";
		this.labelText[2] = "Mike Jastrow - 798141";
		this.labelText[3] = "Manuel Bogus - 791563";
		
		this.licenseText.setText("Music by Bart Kelsey as Bart on http://opengameart.org "
				+ "http://opengameart.org/content/the-adventure-begins-8-bit-remix"
				+ "CC By 3.0 https://creativecommons.org/licenses/by/3.0/" 
				+ "Changes: Converted to MP3");
		this.licenseText.getStyleClass().add("license");
		this.licenseText.setWrapText(true);
		
		this.sceneController = sceneController;
		this.contentOver.getStyleClass().add("content");
		scene = new Scene(rootOver,Configuration.xFields * 50,Configuration.yFields * 50);
		scene.getStylesheets().add(getClass().getResource("overScene.css").toExternalForm());
		scene.setUserData("OverScene");

		this.buildScene();
	}

	/**
	 * Funktion zum erstellen der Szene mit ihren Elementen
	 * 
	 */
	private void buildScene() {

		//Äußere VertikalBox zur Aufnahme aller weiteren Elemente
		VBox verboAeussereBox = new VBox();
		
		verboAeussereBox.getStyleClass().add("verboAeussereBox");			
		for(int i = 0 ; i < 4; i++) {
			overList[i] = new Label(this.labelText[i]);
			overList[i].getStyleClass().add("labelOver"+(i+1));
			verboAeussereBox.getChildren().add(overList[i]);
		}		
		verboAeussereBox.getChildren().add(this.licenseText);
		this.contentOver.getChildren().add(verboAeussereBox);
		this.rootOver.setTop(this.buildMenu());
		this.rootOver.setBottom(this.contentOver);
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
	 * Funktion zur Rueckgabe der Szene
	 * @return komplette Szene mit allen Elementen
	 */
	public Scene getScene() {
		return this.scene;
	}
}
