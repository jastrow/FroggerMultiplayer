package views;

import application.Configuration;
import model.Balken;
import model.Frog;
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
		private static GridPane root = new GridPane();

		// Array für die Spielfelder
		private ImageView[][] imgSpielfelder = new ImageView[Configuration.yFields][Configuration.xFields];
		// Array an Horizontalboxen
		private HBox[] horBox = new HBox[Configuration.yFields];
		//Bilder
		private Image Brett = new Image(getClass().getResource("img/Brett01.png").toExternalForm());
		private Image runterFrosch = new Image(getClass().getResource("img/Frosch_Animation_hochRunter_Stand.png").toExternalForm());
		private Image hochFrosch = new Image(getClass().getResource("img/Frosch_Animation_runterHoch_Stand.png").toExternalForm());
		private Image totFrosch = new Image(getClass().getResource("img/Frosch_GameOver.png").toExternalForm());


		/**
		 * Konstruktor
		 */
		public GameScene() {
			
			super(root,Configuration.xFields * 50,Configuration.yFields * 50);
			
			// new Image(url)
			Image hinterGrundBild = new Image(getClass().getResource("img/Hintergrund_Game.jpg").toExternalForm());
			// new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
			BackgroundSize backgroundSize = new BackgroundSize(Configuration.xFields * 50, Configuration.yFields * 50, false, false, false, false);
			// new BackgroundImage(image, repeatX, repeatY, position, size)
			BackgroundImage backgroundImage = new BackgroundImage(hinterGrundBild, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			// new Background(images...)
			Background background = new Background(backgroundImage);
			
			this.root.setAlignment(Pos.CENTER);
			this.root.setBackground(background);

			this.buildScene();
			//Szene Formatierungs CSS  zuweisen
			this.getStylesheets().add(getClass().getResource("gamescene.css").toExternalForm());
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
			
				for(int i = balkenObjekt.getPositionX() ; i < balkenObjekt.getPositionX() + balkenObjekt.getLaenge(); i++) {
					imgSpielfelder[balkenObjekt.getPositionY()][i].setImage(Brett);
				};
			
		}
		
		
		public void setFrog(Frog frosch) {
			
			if (frosch.getFroschIndex() == 1)
				imgSpielfelder[frosch.getPositionY()][frosch.getPositionX()].setImage(runterFrosch);
			else
				imgSpielfelder[frosch.getPositionY()][frosch.getPositionX()].setImage(hochFrosch);

		}
		
/*		public void bewegeBalken(Balken balkenObjekt, int richtungsindex) {
			
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
			
		} */
	
		
	
		public void baueTotBildschirm(Frog frosch) {
			
			imgSpielfelder[frosch.getPositionY()][frosch.getPositionX()].setImage(totFrosch);
			
			
			
			/*
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
			return vertBox; */
		}	
		
		/**
		 * Hilfsfunktion zur Rückgabe der Szene
		 * @return komplette Szene mit allen Elementen
		 */
		public Scene getScene() {
			return this;
		}

}



