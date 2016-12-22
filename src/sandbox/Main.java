package sandbox;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	//default image
	String imageRessoure = "Frosch_Animation_hochRunter_Bewegung_01.png";	
	ImageView image = new ImageView(new Image(getClass().getResourceAsStream(imageRessoure)));
	
	
    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox();
        
        
        Button button = new Button("Click some arrow");
        Label label = new Label("Click some arrow");
        root.getChildren().add(button);
        root.getChildren().add(label);
        root.getChildren().add(image);
        
        Scene scene = new Scene(root, 200, 200);
        
        primaryStage.setScene(scene);
        primaryStage.show();

        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
        	
        	/**
        	 * Method changes displayed image
        	 * @param newImage
        	 */
        	public void changeImage(String newImage){
        		root.getChildren().remove(image);
        		image.setImage(null);
                imageRessoure = newImage;
                ImageView image = new ImageView(new Image(getClass().getResourceAsStream(newImage)));
                root.getChildren().add(image);
                primaryStage.show();
        	}
        	
        	
            @Override
            public void handle(KeyEvent event) {
            	if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("Enter Pressed");
                }
            	if (event.getCode() == KeyCode.UP) {
                    System.out.println("Up Pressed");
                    changeImage("Frosch_Animation_runterHoch_Bewegung_01.png");
                }
            	if (event.getCode() == KeyCode.DOWN) {
                    System.out.println("Down Pressed");
                    changeImage("Frosch_Animation_hochRunter_Bewegung_01.png");
                }
            	if (event.getCode() == KeyCode.LEFT) {
                    System.out.println("Left Pressed");
                    changeImage("Frosch_Animation_rechtsLinks_Bewegung_01.png");
                }
            	if (event.getCode() == KeyCode.RIGHT) {
                    System.out.println("Right Pressed");
                    changeImage("Frosch_Animation_linksRechts_Bewegung_01.png");
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}