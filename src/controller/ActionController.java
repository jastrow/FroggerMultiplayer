package controller;

import model.Data;

public class ActionController {
	
	private SceneController sceneController;
	
	public ActionController(SceneController sceneController) {
		this.sceneController = sceneController;
		this.sceneController.updateScene(new Data("car",0,1,3));
		this.sceneController.updateScene(new Data("frog",1,2,4));
		this.sceneController.updateScene(new Data("bar",2,3,5));
	}

}
