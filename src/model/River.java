package model;

import java.util.ArrayList;
import java.util.List;

import application.*;

public class River implements SubscriberInterface {

	Integer positionY;
	Boolean leftToRight = false;
	public List<Tree> trees = new ArrayList<Tree>();
	
	//public River(Integer[] riverlines) {
	public River(Integer position) {
		// TODO Auto-generated constructor stub
		this.positionY = position;
		if((position % 2) == 0) {
			this.leftToRight = true;
		}
		Observer.add("time", this);
		Observer.add("tree", this);
	}

	@Override
	public void calling(String trigger, SubscriberDaten daten) {
		if(trigger == "time") {
			this.checkForTrees();
		} else if(trigger == "tree") {
			if(daten.typ == "delete") {
				this.deleteTree(daten.id);
			}
		}
	}

	public void checkForTrees() {
		if(trees.isEmpty()) {
			Integer length = (int)Math.random() * 3 + 2;
			Integer positionX = Configuration.xFields;
			if(this.leftToRight) {
				positionX = ((length-1)*-1);
			}
			Tree baum = new Tree(IdCounter.getId(), positionX, this.positionY, length, this.leftToRight);
			this.trees.add(baum);
		} else {
			
		}
	}
	
	public void deleteTree(Integer id) {
		for(Tree tree: trees) {
			if(id == tree.getId()) {
				trees.remove(tree);
				break;
			}
		}
	}
	

}
