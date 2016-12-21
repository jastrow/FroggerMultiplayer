package views;

import application.Configuration;
import model.Balken;
import model.Frog;
import model.Car;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;

	/**
	 * @author JackRyan
	 *
	 */
	public class GameScene {

		
		private Scene scene;
		
		// Hauptpanel
		private GridPane root = new GridPane();
		private  Group content = new Group();
		//Bilder
		private Image Brett = new Image(getClass().getResource("../img/Brett_01.png").toExternalForm());
		private Image totFrosch = new Image(getClass().getResource("../img/Frosch_GameOver.png").toExternalForm());


		/**
		 * Konstruktor
		 */
		public GameScene() {
			
			scene = new Scene(root,Configuration.xFields * 50,Configuration.yFields * 50);
			this.buildScene();
			//Szene Formatierungs CSS  zuweisen
			scene.getStylesheets().add(getClass().getResource("../gameScene.css").toExternalForm());
		}

		/**
		 * Funktion zum erstellen der Szene mit ihren Elementen
		 * @return komplette Szene mit allen Elementen
		 */
		private void buildScene() {

		}
		
		
		/**
		 * Hilfsfunktion zur Rückgabe der Szene
		 * @return komplette Szene mit allen Elementen
		 */
		public Scene getScene() {
			return this.scene;
		}

}



