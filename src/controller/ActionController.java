package controller;

import model.Data;

public class ActionController {
	
	private SceneController sceneController;
	
	public ActionController(SceneController sceneController) {
		this.sceneController = sceneController;
		this.sceneController.updateScene(new Data("car",0,1,5));
		this.sceneController.updateScene(new Data("car",1,1,4));
		this.sceneController.updateScene(new Data("bar",2,3,3));
		this.sceneController.updateScene(new Data("car",3,4,8));
		this.sceneController.updateScene(new Data("frog",4,2,1));
		this.sceneController.updateScene(new Data("car",3,1,8));
		
	}

}
