package views;

import java.util.ArrayList;
import java.util.Random;

import application.Configuration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import model.Data;

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
		private Image[] bar; //= new Image(getClass().getResource("../img/Brett_01.png").toExternalForm());
		private Image[] frog; //= new Image(getClass().getResource("../img/Frosch_GameOver.png").toExternalForm());
		private Image[] car;
		
		private ArrayList<ImageView> pictureCont = new ArrayList<ImageView>(); 
		
		Random rand = new Random();
		
		/**
		 * Konstruktor
		 */
		public GameScene() {
			
			fillImageBar();
			fillImageCar();
			fillImageFrog();
			scene = new Scene(root,Configuration.xFields * 50,Configuration.yFields * 50);
			//Szene Formatierungs CSS  zuweisen
			scene.getStylesheets().add(getClass().getResource("../gameScene.css").toExternalForm());
			
			for (int i = 0 ; i < 100; i++) {
				rand.nextInt(3);
			}
			
		}
		
		private void fillImageBar() {
			
			for (int i = 0 ; i <= 2 ; i++) {
				bar[i] = new Image(getClass().getResource("../img/Brett_0"+i+".png").toExternalForm());
			}
	
		}
		
		private void fillImageCar() {
			
			for (int i = 0 ; i <= 3 ; i++) {
				car[i] = new Image(getClass().getResource("../img/Auto_0"+i+".png").toExternalForm());
			}
	
		}
		
		private void fillImageFrog() {
			
			frog[0] = new Image(getClass().getResource("../img/Frosch_Animation_hochRunter_Stand.png").toExternalForm());
			frog[1] = new Image(getClass().getResource("../img/Frosch_Animation_runterHoch_Stand.png").toExternalForm());
		
		}
		
		private void deleteObject(Data data) {		
				pictureCont.remove(data.getID());
		}
		
		private ImageView setPosition (Data data, ImageView hilf) {
			hilf.setX(data.getXPosition()*50);
			hilf.setY(data.getYPosition()*50);
			return hilf;
		}
		
		public void updateScene(Data data){
			
			
			
			if (data.getXPosition() == 0 || data.getYPosition() == 0) {
				deleteObject(data);
				return;
			}
			
			switch (data.getName()) {
			
			case "car": {
				if (pictureCont.size() <= data.getID()) {
					ImageView help = pictureCont.get(data.getID());
					pictureCont.set(data.getID(), this.setPosition(data, help));	
				} else {
					ImageView help = new ImageView(car[rand.nextInt(3)]);
				}
				break;
			}
			case "frog": {
				break;
			}
			case "bar": {
				break;
			}
			
			}
		}
		
	
		
		/**
		 * Hilfsfunktion zur Rückgabe der Szene
		 * @return komplette Szene mit allen Elementen
		 */
		public Scene getScene() {
			return this.scene;
		}

}



