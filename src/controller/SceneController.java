package controller;

import application.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import views.*;


public class SceneController implements SubscriberInterface {
	
	public StartScene startScene;
	public OverScene overScene;
	public GameScene gameScene;
	public ScoreScene scoreScene;
	private Stage primaryStage;
	private Integer time;
	private boolean gameRunning = false;
	private boolean firstStart = true;
	private boolean keyPressed = false;

	
	
	
	public SceneController() {
		this.startScene = new StartScene(this);
		this.scoreScene = new ScoreScene(this);
		this.overScene = new OverScene(this);
		this.primaryStage = new Stage();
		Observer.add("time", this);
		Observer.add("close", this);
		Observer.add("end", this);
		Observer.add("stopGame", this);
	}

	
	public void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setScene(this.getStartScene());
		this.primaryStage.show();
	}
	
	public Scene getGameScene() {
		return this.gameScene.getScene();
	}
	
	public Scene getScoreScene() {
		return this.scoreScene.getScene();
	}
	
	public Scene getStartScene() {
		return this.startScene.getScene();
	}
	
	public Scene getOverScene() {
		return this.overScene.getScene();
	}
	
	
	public void newGame(){
		Observer.trigger("resetGame", new SubscriberDaten());
		this.primaryStage.setScene(this.getStartScene());
	}
	
	public void showHighscore(){
		Observer.trigger("readHigh", new SubscriberDaten());
		this.primaryStage.setScene(this.getScoreScene());
	}
	
	public void showOver(){
		this.primaryStage.setScene(this.getOverScene());
	}
	
	public void searchPlayer(){

	}
	
	public void startGame(){
		if(this.gameScene != null) {
			Observer.removeMe(this.gameScene);
		}
		this.gameScene = new GameScene(this);
		this.gameScene.setOnKeyPressed(new EventHandler <KeyEvent>()
        {
            @Override
            public void handle(KeyEvent e)
            {
            	SubscriberDaten data = new SubscriberDaten();
            		
            	if(gameRunning) {
	                if( (e.getCode() == KeyCode.LEFT  || e.getCode() == KeyCode.A) && !keyPressed)
	                {
	                	data.typ = "left";
	                    Observer.trigger("key", data);
	                    keyPressed = true;
	                }
	                else if( (e.getCode() == KeyCode.RIGHT  || e.getCode() == KeyCode.D) && !keyPressed)
	                {
	                	data.typ = "right";
	                    Observer.trigger("key", data);
	                    keyPressed = true;
	                }
	                else if( (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) && !keyPressed)
	                {
	                	data.typ = "up";
	                    Observer.trigger("key", data);
	                    keyPressed = true;
	                }
	                else if( (e.getCode() == KeyCode.DOWN  || e.getCode() == KeyCode.S) && !keyPressed)
	                {
	                	data.typ = "down";
	                    Observer.trigger("key", data);
	                    keyPressed = true;
	                }
            	}
            }
        });
		this.gameScene.setOnKeyReleased(new EventHandler <KeyEvent>()
        {
            @Override
            public void handle(KeyEvent e)
            {            		
            	if(keyPressed) {
	                if( (e.getCode() == KeyCode.LEFT  || e.getCode() == KeyCode.A) ||
	                	(e.getCode() == KeyCode.RIGHT  || e.getCode() == KeyCode.D)  ||
	                	(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) ||
	                	(e.getCode() == KeyCode.DOWN  || e.getCode() == KeyCode.S)
	                ) {
	                    keyPressed = false;
	                }
                }
            }
        });
		this.firstStart = false;
		this.primaryStage.setScene(this.getGameScene());

		this.gameRunning = true;
		Observer.trigger("start", new SubscriberDaten());
		
	}
	
	public void showGameScene() {
		if (gameRunning) {
			this.primaryStage.setScene(this.getGameScene());
			this.primaryStage.show();
		} else {
			this.primaryStage.setScene(this.getStartScene());
			this.primaryStage.show();
		}
	}

	public void submitClose(String actScene){
		SubscriberDaten data = new SubscriberDaten();
		data.name = actScene;
		Observer.trigger("close", data);
	}
	
	public void calling(String trigger, SubscriberDaten data) {
		switch (trigger) {
			case "time": { 
				this.time = data.time;
				break;
			}
			case "close": {
				
				if(data.name == "ScoreScene" || data.name == "OverScene") {
					this.newGame();
				} else {
					Platform.exit();
				}
				
			}
			case "end": {
				this.gameRunning = false;
				break;
			}
			case "stopGame":{
				this.gameRunning = false;
				break;
			}

		}
	}
}
