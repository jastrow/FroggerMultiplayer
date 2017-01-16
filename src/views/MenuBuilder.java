package views;

import java.text.DecimalFormat;

import application.Configuration;
import application.Observer;
import application.SubscriberDaten;
import application.SubscriberInterface;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.HBox;

public class MenuBuilder implements SubscriberInterface {

	public static MenuBuilder menuBuilderInstance;
	HBox menuBox;
	Label timeLabel = new Label();
	
	public MenuBuilder() {}
	
	
	public static MenuBuilder getInstance() {
		if(menuBuilderInstance == null) {
			menuBuilderInstance = new MenuBuilder();
		}
		return menuBuilderInstance;
	}
	
	public static void setLabel(Label label) {
		MenuBuilder obj = MenuBuilder.getInstance();
		obj.setLabelInstance(label);
	}
	public void setLabelInstance(Label label) {
		this.timeLabel = label;
	}
	
	
	public static HBox buildMenu(Boolean timeAndScore) {
		MenuBuilder obj = MenuBuilder.getInstance();
		return obj.buildMenuInstance(timeAndScore);
	}
	public static HBox buildMenu() {
		MenuBuilder obj = MenuBuilder.getInstance();
		return obj.buildMenuInstance(false);
	}
	public HBox buildMenuInstance(Boolean timeAndScore) {

		this.menuBox = new HBox();
		MenuBar menuBar = new MenuBar();
		menuBox.setPrefHeight(20);

		// Menupunkt "Frogger"
		Menu froggerMenu = new Menu("Frogger");
		
			MenuItem neuMenuItem = new MenuItem("Neues Spiel");
			neuMenuItem.setOnAction(actionEvent -> {
				SubscriberDaten data = new SubscriberDaten();
				data.typ = "newGame";
				Observer.trigger("sceneController", data);
			});
			
			MenuItem exitMenuItem = new MenuItem("Exit");
			exitMenuItem.setOnAction(actionEvent -> Platform.exit());
			froggerMenu.getItems().addAll(neuMenuItem, new SeparatorMenuItem(), exitMenuItem);
		
		
		// Menupunkt "Info"
		Menu infoMenu = new Menu("Info");
		
			MenuItem highMenuItem = new MenuItem("Highscore");
			highMenuItem.setOnAction(actionEvent -> {
				SubscriberDaten data = new SubscriberDaten();
				data.typ = "showHighscore";
				Observer.trigger("sceneController", data);
			});
			infoMenu.getItems().addAll(highMenuItem);
	
			MenuItem overMenuItem = new MenuItem("Über..");
			overMenuItem.setOnAction(actionEvent -> {
				SubscriberDaten data = new SubscriberDaten();
				data.typ = "showOver";
				Observer.trigger("sceneController", data);
			});
			infoMenu.getItems().addAll(overMenuItem);
	
			MenuItem music = new MenuItem("Musik an/aus");
			music.setOnAction( actionEvent -> {
				SubscriberDaten subDat = new SubscriberDaten();
				subDat.typ = "toggle";
				Observer.trigger("sound", subDat);
			});
			infoMenu.getItems().add(music);
		
		menuBar.getMenus().addAll(froggerMenu, infoMenu);


		if(timeAndScore) {
			// Menüzeitlabel befüllen
			this.timeLabel.setText("restliche Spielzeit: " + this.formatTime(Configuration.timeEnd) + " Sek.");
			this.timeLabel.getStyleClass().add("timeLabel");
			menuBox.getChildren().add(this.timeLabel);
		}
		
		menuBox.getChildren().add(menuBar);
		return this.menuBox;

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
			formatedTime = format.format((timeToFormat - ((timeToFormat/60) * 60))); 
			return formatedTime; 
		} else {
			return "00";
		}
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		
	}
	
}
