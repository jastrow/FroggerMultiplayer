package views;

import application.Configuration;
import model.Balken;
import model.Frog;
import model.Car;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;

	/**
	 * @author JackRyan
	 *
	 */
	public class GameScene extends Scene {

		// Hauptpanel
		private static GridPane root = new GridPane();

		// Array für die Spielfelder
		private ImageView[][] imgSpielfelder = new ImageView[Configuration.yFields][Configuration.xFields];
		// Array an Horizontalboxen
		private HBox[] horBox = new HBox[Configuration.yFields];
		//Bilder
		private Image Brett = new Image(getClass().getResource("img/Brett01.png").toExternalForm());
		private Image totFrosch = new Image(getClass().getResource("img/Frosch_GameOver.png").toExternalForm());


		/**
		 * Konstruktor
		 */
		public GameScene() {
			
			super(root,Configuration.xFields * 50,Configuration.yFields * 50);
			this.buildScene();
			//Szene Formatierungs CSS  zuweisen
			this.getStylesheets().add(getClass().getResource("gameScene.css").toExternalForm());
		}

		/**
		 * Funktion zum erstellen der Szene mit ihren Elementen
		 * @return komplette Szene mit allen Elementen
		 */
		private void buildScene() {

			//Äußere VertikalBox zur Aufnahme aller weiteren Elemente
			VBox verboAeussereBox = new VBox();
			
			for(int i = 0; i < horBox.length;i++ ) {
				horBox[i] = new HBox(); 
				verboAeussereBox.getChildren().add(horBox[i]);
				
				for(int j = 0 ; j < Configuration.xFields; j++) {
					imgSpielfelder[i][j] = new ImageView();
					imgSpielfelder[i][j].setFitHeight(50);
					imgSpielfelder[i][j].setFitWidth(50);
					horBox[i].getChildren().add(imgSpielfelder[i][j]); 
				};
			};

			this.root.getChildren().add(verboAeussereBox);	
		}
		
		
		public void setBalken(Balken balkenObjekt) {
			for(int i = balkenObjekt.getOldPositionX() ; i < balkenObjekt.getOldPositionX() + balkenObjekt.getLaenge(); i++) {
				imgSpielfelder[balkenObjekt.getOldPositionY()][i].setImage(null);
				};
			
			for(int i = balkenObjekt.getPositionX() ; i < balkenObjekt.getPositionX() + balkenObjekt.getLaenge(); i++) {
				imgSpielfelder[balkenObjekt.getPositionY()][i].setImage(Brett);
				};
			
		}
		
		
		public void setFrog(Frog froschObjekt) {
				imgSpielfelder[froschObjekt.getOldPositionX()][froschObjekt.getOldPositionY()].setImage(null);
				imgSpielfelder[froschObjekt.getPositionX()][froschObjekt.getPositionY()].setImage(froschObjekt.getImage());

		}
		
		public void setCar(Car autoObjekt) {
			imgSpielfelder[autoObjekt.getOldPositionX()][autoObjekt.getOldPositionY()].setImage(null);
			imgSpielfelder[autoObjekt.getPositionX()][autoObjekt.getPositionY()].setImage(autoObjekt.getImage());
			
			
		}
		
		public void baueTotBildschirm(Frog froschObjekt) {
			
			imgSpielfelder[froschObjekt.getPositionY()][froschObjekt.getPositionX()].setImage(totFrosch);
			
		}	
		
		/**
		 * Hilfsfunktion zur Rückgabe der Szene
		 * @return komplette Szene mit allen Elementen
		 */
		public Scene getScene() {
			return this;
		}

}



