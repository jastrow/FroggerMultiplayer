package views;

import java.util.ArrayList;
import java.util.Random;

import application.Configuration;
import controller.SceneController;
import javafx.scene.Group;
import javafx.scene.Scene;
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
		private BorderPane root = new BorderPane();
		private Group content = new Group();
		
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
			scene = new Scene(root,Configuration.xFields * 50,Configuration.yFields * 50 + 30);
			//Szene Formatierungs CSS  zuweisen
			scene.getStylesheets().add(getClass().getResource("../gameScene.css").toExternalForm());
			//Szenenhintergrund hinzufügen
			this.root.setTop(this.sceneController.getMenuBar());
			VBox contentBox = new VBox();
			contentBox.getChildren().add(content);
			contentBox.getStyleClass().add("content");
			this.root.setBottom(contentBox);
			
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

		/**
		 * Hilfsfunktion zum leeren und neu befüllen der Szene
		 *
		 */
		private void updateElements() {
			//Szene leeren 
			this.content.getChildren().clear();
			//Elemente in GUI setzen
			for(ImageView help: pictureCont){
				this.content.getChildren().add(help);
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
				this.content.getChildren().add(deadSign);
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
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)-49);
					this.pictureCont.set(position, help);
				} else {
					help.setImage(this.car[rand.nextInt(3)]);
					help.setFitHeight(50);
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)-49);
					help.setId(data.getName());
					this.pictureCont.add(help);
				}
				break;
			}
			case "frog": {
				
				if (exist) {
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)-49);
					this.pictureCont.set(position, help);
				} else {
					help.setImage(this.frog[rand.nextInt(1)]);
					help.setFitHeight(50);
					help.setFitWidth(50);
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)-49);
					help.setId(data.getName());
					this.pictureCont.add(help);
				}
				break;
			}
			case "bar": {
				
				if (exist) {
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)-49);
					this.pictureCont.set(position, help);
				} else {
					help.setImage(this.bar[rand.nextInt(2)]);
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)-49);
					help.setId(data.getName());
					this.pictureCont.add(help);
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



