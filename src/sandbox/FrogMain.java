package sandbox;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FrogMain extends Application
{

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Froggers");
        FrogViaKey moveFrog = new FrogViaKey();
        primaryStage.setScene(moveFrog);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}