package sandbox;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox pane = new HBox();
        //Image image = new Image("Frosch_Animation_hochRunter_Bewegung_01.png");
        Button button = new Button("Click some arrow");
        Label label = new Label("Click some arrow");
        pane.getChildren().add(button);
        pane.getChildren().add(label);
        
        Scene scene = new Scene(pane, 200, 200);
        
        primaryStage.setScene(scene);
        primaryStage.show();

        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
            	if (event.getCode() == KeyCode.ENTER) {
                    System.out.println("Enter Pressed");
                }
            	if (event.getCode() == KeyCode.UP) {
                    System.out.println("Up Pressed");
                }
            	if (event.getCode() == KeyCode.DOWN) {
                    System.out.println("Down Pressed");
                }
            	if (event.getCode() == KeyCode.LEFT) {
                    System.out.println("Left Pressed");
                }
            	if (event.getCode() == KeyCode.RIGHT) {
                    System.out.println("Right Pressed");
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}