package views;

import java.util.ArrayList;
import java.util.Random;

import application.Configuration;
import controller.SceneController;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.Data;

	/**
	 * @author JackRyan
	 *
	 */
	public class GameScene {
	
		private Scene scene;
		private SceneController sceneController;
		
		// Hauptpanel
		private BorderPane rootGame = new BorderPane();
		private Group contentGame = new Group();
		
		//Bilder
		private Image[] bar = new Image[3]; 
		private Image[] frog = new Image[2];
		private Image[] car  = new Image[4];
		private Image deadFrog  = new Image(getClass().getResource("../img/Frosch_GameOver.png").toExternalForm());
		//Sammelliste für GUI Elemente
		private ArrayList<ImageView> pictureCont = new ArrayList<ImageView>(); 
		//Zufallsobjekt
		Random rand = new Random();
		
		/**
		 * Konstruktor
		 */
		public GameScene(SceneController sceneController) {
			
			this.sceneController = sceneController;
			//Bilderarrays füllen
			fillImageBar();
			fillImageCar();
			fillImageFrog();
			//neue Szene erstellen
			scene = new Scene(rootGame,Configuration.xFields * 50,Configuration.yFields * 50 + 30);
			//Szene Formatierungs CSS  zuweisen
			scene.getStylesheets().add(getClass().getResource("../gameScene.css").toExternalForm());
			//Szenenhintergrund hinzufügen
			this.rootGame.setTop(this.buildMenu());
			VBox contentBox = new VBox();
			contentBox.getStyleClass().add("content");
			contentBox.getChildren().add(this.contentGame);
			this.rootGame.setBottom(contentBox);
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

		
		/**
		 * Hilfsfunktionen zum füllen der Bilderarrays
		 */
		private void fillImageBar() {
			
			for (int i = 0 ; i <= 2 ; i++) {
				this.bar[i] = new Image(getClass().getResource("../img/Brett_0"+i+".png").toExternalForm());
			}
	
		}
		
		private void fillImageCar() {
			
			for (int i = 0 ; i <= 3 ; i++) {
				this.car[i] = new Image(getClass().getResource("../img/Auto_0"+i+".png").toExternalForm());
			}
	
		}
		
		private void fillImageFrog() {
			
			this.frog[0] = new Image(getClass().getResource("../img/Frosch_Animation_hochRunter_Stand.png").toExternalForm());
			this.frog[1] = new Image(getClass().getResource("../img/Frosch_Animation_runterHoch_Stand.png").toExternalForm());
		
		}
		
		/**
		 * Hilfsfunktion zum prüfen ob Objekt bereits in Liste vorhanden
		 *
		 *@param:	data DatenObjekt
		 *@return:	boolean	
		 */
		private boolean checkImageExist(Data data) {
		
			for(ImageView help: pictureCont) {
				if (pictureCont.indexOf(help) == data.getID()) return true;
			}
			
			return false;
		}
		
		private ImageView setPosition(ImageView imgObject, Integer xPosition, Integer yPosition) {
			imgObject.setX(xPosition);
			imgObject.setY(yPosition);
			return imgObject;
		}

		/**
		 * Hilfsfunktion zum leeren und neu befüllen der Szene
		 *
		 */
		private void updateElements() {
			//Szene leeren 
			this.contentGame.getChildren().clear();
			//Elemente in GUI setzen
			for(ImageView help: pictureCont){
				this.contentGame.getChildren().add(help);
			}
		}
		
		
		/**
		 * Funktion zur aktualisierung der Szene
		 *
		 *@param:	data DatenObjekt
		 */
		public void updateScene(Data data){
			 
			//Hilfsvaraiblen deklarienen
			ImageView help = new ImageView();
			Integer position = 0;
			Boolean exist = false;
			
			//ImageView zur Bearbeitung aus Liste laden 
			if(this.checkImageExist(data)) {
						help = this.pictureCont.get(data.getID());
						position = data.getID();
						exist = true;
				}
							
			//prüfen auf GameOver und Anpassung der Szene
			if (data.getXPosition() == 0 && data.getYPosition() == 0) {

				help.setImage(this.deadFrog);
				this.pictureCont.set(position, help);
				this.updateElements();
				ImageView deadSign = new ImageView(this.deadFrog);
				deadSign.setFitHeight(300);
				deadSign.setFitWidth(500);
				deadSign.setX(225);
				deadSign.setY(150);
				this.contentGame.getChildren().add(deadSign);
				return;
			
			}
			
			//prüfen auf Bild löschen und Bild aus Liste entfernen
			if (data.getXPosition() == 0 || data.getYPosition() == 0) {
				
				help.setImage(null);
				this.pictureCont.set(position, help);
				
				return;
			}
			
			
			//prüfen auf Elementtyp und manipulieren oder hinzufügen des Elementes
			switch (data.getName()) {
			
			case "car": {
				
				if (exist) {
					//help.setX((data.getXPosition()*50)-49);
					//help.setY((data.getYPosition()*50)-49);
					//this.pictureCont.set(position, help);
					this.pictureCont.set(position, this.setPosition(help, ((data.getXPosition()*50)-49), ((data.getYPosition()*50)-49)));
				} else {
					help.setImage(this.car[rand.nextInt(3)]);
					help.setFitHeight(50);
					//help.setX((data.getXPosition()*50)-49);
					//help.setY((data.getYPosition()*50)-49);
					help.setId(data.getName());
					//this.pictureCont.add(help);
					this.pictureCont.add(this.setPosition(help, ((data.getXPosition()*50)-49), ((data.getYPosition()*50)-49)));
				}
				break;
			}
			case "frog": {
				
				if (exist) {
					//help.setX((data.getXPosition()*50)-49);
					//help.setY((data.getYPosition()*50)-49);
					//this.pictureCont.set(position, help);
					this.pictureCont.set(position, this.setPosition(help, ((data.getXPosition()*50)-49), ((data.getYPosition()*50)-49)));
				} else {
					help.setImage(this.frog[rand.nextInt(1)]);
					help.setFitHeight(50);
					help.setFitWidth(50);
					//help.setX((data.getXPosition()*50)-49);
					//help.setY((data.getYPosition()*50)-49);
					help.setId(data.getName());
					//this.pictureCont.add(help);
					this.pictureCont.add(this.setPosition(help, ((data.getXPosition()*50)-49), ((data.getYPosition()*50)-49)));

				}
				break;
			}
			case "bar": {
				
				if (exist) {
					//help.setX((data.getXPosition()*50)-49);
					//help.setY((data.getYPosition()*50)-49);
					//this.pictureCont.set(position, help);
					this.pictureCont.set(position, this.setPosition(help, ((data.getXPosition()*50)-49), ((data.getYPosition()*50)-49)));
				} else {
					Integer barLength = rand.nextInt(2);
					help.setImage(this.bar[barLength]);
					//help.setX((data.getXPosition()*50)-49);
					//help.setY((data.getYPosition()*50)-49);
					help.setId(data.getName());
					//this.pictureCont.add(help);
					//this.sceneController.setBar(barLength);
					this.pictureCont.add(this.setPosition(help, ((data.getXPosition()*50)-49), ((data.getYPosition()*50)-49)));
				}
				
				break;
			
			}
			
			}
			//Aktualisierung der GUI Elemente	
			this.updateElements();
		}
		
		/**
		 * Hilfsfunktion zur Rückgabe der Szene
		 * @return komplette Szene mit allen Elementen
		 */
		public Scene getScene() {
			return this.scene;
		}

}



