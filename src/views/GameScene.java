package application.views;

import application.Configuration;
import application.model.Frog;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;

	/**
	 * @author JackRyan
	 *
	 */
	public class GameScene extends Scene {

		// Hauptpanel
		private static GridPane wurzel = new GridPane();

		// Array für die Spielfelder
		private ImageView[][] imgSpielfelder = new ImageView[Configuration.yFields][Configuration.xFields];
		// Array an Horizontalboxen
		private HBox[] horBox = new HBox[Configuration.yFields];

		/**
		 * Konstruktor
		 */
		public GameScene() {
			
			super(wurzel,Configuration.xFields * 50,Configuration.yFields * 50);
			
			// new Image(url)
			Image hinterGrundBild = new Image(getClass().getResource("img/Hintergrund_Game.jpg").toExternalForm());
			// new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
			BackgroundSize backgroundSize = new BackgroundSize(Configuration.xFields * 50, Configuration.yFields * 50, false, false, false, false);
			// new BackgroundImage(image, repeatX, repeatY, position, size)
			BackgroundImage backgroundImage = new BackgroundImage(hinterGrundBild, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			// new Background(images...)
			Background background = new Background(backgroundImage);
			
			this.wurzel.setAlignment(Pos.CENTER);
			this.wurzel.setBackground(background);
			// ImageView hintergrund = new ImageView(getClass().getResource("Hintergrund_Game.jpg").toExternalForm());
			// this.wurzel.getChildren().add();
			this.erstelleScene();
			//Szene Formatierungs CSS  zuweisen
			//this.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		}

		/**
		 * Funktion zum erstellen der Szene mit ihren Elementen
		 * @return komplette Szene mit allen Elementen
		 */
		private void erstelleScene() {

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

			this.wurzel.getChildren().add(verboAeussereBox);	
		}
		
		
	/*	public void setzeBalken(Balken balkenObjekt) {
			
				for(int i = balkenObjekt.getPositionX() ; i < balkenObjekt.getPositionX() + balkenObjekt.getLaenge(); i++) {
					imgSpielfelder[balkenObjekt.getPositionY()][i].setImage(null);
				};
			
		}
		
		
		public void setzeFrosch(Frog frosch) {
			
			if (frosch.getFroschIndex() == 1)
				imgSpielfelder[frosch.getPositionY()][frosch.getPositionX()].setImage(null);
			else
				imgSpielfelder[frosch.getPositionY()][frosch.getPositionX()].setImage(null);

		}
		
		public void bewegeBalken(Balken balkenObjekt, int richtungsindex) {
			
			if (((balkenObjekt.getPositionX() + 1) <= Configuration.xFields) && ((balkenObjekt.getPositionX() -1) >= 0 )) {
			
				if (richtungsindex == 1) {
				imgSpielfelder[balkenObjekt.getPositionY()][balkenObjekt.getPositionX()].setImage(null);
				imgSpielfelder[balkenObjekt.getPositionY()][balkenObjekt.getPositionX() + balkenObjekt.getLaenge()].setImage(null);
				balkenObjekt.setPositionX(balkenObjekt.getPositionX() + 1);
			} else {
				imgSpielfelder[balkenObjekt.getPositionY()][balkenObjekt.getPositionX()-1].setImage(null);
				imgSpielfelder[balkenObjekt.getPositionY()][balkenObjekt.getPositionX()-1 + balkenObjekt.getLaenge()].setImage(null);
				balkenObjekt.setPositionX(balkenObjekt.getPositionX() -1);
			}
			}
					
		} 
		
	public void bewegeFrosch(Frog frosch, int bewegungY, int bewegungX) {
			
					if (frosch.getFeldindex() == 1)
						imgSpielfelder[frosch.getPositionY()][frosch.getPositionX()].setImage(null);
					else
						imgSpielfelder[frosch.getPositionY()][frosch.getPositionX()].setImage(null);
						
					if (imgSpielfelder[frosch.getPositionY() + bewegungY][frosch.getPositionX() + bewegungX].getImage() == null)
						frosch.setFeldindex(1);
					else
						frosch.setFeldindex(2);
			
					frosch.setPositionY(frosch.getPositionY() + bewegungY);
					frosch.setPositionX(frosch.getPositionX() + bewegungX);
			
					setzeFrosch(frosch);
			
		} 
	
		
	
		private VBox baueTotBildschirm(Frog frosch){
			VBox vertBox = new VBox();  
			ImageView ende = new ImageView(getClass().getResource("ende.jpg").toExternalForm());
			ende.setFitHeight((Configuration.yFields-30)/2);
			ende.setFitWidth(Configuration.xFields);
			vertBox.getChildren().add(ende);
			if (frosch.getFroschIndex() == 1) {
				ImageView spieler = new ImageView(getClass().getResource("gestorbenSpieler1.jpg").toExternalForm());
				spieler.setFitHeight((Configuration.yFields-30)/2);
				spieler.setFitWidth(Configuration.xFields);
				vertBox.getChildren().add(spieler);
			} else {
				ImageView spieler = new ImageView(getClass().getResource("gestorbenSpieler2.jpg").toExternalForm());
				spieler.setFitHeight((Configuration.yFields-30)/2);
				spieler.setFitWidth(Configuration.xFields);
				vertBox.getChildren().add(spieler);				
			}
			return vertBox;
		}

		private VBox baueSiegBildschirm(Frog frosch){
			VBox vertBox = new VBox();  
			ImageView ende = new ImageView(getClass().getResource("ende.jpg").toExternalForm());
			ende.setFitHeight((Configuration.yFields-30)/2);
			ende.setFitWidth(Configuration.xFields);
			vertBox.getChildren().add(ende);
			if (frosch.getFroschIndex() == 1) {
				ImageView spieler = new ImageView(getClass().getResource("gewonnenSpieler1.jpg").toExternalForm());
				spieler.setFitHeight((Configuration.yFields-30)/2);
				spieler.setFitWidth(Configuration.xFields);
				vertBox.getChildren().add(spieler);
			} else {
				ImageView spieler = new ImageView(getClass().getResource("gewonnenSpieler2.jpg").toExternalForm());
				spieler.setFitHeight((Configuration.yFields-30)/2);
				spieler.setFitWidth(Configuration.xFields);
				vertBox.getChildren().add(spieler);				
			}
			return vertBox;
		}	*/
		
		/**
		 * Hilfsfunktion zur Rückgabe der Szene
		 * @return komplette Szene mit allen Elementen
		 */
		public Scene getScene() {
			return this;
		}

}



