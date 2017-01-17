package controller;

import application.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import views.*;


/**
 * Steuert die Anzeige und Umschaltung der notwendigen Szenen
 * 
 * @author Die UMeLs
 *
 */
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



	/**
	 * Konstruktor
	 *
	 *
	 */
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

	
	/** 
	 * Szene setzen auf aktuelle Buehne
	 *
	 * @param primaryStage / aktuelle Buehne 
	 * 
	 */
	public void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		this.primaryStage.setScene(this.getStartScene());
		this.primaryStage.show();
	}
	
	/** 
	 * gibt GameScene zurueck
	 *
	 * @return scene / GameScene
	 */
	public Scene getGameScene() {
		return this.gameScene.getScene();
	}
	
	/** 
	 * gibt HighScoreScene zurueck
	 *
	 * @return scene / HighScoreScene
	 */
	public Scene getScoreScene() {
		return this.scoreScene.getScene();
	}
	
	/** 
	 * gibt StartScene zurueck
	 *
	 * @return scene / StartScene
	 */
	public Scene getStartScene() {
		return this.startScene.getScene();
	}
	
	/** 
	 * gibt OverScene zurueck
	 *
	 * @return scene / OverScene
	 */
	public Scene getOverScene() {
		return this.overScene.getScene();
	}
	
	
	/** 
	 * fuehrt Szenenwechsel zu Startbildschirm durch
	 * 
	 */
	public void newGame(){
		Observer.trigger("resetGame", new SubscriberDaten());
		this.primaryStage.setScene(this.getStartScene());
	}
	
	/** 
	 * fuehrt Szenenwechsel zu HighScoreBildschirm durch
	 * 
	 */
	public void showHighscore(){
		Observer.trigger("readHigh", new SubscriberDaten());
		this.primaryStage.setScene(this.getScoreScene());
	}
	
	/** 
	 * fuehrt Szenenwechsel zu UeberBildschirm durch
	 * 
	 */
	public void showOver(){
		this.primaryStage.setScene(this.getOverScene());
	}
	
	
	/** 
	 * startet Spiel und setzt KeyListener fuer die Steuerung
	 * 
	 */
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
            	} else {
            		if(e.getCode() == KeyCode.ENTER) {
            			Observer.trigger("resetGame", new SubscriberDaten());
            			startGame();
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
	
	/** 
	 * zeigt GameScene bzw StartScene je nachdem ob Spiel laeuft oder nicht
	 * 
	 */
	public void showGameScene() {
		if (gameRunning) {
			this.primaryStage.setScene(this.getGameScene());
			this.primaryStage.show();
		} else {
			this.primaryStage.setScene(this.getStartScene());
			this.primaryStage.show();
		}
	}

	
	/** 
	 * setzt Trigger wenn Szene geschlossen wird
	 *
	 * @param actScene / aktuell aktive Szene
	 * 
	 */
	public void submitClose(String actScene){
		SubscriberDaten data = new SubscriberDaten();
		data.name = actScene;
		Observer.trigger("close", data);
	}
	
	/* (non-Javadoc)
	 * @see application.SubscriberInterface#calling(java.lang.String, application.SubscriberDaten)
	 */
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
