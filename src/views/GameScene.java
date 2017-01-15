package views;

import java.text.DecimalFormat;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.sun.prism.paint.Gradient;

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
import javafx.scene.text.Font;


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
	private Image[] carLeftToRight  = new Image[2];
	private Image[] carRightToLeft  = new Image[2];
	private Label timeLabel = new Label();
	private Integer time;
	
	/* Liste der Subscriber Instanzen */
	private Queue<ImageView> frogs = new ConcurrentLinkedQueue<ImageView>();
	private Queue<ImageView> pictureCont = new ConcurrentLinkedQueue<ImageView>();
	//Sammelliste für GUI Elemente
	//private ArrayList<ImageView> pictureCont = new ArrayList<ImageView>(); 
	
	//Zufallsobjekt
	Random rand = new Random();
	Boolean running = false;
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
		VBox contentBox = new VBox();

		//Anmeldung Observer
		Observer.add("car", this);
		Observer.add("tree", this);
		Observer.add("frog", this);
		Observer.add("time", this);
		Observer.add("win", this);
		Observer.add("stopGame", this);
		
		//Bilderarrays füllen
		this.fillImageWood();
		this.fillImageCarToLeft();
		this.fillImageCarToRight();
		

		//Szene Formatierungs CSS  zuweisen
		this.getStylesheets().add(getClass().getResource("gameScene.css").toExternalForm());
		
		//Szene leeren
		contentBox.getChildren().clear();
		this.pictureCont.clear();
		this.rootGame.getChildren().clear();
		this.contentGame.getChildren().clear();
		this.graphicsContext.clearRect(0, 0, 950,600);
		this.running = true;
		
		//Szenenhintergrund hinzufügen
		this.rootGame.setTop(this.buildMenu());

		contentBox.getStyleClass().add("content");
		contentBox.getChildren().add(canvas);
		this.contentGame.getChildren().add(contentBox);
		this.rootGame.setBottom(this.contentGame);
		
		
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
		neuMenuItem.setOnAction(actionEvent -> {
			if (!this.running) {		
				this.sceneController.newGame();
			}
		});

    
		froggerMenu.getItems().addAll(neuMenuItem,new SeparatorMenuItem(), exitMenuItem);
    
		Menu infoMenu = new Menu("Info");
		MenuItem highMenuItem = new MenuItem("Highscore");
    
		highMenuItem.setOnAction(actionEvent -> this.sceneController.showHighscore());
		MenuItem overMenuItem = new MenuItem("Über..");
	    
		overMenuItem.setOnAction(actionEvent -> this.sceneController.showOver());
    
		infoMenu.getItems().addAll(highMenuItem, overMenuItem);
    
		menuBar.getMenus().addAll(froggerMenu, infoMenu);
		
		this.timeLabel.setText("restliche Spielzeit: " + this.formatTime(Configuration.timeEnd) + " Sek.");
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
			this.wood[i] = new Image(getClass().getResource("Brett_0"+i+".png").toExternalForm());
		}

	}
	
	private void fillImageCarToLeft() {
		
		for (int i = 0 ; i <= 1 ; i++) {
			this.carRightToLeft[i] = new Image(getClass().getResource("Auto_0"+(i+2)+".png").toExternalForm());
		}

	}
	
	private void fillImageCarToRight() {
		
		for (int i = 0 ; i <= 1 ; i++) {
			this.carLeftToRight[i] = new Image(getClass().getResource("Auto_0"+i+".png").toExternalForm());
			}
	
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
	 * Hilfsfunktion zum prüfen ob Objekt bereits in Liste vorhanden
	 *
	 *@param:	data DatenObjekt
	 *@return:	boolean	
	 */
	private boolean checkFrogExist(SubscriberDaten data) {
	
		for(ImageView help: frogs) {
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
					
			imgObject.setX((data.xPosition*50)-49);
			imgObject.setY((data.yPosition*50)-49);

		return help;
	}
	
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
	 * Hilfsfunktion zum auslesen des angetriggerten Objektes
	 *
	 */
	
	private ImageView getFrogObject(SubscriberDaten data) {
		ImageView getObject = new ImageView();
				
		for(ImageView help: this.frogs) {
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
			timeToFormat = timeToFormat / 1000 ;
			formatedTime = format.format((timeToFormat - ((timeToFormat/60) * 60))); 
			return formatedTime; 
		} else {
			return "00";
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
		ImageView help = this.getFrogObject(data);
		
		help.setImage(new Image(getClass().getResource("frog_"+data.facing+".png").toExternalForm()));
		help.setId(data.id.toString());
		this.frogs.add(this.setPosition(help, data));
		this.updateElements();

		
	}
	
	/**
	 * Funktion zum Update eines Frosch Objektes
	 *
	 */
	private void updateFrogObject(SubscriberDaten data) {
		
		//Hilfsvaraiblen deklarienen
		ImageView help = this.getFrogObject(data);
		
		help.setImage(new Image(getClass().getResource("frog_"+data.facing+".png").toExternalForm()));
		this.frogs.remove(help);
		help = this.setPosition(help, data);
		this.frogs.add(help);
		this.updateElements();
		
	}
	
	/**
	 * Funktion zum löschen eines GUI Objektes
	 *
	 */
	
	private void deleteGUIObject(SubscriberDaten data) {
		
		for(ImageView help: this.pictureCont) {
			if (data.id == Integer.parseInt(help.getId())) {
				this.pictureCont.remove(help);
			}
		}
		
		//Aktualisierung der GUI Elemente	
		this.updateElements();
	}
	
	/**
	 * Funktion zum löschen eines GUI Objektes
	 *
	 */
	
	private void deleteFrogObject(SubscriberDaten data) {
		
		for(ImageView help: this.frogs) {
			if (data.id == Integer.parseInt(help.getId())) {
				this.frogs.remove(help);
			}
		}
		
		//Aktualisierung der GUI Elemente	
		this.updateElements();
	}
	
	/**
	 * Hilfsfunktion zum auslesen der Position des angetriggerten Objektes in der Liste
	 *
	 */
	
	 private void killFrog(SubscriberDaten data) {
		
		//Hilfsvaraiblen deklarienen
		ImageView help = this.getFrogObject(data);
		Boolean exist = this.checkFrogExist(data);
		Image dead = new Image(getClass().getResource("gameOver_big.png").toExternalForm());

		this.running = false;
		
		if (exist) {
			this.frogs.remove(help);
			this.updateElements();
			help.setImage(new Image(getClass().getResource("gameOver_small.png").toExternalForm()));
			this.frogs.add(help);
			this.updateElements();
			
			//Großes GameOverBild setzen
			this.graphicsContext.drawImage(dead, (Configuration.xFields/2*50)-200, (Configuration.yFields/2*50)-100);

			this.graphicsContext.setFont(new Font("Arial", 20));
			this.graphicsContext.fillText("Nochmal mit ENTER", 365, 583); 
		}
	} 
	 
	/**
	 * Hilfsfunktion zum auslesen der Position des angetriggerten Objektes in der Liste
	 *
	 */
	
	 private void winningFrog(SubscriberDaten data) {
		
		//Hilfsvaraiblen deklarienen
		
		Boolean exist = this.checkFrogExist(data);
		ImageView help = this.getFrogObject(data);
		Image winning = new Image(getClass().getResource("winning_big.png").toExternalForm());

		this.running = false;
		
		if (exist) {
			this.frogs.remove(help);
			this.updateElements();
			help.setImage(new Image(getClass().getResource("winning_small.png").toExternalForm()));
			this.frogs.add(help);
			this.updateElements();
			
			//Großes GameOverBild setzen
			this.graphicsContext.drawImage(winning, (Configuration.xFields/2*50)-200, (Configuration.yFields/2*50)-100);
			
			this.graphicsContext.setFont(new Font("Arial", 20));
			this.graphicsContext.fillText("Nochmal mit ENTER", 365, 583); 
		} 
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
			if (data.name == "Frog") help.setImage(new Image(getClass().getResource("frog_"+data.facing+".png").toExternalForm()));
			this.pictureCont.remove(help);
			help = this.setPosition(help, data);
			this.pictureCont.add(help);
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
		this.graphicsContext.clearRect(0, 0, 950,600);
		//Elemente in GUI setzen
		for(ImageView help: this.pictureCont){
	        this.graphicsContext.restore();
			this.graphicsContext.drawImage(help.getImage(), help.getX(), help.getY());
	        this.graphicsContext.save();
		}
		//Frogs in GUI setzen
		for(ImageView help: this.frogs){
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
		
		Integer actTime = 0;
		
		if (data.time != null) actTime = data.time;
		
		this.timeLabel.setText("restliche Spielzeit: " + this.formatTime(Configuration.timeEnd - actTime) + " Sek.");
		
	}
		
	/************************************** Observerhandling ***************************************/
	
	
	/**
	 * Funktion zum verarbeiten der Observertrigger
	 *
	 */
	public void calling(String trigger, SubscriberDaten data) {
		if (this.running) { 
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
										this.updateFrogObject(data);;
										break;
						}
						case "delete": {
										this.deleteFrogObject(data);
										break;
						}
						case "killed": {
							this.killFrog(data);
							break;
						}
						case "win": {
							SubscriberDaten timeData = new SubscriberDaten();
							timeData.time = this.time;
							Observer.trigger("entry", timeData);
							this.winningFrog(data);
							break;
						}
					}
					break;
				}
				case "time": {
						this.updateTimer(data);
						this.time = data.time;
						if (this.time.equals(Configuration.timeEnd - Configuration.timeSteps)) {
							this.running = false;
						}
						break;
				}
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

