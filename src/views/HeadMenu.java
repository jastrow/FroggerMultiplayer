package views;

import controller.SceneController;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.VBox;

public class HeadMenu {
	
	private SceneController sceneController;
	private MenuBar menuBar = new MenuBar();
	private VBox resultBox = new VBox();
	
	public HeadMenu(SceneController sceneController){
		
		this.sceneController = sceneController;
		this.resultBox.getStylesheets().add(getClass().getResource("../menuBar.css").toExternalForm());
		this.resultBox.setPrefHeight(20);
		
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
    
		this.menuBar.getMenus().addAll(froggerMenu, infoMenu);
		this.resultBox.getChildren().add(menuBar);
	}
	
	public VBox getResultBox(){
		return this.resultBox;
	}
}
