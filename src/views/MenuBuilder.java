package views;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;

public class MenuBuilder implements SubscriberInterface {

	public static MenuBuilder menuBuilderInstance;
	HBox menuBox;
	
	public MenuBuilder() {
		this.buildMenuInstance();
	}
	
	
	public static MenuBuilder getInstance() {
		if(menuBuilderInstance == null) {
			menuBuilderInstance = new MenuBuilder();
		}
		return menuBuilderInstance;
	}
	
	public static HBox buildMenu() {
		MenuBuilder obj = MenuBuilder.getInstance();
		return obj.buildMenuInstance();
	}
	public HBox buildMenuInstance() {
		this.menuBox = new HBox();
		MenuBar menuBar = new MenuBar();
		menuBox.setPrefHeight(20);

		Menu froggerMenu = new Menu("Frogger");
		MenuItem neuMenuItem = new MenuItem("Neues Spiel");
		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(actionEvent -> Platform.exit());
//
//		
//		neuMenuItem.setOnAction(actionEvent -> {
//			if (!this.running) {		
//				this.sceneController.newGame();
//			}
//		});
//		froggerMenu.getItems().addAll(neuMenuItem,new SeparatorMenuItem(), exitMenuItem);
//		Menu infoMenu = new Menu("Info");
//		MenuItem highMenuItem = new MenuItem("Highscore");
//		//OnKlickEvents für MenüPunkt Info
//		highMenuItem.setOnAction(actionEvent -> this.sceneController.showHighscore());
//		MenuItem overMenuItem = new MenuItem("Über..");
//		overMenuItem.setOnAction(actionEvent -> this.sceneController.showOver());
//		infoMenu.getItems().addAll(highMenuItem, overMenuItem);
//
//		MenuItem music = new MenuItem("Musik an/aus");
//		SubscriberDaten subDat = new SubscriberDaten();
//		subDat.typ = "toggle";
//		music.setOnAction( actionEvent -> Observer.trigger("sound", subDat) );
//		infoMenu.getItems().add(music);
//		
//		menuBar.getMenus().addAll(froggerMenu, infoMenu);
//		//Menüzeitlabel befüllen
//		this.timeLabel.setText("restliche Spielzeit: " + this.formatTime(Configuration.timeEnd) + " Sek.");
//		this.timeLabel.getStyleClass().add("timeLabel");
		menuBox.getChildren().add(menuBar);
//		menuBox.getChildren().add(this.timeLabel);

		return this.menuBox;
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		
	}
}
