package views;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.*;
import controller.SceneController;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.*;
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
		private Image[] carLeftToRight  = new Image[2];
		private Image[] carRightToLeft  = new Image[2];
		private Image deadFrog  = new Image(getClass().getResource("../img/Frosch_GameOver.png").toExternalForm());
		
		private Label timeLabel = new Label();
		
		/* Liste der Subscriber Instanzen */
		private Queue<ImageView> pictureCont = new ConcurrentLinkedQueue<ImageView>();
		//Sammelliste für GUI Elemente
		//private ArrayList<ImageView> pictureCont = new ArrayList<ImageView>(); 
		
		//Zufallsobjekt
		Random rand = new Random();
        Canvas canvas = new Canvas(950,650);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		
       /********************************************* Szeneninitialisierung *********************************************/ 
        
		/**
		 * Konstruktor
		 */
		public GameScene(SceneController sceneController) {
			
			//neue Szene erstellen
			super(new StackPane(),Configuration.xFields * 50,Configuration.yFields * 50 + 30);
			this.setRoot(rootGame);
			this.sceneController = sceneController;
					
			//Bilderarrays füllen
			this.fillImageWood();
			this.fillImageCarToLeft();
			this.fillImageCarToRight();
			this.fillImageFrog();
			

			//Szene Formatierungs CSS  zuweisen
			this.getStylesheets().add(getClass().getResource("../gameScene.css").toExternalForm());
			
			//Szenenhintergrund hinzufügen
			this.rootGame.setTop(this.buildMenu());
			VBox contentBox = new VBox();
			contentBox.getStyleClass().add("content");
			contentBox.getChildren().add(canvas);
			this.rootGame.setBottom(contentBox);
			
			//Anmeldung Observer
			Observer.add("car", this);
			Observer.add("tree", this);
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
		
		private void fillImageCarToLeft() {
			
			for (int i = 0 ; i <= 1 ; i++) {
				this.carRightToLeft[i] = new Image(getClass().getResource("../img/Auto_0"+(i+2)+".png").toExternalForm());
			}
	
		}
		
		private void fillImageCarToRight() {
			
			for (int i = 0 ; i <= 1 ; i++) {
				this.carLeftToRight[i] = new Image(getClass().getResource("../img/Auto_0"+i+".png").toExternalForm());
			}
	
		}
		
		private void fillImageFrog() {
			
			this.frog[0] = new Image(getClass().getResource("../img/Frosch_Animation_hochRunter_Stand.png").toExternalForm());
			this.frog[1] = new Image(getClass().getResource("../img/Frosch_Animation_runterHoch_Stand.png").toExternalForm());
		
		}
	
   /********************************************** Hilfsfunktionen ***********************************************/		
		
		
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
		
		/**
		 * Hilfsfunktion zur Positionierung des GUI Objektes 
		 *
		 */
		
		private ImageView setPosition(ImageView imgObject, SubscriberDaten data) {
			
			ImageView help = imgObject;
			
			if ((data.name == "Tree") && ((data.xPosition < data.length) || (data.xPosition > (Configuration.xFields - data.length)))) {
				switch(data.xPosition) {
					case 1:	{ help.setImage(this.wood[0]);
							  break;
					}
					case 2: { help.setImage(this.wood[1]);
					  			break;
					  		}
					case 19: { help.setImage(this.wood[0]);
					  		   break;
					  		}
					case 18: { help.setImage(this.wood[1]);
		  					   break;
		  					 }
				}
			}
			
			//if (data.leftToRight){ 			
				imgObject.setX((data.xPosition*50)-49);
				imgObject.setY((data.yPosition*50)-49);
			//} else {
			//	imgObject.setX((help.getX()) - ((data.xPosition*50)-49));
			//	imgObject.setY((help.getY()) - ((data.yPosition*50)-49));
			//}
			System.out.println("Objektname: " + data.name + " ID: " + data.id + " XPosition: " + data.xPosition + " YPosition: " + data.yPosition+ " Typ: " + data.typ + " LeftToRight: " + data.leftToRight);
			return help;
		}
		
		/**
		 * Hilfsfunktion zum auslesen der Position des angetriggerten Objektes in der Liste
		 *
		 */
		
		/* private int getPosition(SubscriberDaten data) {
			
		int position = 0;
		
			for(ImageView help: this.pictureCont) {
				if (data.id == Integer.parseInt(help.getId())) {
					//position = this.pictureCont.indexOf(help);
				}
			}
		return position;
		
		} */
		
		/**
		 * Hilfsfunktion zum auslesen des angetriggerten Objektes
		 *
		 */
		
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
		 * Hilfsfunktion zum formatieren der Zeitausgabe
		 *
		 */
		
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

		  /********************************************** Arbeitsfuntionen  ***********************************************/		

		/**
		 * Funktion zum erstellen eines neuen Car Objektes
		 *
		 */
		
		private void createNewCarObject(SubscriberDaten data) {
	
			//Hilfsvaraiblen deklarienen
			ImageView help = this.getGUIObject(data);
			if (data.leftToRight) {
				help.setImage(this.carLeftToRight[rand.nextInt(2)]); 
			} else {
				help.setImage(this.carRightToLeft[rand.nextInt(2)]); 
			}
			help.setFitHeight(50);
			help.setId(data.id.toString());
			this.pictureCont.add(this.setPosition(help, data));
			this.updateElements();
			
		}
		
		/**
		 * Funktion zum erstellen eines neuen Tree Objektes
		 *
		 */
		private void createNewTreeObject(SubscriberDaten data) {
			
			//Hilfsvaraiblen deklarienen
			ImageView help = this.getGUIObject(data);
			
			Integer woodLength = 1;
			
			if (data.length != null) woodLength = data.length - 2;
			help.setImage(this.wood[woodLength]);
			help.setId(data.id.toString());
			this.pictureCont.add(this.setPosition(help, data));
			this.updateElements();
			
		}
		
		/**
		 * Funktion zum erstellen eines neuen Frosch Objektes
		 *
		 */
		private void createNewFrogObject(SubscriberDaten data) {
			
			//Hilfsvaraiblen deklarienen
			ImageView help = this.getGUIObject(data);
			
			Integer frogTyp = 1;	
			if (data.length != null) frogTyp = data.length - 1;
			help.setImage(this.frog[frogTyp]);
			help.setFitHeight(50);
			help.setFitWidth(50);
			help.setId(data.id.toString());
			this.pictureCont.add(this.setPosition(help, data));
			this.updateElements();
			
		}
		
		/**
		 * Funktion zum löschen eines GUI Objektes
		 *
		 */
		
		private void deleteGUIObject(SubscriberDaten data) {
			
			for(ImageView help: this.pictureCont) {
				if (data.id == Integer.parseInt(help.getId())) {
					//help.setImage(null); 
					//um multiple Listenzugriffe Zugriff beim löschen zu umgehen wird das entsprechende Element unsichtbar gemacht 
					this.pictureCont.remove(help);
				}
			}
			
			//Aktualisierung der GUI Elemente	
			this.updateElements();
		}
		
		/**
		 * Funktion zum Update des angetriggerten GUI Objektes
		 *
		 */
		private void updateGUIObject(SubscriberDaten data) {
			
				//Hilfsvaraiblen deklarienen
				ImageView help = this.getGUIObject(data);
				Boolean exist = this.checkImageExist(data);
			
			if (exist) {
				
				//int position = this.getPosition(data);
				
				this.pictureCont.remove(help);
				help = this.setPosition(help, data);
				this.pictureCont.add(help);
				//this.pictureCont.set(position, this.setPosition(help, data));
			}
			//Aktualisierung der GUI Elemente	
			this.updateElements();
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
		        this.graphicsContext.restore();
				this.graphicsContext.drawImage(help.getImage(), help.getX(), help.getY());
		        this.graphicsContext.save();
			}
		}
		
		/**
		 * Hilfsfunktion zum Update des Timerfeldes
		 *
		 */
		private void updateTimer(SubscriberDaten data) {
			
			this.timeLabel.setText("restliche Spielzeit: " + this.formatTime(data.time));
			
		}
			
		/************************************** Observerhandling ***************************************/
		
		
		/**
		 * Funktion zum verarbeiten der Observertrigger
		 *
		 */
		public void calling(String trigger, SubscriberDaten data) {
			System.out.println("######Objektname: " + data.name + " ID: " + data.id + " XPosition: " + data.xPosition + " YPosition: " + data.yPosition+ " Typ: " + data.typ + " LeftToRight: " + data.leftToRight);
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
				case "tree": {
					switch (data.typ) {
						case "new": {
										this.createNewTreeObject(data);
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

