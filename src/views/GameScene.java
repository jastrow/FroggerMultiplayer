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
		private Image[] bar = new Image[3]; //= new Image(getClass().getResource("../img/Brett_01.png").toExternalForm());
		private Image[] frog = new Image[2]; //= new Image(getClass().getResource("../img/Frosch_GameOver.png").toExternalForm());
		private Image[] car  = new Image[4];
		
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
		
		private void deleteObject(Integer index) {		
				pictureCont.remove(index);
		}
		
		private boolean checkImageExist(Data data) {
			for(ImageView help: pictureCont) {
				if (help.getId() == data.getID().toString()) return true;
			}
			return false;
		}
		
		private void updateElements() {
			content.getChildren().clear();
			for(ImageView help: pictureCont){
				content.getChildren().add(help);
			}
		}
		
		public void updateScene(Data data){
			 
			ImageView help = new ImageView();
			Integer position = 0;
			Boolean exist = false;
			
			if(this.checkImageExist(data)) {
			
				for(ImageView view: pictureCont) {
				if (view.getId() == data.getID().toString()) {
					help = view;
					position = pictureCont.indexOf(view);
					}	
				}
			exist = true;
			}
			
			if (data.getXPosition() == 0 || data.getYPosition() == 0) {
				deleteObject(position);
				return;
			}
			
			switch (data.getName()) {
			
			case "car": {
				if (exist) {
					help.setX(data.getXPosition()*50);
					help.setY(data.getYPosition()*50);
					pictureCont.set(position, help);
				} else {
					help.setImage(car[rand.nextInt(3)]);
					help.setX(data.getXPosition()*50);
					help.setY(data.getYPosition()*50);
					help.setId(data.getID().toString());
					pictureCont.add(help);
				}
				break;
			}
			case "frog": {
				if (exist) {
					help.setX(data.getXPosition()*50);
					help.setY(data.getYPosition()*50);
					pictureCont.set(position, help);
				} else {
					help.setImage(frog[rand.nextInt(1)]);
					help.setX(data.getXPosition()*50);
					help.setY(data.getYPosition()*50);
					help.setId(data.getID().toString());
					pictureCont.add(help);
					System.out.println(help);
				}
				break;
			}
			case "bar": {
				if (exist) {
					help.setX(data.getXPosition()*50);
					help.setY(data.getYPosition()*50);
					pictureCont.set(position, help);
				} else {
					help.setImage(bar[rand.nextInt(2)]);
					help.setX(data.getXPosition()*50);
					help.setY(data.getYPosition()*50);
					help.setId(data.getID().toString());
					pictureCont.add(help);
				}
				break;
			}
			
			}
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



