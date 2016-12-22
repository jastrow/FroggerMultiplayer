package controller;

import model.Data;

public class ActionController {
	
	private SceneController sceneController;
	
	public ActionController(SceneController sceneController) {
		this.sceneController = sceneController;
		this.sceneController.updateScene(new Data("car",0,1,2));
		this.sceneController.updateScene(new Data("car",1,1,3));
		this.sceneController.updateScene(new Data("bar",2,3,6));
		this.sceneController.updateScene(new Data("car",3,4,10));
		this.sceneController.updateScene(new Data("frog",4,1,1));
		this.sceneController.updateScene(new Data("bar",5,1,5));
		this.sceneController.updateScene(new Data("bar",6,3,7));
		this.sceneController.updateScene(new Data("bar",7,5,8));
		
	}

}
