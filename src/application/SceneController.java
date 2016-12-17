package application;

public class SceneController {
	public GameLogic game;
	
	public StartScene startScene;
	public GameScene gameScene;
	public ScoreScene scoreScene;
	
	public void setGame(GameLogic game) {
		this.game = game;
	}
}
