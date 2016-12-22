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
		private Group root = new Group();
		//private Group content = new Group();
		
		//Bilder
		private Image[] bar = new Image[3]; //= new Image(getClass().getResource("../img/Brett_01.png").toExternalForm());
		private Image[] frog = new Image[2]; //= new Image(getClass().getResource("../img/Frosch_GameOver.png").toExternalForm());
		private Image[] car  = new Image[4];
		private ImageView background = new ImageView(new Image(getClass().getResource("../img/Hintergrund_Game.jpg").toExternalForm()));
		
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
			this.root.getChildren().add(background);
			
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
				System.out.println(index);
		
		}
		
		private boolean checkImageExist(Data data) {
		
			for(ImageView help: pictureCont) {
				if (pictureCont.indexOf(help) == data.getID()) return true;
			}
			
			return false;
		}
		
		private void updateElements() {
			
			System.out.println(root.getChildren().size());
			this.root.getChildren().clear();
			System.out.println(root.getChildren().size());
			this.root.getChildren().add(background);
			for(ImageView help: pictureCont){
				System.out.println(help.getX() + " - " + help.getY() + " - " + help.getId());
				this.root.getChildren().add(help);
			}
		}
		
		public void updateScene(Data data){
			 
			ImageView help = new ImageView();
			Integer position = 0;
			Boolean exist = false;
			
			if(this.checkImageExist(data)) {
			
				for(ImageView view: pictureCont) {
					if (this.checkImageExist(data)) {
						help = view;
						position = pictureCont.indexOf(view);
					}	
				}
				
			exist = true;
			
			}
			
			System.out.println(exist);
			
			if (data.getXPosition() == 0 || data.getYPosition() == 0) {
			
				deleteObject(position);
				
				return;
			}
			
			switch (data.getName()) {
			
			case "car": {
				
				if (exist) {
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)+30);
					pictureCont.set(position, help);
				} else {
					help.setImage(car[rand.nextInt(3)]);
					help.setFitHeight(50);
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)+30);
					help.setId(data.getName());
					pictureCont.add(help);
				}
				break;
			}
			case "frog": {
				
				if (exist) {
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)+30);
					pictureCont.set(position, help);
				} else {
					help.setImage(frog[rand.nextInt(1)]);
					help.setFitHeight(50);
					help.setFitWidth(50);
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)+30);
					help.setId(data.getName());
					pictureCont.add(help);
				}
				break;
			}
			case "bar": {
				
				if (exist) {
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)+30);
					pictureCont.set(position, help);
				} else {
					help.setImage(bar[rand.nextInt(2)]);
					help.setX((data.getXPosition()*50)-49);
					help.setY((data.getYPosition()*50)+30);
					help.setId(data.getName());
					pictureCont.add(help);
				}
				
				break;
			
			}
			
			}
			exist = false;
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



