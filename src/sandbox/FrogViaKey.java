package sandbox;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class FrogViaKey extends Scene
{
    Canvas canvas;
    StackPane stackPane;
    int posX = 450;
    int posY = 550;
    static int canvasWith = 950;
    static int canvasHigth = 600;
    GraphicsContext graphicsContext;
    Timeline timeline;
    int horizontalDirection = 0;
    int verticalDirection = 0;
    Image image;


    public FrogViaKey()
    {
        super(new StackPane(),canvasWith,canvasHigth);
        stackPane = new StackPane();
        initStartscene();
        stackPane.getChildren().add(canvas);
        this.setRoot(stackPane);
        initEvents();
        initTimeline();
        timeline.play();
    }


    public void initStartscene()
    {
        canvas = new Canvas(canvasWith,canvasHigth);
        graphicsContext = canvas.getGraphicsContext2D();
        image = new Image(getClass().getResourceAsStream("Frosch_Animation_runterHoch_Bewegung_01.png"));
        graphicsContext.save();
        graphicsContext.drawImage(image, posX, posY, 50, 50);
        graphicsContext.restore();
    }


    private void initEvents()
    {
        setOnKeyPressed(new EventHandler <KeyEvent>()
        {
            @Override
            public void handle(KeyEvent e)
            {

                if(e.getCode() == KeyCode.LEFT)
                {
                    horizontalDirection = -10;
                }
                else if(e.getCode() == KeyCode.RIGHT)
                {
                    horizontalDirection = +10;
                }
                else if(e.getCode() == KeyCode.UP)
                {
                    verticalDirection = -10;
                }
                else if(e.getCode() == KeyCode.DOWN)
                {
                    verticalDirection = +10;
                }
            }
        });

        setOnKeyReleased(new EventHandler <KeyEvent>()
        {

            @Override
            public void handle(KeyEvent e)
            {
            	horizontalDirection = 0;
                verticalDirection = 0;
            }
        });
    }

    public void initTimeline()
    {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(40), new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent e)
            {
            	if(horizontalDirection != 0)
                {
            		//frog moves to right
                    if(horizontalDirection > 0)
                    {
                    	graphicsContext.clearRect(0, 0, canvasWith,canvasHigth);
                        posX = posX +10;
                        image = new Image(getClass().getResourceAsStream("Frosch_Animation_linksRechts_Bewegung_01.png"));
                        graphicsContext.drawImage(image, posX, posY, 50, 50);

                        //print pressed key and check position
                        if(posX>=950){}
                    }

                    //frog moves to left
                    if(horizontalDirection < 0)
                    {
                    	graphicsContext.clearRect(0, 0, canvasWith,canvasHigth);
                        posX = posX -10;
                        image = new Image(getClass().getResourceAsStream("Frosch_Animation_rechtsLinks_Bewegung_01.png"));
                        graphicsContext.drawImage(image, posX, posY, 50, 50);

                        //print pressed key and check position
                        if(posX<50){}
                    }
                }


            	if(verticalDirection != 0)
                {
            		//frog moves to bottom
                    if(verticalDirection > 0)
                    {
                    	graphicsContext.clearRect(0, 0, canvasWith,canvasHigth);
                        posY = posY +10;
                        image = new Image(getClass().getResourceAsStream("Frosch_Animation_hochRunter_Bewegung_01.png"));
                        graphicsContext.drawImage(image, posX, posY, 50, 50);

                        //print pressed key and check position

                        //game is over
                        if(posY>550){
                        	graphicsContext.clearRect(0, 0, canvasWith,canvasHigth);
                        	image = new Image(getClass().getResourceAsStream("Frosch_GameOver.png"));
                            graphicsContext.drawImage(image, posX, posY-100, 150, 100);
                        }
                    }

                    //frog moves to top
                    if(verticalDirection < 0)
                    {
                    	graphicsContext.clearRect(0, 0, canvasWith,canvasHigth);
                        posY = posY -10;
                        image = new Image(getClass().getResourceAsStream("Frosch_Animation_runterHoch_Bewegung_01.png"));
                        graphicsContext.drawImage(image, posX, posY, 50, 50);

                        //print pressed key and check position
                        if(posY<=10){}
                        else if(posY<0){}
                    }
                }
            }

            public void gameOver(){
            	image = new Image(getClass().getResourceAsStream("Frosch_GameOver.png"));
                graphicsContext.drawImage(image, posX, posY, 200, 100);
            }

        });
        timeline.getKeyFrames().add(keyFrame);
    }
}
