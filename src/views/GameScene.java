package views;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import application.*;
import controller.SceneController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


	/**
	 * @author JackRyan
	 *
	 */
	public class GameScene extends Scene implements SubscriberInterface {
	
		// private Scene scene;
		private SceneController sceneController;
		
		// Hauptpanel
		private BorderPane rootGame = new BorderPane();
		private StackPane contentGame = new StackPane();
		
		//Bilder

		private Image[] wood = new Image[3]; 
		private Image[] frog = new Image[2];
		private Image[] car  = new Image[4];
		private Image deadFrog  = new Image(getClass().getResource("../img/Frosch_GameOver.png").toExternalForm());
		
		private Label timeLabel = new Label();
		
		//Sammelliste für GUI Elemente
		private ArrayList<ImageView> pictureCont = new ArrayList<ImageView>(); 
		
		//Zufallsobjekt
		Random rand = new Random();
        Canvas canvas = new Canvas(950,650);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		
		/**
		 * Konstruktor
		 */
		public GameScene(SceneController sceneController) {
			
			//neue Szene erstellen
			super(new StackPane(),Configuration.xFields * 50,Configuration.yFields * 50 + 30);
			this.setRoot(rootGame);
			this.sceneController = sceneController;
			
	        this.graphicsContext.save();
	        // this.graphicsContext.drawImage(image, posX, posY, 50, 50);
	        this.graphicsContext.restore();
			
			//Bilderarrays füllen
			this.fillImageWood();
			this.fillImageCar();
			this.fillImageFrog();
			

			//Szene Formatierungs CSS  zuweisen
			this.getStylesheets().add(getClass().getResource("../gameScene.css").toExternalForm());
			
			//Szenenhintergrund hinzufügen
			this.rootGame.setTop(this.buildMenu());
			VBox contentBox = new VBox();
			contentBox.getStyleClass().add("content");
			Button start = new Button("zeiges mir");
			start.setOnAction(actionEvent -> Observer.trigger("start",new SubscriberDaten()));
			contentBox.getChildren().add(start);
			contentBox.getChildren().add(canvas);
			this.rootGame.setBottom(contentBox);
			
			//Anmeldung Observer
			Observer.add("car", this);
			Observer.add("wood", this);
			Observer.add("frog", this);
			Observer.add("time", this);
			
			this.setUserData("GameScene");
		}
		
		private HBox buildMenu() {
			
			MenuBar menuBar = new MenuBar();
			HBox menuBox = new HBox();
			
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
			
			this.timeLabel.setText("restliche Spielzeit: " + this.formatTime(Configuration.timeEnd));
			this.timeLabel.getStyleClass().add("timeLabel");
			menuBox.getChildren().add(menuBar);
			menuBox.getChildren().add(this.timeLabel);
			return menuBox;

		}

		
		/**
		 * Hilfsfunktionen zum füllen der Bilderarrays
		 */
		private void fillImageWood() {
			
			for (int i = 0 ; i <= 2 ; i++) {
				this.wood[i] = new Image(getClass().getResource("../img/Brett_0"+i+".png").toExternalForm());
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
		private boolean checkImageExist(SubscriberDaten data) {
		
			for(ImageView help: pictureCont) {
				if (data.id == Integer.parseInt(help.getId())) return true;
			}
			
			return false;
		}
		
		private ImageView setPosition(ImageView imgObject, Integer xPosition, Integer yPosition) {
			ImageView help = imgObject;
			imgObject.setX(xPosition);
			imgObject.setY(yPosition);
			return help;
		}
		
		
		private int getPosition(SubscriberDaten data) {
			
		int position = 0;
		
			for(ImageView help: this.pictureCont) {
				if (data.id == Integer.parseInt(help.getId())) {
					position = this.pictureCont.indexOf(help);
				}
			}
		return position;
		
		}
		
		private ImageView getGUIObject(SubscriberDaten data) {
			ImageView getObject = new ImageView();
			
			for(ImageView help: this.pictureCont) {
				if (data.id == Integer.parseInt(help.getId())) {
					getObject = help;
				}
			}
			return getObject;
			
		}

		/**
		 * Hilfsfunktion zum leeren und neu befüllen der Szene
		 *
		 */
		private void updateElements() {
			//Szene leeren 
			//this.contentGame.getChildren().clear();
			this.graphicsContext.clearRect(0, 0, 950,600);
			//Elemente in GUI setzen
			for(ImageView help: pictureCont){
				this.graphicsContext.drawImage(help.getImage(), help.getX(), help.getY());
				//this.contentGame.getChildren().add(help);
			}
			//this.sceneController.updateStage();
		}
		
		private void createNewCarObject(SubscriberDaten data) {
	
			//Hilfsvaraiblen deklarienen
			ImageView help = this.getGUIObject(data);
			
			help.setImage(this.car[rand.nextInt(3)]);
			help.setFitHeight(50);
			help.setId(data.id.toString());
			this.pictureCont.add(this.setPosition(help, ((data.xPosition*50)-49), ((data.yPosition*50)-49)));
			this.updateElements();
			
		}
		
		private void createNewBarObject(SubscriberDaten data) {
			
			//Hilfsvaraiblen deklarienen
			ImageView help = this.getGUIObject(data);
			
			Integer woodLength = data.length;
			if (data.length == null) { 
				woodLength = 1;
			}
			woodLength = woodLength - 1;
			help.setImage(this.wood[woodLength]);
			//help.setImage(this.wood[woodLength]);
			help.setId(data.id.toString());
			this.pictureCont.add(this.setPosition(help, ((data.xPosition*50)-49), ((data.yPosition*50)-49)));
			this.updateElements();
			
		}
		
		private void createNewFrogObject(SubscriberDaten data) {
			
			//Hilfsvaraiblen deklarienen
			ImageView help = this.getGUIObject(data);
			
			Integer frogTyp = data.length - 1;
			if (frogTyp == null) frogTyp = 0;
			help.setImage(this.frog[rand.nextInt(1)]);
			help.setFitHeight(50);
			help.setFitWidth(50);
			help.setId(data.id.toString());
			this.pictureCont.add(this.setPosition(help, ((data.xPosition*50)-49), ((data.yPosition*50)-49)));
			this.updateElements();
			
		}
		
		private void updateGUIObject(SubscriberDaten data) {
			
				//Hilfsvaraiblen deklarienen
				ImageView help = this.getGUIObject(data);
				int position = this.getPosition(data);
				Boolean exist = this.checkImageExist(data);
			
			if (exist) {
				
				//prüfen auf GameOver und Anpassung der Szene
				if (data.xPosition == 0 && data.yPosition == 0) {

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
				
				this.pictureCont.set(position, this.setPosition(help, ((data.xPosition*50)-49), ((data.yPosition*50)-49)));
			}
			//Aktualisierung der GUI Elemente	
			this.updateElements();
		}
		
		private void deleteGUIObject(SubscriberDaten data) {
			
			for(ImageView help: this.pictureCont) {
				if (data.id == Integer.parseInt(help.getId())) {
					help.setImage(null); //um multiple Zugriff beim löschen zu umgehene
					//this.pictureCont.remove(help);
				}
			}
			
			//Aktualisierung der GUI Elemente	
			this.updateElements();
		}
		
		private void updateTimer(SubscriberDaten data) {
			
			this.timeLabel.setText("restliche Spielzeit: " + this.formatTime(data.time));
			
		}
		
		private String formatTime(Integer timeToFormat) {
			
			if (timeToFormat != null) {
				String formatedTime = "";
				DecimalFormat format = new DecimalFormat("00");
				timeToFormat = timeToFormat / 10 ;
				formatedTime = format.format(( timeToFormat / 60 )) + ":" + format.format((timeToFormat - ((timeToFormat/60) * 60))); 
				return formatedTime; 
			} else {
				return "00:00";
			}
		}
	
		public void calling(String trigger, SubscriberDaten data) {
			switch (trigger) {
				case "car": {
					switch (data.typ) {
						case "new": {
										this.createNewCarObject(data);
										break;
						}
						case "move": {
										this.updateGUIObject(data);
										break;
						}
						case "delete": {
										this.deleteGUIObject(data);
										break;
						}
					}
					break;
				}
				case "wood": {
					switch (data.typ) {
						case "new": {
										this.createNewBarObject(data);
										break;
						}
						case "move": {
										this.updateGUIObject(data);
										break;
						}
						case "delete": {
										this.deleteGUIObject(data);
										break;
						}
					}
					break;
				}
				case "frog": {
					switch (data.typ) {
						case "new": {
										this.createNewFrogObject(data);
										break;
						}
						case "move": {
										this.updateGUIObject(data);
										break;
						}
						case "delete": {
										this.deleteGUIObject(data);
										break;
						}
					}
					break;
				}
				case "time": {
						this.updateTimer(data);
						break;
				}
			}
		}
	
	/**
	 * Hilfsfunktion zur Rückgabe der Szene
	 * @return komplette Szene mit allen Elementen
	 */
	public Scene getScene() {
		return this;
	}
	
	

	

}

